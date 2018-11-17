package concurrency;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by xudong on 2018/7/17.
 */
public class Philosopher implements Runnable {
    private Chopstick left;
    private Chopstick right;
    private final int id;
    private final int ponderFactory;
    private Random rand = new Random(47);
    private void pause() throws InterruptedException{
        if(ponderFactory == 0) return;
        TimeUnit.MILLISECONDS.sleep(rand.nextInt(ponderFactory*250));
    }
    public Philosopher(Chopstick left, Chopstick right, int ident, int ponder){
        this.right = right;
        this.left = left;
        this.id = ident;
        this.ponderFactory = ponder;
    }
    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                System.out.println(this + " " + "thinking");
                this.pause();
                //哲学家想要吃饭
                System.out.println(this + " " + "grabbing right");
                right.take();
                System.out.println(this + " " + "grabbing left");
                left.take();
                System.out.println(this + " " + "eating");
                this.pause();
                right.drop();
                left.drop();
            }
        }catch (InterruptedException e){
            System.out.println(this + " " + "exiting via interrupt");
        }

    }
    public String toString(){
        return "Philosopher" + id;
    }
}
