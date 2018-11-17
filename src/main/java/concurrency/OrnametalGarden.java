package concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by xudong on 2018/7/8.
 * p692
 */
class Count {
    private int count = 0;
    private Random rand = new Random(47);
    public synchronized int increment(){
        int tmp = count;
        if(rand.nextBoolean())
            Thread.yield();
        return (count = ++ tmp );
    }
    public synchronized int value(){
        return count;
    }
}
class Entrance implements Runnable {
    private static Count count = new Count();
    private static List<Entrance> entrances = new ArrayList<Entrance>();
    private int number = 0;
    //不需要同步读
    private final int id;
    private static volatile boolean canceled = false;
    //volatile域上的原子性操作
    public static void cancel(){ canceled = true; }
    public Entrance(int id){
        this.id = id;
        entrances.add(this);
    }

    @Override
    public void run() {
        while (!canceled){
            synchronized (this){
                ++number;
            }
            System.out.println(this + " Total: " + count.increment());
            try{
                TimeUnit.MILLISECONDS.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("Stopping " + this);
    }
    public synchronized int getValue(){ return number; }
    @Override
    public String toString(){
        return "Entrance " + id + ": " + getValue();
    }
    public static int getTotalCount(){
        return count.value();
    }
    public static int sumEntrances(){
        int sum = 0;
        for(Entrance entrance : entrances){
            sum += entrance.getValue();
        }
        return sum;
    }
}
public class OrnametalGarden {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i =0; i < 5; i++){
            exec.execute(new Entrance(i));
        }
        TimeUnit.SECONDS.sleep(3);
        Entrance.cancel();
        exec.shutdown();
        if(!exec.awaitTermination(250,TimeUnit.MICROSECONDS))
            System.out.println("Some tasks were not terminated! ");
        System.out.println("Total: " + Entrance.getTotalCount());
        System.out.println("Sum of Entrances: " + Entrance.sumEntrances());
    }
}
