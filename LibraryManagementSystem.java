import java.util.*;

// Enum for Book Status
enum BookStatus {
    AVAILABLE,
    ISSUED,
    RESERVED,
    LOST
}

// Book entity
class Book {
    private final String ISBN;
    private String title;
    private String author;
    private BookStatus status;

    public Book(String ISBN, String title, String author) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.status = BookStatus.AVAILABLE;
    }

    public String getISBN() { return ISBN; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public BookStatus getStatus() { return status; }

    public void setStatus(BookStatus status) { this.status = status; }
}

// Abstract user
abstract class User {
    private final String userId;
    private String name;

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
}

// Librarian - Admin functionalities
class Librarian extends User {

    public Librarian(String userId, String name) {
        super(userId, name);
    }

    public void addBook(Library library, Book book) {
        library.addBook(book);
    }

    public void removeBook(Library library, String isbn) {
        library.removeBook(isbn);
    }
}

// Member - User functionalities
class Member extends User {
    private List<Book> borrowedBooks;

    public Member(String userId, String name) {
        super(userId, name);
        this.borrowedBooks = new ArrayList<>();
    }

    public boolean borrowBook(Library library, String isbn) {
        Book book = library.issueBook(isbn);
        if (book != null) {
            borrowedBooks.add(book);
            return true;
        }
        return false;
    }

    public void returnBook(Library library, String isbn) {
        Iterator<Book> iterator = borrowedBooks.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getISBN().equals(isbn)) {
                iterator.remove();
                library.returnBook(book);
                break;
            }
        }
    }

    public List<Book> searchBooks(Library library, String keyword) {
        return library.searchBooks(keyword);
    }
}

// Library — the central store
class Library {
    private Map<String, List<Book>> bookCatalog;

    public Library() {
        this.bookCatalog = new HashMap<>();
    }

    public void addBook(Book book) {
        bookCatalog.putIfAbsent(book.getISBN(), new ArrayList<>());
        bookCatalog.get(book.getISBN()).add(book);
        System.out.println("Book added: " + book.getTitle());
    }

    public void removeBook(String isbn) {
        List<Book> books = bookCatalog.get(isbn);
        if (books != null && !books.isEmpty()) {
            books.remove(books.size() - 1);
            System.out.println("One copy of book with ISBN " + isbn + " removed.");
            if (books.isEmpty()) {
                bookCatalog.remove(isbn);
            }
        }
    }

    public Book issueBook(String isbn) {
        List<Book> books = bookCatalog.get(isbn);
        if (books == null) return null;

        for (Book book : books) {
            if (book.getStatus() == BookStatus.AVAILABLE) {
                book.setStatus(BookStatus.ISSUED);
                return book;
            }
        }
        return null;
    }

    public void returnBook(Book book) {
        book.setStatus(BookStatus.AVAILABLE);
    }

    public List<Book> searchBooks(String keyword) {
        List<Book> result = new ArrayList<>();
        for (List<Book> books : bookCatalog.values()) {
            for (Book book : books) {
                if (book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                    result.add(book);
                }
            }
        }
        return result;
    }
}

// Example usage
public class LibrarySystem {
    public static void main(String[] args) {
        Library library = new Library();

        Librarian librarian = new Librarian("L1", "Alice");
        Member member = new Member("M1", "Bob");

        // Adding books
        librarian.addBook(library, new Book("ISBN001", "Design Patterns", "GoF"));
        librarian.addBook(library, new Book("ISBN001", "Design Patterns", "GoF"));
        librarian.addBook(library, new Book("ISBN002", "Clean Code", "Robert C. Martin"));

        // Searching books
        System.out.println("Searching for 'design':");
        for (Book book : member.searchBooks(library, "design")) {
            System.out.println(book.getTitle() + " by " + book.getAuthor());
        }

        // Borrowing a book
        if (member.borrowBook(library, "ISBN001")) {
            System.out.println("Book borrowed successfully!");
        } else {
            System.out.println("Book not available.");
        }

        // Returning a book
        member.returnBook(library, "ISBN001");
        System.out.println("Book returned!");
    }
}


//SEARCH ABSTRACTION

import java.util.*;

// Enum for Book Status
enum BookStatus {
    AVAILABLE, ISSUED, RESERVED, LOST
}

// Book Class
class Book {
    private final String ISBN;
    private final String title;
    private final String author;
    private BookStatus status;

    public Book(String ISBN, String title, String author) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.status = BookStatus.AVAILABLE;
    }

    public String getISBN() { return ISBN; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public BookStatus getStatus() { return status; }
    public void setStatus(BookStatus status) { this.status = status; }
}

// Search Interface
interface Search {
    List<Book> search(String query);
}

// Search Implementation: By Title
class SearchByTitle implements Search {
    private final List<Book> books;

    public SearchByTitle(List<Book> books) {
        this.books = books;
    }

    @Override
    public List<Book> search(String query) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(query.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }
}

// Search Implementation: By Author
class SearchByAuthor implements Search {
    private final List<Book> books;

    public SearchByAuthor(List<Book> books) {
        this.books = books;
    }

    @Override
    public List<Book> search(String query) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(query.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }
}

// Library Class
class Library {
    private final List<Book> allBooks = new ArrayList<>();

    public void addBook(Book book) { allBooks.add(book); }

    public void removeBook(String isbn) {
        allBooks.removeIf(book -> book.getISBN().equals(isbn));
    }

    public List<Book> getAllBooks() { return allBooks; }
}

// Member Class
class Member {
    private final String name;

    public Member(String name) {
        this.name = name;
    }

    public List<Book> searchBooks(Search searchStrategy, String keyword) {
        return searchStrategy.search(keyword);
    }
}

// Driver Code
public class LibrarySystem {
    public static void main(String[] args) {
        Library library = new Library();

        library.addBook(new Book("ISBN001", "Design Patterns", "Erich Gamma"));
        library.addBook(new Book("ISBN002", "Clean Code", "Robert C. Martin"));
        library.addBook(new Book("ISBN003", "Effective Java", "Joshua Bloch"));

        Member member = new Member("Alice");

        // Search by Title
        Search searchByTitle = new SearchByTitle(library.getAllBooks());
        System.out.println("Search by Title: 'Clean'");
        for (Book book : member.searchBooks(searchByTitle, "Clean")) {
            System.out.println(book.getTitle() + " by " + book.getAuthor());
        }

        // Search by Author
        Search searchByAuthor = new SearchByAuthor(library.getAllBooks());
        System.out.println("\nSearch by Author: 'Joshua'");
        for (Book book : member.searchBooks(searchByAuthor, "Joshua")) {
            System.out.println(book.getTitle() + " by " + book.getAuthor());
        }
    }
}
