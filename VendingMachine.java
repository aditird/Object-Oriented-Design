//Idle State -> insert cash -> HasMoneyState (user can insert coin) -> select product button -> SelectionState

public class VendingMachine{
    private State VendingMachineState;
    private Inventory inventory;
    private List<Coin> coinList;
    public VendingMachine(){
        VendingMachineState = new IdleState();
        inventory = new Inventory(10);
        coinList = new ArrayList<>();
    }

    public State getVendingMachineState(){
        return vendingMachineState();
    }
    public void setVendingMachineState(State vendingMachineState){
        this.VendingMachineState=VendingMachineState;
    }

    public inventory getInventory(){
        return inventory();
    }
    public void setInventory(Inventory inventory){
        this.inventory = inventory;
    }
    public List<Coin> getCoinList(){
        return coinList;
    }

    public void setCoinList(List<coin> coinList){
        this.coinList = coinList;
    }
}


public interface State{

    public void clickOnInsertCoin(VendingMachine machine) throws Exception;

    public void clickOnStartProductSelectionButton(VendingMachine machine) throws Exception;

    public void insertCoin(VendingMachine machine, Coin coin) throws Exception;

    public void chooseproduct(VendingMachine machine, int codeNumber) throws Exception;

    public int getChange(int returnChangeMoney) throws Exception;

    public Item dispenseProduct(VendingMachine machine, int codeNumber)throws Exception;

    public List<Coin> refundFullMoney(VendingMachine machine) throws Exception;
}

public class IdleState implements State{

    public IdleState(){
        System.out.println("The vending machine is in idle state");
    }

    public void clickOnInsertCoin(VendingMachine machine){
        machine.setVendingMachineState(new HasMoneyState());
    }

    public void clickOnStartProductSelectionButton(VendingMachine machine){
        throw new Exception(" first you need to click on insert coin button");
    }
    public void insertCoin(VendingMachine machine, Coin coin){
        throw new Exception("you cannot insert coin in idle state");
    }
    //same for other methods - just throw exception
}

public class HasMoneyState implements State{

    public HasMoneyState(){
        System.out.println("The vending machine is in has money state");
    }

    public void clickOnInsertCoin(VendingMachine machine){
        return;
    }

    public void clickOnStartProductSelectionButton(VendingMachine machine){
        machine.setVendingMachineState(new SelectionState())
    }
    public void insertCoin(VendingMachine machine, Coin coin){
        throw new Exception("you cannot insert coin in idle state");
    }
    //same for other methods - just throw exception
    public List<Coin> refundFullMOney(VendingMachine machine){
        System.out.println("returned full money");
        machine.setVendingMachineState(new IdleState());
    }
}
public class SelectionState implements State{
    public SelectionState(){
        System.out.println("The vending machine is in selection state");
    }
    public void clickOnInsertCoin(VendingMachine machine){
        throw new Exception("cannot insert coin in hasMoneyState");
    }

    public void clickOnStartProductSelectionButton(VendingMachine machine){
        machine.setVendingMachineState(new SelectionState())
    }
    public void insertCoin(VendingMachine machine, Coin coin){
        throw new Exception("you cannot insert coin in idle state");
    }
    //same for other methods - just throw exception
    public List<Coin> refundFullMOney(VendingMachine machine){
        System.out.println("returned full money");
        machine.setVendingMachineState(new IdleState());
    }

    public void chooseProduct(VendingMachine machine, int codeNumber){
        Item item = machine.getInventory().getItem(code);

        //implementation
        //go to itemDispense state
    }
}

public class DispenseState implements State{
    DispenseState(VendingMachine machine, int codeNumber) throws Exception{
        System.out.println("in dispense state");
        DispenseProduct(machine, codeNumber);
    }
    public Item dispenseProduct(VendingMachine machine, int codeNumber) throws Exception{
        Item item  = machine.getInventory().getItem(codeNumber);
        machine.getInventory().updateSoldOutItem(codeNumber);
        machine.setVendingMachineState(new IdleState());
        return item;
    }
    //other methods throw exception
}

public class Item{
    ItemType type;
    int price;
    public Itemtype getType(){
        return type;
    }

    //setType, getType, setPrice
}

public class ItemShelf{
    int code;
    Item item;
    boolean soldOut;

    //getItem, setitem, isSoldOut, setSoldOut
}

public class Inventory{
    ItemShelf[] inventory = null;

    Inventory(int itemCount){
        inventory = new ItemShelf[itemCount];
    }

    //setInventory, getInventory, initialEmptyInventory, addItem, getItem, updateSoldOutItem
}

public enum ItemType{
    coke,
    pepsi,
    juice,
    soda;
}







// // Enum to define car models
// enum Model {
//     ALTROZ,
//     NEXON
// }

// // Abstract class for Car
// abstract class Car {
//     protected String engineType;
//     protected String transmission;
//     protected double price;
//     protected Model model;

//     public Car(String engineType, String transmission, double price, Model model) {
//         this.engineType = engineType;
//         this.transmission = transmission;
//         this.price = price;
//         this.model = model;
//     }
//     public abstract void displayDetails();
// }

// // Altroz class extending Car
// class Altroz extends Car {
//     public Altroz(String engineType, String transmission, double price) {
//         super(engineType, transmission, price, Model.ALTROZ);
//     }

//     @Override
//     public void displayDetails() {
//         System.out.println("Model: " + model);
//         System.out.println("Engine Type: " + engineType);
//         System.out.println("Transmission: " + transmission);
//         System.out.println("Price: $" + price);
//     }
// }
// // Nexon class extending Car
// class Nexon extends Car {
//     public Nexon(String engineType, String transmission, double price) {
//         super(engineType, transmission, price, Model.NEXON);
//     }

//     @Override
//     public void displayDetails() {
//         System.out.println("Model: " + model);
//         System.out.println("Engine Type: " + engineType);
//         System.out.println("Transmission: " + transmission);
//         System.out.println("Price: $" + price);
//     }
// }

// // Features class (optional, to encapsulate specific features)
// class Features {
//     private String color;
//     private boolean sunroof;
//     private boolean gps;

//     public Features(String color, boolean sunroof, boolean gps) {
//         this.color = color;
//         this.sunroof = sunroof;
//         this.gps = gps;
//     }

//     public void displayFeatures() {
//         System.out.println("Color: " + color);
//         System.out.println("Sunroof: " + (sunroof ? "Yes" : "No"));
//         System.out.println("GPS: " + (gps ? "Yes" : "No"));
//     }
// }

// // Main class to demonstrate usage
// public class Main {
//     public static void main(String[] args) {
//         Car altroz = new Altroz("1.2L Revotron", "Manual", 15000);
//         Car nexon = new Nexon("1.5L Revotorq", "Automatic", 22000);

//         System.out.println("Altroz Details:");
//         altroz.displayDetails();
//         System.out.println();

//         System.out.println("Nexon Details:");
//         nexon.displayDetails();
//     }
// }
