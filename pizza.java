// Pizza Interface
public interface Pizza {
    int cost();
    String description();
}

public class DeluxePizza implements Pizza {
    public int cost() { return 20; }
    public String description() { return "Deluxe Pizza (Cheese, Olives, Jalapenos)"; }
}

public class FarmhousePizza implements Pizza {
    public int cost() { return 18; }
    public String description() { return "Farmhouse Pizza (Cheese, Mushrooms, Corn)"; }
}

public class Margherita implements Pizza {
    public int cost() { return 12; }
    public String description() { return "Margherita"; }
}

public abstract class ToppingDecorator implements Pizza {
    protected Pizza pizza;
    public ToppingDecorator(Pizza pizza) { this.pizza = pizza; }
}

public class OliveTopping extends ToppingDecorator {
    public OliveTopping(Pizza pizza) { super(pizza); }
    public int cost() { return pizza.cost() + 5; }
    public String description() { return pizza.description() + " + Olive"; }
}

public class SpinachTopping extends ToppingDecorator {
    public SpinachTopping(Pizza pizza) { super(pizza); }
    public int cost() { return pizza.cost() + 4; }
    public String description() { return pizza.description() + " + Spinach"; }
}

public abstract class SizeDecorator implements Pizza {
    protected Pizza pizza;
    public SizeDecorator(Pizza pizza) { this.pizza = pizza; }
}

public class SmallSize extends SizeDecorator {
    public SmallSize(Pizza pizza) { super(pizza); }
    public int cost() { return pizza.cost() + 6; }
    public String description() { return pizza.description() + " (Small)"; }
}

public class MediumSize extends SizeDecorator {
    public MediumSize(Pizza pizza) { super(pizza); }
    public int cost() { return pizza.cost() + 8; }
    public String description() { return pizza.description() + " (Medium)"; }
}

public class LargeSize extends SizeDecorator {
    public LargeSize(Pizza pizza) { super(pizza); }
    public int cost() { return pizza.cost() + 10; }
    public String description() { return pizza.description() + " (Large)"; }
}

public class Side {
    private String name;
    private int cost;

    public Side(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() { return name; }
    public int getCost() { return cost; }
}

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Pizza> pizzas = new ArrayList<>();
    private List<Side> sides = new ArrayList<>();

    private Order() {}

    public int getTotal() {
        int total = 0;
        for (Pizza p : pizzas) total += p.cost();
        for (Side s : sides) total += s.getCost();
        return total;
    }

    public void printOrder() {
        for (Pizza p : pizzas) System.out.println("Pizza: " + p.description() + " -> $" + p.cost());
        for (Side s : sides) System.out.println("Side: " + s.getName() + " -> $" + s.getCost());
        System.out.println("Total Bill: $" + getTotal());
    }

    public static class Builder {
        private Order order = new Order();

        public Builder addPizza(Pizza pizza) {
            order.pizzas.add(pizza);
            return this;
        }

        public Builder addSide(Side side) {
            order.sides.add(side);
            return this;
        }

        public Order build() {
            return order;
        }
    }
}
Example Usage:

java
Copy
Edit
public class Main {
    public static void main(String[] args) {

        // Custom Pizza Build
        Pizza customPizza = new LargeSize(
                                new OliveTopping(
                                    new SpinachTopping(
                                        new Margherita()
                                    )
                                )
                             );

        // Fixed Topping Pizza
        Pizza fixedPizza = new DeluxePizza();

        // Build Order
        Order myOrder = new Order.Builder()
                .addPizza(customPizza)
                .addPizza(fixedPizza)
                .addSide(new Side("Garlic Bread", 7))
                .addSide(new Side("Coke", 3))
                .build();

        myOrder.printOrder();
    }
}





public abstract class BasePizza{
    public abstract int cost();
}

public class Margherita extends BasePizza{
    public int cost(){
        return 12;
    }
}

public class GardenVeggie extends BasePizza{
    public int cost(){
        return 14;
    }
}

public class Pepperoni extends BasePizza{
    public int cost(){
        return 10;
    }
}


public abstract class ToppingDecorator extends BasePizza{
    BasePizza basePizza;
    public ToppingDecorator(BasePizza basePizza){
        this.basePizza=basePizza;
    }
}

public class OliveTopping extends ToppingDecorator{
    public OliveTopping(BasePizza basePizza){
        super(basePizza);
    }
    public int cost(){
        return this.basePizza.cost()+5;
    }
}


public class SpinachTopping extends ToppingDecorator{
    public SpinachTopping(BasePizza basePizza){
        super(basePizza);
    }

    public int cost(){
        return this.basePizza.cost()+4;
    }
}

public abstract class SizeDecorator extends BasePizza{
    public SizeDecorator(BasePizza basePizza){
        this.basePizza=basePizza;
    }
}

public class SmallSize entends SizeDecorator{
    public SmallSize(BasePizza basePizza){
        super(basePizza);
    }
    public int cost(){
        return this.basePizza.cost()+6;
    }
}

public class MediumSize extends SizeDecorator{
    public MediumSize(BasePizza basePizza){
        super(basePizza);
    }
    public int cost(){
        return this.basePizza.cost()+8;
    }
}

public class LargeSize entends SizeDecorator{
    public LargeSize(BasePizza basePizza){
        super(basePizza);
    }
    public int cost(){
        return this.basePizza.cost()+10;
    }
}

public class Main{
    public static void main(String[] args){
        BasePizza basePizza = new LargeSize(new OliveTopping(new GardenVeggie()));
        System.out.println(basePizza.cost());
    }
}
