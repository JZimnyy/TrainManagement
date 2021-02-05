package com.ProjektJakubZimny.Projekt;


import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/klienci").hasAuthority("ADMIN")
                .antMatchers("/adminPanel").hasAuthority("ADMIN")
                .antMatchers("/rejestracjaAdmin").hasAuthority("ADMIN")
                .antMatchers("/bilet/trasa").hasAnyAuthority("USER","ADMIN")
                .antMatchers("/bilet/trasa/przystanki").hasAnyAuthority("USER","ADMIN")
                .antMatchers("/bilet/trasa/przystanki/odjazdy").hasAnyAuthority("USER","ADMIN")
                .antMatchers("/bilet/potwierdz").hasAnyAuthority("USER","ADMIN")
                .antMatchers("/bilet/ok").hasAnyAuthority("USER","ADMIN")
                .antMatchers("/bilety/moje").hasAnyAuthority("USER","ADMIN")
                .antMatchers("/klienci/edit/{id}").hasAuthority("ADMIN")
                .antMatchers("/zapiszZmiany").hasAnyAuthority("USER","ADMIN")
                .antMatchers("/klienci/details/{id}").hasAuthority("ADMIN")
                .antMatchers("/mojeKonto").hasAnyAuthority("USER","ADMIN")
                .antMatchers("/trasy/edytuj/{id}").hasAuthority("ADMIN")
                .antMatchers("/mojeKonto/zmianaHaslo").hasAnyAuthority("USER","ADMIN")
                .antMatchers("/mojeKonto/edytuj").hasAnyAuthority("USER","ADMIN")
                .antMatchers("/bilety").hasAuthority("ADMIN")
                .antMatchers("/przystanki/dodaj").hasAuthority("ADMIN")
                .antMatchers("/przystanki/edit/{id}").hasAuthority("ADMIN")
                .antMatchers("/przystanki/zapiszZmiany").hasAuthority("ADMIN")
                .antMatchers("/trasy/dodaj").hasAuthority("ADMIN")
                .antMatchers("/trasy/dodajPrzystanek").hasAuthority("ADMIN")
                .antMatchers("/trasy/edytuj/{id}").hasAuthority("ADMIN")
                .antMatchers("/trasy/zapiszZmiany").hasAuthority("ADMIN")
                .antMatchers("/mojeBilety/details/{id}").hasAnyAuthority("ADMIN","USER")
                .anyRequest().permitAll()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403");

        http
                .csrf().disable();
    }
    
    @Autowired
    DataSource dataSource;
      
    
        @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select email, haslo, enabled from klient where email=?")
                .authoritiesByUsernameQuery("select email, role from klient where email=?");
        
        
    }
}
