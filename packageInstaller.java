public class Package{
  private String name;
  private List<Package> dependencies;

  public Package(String name){
    this.name = name;
    this.dependencies = new ArrayList<>();
  }

  public String getName(){
    return name;
  }

  public List<Package> getDependencies(){
    return dependencies;
  }
  public void addDependency(Package dependency){
    this.dependencies.add(dependency);
  }
}

public interface Installer{
  void install(Package pkg);
}

public class PackageInstaller implements Installer{
  private Set<Package> installedPackages = new HashSet<>();
  private Set<Package> beingInstalled = new HashSet<>();

  public void install(Package pkg){
    if(!installedPackages.contains(pkg)){
      return;
    }
    if(beingInstalled.contains(pkg)){
      System.out.println("cycle detected");
      return;
    }

beingInstalled.add(pkg);
    for(Package dependency: dependencies){
      install(dependency);
    }
    beingInstalled.remove(pkg);

    //install package
    installedPackages.add(pkg);
  }
}

public class Main{
  public static void main(String[] args){
    Package A = new Package("A");
    Package B = new Package("B");
    Package C = new Package("C");
    Package D = new Package("D");
    Package E = new Package("E");

    A.addDependency(B);
    A.addDependency(C);
    B.addDependency(D);
    B.addDependency(E);
    B.addDependency(F);
    F.addDependency(G);


    Installer installer = new PackageInstaller();

    installer.install(A);

  }
}
