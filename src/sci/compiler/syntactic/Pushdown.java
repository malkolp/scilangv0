package sci.compiler.syntactic;

class Pushdown {

    private static Pushdown instance;

    private Node pointer;

    private Pushdown(String token){
        pointer = new Node(token);
    }

    private static void init(String token){
        if (instance == null)
            instance = new Pushdown(token);
    }

    static Pushdown use(String token){
        init(token);
        return instance;
    }

    static Pushdown close(){
        return instance = null;
    }

    void push(String token){
        Node n = new Node(token);
        n.setBottom(pointer);
        pointer = n;
    }

    String pop(){
        String token = "";
        if (pointer != null){
            token = pointer.getToken();
            Node n = pointer.getBottom();
            pointer = null;
            pointer = n;
        }
        return token;
    }

    String peek(){
        if (pointer != null)
            return pointer.getToken();
        return "";
    }
}

class Node{

    private Node bottom;
    private String token;

    Node(String token){
        this.token = token;
    }

    void setBottom(Node bottom){
        this.bottom = null;
        this.bottom = bottom;
    }

    Node getBottom(){
        return bottom;
    }

    String getToken() {
        return token;
    }
}
