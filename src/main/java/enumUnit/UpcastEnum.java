package enumUnit;

import net.mindview.util.Enums;

/**
 * Created by xudong on 2018/6/15.
 */
enum Search { HITHER, YON }
public class UpcastEnum {
    public static void main(String[] args) {
        Search[] vals = Search.values();
        Enum e = Search.HITHER;
        for(Enum en : e.getClass().getEnumConstants() ){
            System.out.println(en);
        }
        for(int i = 0; i < 3; i++){
            System.out.println(Enums.random(Search.class)+" ");
        }
    }
}
