package enumUnit;

/**
 * Created by xudong on 2018/6/15.
 */

enum Signal { GREEN, YELLOW, RED, };

public class TrafficLight {
        Signal color = Signal.RED;

        public void change(){
            switch(color){
                //注意不用写成 Signal.RED
                case RED:   color = Signal.GREEN;
                            break;
                case GREEN: color = Signal.YELLOW;
                            break;
                case YELLOW: color = Signal.RED;
                            break;
            }
        }

        @Override
        public String toString(){
            return "the trafficLight is " + color;
        }

    public static void main(String[] args) {
        TrafficLight tra = new TrafficLight();
        for(int i=0; i<7; i++ ){
            System.out.println(tra);
            tra.change();
        }

    }


}
