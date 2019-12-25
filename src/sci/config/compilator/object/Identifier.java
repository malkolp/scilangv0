package sci.config.compilator.object;

import sci.config.preprocessor.support.Datatype;
import sci.config.preprocessor.support.Scope_type;

public class Identifier {

    private boolean fixed;
    private int data_type;
    private int scope;

    public Identifier(){
        setData_type();
        setScope();
        this.fixed = false;
    }

    public void setFixed(){
        if (!isFixed())
            this.fixed = true;
    }

    public void setData_type(){
        this.data_type = Datatype.use().INTEGER();
    }

    public void setData_type(int data_type){
        this.data_type = data_type;
    }

    public void setScope(){this.scope = Scope_type.use().FILE();}

    public boolean isFixed() {
        return fixed;
    }

    public int getData_type() {
        return data_type;
    }

    public int getScope(){return scope;}
}
