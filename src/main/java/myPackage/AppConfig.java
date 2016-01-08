package myPackage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ilkaygunel
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
@ComponentScan({ "myPackage" })
@Import({ SecurityConfig.class })
public class AppConfig {

    @Bean
    public InternalResourceViewResolver viewResolver() {
            InternalResourceViewResolver viewResolver 
                      = new InternalResourceViewResolver();
            viewResolver.setViewClass(JstlView.class);
            viewResolver.setPrefix("/pages/");
            viewResolver.setSuffix(".xhtml");
            return viewResolver;
    }

    @Bean(name = "dataSource")
     public DriverManagerDataSource dataSource() {
         DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
         driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
         driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/BlogProjesi");
         driverManagerDataSource.setUsername("root");
         driverManagerDataSource.setPassword("");
         return driverManagerDataSource;
     }	
}