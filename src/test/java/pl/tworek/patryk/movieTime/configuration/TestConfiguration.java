package pl.tworek.patryk.movieTime.configuration;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.tworek.patryk.movieTime.dao.IFilmDAO;
import pl.tworek.patryk.movieTime.dao.IUserDAO;
import pl.tworek.patryk.movieTime.dao.impl.FilmDAOImplStub;
import pl.tworek.patryk.movieTime.dao.impl.UserDAOImplStub;

@Configuration
@ComponentScan(basePackages = {
        "pl.tworek.patryk.movieTime.controllers",
        "pl.tworek.patryk.movieTime.services",
        "pl.tworek.patryk.movieTime.sessionObject",
        "pl.tworek.patryk.movieTime.model.view"
})
public class TestConfiguration {
//    @Bean
//    public IUserDAO userDAO() {
//        return Mockito.mock(IUserDAO.class);
//    }
//
//    @Bean
//    public IFilmDAO filmDAO() {
//        return Mockito.mock(IFilmDAO.class);
//    }
}
