package pl.tworek.patryk.movieTime.database.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.tworek.patryk.movieTime.database.IFilmRepository;
import pl.tworek.patryk.movieTime.model.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JDBCFilmRepositoryImpl implements IFilmRepository {

    @Autowired
    Connection connection;


    @Override
    public List<Film> getAllMovies() {
        List<Film> filmList = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM tfilm WHERE category=?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, Film.Category.MOVIE.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                filmList.add(this.mapResultSetToFilm(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filmList;
    }

    @Override
    public List<Film> getAllTvShows() {
        List<Film> filmList = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM tfilm WHERE category=?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, Film.Category.TVSHOW.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                filmList.add(this.mapResultSetToFilm(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filmList;
    }

    @Override
    public List<Film> getAllFilms() {
        List<Film> filmList = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM tfilm";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                filmList.add(this.mapResultSetToFilm(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filmList;
    }


    @Override
    public void addFilm(Film film) {
        try {
            String SQL = "INSERT INTO tfilm(title, productionYear, director, length, genre, rate, rateSum, voteCount, category) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, film.getTitle());
            preparedStatement.setString(2, film.getProductionYear());
            preparedStatement.setString(3, film.getDirector());
            preparedStatement.setString(4, film.getLength());
            preparedStatement.setString(5, film.getGenre());
            preparedStatement.setDouble(6, film.getRate());
            preparedStatement.setDouble(7, film.getRateSum());
            preparedStatement.setInt(8, film.getVoteCount());
            preparedStatement.setString(9, film.getCategory().toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateFilm(Film film) {
        try {
            String SQL = "UPDATE tfilm SET title=?, productionYear=?, director=?, length=?, genre=?, rate=?, rateSum=?, voteCount=?, category=? WHERE id=?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);


            preparedStatement.setString(1, film.getTitle());
            preparedStatement.setString(2, film.getProductionYear());
            preparedStatement.setString(3, film.getDirector());
            preparedStatement.setString(4, film.getLength());
            preparedStatement.setString(5, film.getGenre());
            preparedStatement.setDouble(6, film.getRate());
            preparedStatement.setDouble(7, film.getRateSum());
            preparedStatement.setInt(8, film.getVoteCount());
            preparedStatement.setString(9, film.getCategory().toString());
            preparedStatement.setInt(10, film.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Film getFilmById(int id) {
        try {
            String SQL = "SELECT * FROM tfilm WHERE id=?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return this.mapResultSetToFilm(resultSet);
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void deleteFilm(int id) {
        try {
            String SQL = "DELETE FROM tfilm WHERE id=?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Film getFilmByRate(double rate) {
        try {
            String SQL = "SELECT * FROM tfilm where rate=?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setDouble(1, rate);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                this.mapResultSetToFilm(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Film mapResultSetToFilm(ResultSet resultSet) throws SQLException {
        Film film = new Film();
        film.setId(resultSet.getInt("id"));
        film.setCategory(Film.Category.valueOf(resultSet.getString("category")));
        film.setDirector(resultSet.getString("director"));
        film.setGenre(resultSet.getString("genre"));
        film.setLength(resultSet.getString("length"));
        film.setProductionYear(resultSet.getString("productionYear"));
        film.setTitle(resultSet.getString("title"));
        film.setVoteCount(resultSet.getInt("voteCount"));
        film.setRate(resultSet.getDouble("rate"));
        film.setRateSum(resultSet.getDouble("rateSum"));

        return film;
    }

}
