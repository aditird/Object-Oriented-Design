public class MenuItem{
  private final int id;
  private final String name;
  private final String description;
  private final double price;
  private final boolean isAvailable;

  public MenuItem(int id, String name, String description, double price, boolean isAvailable){
    this.id=id;
    this.name=name;
    this.description=description;
    this.price=price;
    this.isAvailable=isAvailable;
  }

  public int getId(){

  }

  public String getName(){

  }
  public String getDescription(){

  }
  public boolean isAvailable(){
    return isAvailable;
  }
  public double getPrice(){
    return price;
  }
}

public class Order{
  private int id;
  private final List<MenuItem> items;
  private OrderStatus orderStatus;
  private double totalAmount;
  private final timeStamp;

  //constructor

  public void setStatus(){

  }
  public List<MenuItem> getItems(){

  }
  public double getTotalAmount(){

  }
  //getId, getTimeStamp
}

public enum OrderStatus{
  PENDING,
  PREPARING,
  READY,
  COMPLETED,
  CANCELLED;
}

public class Reservation{
  private final int id;
  private final String customerName;
  private final String contactNumber;
  private final int partySize;
  private final timeStamp reservationTime;

  //constructor

}

//SINGLETON CLASS (ONLY ONE INSTANCE OF THIS CLASS)
public class Restaurant{
  private static Restaurant instance;
  private final List<MenuItem> menu;
  private final Map<Integer, Order> orders;
  private final List<Reservation> reservations;
  private final List<Staff> staff;
  private final Map<Integer, Payment> payments;

  private Restaurant(){
    menu = new CopyOnWriteArrayList<>();
    orders = new ConcurrentHashMap<>();
    reservations = new CopyOnWriteArrayList<>();
    payments = new ConcurrentHashMap<>();
    staff = new CopyOnWriteArrayList<>();
  }

  public static synchronized Restaurant getInstance(){
    if(instance == null){
      intsance = new Restaurant();
    }
    return instance;
  }

  public void addMenuItem(MenuItem item){
    menu.add(item);
  }
  public void removeMenuItem(MenuItem item){
    menu.remove(item);
  }
  public List<MenuItem> getMenu(){
    return new ArrayList<>(menu);
  }
  public void placeOrder(Order order){
    orders.put(orders.getid(),order);
    notifykitchen(order);
  }
  public void updateOrderStatus(int orderId, OrderStatus status){
    Order order = orders.get(orderId);
    if(order != null){
      order.setStatus(status);
      notifyStaff(order);
    }
  }

  public void makeReservation(Reservation reservation){
    reservations.add(reservation);
  }
  //cancel reservation, addstaff, removeStaff
  public void notifykitchen(){

  }
  public void notifyStaff(){

  }
}
public class Staff{
  private final int id;
  private final String name;
  private final String role;
  private final String contactNumber;

  public Staff(int id, String name, String role, String contactNumber){

  }
}


public class Payment{
  private final int id;
  private final double amount;
  private final PaymentMethod paymentMethod;
  private final PaymentStatus status;

  //constructor

  //methods - getId, getAmount, getMethod, getStatus
}

public enum PaymentMethod{
  CASH,
  CARD,
  MOBILEPAYMENT;
}

public enum PaymentStatus{
  PENDING,
  COMPLETED,
  FAILED;
}

//main
public class RestaurantManagementDemo{
  public static void run(){
    Restaurant restaurant = Restaurant.getInstance();
    restaurant.addMenuItem(new MenuItem(1,"Burger", "very delicious burger", 9.99, true));

    //place orders

    //make a reservation

    //make a payments

    //update order status

    //add Staff

    //get menu
  }
}
