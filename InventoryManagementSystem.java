//product
public class product{
  int productId;
  String productName;
}

//product category
public class ProductCategory{
  int productCategoryId;
  String categoryName;
  List<Product> products = new ArrayList<>();
  double price;

public void addProduct(Product product){
  products.add(product);
}

  public void removeProduct(int count){
    for(int i=1;i<=count;i++){
      products.remove(0);
    }
  }
}


//Inventory
public class Inventory{
  List<ProductCategory> productCategoryList;

  Inventory(){
    productCategoryList = new ArrayList<>();
  }


  public void addCategory(int categoryId, String name, int prices){
    ProductCategory pc = new ProductCategory();
    pc.price= prices;
    pc.categoryName =name;
    pc.productCategoryId = categoryId;
    productCategoryList.add(ProductCategory);
  }

  public void addProduct(Product product, int productCategoryId){
    doe(ProductCategory prc = productCategoryList){
      if(prc == productCategoryId){
        //add
      }
    }
  }

  public void removeProduct(){
    //remove product
  }
}


public class Warehouse{
  Inventory inventory;
  Address address;
public void removeItemFromInventory(Map<Integer,Integer> productCategoryVsCount){

}
  //add
}

public abstract class WarehouseSelectionStrategy{
  public abstract Warehouse selectWarehouse(List<Warehouse> warehouseList);
}

public class NearestWarehouseSelectionStrategy(List<Warehouse> warehouseList){
  return warehouseList.get(0);
}

public class WarehouseController{
  List<Warehouse> warehouseList;
  WarehouseSelectionStrategy strategy = null;

  WarehouseController(List<Warehouse> warehouseList, WarehouseSelectionStrategy strategy){
    this.warehouseList = warehouseList;
    this.strategy = strategy;
  }

  //add addwarehouse

  //remove Warehouse

  public Warehouse selectWarehouse(WarehouseSelectionStrategy strategy){
    this.strategy = strategy;
    return strategy.selectWarehouse(warehouseList);
  }

}

public class user{
  int id,
  String name;
  Address address;
  Cart cart;
  List<Integer> orderids;

  public User(){
    cart = new Cart();
    orderIds = new ArrayList<>();
  }

  public Cart getUserCart(){
    return cart;
  }
}

public class UserController{
  List<user> userList;

  public UserController(){
    userList = new userList<>();
  }

  //add user, remove user, getUser
}

public class Cart{
  Map<Integer, Integer> productCategoryIdVsCount;
  Cart(){
    productCategoryIdVsCount = new HashMap<>();
  }

  public void addItemToCart(int productCategoryId, int count){
    map.put
  }

  public void removeItemFromCart(int productCategoryId, int count){

  }
}

public class Order{
  User user;
  Address address;
  Map<Integer, Integer> productCategoryIdVsCount
  Warehouse warehouse;
  Payment payment;
  Invoice invoice;
  OrderStatus status;

  Order(User user, Warehouse warehouse){
    this.user = user;
    this.productCategoryIdVsCount;
    this.warehouse = warehouse;
    this.address = address;
    invoice = new Invoice();
    invoice.generateInvoice();
  }

public void checkout(){
  warehouse.removeItemFromInventory(productCategoryIdVsCount)
  boolean isPaymentSuccess = makepayment(new CardPayment());
  if(isPaymentSuccess){
    user.getUserCart().emptyCart();
  }else{
    warehouse.addItemToInventory(productCategoryIdVsCount);
  }
}

//makepayment
//generateInvoice

}


public class OrderController{
  //createneworder

  //remove order


  //getOrderByOrderId
}



public class Payment{

  PaymentMode mode;

  Payment(PaymentMode mode){
    this.mode=mode;
  }

  public boolean makePayment(){
    return mode.makepayment();
  }
}


public interface paymentMode{
  public boolean makePayment();
}


public class CardPayment implements PaymentMode{
  public boolean makePayment(){
    System.out.println("card payment done")
  }
}
