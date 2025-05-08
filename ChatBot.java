import java.util.*;

// --- 1. Query Class ---
public class Query {
    private String message;

    public Query(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

// --- 2. Response Class ---
public class Response {
    private String message;

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

// --- 3. PredefinedResponseRepository Class ---
public class PredefinedResponseRepository {
    private Map<String, String> responses;

    public PredefinedResponseRepository() {
        responses = new HashMap<>();
        responses.put("Hello", "Hi there! How can I help you?");
        responses.put("What is your name?", "I'm a simple chatbot.");
        responses.put("How are you?", "I'm just a bot, but I'm doing well!");
        responses.put("What is the weather like?", "It's sunny today!");
        responses.put("Goodbye", "Goodbye! Have a nice day!");
    }

    // Get response based on the user's query
    public Response getResponse(Query query) {
        String responseMessage = responses.getOrDefault(query.getMessage(), "Sorry, I don't understand that.");
        return new Response(responseMessage);
    }
}

// --- 4. ChatbotEngine Class ---
public class ChatbotEngine {
    private PredefinedResponseRepository responseRepository;

    public ChatbotEngine(PredefinedResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }

    // Process the query and return a response
    public Response processQuery(Query query) {
        return responseRepository.getResponse(query);
    }
}

// --- 5. ChatBotApp Class (Main Application) ---
public class ChatBotApp {
    private ChatbotEngine chatbotEngine;

    public ChatBotApp() {
        PredefinedResponseRepository repository = new PredefinedResponseRepository();
        this.chatbotEngine = new ChatbotEngine(repository);
    }

    // Start a conversation with the chatbot
    public void startChat(String queryMessage) {
        Query query = new Query(queryMessage);
        Response response = chatbotEngine.processQuery(query);
        System.out.println("User: " + queryMessage);
        System.out.println("Bot: " + response.getMessage());
    }
}

// --- 6. Main Class (Entry Point) ---
public class Main {
    public static void main(String[] args) {
        // Initialize the chatbot app
        ChatBotApp chatbotApp = new ChatBotApp();

        // Start a conversation with the chatbot
        chatbotApp.startChat("Hello");
        chatbotApp.startChat("What is your name?");
        chatbotApp.startChat("How are you?");
        chatbotApp.startChat("What is the weather like?");
        chatbotApp.startChat("Goodbye");
        chatbotApp.startChat("What is your favorite color?");
    }
}

////////////////////////////ORRRRRR///////////////////////////////////

//chatbot class - main class that interacts with user.
//NLPProcessor - detects intent from user.
//Response repository - stored predefined responses.
//message handler - Handles the flow of messages.

//NLPProcessor - extracts intent from input.

interface IntentExtractor{
  String extractIntent(String message);
}
public class NLPProcessor extends IntentExtractor{
  public String extractIntent(String message){
    if(message.toLowerCase().contains("stock")){
      return "stock_price";
    }else if(message.toLowerCase().contains("invest")){
      return "investment_advice";
    }
  }
}



interface ResponseProvider{
  String getResponse(String intent);
}
//Response repository - stores predefined responses.
public class ResponseRepository extends ResponseProvider{
  private final Map<String, String> responseMap;
  private static ResponseRepository instance;

  public ResponseRepository(){
    responseMap = new HashMap<>();
    responseMap.put("stock_price", "Stock price not available!");
    responseMap.put("invest", "Consult a professional");
  }

  public static ResponseRepository getInstance(){
    if(instance == null){
      synchronized(ResponseRepository.class){
        if(instance == null){
          intance = new ResponseRepository();
        }
      }
    }
  }
  public String getResponse(String intent){
    return responseMap.getOrDefault(intent, "I'm sorry I don't understand :( ");
  }
}


//MessageHandler - orchestrates message processing.
public class MessageHandler{
  private final IntentExtractor nlpProcessor;
  private final ResponseProvider responseRespository;
  public MessageHandler(IntentExtractor nlpProcessor, ResponseProvider responseRespository){
    this.nlpProcessor = nlpProcessor;
    this.responseRespository  = responseRespository;
  }

  public String processMessage(String message){
    String intent = nlpProcessor.extractIntent(message);
    return ResponseRepository.getResponse(intent);
  }
}

//chatbot class - main class that interacts with the user.
public class Chatbot{
  private final MessageHandler messageHandler;
  public Chatbot(){
    this.messageHandler = new MessageHandler(new NLPProcessor(), ResponseRepository.getInstance());
  }

  public void start(){
    Sacnner scanner = new Scanner(System.in);
    System.out.println("Welcome to chatbot! Type your Query! (Type exit when you want to leave this chatbot)");

    while(true){
      String input = scanner.nextLine();
      if(input.equalsIgnoreCase("exit")){
        System.out.println("Goodbye!");
        break;
      }

      System.out.println("Chatbot: "+ messageHandler.processMessage(input));
    }
    scanner.close();
  }
}

//You may combine multiple services. For example:
//Use RabbitMQ for handling user-specific message queues (e.g., chatbot conversations).
//Use Kafka for streaming real-time financial data and integrating with backend systems.
//Use SQS or Google Pub/Sub for message queuing across different modules (e.g., event-driven workflows like user notifications).
