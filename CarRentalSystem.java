public class User{
  String Id;
  String Name;
  int driving License;
  public int getUserId(){
    return Id;
  }

  //getname, getdrivingLicense
  //set all methods
}

//enums
public enum VehicleType{
  Car;
}

//status
public enum status{
  active,
  inactive;
}
public class Vehicle{
  int vehicleId;
  int vehicleNumber;
  VehicleType vehicleType;
  String companyName;
  Date manifacturingDate;
  Status statu;
  int hourlyCost;
  int dailyCost;
  int noOfSeats;
  //getVehicleId, setVehicleId,
}

public class Location{
  String addres;
  int pinCode;
  String city;
  String state;
  String country;
  Location(String address, int pinCode, String city, String countruy){
    this.address=address;
    this.pinCode=pinCode;
    this.city=city;
    this.country=country;
  }
}
public enum resType{
  DAILY,
  HOURLY;
}
public enum resStatus{
  INPROGRESS,
  COMPLETED,
  SCHEDULED,
  CANCELLED;
}
public class Reservation{
  int reservationId;
  User user;
  Vehicle Vehicle;
  Date bookingDate;
  Location pickUpLoc;
  Location dropLoc;
  reservationType resType;
  ResStatus resStatus;
  Location location;
  public int createReserve(User user, Vehicle vehicle){
    //generate Id;
    reservationId = "123455";
    this.user = user;
    this.vehicle = vehicle;
    resType = resType.DAILY;
    resStatus = resStatus.SCHEDULED;

    return reservationId;

  }
}

public class Store{
  int storeId;
  Location storeLocation;
  List<Reservation> reservations;
  InventoryManagement invMgmt;

  public List<Vehicles> getVehicles(VehicleType vehicleType){
    return invMgmt.getVehicles();
  }

  //setVehicles
  //create reservation
  //complete reservation
}

public class InventoryManagement{
  List<Vehicle> vehicles;

  InventoryManagement(List<vehicle> vehicles){
    this.vehicles=vehicles;
  }

  //getvehicles, setVehicles
}

public class VehicleRentalSystem{
  List<Store> stores;
  List<User> userList;

  VehicleRentalSystem(List<Store> stores, List<User> users){
    this.stores = stores;
    this.userlist = userList;
  }

  public Store getStore(Location location){
    return stores.get(location);
  }
  //add stores, remove stores, add users, remove users
}

public class Bill{
  Reservation reservation;
  double totalBill;
  boolean isPaid;
  Bill(Reservation reservation){
    this.reservation=reservation;
    this.totalBill = computeBill();
    isPaid = false;
  }

  public computeBill(){
    return 100;
  }

  public void setPaid(){
    isPaid=true;
  }
}
public enum PaymentMode{
  cash,
  card;
}

public class Payment{
  public void payBill(){
    //process bill
  }
}
