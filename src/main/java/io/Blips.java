package io;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;

/**
 * Created by xudong on 2018/6/28.
 * p575
 */
class Blip1 implements Externalizable {
     public Blip1(){
        System.out.println("Blips1 Constructor");
    }
    @Override
    public void writeExternal(ObjectOutput out) throws IOException{
        System.out.printf("Blip1.writeExternal");
    }
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException{
        System.out.println("Blip1.readExternal");
    }
}

class Blip2 implements Externalizable{
    Blip2(){
        System.out.println("Blip2 Constructor");
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        System.out.println("Blip2.writeExternal");
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        System.out.println("Blip2.readExternal");
    }
}

public class Blips {
    public static void main(String[] args) throws IOException, ClassNotFoundException{
        System.out.println("Constructing objects");
        Blip1 b1 = new Blip1();
        Blip2 b2 = new Blip2();

        ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("C:\\Users\\xudong\\Desktop\\Blips.out"));
        System.out.println("Saving Objects: ");
        o.writeObject(b1);
        o.writeObject(b2);
        o.close();

        ObjectInputStream in = new ObjectInputStream(new FileInputStream("C:\\Users\\xudong\\Desktop\\Blips.out"));
        System.out.println("Recovering blip1");
        b1 = (Blip1)in.readObject();

        //发生异常
        System.out.println("Recovering blip2");
        b2 = (Blip2)in.readObject();
    }
}


