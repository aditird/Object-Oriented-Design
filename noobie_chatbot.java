
// Message
// SendMessage
// ReceiveMessage
// ChatServer
// Connections
// 	- Create Chat
// 	- Close Connection
//	- Destroy Chat
// Chat
// 	- sendMessage
//	- receiveMessage # Pub sub
// Repository


  

public class ChatServer{
  Map<int, Connection> connections;
  Repository repo;
  public ChatServer(){
    connections = new HashMap<>();
  }
  
  public void addConnection(){
    // dynamically decide Repo
    repo = new Repository();
    Connection connection = new Connection();
    connections.put(connection.getConnectionId(), connection);
  }
  
  public void removeConnection(int connectionId){
    if(!connections.containsKey(connectionId)){
      System.out.println("Connection not found");
      return;
    }
    Connection connection = connections.get(connectionId);
    connection.closeConnection;
    connections.remove(connectionId);
  }
}

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


public class Repository{
  Map<String, String> faq;
  public Repository(){
    faq = new HashMap<>();
    addFaqs();
  }
  
  public void addFaqs(){
    faq.put("Where is Stock?", "Profile");
    faq.put("Best Stock", "GS");
    faq.put("Total earnings", "Dashboard");
  }
  
  public String getResponse(String question){
    if(faq.containsKey(question)){
      return faq.get(question);
    }
    System.out.println("Not Found");
    return "Not Found. Ask something else"
  }
}


public class Chat{
  private int chatId;
  private Repository repo;
  public Chat(Repository repo){
    this.chatId = random.nextInt();
    this.repo = repo;
  }
  
  // receives through websocket connection
  public String receiveMessage(Message receivedMessage){
    return processMessage(receivedMessage.getgetMessage())
  }
  
  public String processMessage(String message){
    return repo.getResponse(message);
  }
  
}


public class Connection{
  private int connectionId;
  private Chat chat;
  private Repository repo;
  
  public Connection(Repository repo){
    this.connectionId = random.nextInt();
    this.chat = new Chat(repo);
  }
  
  public int getConnectionId(){
    return this.connectionId; 
  }
  
  public void closeConnection(int connectionId){
    // cleanup
    this.chat = null;
  }
  
}


