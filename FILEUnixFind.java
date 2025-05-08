


public interface FileSystem{
    String getName();
    boolean isFile();
    long getSize();
    boolean isDirectory();
    void ls();
}

public class File implements FileSystem{
    private String fileName;
    private long fileSize;

    public File(String fileName, long fileSize){
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    public String getName(){
        return fileName;
    }

    public long getSize(){
        return fileSize;
    }

    public boolean isFile(){
        return true;
    }

    public boolean isDirectory(){
        return false;
    }

    public void ls(){
        System.out.println("File "+fileName);
    }

}

public class Directory implements FileSystem{
    private String name;
    private List<FileSystem> children;

    public Directory(String name){
        this.name = name;
        this.children = new ArrayList<>();
    }

    public void add(FileSystem fs){
        children.add(fs);
    }

    public List<FileSystem> getChildren{
        return children;
    }

    public String getName(){
        return name;
    }
    public boolean isFile(){
        return false;
    }
    public long getSize(){
        //get size of total children
        return 0;
    }

    public boolean isDirectory(){
        return true;
    }

    public void ls(){
        for(FileSystem fs: children){
            fs.ls();
        }
    }
}

public interface FileMatcher{
    boolean match(FileSystem fs);
}

public class SizeGreaterThanMatcher implements FileMatcher{
    private long size;
    public SizeGreaterThanMatcher(long size){
        this.size = size;
    }
    public boolean match(FileSystem fs){
        return fs.isFile() && fs.getSize() > size;
    }
}

public class ExtensionMatcher implements FileMatcher{
    private String extension;
    public ExtensionMatcher(String extension){
        this.extension = extension;
    }
    public boolean match(FileSystem fs){
        return fs.isFile() && fs.getName().endsWith(extension);
    }
}

//optional AND
public class AndMatcher implements FileSystem{
    private FileMatcher first;
    private FileMatcher second;
    public AndMatcher(FileMatcher first, FileMatcher second){
        this.first = first;
        this.second = second;
    }
    public boolean match(FileSystem fs){
        return first.match(fs) && second.match(fs);
    }
}

//similarly OR

public class FindFile{
    public List<File> find(FileSystem root, FileMatcher matcher){
        List<File> result = new ArrayList<>();
        dfs(root, matcher, result, new HashSet<>());
    }

    public void dfs(FileSystem current, FileMatcher matcher, List<File> result, Set<FileSystem> visited){
        visited.add(current);

        if(matcher.match(current)){
            result.add((File)current));
        }

        if(current.isDirectoy()){
            for(FileSystem child: current.getChildren()){
                dfs(child, matcher, result, visited);
            }
        }
    }
}
public class Main{
    public static void main(String[] args){
        File file1 = new File("reports.xml", 6*1024*1024);
        File file2 = new File("notes.txt", 1024);
        File file3 = new File("data.xml", 2*1024*1024);

        Directory subDir = new Directory("SubFolder");
        subDir.add(file3);

        Directory root = new Directory("RootFolder");
        root.add(file1);
        root.add(file2);
        root.add(subDir);

        FindFile finder = new FindFile();

        List<File> largerThan5MB = finder.find(root, new SizeGreaterThanMatcher(5*1024*1024));
        for(File f: largerThan5MB){
            f.ls();
        }

        List<File> xmlFiles = finder.find(root, new ExtensionMatcher(".xml"));
        for(File f: xmlFiles){
            f.ls();
        }


    }
}
