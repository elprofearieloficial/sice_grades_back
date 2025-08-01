package unpa.entity.utils;

public class SemestreUtils {
    private static final String[] SEMESTRES = {
            "-------",        // índice 0 (para cuando el semestre no sea válido)
            "Primero",        // 1
            "Segundo",        // 2
            "Tercero",        // 3
            "Cuarto",         // 4
            "Quinto",         // 5
            "Sexto",          // 6
            "Séptimo",        // 7
            "Octavo",         // 8
            "Noveno",         // 9
            "Décimo",         // 10
            "Onceavo",        // 11
            "Doceavo",        // 12
            "Treceavo",       // 13
            "Catorceavo",     // 14
            "Quinceavo"       // 15
    };

    public static String getSemestreTxt(int semestre) {
        return (semestre >= 1 && semestre <= 15) ? SEMESTRES[semestre] : SEMESTRES[0];
    }
}
