package enumUnit;

import net.mindview.util.OSExecute;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.TreeSet;
//import static net.mindview.util.OSExecute;

/**
 * Created by xudong on 2018/6/15.
 * 利用反射技术分析Enum类中的方法
 */

enum Explore { HERE, THERE }
public class Reflection {
    public static Set<String> analyze(Class<?> enumClass){
        System.out.println("----- Analysing "+ enumClass + "-----");

        //接口
        System.out.println("Interface: ");
        for(Type t : enumClass.getGenericInterfaces()){
            System.out.println(t);
        }
        //父类方法
        System.out.println("Base: "+ enumClass.getSuperclass());
        System.out.println("Methods: ");
        Set<String> methods = new TreeSet<String>();
        for(Method method : enumClass.getMethods()){
            methods.add(method.getName());
        }
        System.out.println(methods);
        return methods;

    }

    public static void main(String[] args) {
        Set<String> exploreMrthods = Reflection.analyze(Explore.class);
        Set<String> enumMethods =Reflection.analyze(Enum.class);
        System.out.println("Explore.containAll(Enum)? : "+ exploreMrthods.containsAll(enumMethods));
        System.out.println("Explore.removeAll(Enum)? :"+ exploreMrthods.removeAll(enumMethods));
        System.out.println(exploreMrthods);
        //反编译 enum 类
        OSExecute.command("javap Explore");
    }
}
