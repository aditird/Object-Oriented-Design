




//chatbot

//requirements
//send a query
//get a resonse
//store history



//query
//reponse
//chat
//account - user info, list of chats
//repository
//Connection
//ChatBotApp

public interface Message{
  public String getMessage();
}

public class SendMessage implements Message{
  String message;
  public SendMessage(String message){
    this.message = message;
  }
  public String getMessage(){
    return this.message;
  }
}

public class ReceiveMessage implements Message{
  String message;
   public ReceiveMessage(String message){
    this.message = message;
  }
  public String getMessage(){
    return this.message;
  }
}

public class Chat{
  Map<Query, Response> chatHistory;
  String id;
  Repository repo;
  Account account;
  public Chat(Account account, Repository repo){
    this.chatHistory = new HashMap<>()
    id = random.nextInt();
    this.account = account;
    this.repo = repo
  }

  public void ReceiveMessage(Message receiveMessage){
    String response = repo.getResponse(receiveMessage.getMessage())
      Message sendMessage = new SendMessage(response)


  }

  public void SendMessage(Message sendMessage){
    System.out.println(sendMessage.getMessage())

  }
}



public class Repository{
  Map<String, String> repoMap;
  public Repository(){
    repoMap = new HashMap<>()
    repoMap.put("What is the current stock price", "123");
    repoMap.put("Where can I see my stocks", "go to profile");
  }

  public List<String> getQuestions(){
    System.out.println("These are your list of questions!")
      List<String> keyList = new ArrayList<>(map.keySet());
    return keyList;
  }

  public void addToRepo(String question, String answer){
    repoMap.put(question, answer);
  }

  public void removeFromRepo(String question, String answer){
    repoMap.remove(question);
  }

  public String getResponse(String input){
    mapRepo.getOrDefault(input, "No reponse for this input");
  }
  }
}


public class ChatBotApp{

  Repository repo;
  AccountsManager accounts;
  Connections connections;

  public static void main(){
    initialize();
    Account account = this.accounts.addAccount();
    Chat chat = new Chat(account)
    Account.addChat(chat);
    connections.addConnection("172.98.12.201",7890, account.getAccountId());

    Message sendMessage = new SendMessage("Wasssup!")
    chat.sendMessage()
  }

  public static void initialize(){
    this.repo = new Repository();
    this.accounts = AccountsManager.getInstance();
    this.connections = Connections;

  }





}
