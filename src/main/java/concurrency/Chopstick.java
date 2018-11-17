package concurrency;

/**
 * Created by xudong on 2018/7/17.
 * p719 哲学家就餐问题
 */
//筷子类
public class Chopstick {
    private boolean taken = false;
    public synchronized void take() throws InterruptedException{
        while(taken){
            wait();
        }
        taken = true;
    }
    public synchronized void drop(){
        taken = false;
        notifyAll();
    }
}
