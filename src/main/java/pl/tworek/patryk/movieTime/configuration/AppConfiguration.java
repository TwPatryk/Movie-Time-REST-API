package pl.tworek.patryk.movieTime.configuration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@ComponentScan("pl.tworek.patryk.movieTime")
public class AppConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
    }

//    @Bean
//    public Connection connection() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            return  DriverManager.getConnection("jdbc:mysql://localhost:3306/movie-time2","root", "");
//            //return  DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&characterEncoding=utf8","root","");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
