class LRUCache {
    Node left;
    Node right;
    int capacity;
    HashMap<Integer, Node> map;
    public LRUCache(int capacity) {
        map = new HashMap<>();
        this.capacity = capacity;
        this.left = new Node(0,0);
        this.right = new Node(0,0);
        this.left.next = this.right;
        this.right.prev = this.left;
    }

    public int get(int key) {
        if(map.containsKey(key)){
            remove(map.get(key));
            insert(map.get(key));
            return map.get(key).val;
        }
        return -1;
    }

    public void put(int key, int val) {
        if(map.containsKey(key)){
            remove(map.get(key));
        }
        map.put(key, new Node(key, val));
        insert(map.get(key));

        if(map.size() > capacity){
            Node node = this.left.next;
            remove(node);
            map.remove(node.key);
        }
    }

    public void insert(Node node){
        Node prev = this.right.prev;
        Node next = this.right;
        prev.next = node;
        node.next = next;
        node.prev = prev;
        next.prev = node;
    }

    public void remove(Node node){
        Node prev = node.prev;
        Node next = node.next;
        prev.next = next;
        next.prev = prev;
    }
}

class Node{
    int val;
    Node prev;
    Node next;
    int key;
    Node(int key, int val){
        this.key=key;
        this.val = val;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
