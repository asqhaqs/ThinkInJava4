package io;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by xudong on 2018/6/26.
 * p541
 */
public class MemoryInput {
    public static void main(String[] args) throws IOException{
        StringReader sr = new StringReader(BufferedInputFile.read("C:\\Users\\xudong\\Desktop\\myProjects\\ThinkInJava4\\src\\main\\java\\io\\BufferedInputFile.java"));
        int c;
        while ((c = sr.read()) != -1){
            System.out.print((char)c);
        }
    }
}
