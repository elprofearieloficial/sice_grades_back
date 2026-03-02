package unpa.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class TenantRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        // Aquí lee la bandera que setearemos después (ej. "CAMPUS1" o "CAMPUS2")
        return TenantContext.getCurrentTenant();
    }
}