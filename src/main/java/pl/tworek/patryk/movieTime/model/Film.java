package pl.tworek.patryk.movieTime.model;

public class Film {
    private int id;
    private String title;
    private String productionYear;
    private String director;
    private String length;
    private String genre;
    private double rate;
    private double rateSum = 0.0;
    private int voteCount = 0;
    private Category category;

    public Film() {
    }

    public Film(int id, String title, String productionYear, String director, String length, String genre, double rate, double rateSum, int voteCount, Category category) {
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

    public enum Category {
        MOVIE,
        TVSHOW
    }
}
