package com.datajpa.app;

//import com.datajpa.app.models.entity.Cliente;
//import org.springframework.beans.factory.annotation.Autowired;
import com.datajpa.app.auth.handler.LoginSuccessHandler;
import com.datajpa.app.services.JpaUserDetailsServices;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;

//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * @author LuisMD
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SpringSecurityConfig {

    @Autowired
    LoginSuccessHandler successHandler;//mandar mensaje de inicio de sesion

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JpaUserDetailsServices jpaDS;
   
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //CREAMOS ROLES DE USUARIO
   
    @Bean
    @Primary
    public AuthenticationManagerBuilder userDetailsService(AuthenticationManagerBuilder builder) throws Exception{

         builder.userDetailsService(jpaDS)
         .passwordEncoder(bCryptPasswordEncoder());
         

       return builder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico","/locale").permitAll()
                /*.antMatchers("/ver/**").hasAnyRole("USER")
                .antMatchers("/uploads/**").hasAnyRole("USER")
                .antMatchers("/lista/**").hasAnyRole("USER")
                .antMatchers("/form/**").hasAnyRole("ADMIN")
                .antMatchers("/facturas/ep0dofesk/**").hasAnyRole("ADMIN")
                .antMatchers("/drop/**").hasAnyRole("ADMIN")
                .antMatchers("/delete/**").hasAnyRole("ADMIN")
                .antMatchers("/lista/**").hasAnyRole("ADMIN")   
                .antMatchers("/uploads/**").hasAnyRole("ADMIN")*/
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .successHandler(successHandler)//Para agregar mensajes flash
                .loginPage("/login")
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/error_403")
                ;

             

        return http.build();
    }


    


    
}
