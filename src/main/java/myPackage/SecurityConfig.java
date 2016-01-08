package myPackage;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception 
    {
      auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery("select kullaniciAdi,password, enabled from uyeler where kullaniciAdi=?")
      .authoritiesByUsernameQuery("select kullaniciAdi, role from userroles where kullaniciAdi=?")
      .passwordEncoder(new Md5PasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception 
    { 
      http.authorizeRequests()
        .antMatchers("/adminPage/**").access("hasRole('ROLE_ADMIN')")
        .antMatchers("/guestPage/**").access("hasRole('ROLE_GUEST') or hasRole('ROLE_ADMIN')")
        .and().formLogin().loginPage("/girisYap.xhtml").defaultSuccessUrl("/guestPage/guestPage.xhtml")
        .usernameParameter("j_username").passwordParameter("j_password")
        .and().csrf().csrfTokenRepository(csrfTokenRepository());	
      http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }
    
    private CsrfTokenRepository csrfTokenRepository() 
    { 
        //Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository(); 
        repository.setSessionAttributeName("_csrf");
        return repository; 
    }
}