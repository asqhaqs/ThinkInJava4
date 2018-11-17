package io;

import java.io.*;

/**
 * Created by xudong on 2018/6/28.
 */
public class Blip3 implements Externalizable {

    private int i ;
    private String s;  //No initialization
    public Blip3(){
        System.out.println("Blip3 Constructor");
    }

    public Blip3(String x, int a){
        System.out.println("Blip3(String x, int a)");
        s = x;
        i = a;
    }

    public String toString(){
        return s + i;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        System.out.println("blip3.writeExternal");
        out.writeObject(s);
        out.writeInt(i);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        System.out.println("blip3.readExternal");
        s = (String) in.readObject();
        i = in.readInt();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException{
        System.out.println("Constructing objects");
        Blip3 b3 = new Blip3("A String",47);
        System.out.println(b3);
        ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("C:\\Users\\xudong\\Desktop\\Blip3.out"));
        System.out.println("Saving object");
        o.writeObject(b3);
        o.close();
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("C:\\Users\\xudong\\Desktop\\Blip3.out"));
        System.out.println("Recovering object");
        b3 = (Blip3) in.readObject();
        System.out.println(b3);

    }
}
