public abstract class Employee{
  private Call currentCall = null;
  protected Rank rank;

  public Employee(CallHandler handler){

  }

  public void receiveCall(Call call){

  }

  public void callCompleted(){

  }

  public boolean assignCall(){

  }

  public boolean isFree(){
    return currentCall == null;
  }

  public void escalateAndReassign(){

  }

  public Rank getRank(){

  }
}
class Director extends Employee{
  public Director(){
    rank = Rank.Director;
  }
}

class Manager extends Employee{
  public Manager(){
    rank = rank.Manager;
  }
}

class Respondant extends Employee{
  public Respondant(){
    rank = rank.Respondant;
  }
}
public class Call{
  private Rank rank;
  private Caller caller;
  private Employee handler;
  public Call(caller c){
    this.caller = c;
    rank = rank.Responder;
  }

  public void setHandler(Employee e){
    handler = e;
  }

  //reply, getRank, setRank, incrementRank, disconnect
}

public class CallHandler{
  private final int levels = 3;

  private final int num_respondants = 10;
  private final int num_managers = 4;
  private final int num_directors = 2;

  List<List<Employee>> employeeLevels;
  List<List<Call>> callQueues;

  public CallHandler(){

  }

  public Employee getHandlerForCall(Call call){

  }

  public void dispatchCall(Caller caller){
    Call call = new Call(caller);
    dispatchCall(call);
  }

  public void dispatchCall(Call call){
    Employee e = getHandlerForCall(call);
    if(e != null){
      call.receiveCall(call);
    }else{
      callQueues.get(call.getRank().getValue().add(call))
    }

  }


}
