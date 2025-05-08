enum SeatCategory{
    Silver,
    Gold, Platinum
}

enum City{
    Bangalore,
    Tempe
}
public class Movie{
    int movieId;
    String moviename;
    int movieDuration;

    public int getMovieId(int movieId){
        this.movieId = movieId;
    }

    public void setMovieid()

    public String getMovieName()

    public void setMovieName()

    public int getMovieDuration()

    public int setMovieDuration()
}

public class MovieController{
    Map<City, List<Movie> cityVsMovies;
    List<movie> allMovies;
    MovieController(){
        cityVsMovies = new HashMap<>();
        allMovies = new ArrayList<>();
    }

    void addMovie(Movie movie, City city){
        allMovies.add(movie);
        List<Movie> movies = cityVsMovies.getOrDefault(city, new ArrayList<>());
        movies.add(movie);
        cityVsMovies.put(city, movies);
    }

    Movie getMovieByName(String movieName){
        for(Movie movie:allMovies){
            if(movie.getMovieName().equaks(movieName)){
                retrun movie;
            }
        }
        return null;
    }
}

public class Theatre{
    int theareId;
    String address;
    City city;
    List<screens> screen = new ArrayList<>();
    List<shows> show = new Arraylist<>();

    methods(getters and setters)

    getTheatreId
    setTheatreId
    getAddress
    setAddress
    gerScreen
    setScreen
    getShows
    setShows
    getCity
    setcity
}

public class Screen{
    int screenId;
    List<seat> seats = new ArrayList<>();

    getter and setter methods:
    getscreenId
    setScreenId
    getSeats
    setSeats
}

public class Seat{
    int seatId;
    int row;
    SeatCategory seat;

    public int getSeatId
    public void seatSeatId
    public void setRow
    public void getSeatCategory
    public void setSeatCategory
}

public class Screen{
    int screenId;
    List<Seat> seats = new ArrayList<>();

    public int getScreenId
    public int setScreenId
    public getSeats
    public setSeats
}

public class TheatreController{
    Map<City, List<Theatre>> cityVsTheatre;
    List<Theatre> listOfTheatres;

    TheatreController(){
        cityVsTheatre = new HashMap<>();
        listOfTheatres = new ArrayList<>();
    }

    void addTheatre(Theatre theatre, City city){
        listOfTheatres.add(theatre);
        List<Theatre> theatres = cityVsTheatre.getOrDefault(city, new ArrayList<>());
        theatres.add(theatre);
        cityVsTheatre.put(city, theatres);
    }

    Map<> getAllShow(Movie movie, City city){
        for all theatres
            for all shows
                add
      return theatreVsShows
    }
    }

public class Booking{
    Show show;
    List<Seat> bookedSeats = new Arraylist<>();
    Payment payment;

    methods
    getShow
    setShow
    getBookedSeats
    SetBookedSeats
    getPayment
    setPayment
}
public class Payment{
    int paymentId;
}

public class BookMYSHOW{
    MOvieController moviecontroller;
    TheatreController theatrecontroller;

    BookMyShow(){
        moviecontroller = new MovieController();
        theatrecontroller = new TheatreController();
    }

    public static void main(String args[]){
        BookMyShow bookMyShow = new BookMyShow();
        bookMyShow.initialize();
        bookMyShow.createBooking(City.Bangalore, "Bahubali");
        bookMyShow.createBooking(City.Bangalore, "Avengers");

        public void initialize();
        public void createTheatre();
        public void createScreen();
        public void createShows();
        public void createSeats();
    }


}
}
