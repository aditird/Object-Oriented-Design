//COMPOSITE DESIGN PATTERN

//Interface
public Interface FileSystem{
  public void ls();
}

//LEAF - file
public class File implements FileSystem{
  String fileName;
  public File(String name){
    this.fileName = name;
  }
  public void ls(){
    System.out.println("filename-" + fileName);
  }
}

public class Directory implements FileSystem{
  String directoryName;
  List<FileSystem> fileSystem;

  public Directory(String name){
    this.directoryName = name;
    this.fileSystem = new ArrayList<>();
  }

  public void add(FileSystem fileSystem){
    fileSystem.add()
  }

  public void ls(){
    for(FileSystem fileSystemObj:fileSystem){
      fileSystemObj.ls();
    }
  }
}

public class Main{
  public static void main(String[] args){
    Directory dr = new Directory("movie");
    FileSystem fs = new File("Avatar");
    dr.add(fs);
    Directory dr1 = new Directory("Comedy");
    File xyz = new File("XYZ");
    dr1.add(xyz);
    dr.ls();
  }
}
