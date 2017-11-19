package model;

public class User_Subjects {



    private int id;
    private int id_number;
    private int subject_id;
    private String semester;
    private int year;

    public User_Subjects() {
    }

    public User_Subjects(int id, int id_number, int subject_id, String semester, int year) {
        this.id = id;
        this.id_number = id_number;
        this.subject_id = subject_id;
        this.semester = semester;
        this.year = year;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_number() {
        return id_number;
    }

    public void setId_number(int id_number) {
        this.id_number = id_number;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }



    public String toSQL() {
        return "("+null+","+id_number+","+subject_id+",'"+ semester +"','+year+');";
    }

}
