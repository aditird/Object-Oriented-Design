//ATM States

public abstract class ATMStates{
//Different operations
  public void insertCard(ATM atm, Card card){
    System.out.println("...");
  }

  public void authenticatePin(ATM atm, Card card, int pin){
    Sysout;
  }

  public void selectOperation(ATM atm, Card card, TransactionType type){

  }
  public void cashWithdrawal(ATM atm, Card card, int amount){

  }
  public void displayBalance(ATM atm, Card card){

  }
  public void returnCard(){

  }
  public void exit(){

  }
}

public class IdleState extends ATMStates{
  public void insertCard(ATM atm, Card card){
    System.out.println("card is inserted");
    atm.setCurrentAtmState(new hasCard());
  }
}
//hasCard
public class HasCard extends ATMStates{
  public void authenticatePin(ATM atm, Card card, int pin){
    boolean isCorrectPin = card.isCorrectPin();
    if(isCorrectPin){
      atm.setCurrentAtmState(new CheckBalance());
    }else{
      exit();
    }
  }
  public void exit(ATM atm){
    atm.setCurrentAtmState(new IdleState());
  }
  public void returnCard(){

  }
}
//checkBalance State
public class CheckBalance extends ATMState{

  public void displayBalance(ATM atm, Card card){

  }
  public void returnCard(ATM atm, Card card){

  }

}
//cashWithdrwal State
public class CashWithDrawl extends ATMState{

  public void cashWithdrawal(ATM atm, Card card, int amount){
    if insufficient exit()
    else{
      card.deductBalance(amount);
      atm.deductAmount(amount);
      cashWithdrawProcessor withdrawProcessor = new TwoThousandProcessor(new FivehundredProcessor(new OneHundredProcessor(null)));
      withdrawProcessor.withdraw(atm, amount);
      exit(atm);
    }

  }

  //exit()
  //returncard()

}

public class Card{
  private int cardNum;
  private int cvv;
  private int name;
  private bankAcc bankAccount;

  public boolean isCorrectPin(){

  }
  private int getBankBalance(){

  }
  //setBankAcc
  //deductBankBalance
}

public class User{
  Card card;
  BankAcc bankAcc;
  //getCard
  //setCard
}

public class BankAcc{
  //withdraw balance()
}

public enum TransactionType{
  CASH_WITHDRAWAL;
  BALANCE_CHECK;

}

//Atm

public class Atm{
  private static ATM atmObj = new ATM();

  ATMState currentATMState;

  private int atmBalance;
  int noOfTwoThousandNotes;
  int noOfFiveHundredNotes;
  int noOfOneHundredNotes;

  private ATM(){

  }

  public void setCurrentAtmState(ATMState currentATMState){
    this.currentATMState;
  }

  public static ATM getATMObject{
    atmObj.setCurrentAtmState(new IdleState());
    return atmObj;

  }

  //setatmbalance
  //getnooftwothousandnotes

}


public abstract class cashWithdrawProcessor{
  cashWithdrawProcessor nextCashWithProcessor;
  CashWithdrawProcessor(cashWithdrawProcessor cashprs){
    this.nextCashWithProcessor = cashprs;
  }

  public void withdraw(ATM atm, int amount){
    if(nextCashWithProcessor != null){
      nextCashWithProcessor.withdraw(atm, amount);
    }
  }
}

public class TwoThousandProcessor extends CashWithdrawProcessor{
  public TwoThousandProcessor(CashWithdrawProcessor nextCashWithProcessor){
    super(nextCashWithProcessor);
  }

  public void withdraw(ATM atm, int amount){
    //check Balance
    //if balance is still left
    if(balance != 0){
      super.withdraw(atm, balance);
    }
  }
}

public class FourThousandProcessor extends CashWithdrawProcessor{
  public FourThousandProcessor(CashWithdrawProcessor nextCashWithProcessor){
    super(nextCashWithProcessor);
  }

  public void withdraw(ATM atm, int amount){
    //check Balance


    //if balance is still left
    if(balance != 0){
      super.withdraw(atm, balance);
    }
  }
}

public class OneHundredProcessor extends CashWithdrawProcessor{
  public OneHundredProcessor(CashWithdrawProcessor nextCashWithProcessor){
    super(nextCashWithProcessor);
  }

  public void withdraw(ATM atm, int amount){
    //check Balance


    //if balance is still left
    if(balance != 0){
      super.withdraw(atm, balance);
    }
  }
}


//ATM Room (main)

public class ATMRoom{
  public static void main(String[] args){
    ATMRoom room = new ATMRoom();

    room.Initialize();
  }

  public void Initialize(){
    //getATMObject
    //atm.setBalance
  }

  private User createUser(){

  }

  //createBankAccount
}
