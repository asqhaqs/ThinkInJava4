package annotations;

/**
 * Created by xudong on 2018/6/21.
 * p626
 */
@DBTable(name = "MEMBER")
public class Member {
    @SQLInteger Integer age;
    @SQLString(30) String firstName;
    @SQLString(50) String lastName;
    @SQLString(value = 30, constraints = @Constraints(primaryKey = true)) String handle;
    static int memberCount;
    public String getHandle(){
        return handle;
    }
    public String getFirstName(){
        return  firstName;
    }
    public String getLastName(){
        return lastName;
    }
    @Override
    public String toString(){
        return handle;
    }
    public Integer getAge(){
        return age;
    }
}
