package annotations;

/**
 * Created by xudong on 2018/6/22.
 * p630
 */
@ExtractInterface("Imultipler")
public class Multiplier {
    public int multiply(int x, int y){
        int total = 0;
        for(int i =0; i<x; i++){
            total = add(total,y);
        }
        return total;
    }
    private int add(int i, int j){  return i+j; }

}
