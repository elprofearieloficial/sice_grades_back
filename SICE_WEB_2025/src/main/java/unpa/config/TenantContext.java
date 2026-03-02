package unpa.config; // <-- Cambia esto por tu paquete real

public class TenantContext {

    // ThreadLocal asegura que cada hilo (petición web) tenga su propia variable aislada
    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

    public static void setCurrentTenant(String tenant) {
        CURRENT_TENANT.set(tenant);
    }

    public static String getCurrentTenant() {
        return CURRENT_TENANT.get();
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }
}