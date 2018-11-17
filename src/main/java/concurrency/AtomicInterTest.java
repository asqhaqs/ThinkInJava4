package concurrency;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xudong on 2018/7/7.
 */
public class AtomicInterTest implements Runnable {
    private AtomicInteger i = new AtomicInteger(0);
    public int getValue() {
        return  i.get();
    }
    private void evenIncrement(){
        i.addAndGet(2); //  ++i
    }
    @Override
    public void run() {
        while (true){
            evenIncrement();
        }
    }

    public static void main(String[] args) {
        //Timer 为线程调度类， 五秒执行一次， 不重复
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.err.println("Aborting");
                System.exit(0);
            }
        }, 5000 );  // 主线程 5 秒后终止
        ExecutorService exec = Executors.newCachedThreadPool();
        AtomicInterTest ai = new AtomicInterTest();
        exec.execute(ai);
        while(true){
            int val = ai.getValue();
            if(val % 2 != 0){
                System.out.println(val);
                exec.shutdown();
                System.exit(0);
            }
        }

    }
}
