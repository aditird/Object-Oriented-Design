//using linked list
public class Node{
  int val;
  Node next;
  public Node(){
    this.val = val;
    this.next = next;
  }
}

public class Queue{
  private Node front;
  private Node rear;
  public Queue(){
    this.front = null;
    this.rear = null;
  }

public void enQueue(int val){
    Node newNode = new Node(val);
    if(rear == null){
      rear = front = newNode;
    }else{
    rear.next = newNode;
    rear = newNode;
  }
}

public void deQueue(){
  if(isEmpty()){
    throw new RuntimeException("Queue is empty");
  }
  int val = front.value;
  front = front.next;
  if(front == null){
    rear = null;
  }
  return null;
}
}

//remove after it reaches certain freq::
class Node{
  int val;
  Node next;
  Node prev;
  Node(int val){
    this.val = val;
  }
}


public class Queue{
  private Node front = null;
  private Node rear = null;
  private HashMap<Integer, Integer> freqMap = new HashMap<>();
  private HashMap<Integer, List<Node>> valueToNodes = new HashMap<>();
  private final int THRESHOLD = 5;

  public void enqueue(int value){ //insert at the end
    Node newNode = new Node(value);
    if(tail == null){ //queue is empty
      front = rear = null;
    }
    else{
      tail.next = newNode;
      newNode.prev = tail;
      tail = newNode;
    }
    valueToNodes.putIfAbsent(value, new ArrayList<>());
    valueToNodes.get(value).add(newNode);

    freqMap.put(value, freqMap.getOrDefault(value,0)+1);
    if(freqMap.get(value) == TRESHOLD){
      removeAll(value);
      freqMap.remove(value);
    }
  }

  public int dequeue(){ //remove from the front
    if(front == null){
      throw new RuntimeException("Queue is empty");
    }
    int val = head.value;
    Node temp = head;

    if(head == tail){
      head = tail = null;
    }else{
      head = head.next;
      head.prev = null;
    }

    valueToNodes.get(val).remove(temp);
    if(valueToNodes.get(val).isEmpty()){
      valueToNodes.remove(val);
    }

    freqMap.put(val, freqMap.get(val)-1);
    if(freqMap.get(val) == 0) freqMap.remove(val);

    return val;
  }

  public int peek(){
    if(front == null){
      throw new RuntimeException("Queue is empty");
    }
    return front.value;
  }

  private void removeAll(int val){

    if(!valueToNodes.containsKey(val)){
      return;
    }

    for(Node node: valueToNodes.get(val)){
      if(node.prev != null) node.prev.next = node.next;
      else head=node.next;

      if(node.next != null) node.next.prev = node.prev;
      else tail = node.prev;
    }

    valueToNodes.remove(val);
    freqMap.remove(val);
  }
}
