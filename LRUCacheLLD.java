interface EvictionPolicy{
    void keyAccessed(int key);
    int evictKey();
}

class Node{
    int key;
    Node prev;
    Node next;
    Node(int key){
        this.key = key;
    }
}

class LRUEvictionPolicy implements EvictionPolicy{
    private final Map<Integer, Node> map = new HashMap<>();
    private final Node head = new Node(0);
    private final Node tail = new Node(0);

    public LRUEvictionPolicy(){
        head.next = tail;
        tail.next = head;
    }

    public void keyAccessed(int key){
        if(map.contains(key)){
            remove(map.get(key));
        }
        Node node = new Node(key);
        add(node);
        map.put(key, node);
    }

    public int evictKey(){
        if(head == tail){
            return -1;
        }
        Node lru = head.next;
        remove(lru);
        map.remove(lru.key);
        return lru.key;
    }

    private void add(Node node){
        Node prev = tail.prev;
        prev.next = node;
        node.prev = prev;
        node.next = tail;
        tail.prev = node;
    }

    private void remove(Node node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}

interface Cache{
    String get(int key);
    void put(int key, String value);
}

class CacheEntry{
    String value;
    CacheEntry(String value){
        this.value = value;
    }
}

class LRUCache implements Cache{
    private final int capacity;
    private final Map<Integer, CacheEntry> map;
    private final EvictionPolicy evictionPolicy;

    public LRUCache(int capacity){
        this.capacity;
        this.map = new HashMap<>();
        this.evictionPolicy = new LRUEvictionPolicy();
    }

    public String get(int key){
        if(!map.containsKey(key)) return null;
        evictionPolicy.keyAccessed(key);
        return map.get(key).value;
    }

    public void put(int key, String value){
        if(map.containsKey(key)){
            evictionPolicy.keyAccessed(key);
        }
        else{
            if(map.size() >= capacity){
                int evict = evictionPolicy.evictKey();
                if(evict != -1) map.remove(evict);
            }
            evictionPolicy.keyAccessed(key);
        }
        map.put(key, new CacheEntry(value));
    }
}
