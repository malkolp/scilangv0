package sci.config.compilator.object;

public class Terminal_property {

    private int precedence;
    private StringBuilder childList;

    public Terminal_property(int precedence,String child){
        childList = new StringBuilder();
        this.precedence = precedence;
        childList.append(child.replace(" ","<AND>"));
    }

    public void addChild(String child){
        child = child.replace(" ","<AND>");
        childList.append("<OR>").append(child);
    }

    public int getPrecedence() {
        return precedence;
    }

    public String getChild() {
        return childList.toString();
    }
}
