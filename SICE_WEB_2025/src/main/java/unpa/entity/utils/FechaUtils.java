package unpa.entity.utils;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

public class FechaUtils {
    private static final String[] MESES = {
            "", // índice 0 (no se usa)
            "enero", "febrero", "marzo", "abril", "mayo", "junio",
            "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"
    };

    public static String getMesTexto(String mes1) {
        try {
            int mes = Integer.parseInt(mes1);
            return (mes >= 1 && mes <= 12) ? MESES[mes] : "";
        } catch (NumberFormatException e) {
            return "";
        }
    }

    public static String getDateToText(Date fecha) {
        if (fecha == null) return "";

        // Convertimos Date a LocalDate
        LocalDate localDate = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int dia = localDate.getDayOfMonth();
        String mes = localDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        int anio = localDate.getYear();

        // Armamos el texto
        if (dia == 1) {
            return "el " + dia + " día del mes de " + mes + " de " + anio;
        } else {
            return "los " + dia + " días del mes de " + mes + " de " + anio;
        }
    }

    public static String getDateToText(String fechaStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate fecha = LocalDate.parse(fechaStr, formatter);

            int dia = fecha.getDayOfMonth();
            String mes = fecha.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
            int anio = fecha.getYear();

            return (dia == 1)
                    ? "el " + dia + " día del mes de " + mes + " de " + anio
                    : "los " + dia + " días del mes de " + mes + " de " + anio;

        } catch (Exception e) {
            return "";
        }
    }

    // Formato 2: "8 de julio de 2025"
    public static String getFechaFormatoSimple(String fechaStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate fecha = LocalDate.parse(fechaStr, formatter);
            return getFechaFormatoSimple(fecha);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getFechaFormatoSimple(Date fecha) {
        if (fecha == null) return "";
        LocalDate localDate = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return getFechaFormatoSimple(localDate);
    }

    private static String getFechaFormatoSimple(LocalDate fecha) {
        int dia = fecha.getDayOfMonth();
        String mes = fecha.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        int anio = fecha.getYear();

        return dia + " de " + mes + " de " + anio;
    }

    public static String getFechaActualAsString(){
        LocalDate fecha = LocalDate.now(); // fecha actual sin hora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fecha.format(formatter);

    }

    public static Date getFechaActual(){
        return new Date();
    }
}
