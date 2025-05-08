public class InstanceType{
  String type;
  int generation;

  public InstanceType(String raw){
    this.type = raw.substring(0,i);
    this.generation = Integer.parseInt(raw.substring(i));
  }
  public String toString(){
    return type+generation;
  }
}

public interface DiscountRule{
  String getName();
  boolean matches(List<InstanceType> instances);
}


public class Compute5Plus implements DiscountRule{
  public String getName(){
    return "Compute5Plus";
  }
  public boolean macthes(List<InstanceType> instances){
    for(InstanceType inst: instances){
      if (!inst.generation >= 5 && (inst.type.equals("m")) || inst.type.equals("c")){
        return false;
      }
    }
      return true;
  }
}


public class GeneralCompute456 implements DiscountRule{
  public String getName(){
    return "GeneralCompute456";
  }

  public boolean matches(List<intstanceType> instances){
    if(instances.size() != 3) return false;

    Set<String> expected = new HashSet<>(Arrays.asList("m4","m5","m6"));
    Set<String> actual = new HashSet<>();

    for(InstanceType inst: instances){
      actual.add(inst.toString());
    }
    return actual.equals(expected);
  }
}

public class SameInstanceType implements DiscountRule{
  public String getName(){
    return "SameInstanceType";
  }

  public boolean matches(List<InstanceType> instances){
    if(instances.isEmpty()) return false;
    String firstType = instances.get(0).type;
    for (InstanceType inst: instances){
      if(!inst.type.equals(firstType)):
      return false;
    }
  }
  return true;
}

public class DiscountDetector{
  private List<DiscountRule> rules;

  public DiscountDetector(){
    rules = new ArrayList<>();
    rules.add(new Compute5Plus);
    rules.add(new GeneralCompute456);
    rules.add(new SameInstanceType());
  }

  public String detectBestDiscount(List<String> rawInstances){
    List<InstanceType> instances = new ArrayList<>();
    for (String raw: rawInstances){
      instances.add(new InstanceType(raw));
    }

    for(DiscountRule rule:rules){
      if(rule.matches(instance)){
        return rule.getName();
      }
      return "NoDiscount";
    }
  }
}

public class Main{
  public static void main(String[] args){
    DiscountDetector detector = new DiscountDetector();
    System.out.println(detector.detectBestDiscount(Arrays.asList("m3","m5","m6")));
  }
}
public enum Dates{
  STARTDATE(LocalDate.of(2025,4,1)),
  ENDDATE(LocalDate.of(2025,4,7));
}

public interface PriorityProvider{
  List<DiscountRule> getOrderedRules();
}

public class DefaultPriorityProvider implements PriorityProvider{
  public List<DiscountRule> getOrderedRules(){
    List<DiscountRule> rules = new ArrayList<>();
    rules.add(new Compute5Plus());
    rules.add(new NonGPU6Plus());
        rules.add(new GeneralCompute456());
        rules.add(new SameInstanceType());
        return rules;
  }
}
public class PromoWeekPriorityProvider implements PriorityProvider {
    public List<DiscountRule> getOrderedRules() {
        List<DiscountRule> rules = new ArrayList<>();
        rules.add(new GeneralCompute456()); // TEMPORARILY top priority
        rules.add(new Compute5Plus());
        rules.add(new NonGPU6Plus());
        rules.add(new SameInstanceType());
        return rules;
    }
}
public class TimeAwarePriorityProvider implements PriorityProvider{
  private final promoStart = Dates.STARTDATE;
  private final endDate = dates.ENDDATE;

  private final PriorityProvider defaultProvider = new DefaultPriorityProvider();
  private final PriorityProvider promoProvider = new PromoWeekPriorityProvider();

  public List<DiscountRule> getOrderedRules(){
    LocalDate today = LocatDate.now();
    if(!today.isBefore(promoStart) && !today.isAfter(promoEnd)){
      return promoProvider.getOrderedRules();
    }
    return defaultProvider.getOrderedRules();
  }

}

public class DiscountDetector{
  private final PriorityProvider priorityProvider;
  public DiscountDetector(PriorityProvider provider){
    this.priorityProvider=priorityProvider;
  }

  public String detectBestDiscount(List<String> rawInstances){
    List<InstanceType> instances = new ArrayList<>();
       for (String raw : rawInstances) {
           instances.add(new InstanceType(raw));
       }

       for(DiscountRule rule: priorityProvider.getOrderedRules()){
         if(rule.matches(instance)){
           return rule.getName();
         }
       }
       return "NoDiscount";
  }
}
