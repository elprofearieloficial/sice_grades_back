package unpa.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebStaticConfig implements WebMvcConfigurer {

    @Value("${app.uploads.dir:./uploads/perfiles}")
    private String uploadDir;

    @Value("${app.uploads.credencial-dir:./uploads/credenciales}")
    private String credencialUploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        registry.addResourceHandler("/vm2/fotos-perfil/**")
                .addResourceLocations("file:" + uploadPath + "/");

        Path credencialPath = Paths.get(credencialUploadDir).toAbsolutePath().normalize();
        registry.addResourceHandler("/vm2/fotos-credencial/**")
                .addResourceLocations("file:" + credencialPath + "/");
    }
}
