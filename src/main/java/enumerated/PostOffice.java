package enumerated;

import net.mindview.util.Enums;

import java.util.Iterator;

import static net.mindview.util.Print.print;

/**
 * Created by xudong on 2018/6/20.
 * 实现职责链
 * page606
 */
class Mail{
    enum GeneraDelivery { YES, NO1, NO2, NO3, NO4 }
    enum Scannability { UNSCANNABLE, YES1, YES2, YES3, YES4 }
    enum Readability { ILLEGBIBLE, YES1, YES2, YES3, YES4 }
    enum Addres {INCORRECT, OK1, OK2, OK3, OK4, OK5 }
    enum ReturnAddres { MISSING, OK1, OK2, OK3, OK4}
    GeneraDelivery generaDelivery;
    Scannability scannability;
    Readability readability;
    Addres addres;
    ReturnAddres returnAddres;
    static long counter = 0;
    long id = counter++;
    @Override
    public String toString(){ return "Mail " + id; };
    public String details() {
        return toString() +
                ", General Delivery: " + generaDelivery +
                ", Address Scanability: " + scannability +
                ", Address Readability: " + readability +
                ", Address Address: " + addres +
                ", Return address: " + returnAddres;
    }

    // 产生 测试用的 mail 类
    public static Mail randomMail(){
        Mail m = new Mail();
        m.generaDelivery = Enums.random(GeneraDelivery.class);
        m.scannability = Enums.random(Scannability.class);
        m.readability = Enums.random(Readability.class);
        m.addres = Enums.random(Addres.class);
        m.returnAddres = Enums.random(ReturnAddres.class);
        return m;
    }

    public static Iterable<Mail> generator(final int counte){
        return new Iterable<Mail>() {
            int n = counte;
            @Override
            public Iterator<Mail> iterator() {
                return new Iterator<Mail>() {
                    @Override
                    public boolean hasNext() {
                        return n-- > 0;
                    }

                    @Override
                    public Mail next() {
                        return randomMail();
                    }

                    @Override
                    public void remove(){
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }
}

public class PostOffice {

    enum MailHandler {
        GENERAL_DELIVERY {
            boolean handle(Mail m){
                switch (m.generaDelivery){
                    case YES:
                        print("Using general delivery for " + m);
                        return true;
                    default: return false;
                }
            }
        },
        MACHINE_SCAN {
            boolean handle(Mail m){
                switch (m.scannability){
                    case UNSCANNABLE: return false;
                    default:
                        switch (m.addres){
                            case INCORRECT: return false;
                            default:
                                print("Delivering "+ m + " automatically");
                                return  true;
                        }
                }
            }
        },
        VISUAL_INSPECTION {
            boolean handle(Mail m){
                switch (m.readability){
                    case ILLEGBIBLE: return false;
                    default:
                        switch (m.addres){
                            case INCORRECT: return false;
                            default:
                                print("Delivering " + m + "normally");
                                return true;
                        }
                }
            }
        },
        RETURN_TO_SENDER{
            boolean handle(Mail m){
                switch(m.returnAddres){
                    case MISSING: return false;
                    default:
                        print("Returning " + m + "to sender");
                        return true;
                }
            }
        };

        abstract boolean handle(Mail m);
    }

    static void handle( Mail m ){
        for(MailHandler handler : MailHandler.values()){
            if(handler.handle(m)){
                return;
            }
        }
        print(m + "is a dead letter");
    }

    public static void main(String[] args) {
        for(Mail m : Mail.generator(10)){
            print(m.details());
            handle(m);
            print("==========================");
        }
    }

}
