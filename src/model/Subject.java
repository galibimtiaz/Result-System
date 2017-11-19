package model;

public class Subject {

    private int id;
    private String sub_code;
    private String name;
    private String dept;
    private Double creditHour;

    public Subject() {
    }

    public Subject(int id, String subCode, String name, String dept,Double creditHour) {
        this.id = id;
        this.sub_code = subCode;
        this.name = name;
        this.dept = dept;
        this.creditHour = creditHour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSub_code() {
        return sub_code;
    }

    public void setSub_code(int String) {
        this.sub_code = sub_code;
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


    public String toSQL() {
        return "("+null+",'"+sub_code+"','"+name+"','"+dept+"',"+creditHour+");";
    }

    public Double getCreditHour() {
        return creditHour;
    }

    public void setCreditHour(Double creditHour) {
        this.creditHour = creditHour;
    }
}
