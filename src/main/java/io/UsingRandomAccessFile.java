package io;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by xudong on 2018/6/26.
 */
public class UsingRandomAccessFile {
    static String file = "C:\\Users\\xudong\\Desktop\\rtest.dat";
    static void display() throws IOException{
        RandomAccessFile rf = new RandomAccessFile(file,"r");
        for(int i = 0; i < 7; i++){
            System.out.println("Value " + i + ": " + rf.readDouble());
        }
        System.out.println(rf.readUTF());
        rf.close();
    }

    public static void main(String[] args) throws IOException{
        RandomAccessFile rf = new RandomAccessFile(file,"rw");
        for(int i = 0; i < 7; i++){
            rf.writeDouble(i*1.414);
        }
        rf.writeUTF("the end of the file");
        rf.close();
        display();
        rf = new RandomAccessFile(file, "rw");
        rf.seek(5*8);  //double 是 8 字节，用该函数来寻找修改位置
        rf.writeDouble(47.0001);
        rf.close();
        display();
    }
}
