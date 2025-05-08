

public interface Operation{
  public Double performOperation(int operand1, int operand2);
}

public class AddOperation implements Operation{
  public Double performOperation(int operand1, int operand2){
    return operand1+operand2;
  }
}

public class SubtractOperation implements Operation{
  public Double performOperation(int operand1, int operand2){
    return operand1-operand2;
  }
}
public class MultipleOperation implements Operation{
  public Double performOperation(int operand1, int operand2){
    return operand1*operand2;
  }
}

public class DivideOperation implements Operation{
  public Double performOperation(int operand1, int operand2){
    if(operand2 == 0){
      throw new ArithmeticException("Cannot divide by zero");
    }
    return operand1/operand2;
  }
}

public class SquareRootOperation implements Operation{
  public Double performOperation(int operand1){
    if(operand2 == 0){
      throw new ArithmeticException("Cannot root negatives");
    }
    return Math.sqrt(operand1);
  }
}
class OperationFactory{
  private static final Map<String, Operation> operationsMap = new HashMap<>();

  static{
    operationsMap.put("+", new AddOperation());
    operationsMap.put("-", new SubtractOperation());
    operationsMap.put("*", new MultiplyOperation());
    operationsMap.put("/", new DivideOperation());
    operationsMap.put("sqrt", new SqrtOperation());
  }

  public static boolean isOperator(String token){
    return operationsMap.containsKey(token);
  }

  public static Operation getOperation(String token){
    if(!operationsMap.containsKey(token)){
      throw new UnsupportedOPerationException("Unknown operator: "+token);
    }
    return operationsMap.get(token);
  }
}

public class PostfixCalculator{

  public static int evaluatePostfix(String[] tokens){
    Stack<Integer> stack  = new Stack<>();

    for(String token: tokens){
      if(OperationFactory.isOperator(token)){
        Operation op = OperationFactory.getOperation(token);
        if(token.equals("sqrt")){
          int a = token.pop();
          result = op.performOperation(a,0);
        }else{
          int a = stack.pop();
          int b = stack.pop();
          result = op.performOperation(a,b);
        }
        stack.push(result);
      }else{
        stack.push(token);
      }
    }
    return stack.pop();

    }
  }
}
