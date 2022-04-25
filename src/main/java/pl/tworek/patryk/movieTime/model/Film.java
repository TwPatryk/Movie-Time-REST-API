package pl.tworek.patryk.movieTime.model;

import javax.persistence.*;

@Entity(name = "tfilm")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String productionYear;
    private String director;
    private String length;
    private String genre;
    private double rate;
    private double rateSum;
    private int voteCount;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String filePath;

    public Film() {
    }

    public Film(int id, String title, String productionYear, String director, String length, String genre, double rate, double rateSum, int voteCount, Category category, String filePath) {
        this.id = id;
        this.title = title;
        this.productionYear = productionYear;
        this.director = director;
        this.length = length;
        this.genre = genre;
        this.rate = rate;
        this.rateSum = rateSum;
        this.voteCount = voteCount;
        this.category = category;
        this.filePath = filePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(String productionYear) {
        this.productionYear = productionYear;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getRateSum() {
        return rateSum;
    }

    public void setRateSum(double rateSum) {
        this.rateSum = rateSum;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public enum Category {
        MOVIE,
        TVSHOW
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", productionYear='" + productionYear + '\'' +
                ", director='" + director + '\'' +
                ", length='" + length + '\'' +
                ", genre='" + genre + '\'' +
                ", rate=" + rate +
                ", rateSum=" + rateSum +
                ", voteCount=" + voteCount +
                ", category=" + category +
                ", filePath='" + filePath + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Film) {
            Film film = (Film) obj;
            if(this.id == film.getId() &&
            this.title.equals(film.getTitle()) &&
            this.productionYear.equals(film.getProductionYear()) &&
            this.director.equals(film.getDirector()) &&
            this.length.equals(film.getLength()) &&
            this.genre.equals(film.getGenre()) &&
            this.rate == film.getRate() &&
            this.rateSum == film.getRateSum() &&
            this.voteCount == film.getVoteCount() &&
            this.category.equals(film.getCategory())  &&
            this.filePath.equals(film.getFilePath())) {
                return true;
            }
        }
        return false;
    }
}
