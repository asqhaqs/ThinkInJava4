package concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * Created by xudong on 2018/7/17.
 * p715 阻塞队列
 */
//吐司类
class Toast{
    //三个步骤 做土司，涂黄油，涂果酱
    public enum Status { DRY, BUTTRED, JAMMED }
    private Status status = Status.DRY;
    private final int id;
    public Toast(int idn){
        this.id = idn;
    }
    public void butter(){
        status = Status.BUTTRED;
    }
    public void jam(){
        status = Status.JAMMED;
    }
    public Status getStatus(){
        return status;
    }
    public int getId(){
        return id;
    }
    @Override
    public String toString(){
        return "Toast " + id + ": " + status;
    }
}
class ToastQueue extends LinkedBlockingDeque<Toast>{

}

//吐司师傅，只负责做吐司
class Toaster implements Runnable{
    private ToastQueue toastQueue;
    private int count = 0;
    private Random rand = new Random(47);
    public Toaster(ToastQueue toastQueue){
        this.toastQueue = toastQueue;
    }
    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(500));
                Toast t = new Toast(count++);
                System.out.println(t);
                toastQueue.put(t);
            }
        }catch (InterruptedException e){
            System.out.println("Toaster interrupter");
        }
        System.out.println("Toaster off");

    }
}
//涂黄油师傅
class Butterer implements Runnable{
    private ToastQueue dryQueue, butteredQueue;
    public Butterer(ToastQueue dry, ToastQueue butt){
        this.dryQueue = dry;
        this.butteredQueue = butt;
    }
    @Override
    public void run() {
        try{
            while(!Thread.interrupted()){
                Toast t = dryQueue.take();
                t.butter();
                System.out.println(t);
                butteredQueue.add(t);
            }
        }catch (InterruptedException e){
            System.out.println("Butterer interrupted");
        }
        System.out.println("Butterer off");
    }
}
//果酱师傅
class Jammer implements Runnable{
    private ToastQueue butteredQueue, finishedQueue;
    public Jammer(ToastQueue butt, ToastQueue finish){
        this.butteredQueue = butt;
        this.finishedQueue = finish;
    }
    @Override
    public void run() {
        try{
            while(!Thread.interrupted()){
                Toast t = butteredQueue.take();
                t.jam();
                System.out.println(t);
                finishedQueue.add(t);
            }
        }catch (InterruptedException e){
            System.out.println("Jammer interrupted");
        }
        System.out.println("Jammer off");
    }
}
//消费吐司
class Eater implements Runnable{
    private  ToastQueue finishedQueue;
    private  int counter = 0;
    public Eater(ToastQueue finish){
        this.finishedQueue = finish;
    }
    @Override
    public void run() {
        try{
            while(!Thread.interrupted()){
                Toast t = finishedQueue.take();
                if( t.getId() != counter++ || t.getStatus() != Toast.Status.JAMMED){
                    System.out.println(">>>>Error: " + t);
                    System.exit(1);
                }else {
                    System.out.println("Chomp!" + t);
                }
            }
        }catch (InterruptedException e){
            System.out.println("Eater interrupted");
        }
        System.out.println("Eater off");
    }
}
public class ToastOMatic {
    public static void main(String[] args) throws InterruptedException {
        ToastQueue dryQueue = new ToastQueue(),
                butterQueue = new ToastQueue(),
                finishedQueue = new ToastQueue();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Toaster(dryQueue));
        exec.execute(new Butterer(dryQueue,butterQueue));
        exec.execute(new Jammer(butterQueue,finishedQueue));
        exec.execute(new Eater(finishedQueue));
        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
    }
}
