public interface Operation{
  public double calculate(double a, double b);
}


public class Add implements Operation{
  public double calculate(double a, double b){
    return a+b;
  }
}

public class Subtract implements Operation{
  public double calculate(double a, double b){
    return a-b;
  }
}

public class Multiply implements Operation{
  public double calculate(double a, double b){
    return a*b;
  }
}

public class Divide implements Operation{
  public double calculate(double a, double b){
    if(b == 0) throw new ArithmeticException("Cannot divide by zero");
    return a/b;
  }
}

public class OperationsFactory{
  Map<String, Operation> operationMap = new HashMap<>();

  public OperationsFactory(){
    operationMap.put("+", new Add());
    operationMap.put("-", new Subtract());
    operationMap.put("*", new Multiply());
    operationMap.put("-", new Divide());
  }

  public double compute(String operator, double a, double b){
    !(operationMap.containsKey(operator)){
      System.out.println("Unsupported opeartion");
    }else{
      return operationMap.get(operator).calculate(a,b);
    }
  }
}

public class ExpressionParser{
  public double evaluate(String input, OperationsFactory opsFactory){

    String[] token= input.split(" ");
    double a = token[0];
    String operator = token[1];
    double b = token[2];

    return opsFactory.compute(opertor,a,b);
  }
}

public class Main{
  public static void main(String[] args){

    Scanner scanner = new Scanner(System.in);
    OperationsFactory opsFac = new OperationsFactory();
    ExpressionParser expParser = new ExpressionParser();

    String expression = scanner.nextLine();

    try{
      double result = expParser(expression, opsFac);
      System.out.println("Result is "+result);
    }catch(Exception e){
      System.out.prinltn("Cannot compute.");
    }

    scanner.close();
  }
}
