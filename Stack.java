
//using array
public class Stack<T>{
  private T[] array;
  private int top;
  private int capacity;

  public Stack(int capacity){
    this.capacity = capacity;
    this.array = (T[]) new Object[capacity];
    this.top = -1;
  }

  public void push(T value){
    if(top == capacity-1){
      throw new IllegalStateException("Stack overflow");
    }
    top++;
    array[top] = value;
  }

  public T pop(){
    if (isEmpty()){
      throw new IllegalStateException("stack underflow");
    }
    T value = array[top];
    array[top] = null;
    top--;
    return value;
  }

  public T peek(){
    if (isEmpty()){
      throw new IllegalStateException("stack underflow");
    }

    return array[top];
  }

  public boolean isEmpty(){
    return top == -1;
  }

  public int size(){
    return top+1;
  }
}



//Using Linked list
public class Node{
  int value;
  Node next;
  public Node{
    this.value = value;
    this.next = next;
  }
}

public class Stack{
  private Node top;
  public Stack(){
    this.top=top;
  }

  public void push(int val){
    Node newNode = new Node(val);
    newNode.next = top;
    top = newNode;
  }

  public int pop(){
    if(isEmpty()){
      throw new RuntimeException("Stack is empty");
    }
    int val = top.value;
    top = top.next;
    return val;
  }

  public int peek(){
    if(isEmpty()){
      throw new RuntimeException("Stack is empty");
    }
    return top.value;
  }
  public boolean isEmpty(){
    return top == null;
  }
}
