package com.datajpa.app.configurarDirectoryImg;

import java.nio.file.Paths;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 *
 * @author LuisMD
 */

@Configuration
public class MvcConfig  implements WebMvcConfigurer{

    /*
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry r) {

       //WebMvcConfigurer.super.addResourceHandlers(r);
       //4 ESTACONFIGURACION FUNCIONA CON COMENTARIO 1 Y 2 DEL CONTROLLER
       //r.addResourceHandler("/uploads/**").addResourceLocations("file:/C:/Windows/Temp/uploads/");//ESTO SE MAPEA AL SHOWDATEILS PARA SER MOSTRADA
        
       
        String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
        r.addResourceHandler("/uploads/**").addResourceLocations(resourcePath);
        log.info("Direcion : "+resourcePath);
    }*/
  
    public void addViewControllers(ViewControllerRegistry r) {
        r.addViewController("/error_403").setViewName("error_403");
    }

    @Bean
    //Donde se aguardar nuestro parametro local y se guarda en la sesion
    public LocaleResolver localeResolver(){
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("es","ES"));

       return localeResolver;
    }

    @Bean
    //Interceptor que se encarga de de modificar o cambiar el  leguaje cada vez que nos llega el parametro por url
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("lang");
        return localeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry r) {
       r.addInterceptor(localeChangeInterceptor());
    }
}
