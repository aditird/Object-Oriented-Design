
/*Some question about writing a program to run checks on an blinking light, e.g. if light is blinking red then make it blue,
if it's blue then make it green,
 if it's not blinking then make it blue.. they wanted to make it so that you could easily add and remove rules/checks.

colors - red, blue, IllegalArgumentException
red -> blue
blue -> green
off -> blue
*/


//toggling on off capability
//extensible to support future colors or behavoiors

//main controller

//handle series of transitions in a decoupled way!

public abstract class lightController{
  lightController nextLightController;

  public lightController(lightController nextLightController){
    this.nextLightController = nextLightController;
  }

  public void changeColor(){
    System.out.println("Changing the color of the lamp");
  }

  public String getStatus(){
    System.out.println("status");
  }

}

public class RedLightController extends lightController{

  public RedLightController(lightController redLightController){
    super(redLightController);
  }

  public void changeColor(){
    System.out.println("changing color to red");
  }

}

public class BlueLightController extends lightController{

  public BlueLightController(lightController blueLightController){
    super(blueLightController);
  }

  public void changeColor(){
    System.out.println("changing color to blue");
  }

}

public class GreenLightController extends lightController{

  public GreenLightController(lightController greenLightController){
    super(blueLightController);
  }

  public void changeColor(){
    System.out.println("changing color to green");
  }
}

public class OffLightHandler extends lightController{
  public OffLightHandler(light nextHandler){
    super(nextHandler);
  }

  public void changeColor(Light light){
    if(nextHandler == null){

    }
  }
}

public class Light{

  public static void main(String[] args){
    lightController lc = new RedLightController(new BlueLightController(new GreenLightController(null)));

  }
}
