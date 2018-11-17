package concurrency;

/**
 * Created by xudong on 2018/7/7.
 */
public class SerialNumberGenerator {
        private static volatile int serialNumber = 0;
        public static int nextSerialNumber(){
            return  serialNumber++;
        }
}
