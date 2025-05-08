import java.util.*;

// Enum for Elevator State
enum ElevatorState {
    MOVING_UP, MOVING_DOWN, IDLE, MAINTENANCE
}

// Enum for Direction
enum Direction {
    UP, DOWN, NONE
}

// Request class
class Request {
    int floor;
    Direction direction;

    public Request(int floor, Direction direction) {
        this.floor = floor;
        this.direction = direction;
    }
}

// Elevator class
class Elevator {
    private int id;
    private ElevatorState state;
    private Direction direction;
    private int currentFloor;
    private List<Request> requests;

    public Elevator(int id) {
        this.id = id;
        this.currentFloor = 0; // Assume ground floor
        this.state = ElevatorState.IDLE;
        this.direction = Direction.NONE;
        this.requests = new ArrayList<>();
    }

    // Move elevator based on requests
    public void move() {
        if (!requests.isEmpty()) {
            Request req = requests.get(0);
            if (req.floor > currentFloor) {
                state = ElevatorState.MOVING_UP;
            } else {
                state = ElevatorState.MOVING_DOWN;
            }
            currentFloor = req.floor;
            requests.remove(0);
            state = ElevatorState.IDLE;
        }
    }

    public void addRequest(Request request) {
        requests.add(request);
    }

    public boolean isAvailable() {
        return state == ElevatorState.IDLE;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }
}

// Strategy Pattern Interface
interface SelectElevatorStrategy {
    Elevator selectElevator(Request request, List<Elevator> elevators);
}

// Nearest Elevator Strategy
class NearestElevatorStrategy implements SelectElevatorStrategy {
    @Override
    public Elevator selectElevator(Request request, List<Elevator> elevators) {
        Elevator nearestElevator = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            int distance = Math.abs(elevator.getCurrentFloor() - request.floor);
            if (elevator.isAvailable() && distance < minDistance) {
                minDistance = distance;
                nearestElevator = elevator;
            }
        }
        return nearestElevator;
    }
}

// Least Busy Elevator Strategy (TODO: Implement logic)
class LeastBusyElevator implements SelectElevatorStrategy {
    @Override
    public Elevator selectElevator(Request request, List<Elevator> elevators) {
        Elevator leastBusyElevator = null;
        int minRequests = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            int requestCount = elevator.requests.size();
            if (elevator.isAvailable() && requestCount < minRequests) {
                minRequests = requestCount;
                leastBusyElevator = elevator;
            }
        }
        return leastBusyElevator;
    }
}

// Singleton Pattern for Elevator Controller
class ElevatorController {
    private static ElevatorController instance;
    private List<Elevator> elevators;
    private SelectElevatorStrategy strategy;

    private ElevatorController(SelectElevatorStrategy strategy, int numElevators) {
        this.strategy = strategy;
        this.elevators = new ArrayList<>();
        for (int i = 0; i < numElevators; i++) {
            elevators.add(new Elevator(i));
        }
    }

    public static synchronized ElevatorController getInstance(SelectElevatorStrategy strategy, int numElevators) {
        if (instance == null) {
            instance = new ElevatorController(strategy, numElevators);
        }
        return instance;
    }

    public Elevator requestElevator(int floor, Direction direction) {
        Request request = new Request(floor, direction);
        Elevator elevator = strategy.selectElevator(request, elevators);
        if (elevator != null) {
            elevator.addRequest(request);
            return elevator;
        } else {
            System.out.println("No elevator available");
            return null;
        }
    }
}

// Test Case
public class ElevatorSystemTest {
    public static void main(String[] args) {
        // Initialize elevator controller with 3 elevators using Nearest Elevator Strategy
        ElevatorController controller = ElevatorController.getInstance(new NearestElevatorStrategy(), 3);

        // Simulate elevator requests
        controller.requestElevator(5, Direction.UP);
        controller.requestElevator(2, Direction.DOWN);
        controller.requestElevator(7, Direction.UP);
    }
}
