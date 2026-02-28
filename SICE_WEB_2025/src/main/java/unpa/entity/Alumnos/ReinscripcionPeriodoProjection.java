package unpa.entity.Alumnos;

public interface ReinscripcionPeriodoProjection {
    Integer getPrimerSemestreNum();
    String getPrimerCiclo();
    String getPrimerPeriodo();
    Integer getUltimoSemestreNum();
    String getUltimoCiclo();
    String getUltimoPeriodo();
}