import java.util.ArrayList;
import java.util.List;

// Enums
enum VehicleType { TwoWheeler, FourWheeler }

// Vehicle class
class Vehicle {
    int vehicleNo;
    VehicleType vehicleType;

    Vehicle(int vehicleNo, VehicleType vehicleType) {
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
    }
}

// ParkingSpot class
class ParkingSpot {
    int id;
    boolean isEmpty;
    Vehicle vehicle;
    int price;

    ParkingSpot(int id, int price) {
        this.id = id;
        this.isEmpty = true;
        this.vehicle = null;
        this.price = price;
    }

    void parkVehicle(Vehicle v) {
        this.vehicle = v;
        this.isEmpty = false;
    }

    void removeVehicle() {
        this.vehicle = null;
        this.isEmpty = true;
    }
}

// ParkingSpotManager class
abstract class ParkingSpotManager {
    List<ParkingSpot> spots;

    ParkingSpotManager(List<ParkingSpot> spots) {
        this.spots = spots;
    }

    abstract ParkingSpot findParkingSpace();

    void parkVehicle(Vehicle v) {
        ParkingSpot spot = findParkingSpace();
        if (spot != null) {
            spot.parkVehicle(v);
        }
    }

    void removeVehicle(Vehicle v) {
        for (ParkingSpot spot : spots) {
            if (spot.vehicle != null && spot.vehicle.equals(v)) {
                spot.removeVehicle();
                break;
            }
        }
    }
}

// TwoWheelerManager and FourWheelerManager classes
class TwoWheelerManager extends ParkingSpotManager {
    TwoWheelerManager(List<ParkingSpot> spots) {
        super(spots);
    }

    @Override
    ParkingSpot findParkingSpace() {
        for (ParkingSpot spot : spots) {
            if (spot.isEmpty) {
                return spot;
            }
        }
        return null;
    }
}

class FourWheelerManager extends ParkingSpotManager {
    FourWheelerManager(List<ParkingSpot> spots) {
        super(spots);
    }

    @Override
    ParkingSpot findParkingSpace() {
        for (ParkingSpot spot : spots) {
            if (spot.isEmpty) {
                return spot;
            }
        }
        return null;
    }
}

// Ticket class
class Ticket {
    long entryTime;
    ParkingSpot parkingSpot;
    Vehicle vehicle;

    Ticket(long entryTime, ParkingSpot parkingSpot, Vehicle vehicle) {
        this.entryTime = entryTime;
        this.parkingSpot = parkingSpot;
        this.vehicle = vehicle;
    }
}

// EntranceGate class
class EntranceGate {
    ParkingSpotManager manager;

    EntranceGate(ParkingSpotManager manager) {
        this.manager = manager;
    }

    Ticket processEntry(Vehicle vehicle) {
        ParkingSpot spot = manager.findParkingSpace();
        if (spot != null) {
            manager.parkVehicle(vehicle);
            return new Ticket(System.currentTimeMillis(), spot, vehicle);
        }
        return null;
    }
}

// ExitGate class
class ExitGate {
    ParkingSpotManager manager;

    ExitGate(ParkingSpotManager manager) {
        this.manager = manager;
    }

    void processExit(Ticket ticket) {
        manager.removeVehicle(ticket.vehicle);
    }
}

public class Main {
    public static void main(String[] args) {
        // Initialize parking spots
        List<ParkingSpot> twoWheelerSpots = new ArrayList<>();
        List<ParkingSpot> fourWheelerSpots = new ArrayList<>();

        for (int i = 1; i <= 50; ++i) {
            twoWheelerSpots.add(new ParkingSpot(i, 10));
        }
        for (int i = 51; i <= 100; ++i) {
            fourWheelerSpots.add(new ParkingSpot(i, 20));
        }

        // Create managers
        TwoWheelerManager twoWheelerManager = new TwoWheelerManager(twoWheelerSpots);
        FourWheelerManager fourWheelerManager = new FourWheelerManager(fourWheelerSpots);

        // Create EntranceGate and ExitGate objects
        EntranceGate entranceGate = new EntranceGate(twoWheelerManager);
        ExitGate exitGate = new ExitGate(twoWheelerManager);

        // Example usage
        Vehicle vehicle = new Vehicle(123, VehicleType.TwoWheeler);
        Ticket ticket = entranceGate.processEntry(vehicle);

        if (ticket != null) {
            System.out.println("Vehicle parked at spot: " + ticket.parkingSpot.id);
        } else {
            System.out.println("No available parking spot.");
        }

        // Vehicle leaves
        exitGate.processExit(ticket);
        System.out.println("Vehicle exited.");
    }
}
