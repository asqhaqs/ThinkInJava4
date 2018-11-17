package io;

import java.io.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by xudong on 2018/6/28.
 * p578
 */
public class Logon implements Serializable{
    private Date date = new Date();
    private String username;
    private transient String password;
    public Logon(String name,String pwd){
        this.username = name;
        this.password = pwd;
    }
    @Override
    public String toString(){
        return "logon info: \n  username: " + username + "\n  date: " + date + "\n   passwd: " + password;
    }

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Logon a = new Logon("Xudong","hiGaoZhan");
        System.out.println("logon a = " + a);
        ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("C:\\Users\\xudong\\Desktop\\Logon.out"));
        o.writeObject(a);
        o.close();
        TimeUnit.SECONDS.sleep(1);
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("C:\\Users\\xudong\\Desktop\\Logon.out"));
        System.out.println("Recovering object at " + new Date());
        a = (Logon)in.readObject();
        System.out.println("logon a = " + a);
    }
}
