package unpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import unpa.entity.Alumnos.*;
import unpa.entity.Carrera;
import unpa.entity.materias.MateriaCursadaDTO;
import unpa.entity.materias.MateriaResultadoDTO;
import unpa.entity.utils.FechaUtils;
import unpa.repository.MateriaRepository;
import unpa.repository.alumnos.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private DatosPersonalesRepository datosPersonalesRepository;
    @Autowired
    private FichaRepository fichaRepository;
    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private ReinscripcionRepository reinscripcionRepository;

    @Value("${valores.promedioMinimo:6}")
    private float promedioMinimo; // si usas una constante, puedes usar 6 directamente



    public AlumnoConstanciaDTO obtenerDatosConstancia(String idAlumno) {
        AlumnoMatriculado alumno = alumnoRepository.findById(idAlumno)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));


        DatosPersonales datos = alumno.getDatosPersonales();


        FichaId fichaId = new FichaId(datos.getId().getIdDat(), datos.getId().getAnoDat());
        Ficha ficha = fichaRepository.findById(fichaId).get();

       // Estado estadoNac = estadoRepository.findById(datos.getIdEdonacFk()).orElse(null);
        Carrera carrera = carreraRepository.findById(alumno.getCarrera()).orElse(null);

        String nombreCompleto = String.join(" ",
                datos.getNombre(), datos.getApPaterno(), datos.getApMaterno());

        Date fechaNac = datos.getFechaNacimiento() != null ? datos.getFechaNacimiento() : FechaUtils.getFechaActual();
        LocalDate nacimiento = fechaNac.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        int edad= Period.between(nacimiento, LocalDate.now()).getYears();

        return AlumnoConstanciaDTO.builder()
                .nombre(datos.getNombre())
                .apellidoPaterno(datos.getApPaterno())
                .apellidoMaterno(datos.getApMaterno())
                .sexo(datos.getSexo())
                .lugarNacimiento(datos.getLugarNacimiento())
                .estadoNacimiento(datos.getEdoNacimiento())
                .fechaNacimiento(fechaNac.toString())
                .edad(edad)
                .nacionalidad(Optional.ofNullable(datos.getNacionalidad()).orElse("Mexicana"))
                .curp(datos.getCurp())
                .email(datos.getEmail())
                .carrera(carrera != null ? carrera.getId() : "")
                .noFicha(ficha != null ? String.valueOf(ficha.getId().getIdDat()) : "")
                .anioFicha(ficha != null ? String.valueOf(ficha.getId().getAnoDat()) : "")
                .nss(alumno.getNss())
                .matricula(alumno.getId())
                .ultimoSemestre(reinscripcionRepository.findUltimoSemestreByAlumno(alumno.getId()).get())
                .build();
    }

    public MateriaResultadoDTO obtenerMaterias(String matricula) {
        List<MateriaCursadaDTO> materias = alumnoRepository.findMateriasPorAlumno(matricula);

        List<MateriaCursadaDTO> materiasAprobadas = new ArrayList<>();
        List<MateriaCursadaDTO> materiasReprobadas = new ArrayList<>();
        List<MateriaCursadaDTO> materiasSinCalificacion = new ArrayList<>();

        Set<String> clavesAprobadas = new HashSet<>();

        for (MateriaCursadaDTO materia : materias) {
            String clavePlan = materia.getClave() + "-" + materia.getPlan();
            Float calificacion = materia.getCalificacion();

            if (calificacion == null) {
                materiasSinCalificacion.add(materia);
            } else if (calificacion >= promedioMinimo && calificacion <= 10) {
                materiasAprobadas.add(materia);
                clavesAprobadas.add(clavePlan);
            }
        }

        for (MateriaCursadaDTO materia : materias) {
            Float calificacion = materia.getCalificacion();
            String clavePlan = materia.getClave() + "-" + materia.getPlan();

            if (calificacion != null && (calificacion < promedioMinimo || calificacion > 10)) {
                if (!clavesAprobadas.contains(clavePlan)) {
                    materiasReprobadas.add(materia);
                }
            }
        }

        return new MateriaResultadoDTO(materiasAprobadas, materiasReprobadas, materiasSinCalificacion);
    }

    public List<MateriaCursadaDTO> obtenerMateriasAdeudadas(String matricula) {
        // Obtener alumno
        AlumnoMatriculado alumno = alumnoRepository.findById(matricula).orElseThrow();

        // Obtener último semestre inscrito
        int semestreActual = reinscripcionRepository.findUltimoSemestreByAlumno(matricula).get();

        // Obtener todas las materias del plan hasta ese semestre
        List<MateriaCursadaDTO> materiasDelPlan = materiaRepository.findMateriasDelPlan("alumno.get")
                .stream()
                .filter(m -> m.getSemestre() <= semestreActual)
                .toList();

        // Obtener claves de materias cursadas
        List<MateriaCursadaDTO> materiasCursadas = alumnoRepository.findMateriasPorAlumno(matricula);
        Set<String> clavesCursadas = materiasCursadas.stream()
                .map(m -> m.getClave() + "-" + m.getPlan())
                .collect(Collectors.toSet());

        // Filtrar las que no ha cursado
        List<MateriaCursadaDTO> materiasAdeudadas = materiasDelPlan.stream()
                .filter(m -> !clavesCursadas.contains(m.getClave() + "-" + m.getPlan()))
                .toList();

        return materiasAdeudadas;
    }

    public double calcularPromedio(String matricula) {
        MateriaResultadoDTO materiaResultadoDTO= obtenerMaterias(matricula);
        int totalMaterias = 0;
        double sumaCalificaciones = 0.0;

        // Materias aprobadas (siempre cuentan)
        List<MateriaCursadaDTO> aprobadas = materiaResultadoDTO.getMateriasAprobadas();
        totalMaterias += aprobadas.size();
        sumaCalificaciones += aprobadas.stream()
                .mapToDouble(MateriaCursadaDTO::getCalificacion)
                .sum();

        // Materias reprobadas con calificación válida
        List<MateriaCursadaDTO> reprobadas = materiaResultadoDTO.getMateriasReprobadas();
        for (MateriaCursadaDTO materia : reprobadas) {
            Float calif = materia.getCalificacion();
            if (calif != null && calif >= 0.0 && calif < 6.0) {
                sumaCalificaciones += calif;
                totalMaterias++;
            }
        }

        // Materias que debió cursar también cuentan (aunque no tenga calificación)
        totalMaterias +=  obtenerMateriasAdeudadas(matricula).size();

        if (totalMaterias == 0) {
            return 0.0;
        }

        return sumaCalificaciones / totalMaterias;
    }

    // aquí puedes agregar el método que genera el PDF con JasperReports
}
