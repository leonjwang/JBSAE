package jbsae.files.saves;

import jbsae.files.*;

public class SaveFi extends Fi{
    public SaveFi(String name){
        super(name);
    }

    public Read read(){
        return new Read(this);
    }

    public Write write(){
        return new Write(this);
    }

}

