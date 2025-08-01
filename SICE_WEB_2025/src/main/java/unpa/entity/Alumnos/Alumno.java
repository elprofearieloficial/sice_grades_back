package unpa.entity.Alumnos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Alumno {
    @Id
    private String matricula;
    private String password; // Encriptada
    private String nombre;
}

