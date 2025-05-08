public enum driverStatus {
    available, busy;
}

public enum RideStatus {
    completed, ongoing, notstarted, cancelled
}

// Account
public abstract class Account {
    String id;
    String name;
    String homeAddress;
    Integer phoneNumber;
    Integer ratings;

    public Account(String id, String name, String address, Integer phoneNumber) {
        this.id = id;
        this.name = name;
        this.homeAddress = address;
        this.phoneNumber = phoneNumber;
        this.ratings = 0;
    }

    public String getName() {
        return name;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }
}

// User
public class User extends Account {
    public User(String id, String name, String address, Integer phoneNumber) {
        super(id, name, address, phoneNumber);
    }
}

// Driver
public class Driver extends Account {
    Vehicle vehicle;
    driverStatus status;

    public Driver(String id, String name, String address, Integer phoneNumber, Vehicle vehicle) {
        super(id, name, address, phoneNumber);
        this.vehicle = vehicle;
        this.status = driverStatus.available;
    }

    public void setStatus(driverStatus status) {
        this.status = status;
    }

    public driverStatus getStatus() {
        return status;
    }

    public String getLocation() {
        return "DummyLocation"; // Simulated location
    }
}

// DriverManager
public class DriverManager {
    List<Driver> drivers;
    MatchDriverStrategy strategy;

    public DriverManager(MatchDriverStrategy strategy) {
        this.drivers = new ArrayList<>();
        this.strategy = strategy;
    }

    public List<Driver> getListOfDrivers() {
        return drivers;
    }

    public void addDriver(Driver driver) {
        drivers.add(driver);
    }

    public void removeDriver(Driver driver) {
        drivers.remove(driver);
    }

    public Driver getDriver(Location loc) {
        return strategy.assignDriver(drivers, loc);
    }
}

// MatchDriverStrategy
public interface MatchDriverStrategy {
    Driver assignDriver(List<Driver> drivers, Location loc);
}

// NearestDriverAlgo
public class NearestDriverAlgo implements MatchDriverStrategy {
    public Driver assignDriver(List<Driver> drivers, Location loc) {
        // Assign nearest driver (dummy logic for now)
        for (Driver driver : drivers) {
            if (driver.getStatus() == driverStatus.available) {
                return driver;
            }
        }
        return null; // No driver available
    }
}

// RatingsAlgo
public class RatingsAlgo implements MatchDriverStrategy {
    public Driver assignDriver(List<Driver> drivers, Location loc) {
        // Assign highest-rated driver
        Driver bestDriver = null;
        int maxRatings = -1;
        for (Driver driver : drivers) {
            if (driver.getStatus() == driverStatus.available && driver.ratings > maxRatings) {
                maxRatings = driver.ratings;
                bestDriver = driver;
            }
        }
        return bestDriver;
    }
}

// Ride
public class Ride {
    User user;
    Driver driver;
    Location fromLoc;
    Location toLoc;
    Invoice invoice;
    RideStatus rideStatus;
    Payment payment;

    public Ride(User user, Driver driver, Location fromLoc, Location toLoc, Invoice invoice, Payment payment) {
        this.user = user;
        this.driver = driver;
        this.fromLoc = fromLoc;
        this.toLoc = toLoc;
        this.invoice = invoice;
        this.payment = payment;
        this.rideStatus = RideStatus.notstarted;
    }

    public void startRide() {
        driver.setStatus(driverStatus.busy);
        rideStatus = RideStatus.ongoing;
        System.out.println("Ride started.");
    }

    public void endRide() {
        driver.setStatus(driverStatus.available);
        rideStatus = RideStatus.completed;
        System.out.println("Ride completed.");
    }
}

// Location
public class Location {
    double latitude;
    double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

// Invoice
public class Invoice {
    User user;
    Driver driver;
    double cost;

    public Invoice(User user, Driver driver, double cost) {
        this.user = user;
        this.driver = driver;
        this.cost = cost;
    }
}

// Payment
public interface Payment {
    void pay(double amount);
}

// CardPayment
public class CardPayment implements Payment {
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using Card.");
    }
}

