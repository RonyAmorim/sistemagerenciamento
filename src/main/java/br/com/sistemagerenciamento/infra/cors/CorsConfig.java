package br.com.sistemagerenciamento.infra.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * Classe de configuração para habilitar o CORS
 *
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    /*
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("https://tec-flux-angular.vercel.app")
                .allowedMethods("GET","POST","DELETE","UPDATE");
    }
    */
}
