public class User{
  private String userId;
  private String name;
  private String address;

  public User(String userId, String name, String address){
    this.userId = userId;
    this.name=name;
    this.address=address;
  }

  //getters and setters
  public String getId(){
    return userId;
  }

  public String name(){
    return name;
  }

  public String address(){
    return address;
  }
}

public enum TaskStatus{
  COMPLETED,
  IN_PROGRESS,
  PENDING;
}

public class Task{
  private final String id;
  private String description;
  private Date dueDate;
  private int priority;
  private TaskStatus status;
  private final User assignedUser;

  public Task(String id, String description, Date dueDate, int priority, TaskStatus status, User assignedUser){
    this.id = id;
    this.description=description;
    this.dueDate=dueDate;
    this.priority=priority;
    this.status=TaskStatus.PENDING;
    this.assignedUser=assignedUser;
  }

  //getters and setters
}

//SINGLETON PATTERN
public class TaskManager{
  private static TaskManager instance;
  private final Map<String, Task> tasks;
  private final Map<String, List<Task>> userTasks;

  private TaskManager(){
    tasks = new HashMap<>();
    userTasks = new HashMap<>();
  }

  private static synchronized TaskManager getInstance(){
    if(instance == null){
      instance = new TaskManager;
    }else{
      return instance;
    }
  }

  public void createTask(Task task){
    tasks.put(task.getId(),task);
    assignTaskToUser(task.assignedUser(), task);
  }

  public void assignTaskToUser(User user, Task task){
    userTasks.computeIfAbsent(user.getId(), k-> CopyOnWriteArrayList<>()).add(task);
  }

  public void updateTask(Task updateTask){
    Task existingTask = tasks.get(updateTask.getId());
    if(existingTask != null){
      synchronized(existingTask){
        existingTask.setTitle(updateTask.getTitle());
        existingTask.setDescription(updateTask.getDescription());
        existingTask.setDueDate(updatedTask.getDueDate());
        existingTask.setPriority(updatedTask.getPriority());
        existingTask.setStatus(updatedTask.getStatus());
        User previousUser = existingTask.getAssignedUser();
        User newUser = updatedTask.getAssignedUser();
        if(!previousUser.equals(newUser)){
          unassignTaskFromUser(previousUser, existingTask);
          assignTaskToUser(newUser, existingTask);
        }
      }
    }
  }

  public void deleteTask(String taskId){
    Task task = tasks.remove(taskId);
    if(task != null){
      unassignTaskFromUser(task.getAssignedUser(), task);
    }
  }

  public List<Task> searchTasks(String keyword){
    List<Task> matchingTasks = new ArrayList<>();
    for(Task task:tasks.values()){
      if(task.getTitle().contains(keyword) || task.getDescription().contains(keyword)){
        matchingTasks.add(tasks);
      }
    }
  }

  public List<Task> filterTask(TaskStatus status, Date startDate, Date endDate, int priority){
    List<Task> filteredTasks = new ArrayList<>();
    for(Task task:task.values()){
      if(task.getStatus() == status && task.getDueDate().compareTo(startDate) >= 0 &&
                    task.getDueDate().compareTo(endDate) <= 0 &&
                    task.getPriority() == priority){
                      filteredTasks.add(task);
                    }
    }
    return filterTasks;
  }

  public void markTaskCompleted(String taskId){
    Task task = tasks.get(taskId);
    if(task != null){
      synchronized(task){
        task.setStatus(TaskStatus.COMPLETED);
      }
    }
  }

  public List<Task> getTaskHistory(User user){
    return new ArrayList<>(userTasks.getOrDefault(user.getId(), new ArrayList));
  }
  //assignTaskToUser
  //unassignTaskFromUser
}

public class TaskManagerSystemDemo{
  public static void main(String[] args){
    TaskManager taskManager = new TaskManager();

    User user1 =
    //user2
    //user3
    //task1
    //task2
    taskManager.createTask(task1);

  }
}
