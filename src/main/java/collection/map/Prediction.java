package collection.map;

import java.util.Random;

/**
 * Created by xudong on 2018/11/17.
 * 散列与散列码
 */
public class Prediction {
    private static Random rand = new Random(47);
    private boolean shadow = rand.nextDouble() > 0.5;
    public String toString(){
        if(shadow){
            return "Six more weeks of winter!";
        }else {
            return "Early Spring";
        }
    }
}
