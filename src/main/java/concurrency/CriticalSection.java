package concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xudong on 2018/7/7.
 * p686
 */
class Pair{
    //非线程安全
    private int x, y;
    public Pair(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Pair(){
        this(0,0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void incrementX(){ x++; }
    public void incrementY(){ y++; }
    @Override
    public String toString(){
        return "x: " + x + " y: " + y;
    }

    public class PairValuesNotEqualException extends RuntimeException {
        public PairValuesNotEqualException(){
            super("Pair values not equal: " + Pair.this); //注意，这里是内部类调用外部类
        }
    }

    public void checkState(){
        if(x != y){
            throw new PairValuesNotEqualException();
        }
    }
}

//在一个线程安全类里保护 Pair 类
abstract class PairManager {
    AtomicInteger checkCounter = new AtomicInteger(0);
    protected Pair p = new Pair();
    private List<Pair> storage = Collections.synchronizedList(new ArrayList<Pair>());
    public synchronized Pair getPair(){
        //拷贝一个Pair函数，来保证安全
        return new Pair(p.getX(),p.getY());
    }
    //评估时间消耗
    protected void store(Pair p){
        storage.add(p);
        try{
            TimeUnit.MILLISECONDS.sleep(50);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public abstract void increment();
}

//同步整个方法
class PariManager1 extends PairManager{

    //对类加锁
    @Override
    public synchronized void increment() {
        p.incrementX();
        p.incrementY();
        store(getPair());
    }
}

//使用临界区 对象不加锁的时间更长 所以效率高
class PairManager2 extends PairManager{

    @Override
    public void increment() {
        Pair temp;
        //对类加锁
        synchronized (this){
            p.incrementY();
            p.incrementX();
            temp = getPair();
        }
        //因为使用了 synchronized Arraylist ，所以该操作是线程安全的，不用写到同步块中
        store(temp);
    }
}

class PairManipulator implements Runnable {
    private  PairManager pm;
    public PairManipulator(PairManager pm){
        this.pm = pm;
    }
    @Override
    public void run() {
        while (true){
            pm.increment();
        }
    }

    @Override
    public String toString(){
        return "Pair: " + pm.getPair() + " checkCounter= " + pm.checkCounter.get();
    }
}

class PairChecker implements Runnable {
    private PairManager pm;
    public PairChecker(PairManager pm){
        this.pm = pm;
    }
    @Override
    public void run() {
        while(true){
            pm.checkCounter.incrementAndGet();
            pm.getPair().checkState();
        }
    }
}


public class CriticalSection {
    //测试这两种方法
    static void testApproaches(PairManager pam1, PairManager pam2){
        ExecutorService exec = Executors.newCachedThreadPool();
        PairManipulator pm1 = new PairManipulator(pam1),
                pm2 = new PairManipulator(pam2);
        PairChecker pcheck1 = new PairChecker(pam1),
                pcheck2 = new PairChecker(pam2);
        exec.execute(pm1);
        exec.execute(pm2);
        exec.execute(pcheck1);
        exec.execute(pcheck2);

        try{
            TimeUnit.MILLISECONDS.sleep(500);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.println( "pm1: " + pm1 + "\npm2: " + pm2);
        System.exit(0);
    }

    public static void main(String[] args) {
        PairManager pman1 = new PariManager1(),
                pman2 = new PairManager2();
        testApproaches(pman1,pman2);
    }
}
