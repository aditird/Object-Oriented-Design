import java.util.*;
import java.util.stream.Collectors;

// Book Entity
class Book {
    private final int bookId;
    private final String title;
    private final String genre;
    private final int publishedYear;
    private final Author author;
    private final List<Review> reviews = new ArrayList<>();

    public Book(int bookId, String title, String genre, int publishedYear, Author author) {
        this.bookId = bookId;
        this.title = title;
        this.genre = genre;
        this.publishedYear = publishedYear;
        this.author = author;
    }

    public int getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public int getPublishedYear() { return publishedYear; }
    public Author getAuthor() { return author; }
    public List<Review> getReviews() { return reviews; }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public double getAverageRating() {
        return reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
    }

    @Override
    public String toString() {
        return title + " by " + author.getName() + " (" + publishedYear + ")";
    }
}

// Author Entity
class Author {
    private final int authorId;
    private final String name;

    public Author(int authorId, String name) {
        this.authorId = authorId;
        this.name = name;
    }

    public int getAuthorId() { return authorId; }
    public String getName() { return name; }
}

// Review Entity
class Review {
    private final User user;
    private final int rating; // 1 to 5
    private final String comment;

    public Review(User user, int rating, String comment) {
        this.user = user;
        this.rating = rating;
        this.comment = comment;
    }

    public int getRating() { return rating; }
    public String getComment() { return comment; }
}

// User Entity
class User {
    private final int userId;
    private final String name;

    public User(int userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public int getUserId() { return userId; }
    public String getName() { return name; }

    public void addReview(Book book, int rating, String comment) {
        Review review = new Review(this, rating, comment);
        book.addReview(review);
    }
}

// Singleton Book Repository (Data Storage)
class BookRepository {
    private static BookRepository instance;
    private final List<Book> books = new ArrayList<>();

    private BookRepository() {}

    public static BookRepository getInstance() {
        if (instance == null) {
            instance = new BookRepository();
        }
        return instance;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getBooks() {
        return books;
    }
}

// Factory Pattern for Book Creation
class BookFactory {
    public static Book createBook(int bookId, String title, String genre, int publishedYear, Author author) {
        return new Book(bookId, title, genre, publishedYear, author);
    }
}

// Strategy Pattern for Searching Books
interface BookSearchStrategy {
    List<Book> search(String query, List<Book> books);
}

// Search by Title
class SearchByTitle implements BookSearchStrategy {
    @Override
    public List<Book> search(String query, List<Book> books) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(query))
                .collect(Collectors.toList());
    }
}

// Search by Author
class SearchByAuthor implements BookSearchStrategy {
    @Override
    public List<Book> search(String query, List<Book> books) {
        return books.stream()
                .filter(book -> book.getAuthor().getName().equalsIgnoreCase(query))
                .collect(Collectors.toList());
    }
}

// Search by Genre
class SearchByGenre implements BookSearchStrategy {
    @Override
    public List<Book> search(String query, List<Book> books) {
        return books.stream()
                .filter(book -> book.getGenre().equalsIgnoreCase(query))
                .collect(Collectors.toList());
    }
}

// Book Service (Manages Books)
class BookService {
    private final BookRepository bookRepository = BookRepository.getInstance();

    public void addBook(int bookId, String title, String genre, int publishedYear, Author author) {
        Book book = BookFactory.createBook(bookId, title, genre, publishedYear, author);
        bookRepository.addBook(book);
    }

    public List<Book> searchBooks(String query, BookSearchStrategy strategy) {
        return strategy.search(query, bookRepository.getBooks());
    }
}

// Main Class (Testing)
public class GoodreadsApp {
    public static void main(String[] args) {
        BookService bookService = new BookService();
        BookRepository bookRepository = BookRepository.getInstance();

        // Create Authors
        Author author1 = new Author(1, "J.K. Rowling");
        Author author2 = new Author(2, "George R.R. Martin");

        // Add Books
        bookService.addBook(1, "Harry Potter", "Fantasy", 1997, author1);
        bookService.addBook(2, "Game of Thrones", "Fantasy", 1996, author2);
        bookService.addBook(3, "The Hobbit", "Fantasy", 1937, new Author(3, "J.R.R. Tolkien"));

        // Create Users
        User user1 = new User(1, "Alice");
        User user2 = new User(2, "Bob");

        // Alice adds reviews
        user1.addReview(bookRepository.getBooks().get(0), 5, "Amazing book!");
        user1.addReview(bookRepository.getBooks().get(1), 4, "Great read!");

        // Bob adds reviews
        user2.addReview(bookRepository.getBooks().get(0), 4, "Loved it!");
        user2.addReview(bookRepository.getBooks().get(2), 5, "Masterpiece!");

        // Search Books
        System.out.println("Search by Title:");
        bookService.searchBooks("Harry Potter", new SearchByTitle()).forEach(System.out::println);

        System.out.println("\nSearch by Author:");
        bookService.searchBooks("J.K. Rowling", new SearchByAuthor()).forEach(System.out::println);

        System.out.println("\nSearch by Genre:");
        bookService.searchBooks("Fantasy", new SearchByGenre()).forEach(System.out::println);

        // Display Average Rating
        System.out.println("\nAverage Rating of 'Harry Potter': " + bookRepository.getBooks().get(0).getAverageRating());
    }
}
