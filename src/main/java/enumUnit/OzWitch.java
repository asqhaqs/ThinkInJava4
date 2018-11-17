package enumUnit;

/**
 * Created by xudong on 2018/6/15.
 */
public enum  OzWitch {
    WEST("Miss Gulch, aka the Wicked Witch of the West"),
    NORTH("Glinda, the Good Witch of the North"),
    EAST("Wicked Witch of the East, wearer of the Ruby Slippers, crushed by Dorothy's house"),
    SOUTH("Good by inference, but missing");

    private String description;

    //构造函数必须是包或者私有函数
    private OzWitch(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public static void main(String[] args) {
        for(OzWitch with: OzWitch.values()){
            System.out.println(with+" "+with.getDescription());
        }
    }
}
