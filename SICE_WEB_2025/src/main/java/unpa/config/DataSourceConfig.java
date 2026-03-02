package unpa.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    // 1. Creamos la conexión para el Campus 1
    @Bean
    @ConfigurationProperties(prefix = "tenant.campus1.datasource")
    public DataSource campus1DataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    // 2. Creamos la conexión para el Campus 2
    @Bean
    @ConfigurationProperties(prefix = "tenant.campus2.datasource")
    public DataSource campus2DataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    // 3. Unimos ambas en el Enrutador
    @Bean
    @Primary // Le decimos a Spring JPA que use este bean por defecto para todo
    public DataSource dataSource(
            @Qualifier("campus1DataSource") DataSource campus1DataSource,
            @Qualifier("campus2DataSource") DataSource campus2DataSource) {

        TenantRoutingDataSource routingDataSource = new TenantRoutingDataSource();

        // Mapa de nuestras bases de datos
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("CAMPUS1", campus1DataSource);
        targetDataSources.put("CAMPUS2", campus2DataSource);

        routingDataSource.setTargetDataSources(targetDataSources);

        // Base de datos por defecto (si no hay bandera, usa escolares)
        routingDataSource.setDefaultTargetDataSource(campus1DataSource);

        // Obligatorio para inicializar el enrutador
        routingDataSource.afterPropertiesSet();

        return routingDataSource;
    }
}