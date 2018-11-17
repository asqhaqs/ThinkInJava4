package annotations;

import net.mindview.util.OSExecute;

/**
 * Created by xudong on 2018/6/22.
 */
public class AtUnitExample1 {
    public String method1(){
        return "This is methodOne";
    }
    public int methodTwo(){
        System.out.println("This is method 2");
        return 2;
    }
    @Test boolean methodOneTest(){
        return method1().equals("This is methodOne");
    }
    @Test boolean m2(){
        return methodTwo() == 2;
    }
    @Test private boolean m3(){
        return true;
    }
    @Test boolean failureTest(){
        return false;
    }
    @Test boolean anotherDisappointment(){
        return false;
    }

    public static void main(String[] args) {
        OSExecute.command("java.net.mindview.atunit.AtUnit AtUnitExample1");
    }

}
