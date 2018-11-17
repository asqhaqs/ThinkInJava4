package enumerated;

import java.util.Random;

/**
 * Created by xudong on 2018/6/20.
 * p613
 */
interface Item{
    OutCome compete(Item it);
    OutCome eval(Paper p);
    OutCome eval(Scissors s);
    OutCome eval(Rock r);
}

class Paper implements Item{
    public OutCome compete(Item it){
        return it.eval(this);
    }
    public OutCome eval(Paper p){
        return OutCome.DRAW;
    }
    public OutCome eval(Scissors s){
        return OutCome.WIN;
    }
    public OutCome eval(Rock r){
        return OutCome.lOSE;
    }
    public String toString(){
        return "Paper";
    }
}

class Scissors implements Item{
    public OutCome compete(Item it){
        return it.eval(this);
    }
    public OutCome eval(Paper p){
        return OutCome.lOSE;
    }
    public OutCome eval(Scissors s){
        return OutCome.DRAW;
    }
    public OutCome eval(Rock r){
        return OutCome.WIN;
    }
    public String toString(){
        return "Scissors";
    }
}

class Rock implements Item{
    public OutCome compete(Item it){
        return it.eval(this);
    }
    public OutCome eval(Paper p){
        return OutCome.WIN;
    }
    public OutCome eval(Scissors s){
        return OutCome.lOSE;
    }
    public OutCome eval(Rock r){
        return OutCome.DRAW;
    }
    public String toString(){
        return "Scissors";
    }
}

public class RoShamBo1 {
    static final int SIZE = 20;
    private static Random random = new Random(47);
    public static Item newItem(){
        switch (random.nextInt(3)){
            default:
            case 0: return new Scissors();
            case 1: return new Paper();
            case 3: return new Rock();
        }
    }
    public static void match(Item a, Item b){
        System.out.println(a + " vs. "+ b + ": "+ a.compete(b));
    }

    public static void main(String[] args) {
        for(int i = 0; i<SIZE; i++){
            match(newItem(),newItem());
        }
    }

}