// Paypal
public class Paypal implements Payment {
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using PayPal.");
    }
}

// AlgorithmFactory
public class AlgorithmFactory {
    public static MatchDriverStrategy createAlgorithm(String algorithm) {
        if ("nearest driver".equalsIgnoreCase(algorithm)) {
            return new NearestDriverAlgo();
        } else if ("ratings".equalsIgnoreCase(algorithm)) {
            return new RatingsAlgo();
        }
        throw new IllegalArgumentException("Invalid algorithm type");
    }
}

// AppManager (Singleton)
public class AppManager {
    private static AppManager instance;
    private final DriverManager driverManager;

    private AppManager(String algorithmType) {
        MatchDriverStrategy strategy = AlgorithmFactory.createAlgorithm(algorithmType);
        driverManager = new DriverManager(strategy);
    }

    public static synchronized AppManager getInstance(String algorithmType) {
        if (instance == null) {
            instance = new AppManager(algorithmType);
        }
        return instance;
    }

    public DriverManager getDriverManager() {
        return driverManager;
    }
}

// Main
public class Main {
    public static void main(String[] args) {
        AppManager manager = AppManager.getInstance("nearest driver");
        DriverManager driverManager = manager.getDriverManager();

        Driver driver1 = new Driver("D1", "John", "Street 1", 12345, null);
        Driver driver2 = new Driver("D2", "Doe", "Street 2", 67890, null);

        driverManager.addDriver(driver1);
        driverManager.addDriver(driver2);

        User user = new User("U1", "Alice", "Street 3", 11223);
        Location userLoc = new Location(12.34, 56.78);

        Driver assignedDriver = driverManager.getDriver(userLoc);

        if (assignedDriver != null) {
            System.out.println("Driver assigned: " + assignedDriver.getName());
            Ride ride = new Ride(user, assignedDriver, userLoc, new Location(23.45, 67.89), new Invoice(user, assignedDriver, 100.0), new CardPayment());
            ride.startRide();
            ride.endRide();
        } else {
            System.out.println("No driver available.");
        }
    }
}


/*
IMPORTANT POINTS

To handle concurrent ride requests, I would first ensure that the driver assignment
logic is thread-safe to avoid race conditions. For instance, I could use
a distributed lock (e.g., using Zookeeper or Redis) to ensure that a driver is
not assigned to multiple rides simultaneously.

On the database side, I would implement partitioning to handle large-scale reads and writes efficiently.
For real-time scalability, the system would use microservices, with each service independently deployed and scaled.
For example, a dedicated Ride Service would handle ride requests, while a Driver Service would manage driver availability.
 Communication between these services would be facilitated using a message queue like Kafka to ensure smooth processing
 under heavy load.

 To integrate real-time location tracking, I would use WebSockets for continuous communication between the server and
  the driver/rider apps. This ensures low-latency updates for both parties. On the backend, I would store driver locations
  in a Redis instance with geo-spatial indexing, allowing the system to quickly find the nearest drivers.

The frontend would use a library like Google Maps to visualize movement, with driver locations updated based on deltas
 rather than full coordinates to minimize bandwidth usage. This system could also integrate alerts for nearby drivers
 when a new ride request is initiated.

 To make the design extensible, I would use modular interfaces like PricingStrategy and RideType. For example, PricingStrategy
 could have implementations for flat-rate pricing, time-based pricing, and surge pricing, which can be swapped dynamically. For r
 ide-sharing, I would extend the Ride class to a SharedRide class that maintains a list of riders and implements logic for
 splitting fares and matching destinations.

To incorporate driver preferences, the Driver class would have fields like preferredDistance and preferredRiderTypes.
These preferences would be factored into the driver-matching algorithm, making it more personalized. Finally, I’d use
feature toggles to roll out these changes incrementally, ensuring smooth adoption.


*/
