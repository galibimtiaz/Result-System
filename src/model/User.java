package model;

/**
 * Created by mtush on 2/9/2017.
 */
public class User {

    private int id;
    private String name;
    private int user_id;
    private String dept;
    private String userType;
    private int id_number;


    public User(){

    }

    public User(int id, String name, int user_id, String dept, String userType, int id_number) {
        this.id = id;
        this.name = name;
        this.user_id = user_id;
        this.dept = dept;
        this.userType = userType;
        this.id_number = id_number;
    }


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String toSQL() {
        return "("+null+",'"+name+"','"+dept+"','"+userType+"',"+ id_number +","+ user_id +");";
    }

    @Override
    public String toString() {
        return "("+null+",'"+name+"','"+dept+"','"+userType+"',"+ id_number +","+ user_id +");";
    }

    public int getId_number() {
        return id_number;
    }

    public void setId_number(int id_number) {
        this.id_number = id_number;
    }
}
