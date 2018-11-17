package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by xudong on 2018/6/27.
 */
public class OSExecute {
    public static void command (String command){
        boolean err = false;
        try{
            Process process = new ProcessBuilder(command.split(" ")).start();
            BufferedReader results = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s;
            while ((s = results.readLine())!=null){
                System.out.println(s);
            }
            BufferedReader errors = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((s = errors.readLine()) !=  null){
                System.err.println(s);
                err = true;
            }
        }catch (Exception e){
            if(!command.startsWith("CMD /C")){
                command("CMD /C " + command);
            }else {
                throw new RuntimeException(e);
            }
        }
        if(err){
            throw new OSExcuteException("Errors executing " + command);
        }
    }


    public static void main(String[] args) {
        OSExecute.command("javap C:\\Users\\xudong\\Desktop\\myProjects\\ThinkInJava4\\src\\main\\java\\util\\OSExecute");
    }
}
