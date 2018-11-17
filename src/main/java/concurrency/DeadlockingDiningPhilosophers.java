package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by xudong on 2018/7/17.
 * 哲学家就餐问题死锁 p720
 */
public class DeadlockingDiningPhilosophers {
    public static void main(String[] args) throws Exception{
        int ponder = 5;
        if(args.length > 0){
            ponder = Integer.parseInt(args[0]);
        }
        int size = 5;
        if(args.length > 1)
            size = Integer.parseInt(args[1]);
        ExecutorService exec = Executors.newCachedThreadPool();
        Chopstick[] chopsticks = new Chopstick[size];
        for(int i = 0; i < size; i++){
            chopsticks[i] = new Chopstick();
        }
        for(int i = 0; i < size; i++){
            exec.execute(new Philosopher(chopsticks[i], chopsticks[(i+1)%size], i, ponder));
        }
        if(args.length == 3 && args[2].equals("Timeout"))
            TimeUnit.SECONDS.sleep(5);
        else {
            System.out.println("Press 'Enter' to quit");
            System.in.read();
        }
        exec.shutdownNow();
    }
}
