// Strategy for Load Balancing Algorithms
public interface LoadBalancingAlgorithm {
    Server getServer(List<Server> servers);
}

public class RoundRobin implements LoadBalancingAlgorithm {
    private int currIndex = -1;

    @Override
    public Server getServer(List<Server> servers) {
        if (servers.isEmpty()) return null;
        currIndex = (currIndex + 1) % servers.size();
        return servers.get(currIndex);
    }
}

public class LeastConnectionsAlgorithm implements LoadBalancingAlgorithm {
    @Override
    public Server getServer(List<Server> servers) {
        return servers.stream()
                .min(Comparator.comparingInt(Server::getCurrentLoad))
                .orElse(null);
    }
}

// Interface for Load Balancer
public interface ILoadBalancer {
    void handleRequest(Request request);
    void addServer(Server server);
    void removeServer(Server server);
    void setAlgorithm(LoadBalancingAlgorithm algorithm);
}

// Load Balancer Implementation
public class LoadBalancer implements ILoadBalancer {
    private final List<Server> servers = new ArrayList<>();
    private LoadBalancingAlgorithm algorithm;

    public LoadBalancer(LoadBalancingAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public void handleRequest(Request request) {
        Server server = algorithm.getServer(servers);
        if (server != null) {
            server.forwardRequest(request);
        } else {
            System.out.println("No servers available to handle the request: " + request.getId());
        }
    }

    @Override
    public void addServer(Server server) {
        servers.add(server);
    }

    @Override
    public void removeServer(Server server) {
        servers.remove(server);
    }

    @Override
    public void setAlgorithm(LoadBalancingAlgorithm algorithm) {
        this.algorithm = algorithm;
    }
}

// Algorithm Factory
public class AlgorithmFactory {
    public static LoadBalancingAlgorithm createAlgorithm(String algorithmType) {
        switch (algorithmType) {
            case "RoundRobin":
                return new RoundRobin();
            case "LeastConnections":
                return new LeastConnectionsAlgorithm();
            default:
                throw new IllegalArgumentException("Invalid algorithm type: " + algorithmType);
        }
    }
}

// Health Checker
public class HealthChecker {
    private final List<Server> servers;

    public HealthChecker(List<Server> servers) {
        this.servers = servers;
    }

    public void monitor() {
        for (Server server : servers) {
            if (!ping(server)) {
                server.setStatus("DOWN");
            } else {
                server.setStatus("UP");
            }
        }
    }

    private boolean ping(Server server) {
        // Simulated ping logic
        return Math.random() > 0.2; // 80% chance server is up
    }
}

// Load Balancer Manager (Singleton)
public class LoadBalancerManager {
    private static LoadBalancerManager instance;
    private final ILoadBalancer loadBalancer;

    private LoadBalancerManager(String algorithmType) {
        LoadBalancingAlgorithm algorithm = AlgorithmFactory.createAlgorithm(algorithmType);
        loadBalancer = new LoadBalancer(algorithm);
    }

    public static synchronized LoadBalancerManager getInstance(String algorithmType) {
        if (instance == null) {
            instance = new LoadBalancerManager(algorithmType);
        }
        return instance;
    }

    public ILoadBalancer getLoadBalancer() {
        return loadBalancer;
    }
}

// Test Classes
class Server {
    private final String id;
    private int currentLoad;
    private String status;

    public Server(String id) {
        this.id = id;
        this.currentLoad = 0;
        this.status = "UP";
    }

    public void forwardRequest(Request request) {
        currentLoad++;
        System.out.println("Request " + request.getId() + " forwarded to server " + id);
    }

    public int getCurrentLoad() {
        return currentLoad;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

class Request {
    private final String id;

    public Request(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

// Main Class to Test the Implementation
public class Main {
    public static void main(String[] args) {
        // Initialize Load Balancer with RoundRobin Algorithm
        LoadBalancerManager manager = LoadBalancerManager.getInstance("RoundRobin");
        ILoadBalancer loadBalancer = manager.getLoadBalancer();

        // Add Servers
        Server server1 = new Server("Server1");
        Server server2 = new Server("Server2");
        loadBalancer.addServer(server1);
        loadBalancer.addServer(server2);

        // Handle Requests
        for (int i = 1; i <= 5; i++) {
            Request request = new Request("Request" + i);
            loadBalancer.handleRequest(request);
        }

        // Change Algorithm to LeastConnections
        loadBalancer.setAlgorithm(new LeastConnectionsAlgorithm());
        System.out.println("\nSwitched to LeastConnections Algorithm\n");

        // Handle Requests Again
        for (int i = 6; i <= 10; i++) {
            Request request = new Request("Request" + i);
            loadBalancer.handleRequest(request);
        }
    }
}
