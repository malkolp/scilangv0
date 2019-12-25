package sci.config.compilator.object;

public class Parse_tree {

    private String token;
    private Parse_tree[] child;

    public Parse_tree(String token){
        this.token = token;
        child = new Parse_tree[0];
    }

    public void add(Parse_tree node){
        Parse_tree[] tree = new Parse_tree[child.length+1];
        System.arraycopy(child, 0, tree, 0, child.length);
        child = null;
        child = tree;
    }

    public String getToken() {
        return token;
    }

    public Parse_tree[] getChild() {
        return child;
    }

    public boolean isEmpty(){
        return child.length == 0;
    }
}
