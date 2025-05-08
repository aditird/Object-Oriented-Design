/*
Create a system that verifies custom commands written for Alexa. Each command is linked to particular words
 that activate the application. To guarantee the validity of commands, the solution will perform verification checks,
 ensuring adherence to predefined rules. In case of any violations, users will receive concise feedback indicating the
  specific rules that were breached and the words involved.
  */


public interface Rule{
  Result verify(String command);
}

//top capture validation failure

public class Result{
  private boolean isValid;
  private List<String> errors;
  public Result(boolean isValid, List<String> errors){
    this.isValid = isValid;
    this.errors = errors;
  }

  public boolean isValid(){
    return isValid;
  }

  public List<String> getErrors(){
    return errors;
  }

  public void addError(String error){
    this.errors.add(error);
  }
}
public class WakeWordRule implements Rule{
  private static final WAKE_WORD = "Alexa";

  public Result verify(String command){
    List<String> errors = new ArrayList<>();
    String[] tokens = command.trim().split("\\s");

    if(tokens.length < 2 || !tokens[0].equalsIgnoreCase(WAKE_WORD)){
      errors.add("Invalid wake word: "+(tokens.length > 0 ? tokens[0]:""))
    }
    return new Result(errors.isEmpty(), errors);
  }
}

public class VerbRule implements Rule{
  private Set<String> validVerbs;
  public VerbRule(Set<String> validVerbs){
    this.validVerbs = validVerbs;
  }
  public Result verify(String command){
    List<String> errors = new ArrayList<>();
    String[] tokens = command.trim().split("\\s+");

    if (tokens.length < 2 || !validVerbs.contains(tokens[1].toLowerCase())){
      errors.add("Invalid verb: "+tokens.length > 1 ? tokens[1] : "") + "'. Expected one of "+validVerbs);
    }
    return new Result(errors.isEmpty(), errors);
  }
}

public class CommandValidator{
  private List<Rule> rules;

  public CommandValidator(Set<String> validVerbs){
    this.rules = Arrays.asList(
    new WakeWordRule();
    new VerbRule(validVerbs);
    );
  }

  public Result validateCommand(String command){
    List<String> allErrors = new ArrayList<>();
    boolean isValid = true;

    for(Rule rule: rules){
      Result result = rule.verify(command);
      if(!result.isValid()){
        isValid = false;
        allErrors.add(result.getErrors());
      }
    }
    return new Result(isValid, allErrors);
  }
}

public class AlexaCommandChecker {

    public static void main(String[] args) {
        Set<String> verbs = new HashSet<>(Arrays.asList("play", "call", "open", "set", "turn", "start"));
        CommandValidator validator = new CommandValidator(verbs);

        testCommand(validator, "Alexa play music");
        testCommand(validator, "Alex play music");
        testCommand(validator, "Alexa table music");
        testCommand(validator, "Alexa");
        testCommand(validator, "");
    }

    private static void testCommand(CommandValidator validator, String command) {
        System.out.println("Testing: \"" + command + "\"");
        Result result = validator.validateCommand(command);
        if (result.isValid()) {
            System.out.println("✅ Valid command\n");
        } else {
            System.out.println("❌ Invalid command:");
            for (String error : result.getErrors()) {
                System.out.println(" - " + error);
            }
            System.out.println();
        }
    }
