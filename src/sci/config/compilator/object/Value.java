package sci.config.compilator.object;

public class Value {

    private String value;
    private int data_type;

    public Value(String value){
        setValue(value);
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setData_type(int data_type){this.data_type = data_type;}

    public String getValue() {
        return value;
    }

    public int getData_type() { return data_type; }
}
