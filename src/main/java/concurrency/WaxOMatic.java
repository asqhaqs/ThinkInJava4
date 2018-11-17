package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by xudong on 2018/7/17.
 * p705
 */
class Car1{
    private boolean waxOn = false;
    public synchronized void waxed(){
        waxOn = true; //Ready for buff
        notifyAll();
    }
    public synchronized void buffed(){
        waxOn = false;// Read for another coat of wax
        notifyAll();
    }
    public synchronized void waitForWaxing() throws InterruptedException {
        while(waxOn == false){
            wait();
        }
    }
    public synchronized void waitForBuffing() throws InterruptedException {
        while (waxOn == true){
            wait();
        }
    }

}

class WaxOn implements Runnable{

    private Car1 car;
    public WaxOn(Car1 c){ car = c; }
    @Override
    public void run() {
        try{
            while(!Thread.interrupted()){
                System.out.println("Wax On");
                TimeUnit.MILLISECONDS.sleep(200);
                car.waxed();
                car.waitForBuffing();
            }
        } catch (InterruptedException e) {
            System.out.println("Exiting via interrupt");
        }
        System.out.println("Ending Wax On task");
    }
}

class WaxOff implements Runnable{

    private Car1 car;
    public WaxOff(Car1 car){ this.car = car;}
    @Override
    public void run() {
        try{
            while(!Thread.interrupted()){
                car.waitForWaxing();
                System.out.println("Wax OFF");
                TimeUnit.MILLISECONDS.sleep(200);
                car.buffed();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
public class WaxOMatic {
    public static void main(String[] args) throws InterruptedException {
        Car1 car = new Car1();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new WaxOff(car));
        exec.execute(new WaxOn(car));
        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
    }
}
