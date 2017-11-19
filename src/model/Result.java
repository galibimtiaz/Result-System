package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by mtush on 2/4/2017.
 */
public class Result {

    private  SimpleIntegerProperty id;



    private SimpleIntegerProperty student_id;
    private SimpleStringProperty studentName;
    private SimpleIntegerProperty year;
    private SimpleStringProperty semester;
    private SimpleStringProperty courseCode;
    private SimpleDoubleProperty creditHour;



    private SimpleDoubleProperty cgpa;
    private SimpleStringProperty subjectName;
    private SimpleStringProperty grade;
    private SimpleIntegerProperty total;

    private SimpleIntegerProperty class_test;
    private SimpleIntegerProperty assignment;
    private SimpleIntegerProperty attendance;
    private SimpleIntegerProperty mid;
    private SimpleIntegerProperty _final;

    public Result(){

    }


    public Result(int id, int student_id, String studentName, int year, String semester, String courseCode, Double creditHour, String subjectName, String grade, int total, int class_test, int assignment, int attendance, int mid, int _final, double cgpa) {
        this.id =  new SimpleIntegerProperty(id);
        this.student_id = new SimpleIntegerProperty(student_id);
        this.studentName =  new SimpleStringProperty(studentName);
        this.year = new SimpleIntegerProperty(year);
        this.semester =  new SimpleStringProperty(semester);
        this.courseCode =  new SimpleStringProperty(courseCode);
        this.cgpa = new SimpleDoubleProperty(cgpa);
        this.creditHour = new SimpleDoubleProperty(creditHour);
        this.subjectName =  new SimpleStringProperty(subjectName);
        this.grade =  new SimpleStringProperty(grade);
        this.total =  new SimpleIntegerProperty(total);
        this.class_test = new SimpleIntegerProperty(class_test);
        this.assignment = new SimpleIntegerProperty(assignment);
        this.attendance = new SimpleIntegerProperty(attendance);
        this.mid = new SimpleIntegerProperty(mid);
        this._final = new SimpleIntegerProperty(_final);
    }




    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getStudent_id() {
        return student_id.get();
    }

    public void setStudent_id(int student_id) {
        this.student_id.set(student_id);
    }

    public String getStudentName() {
        return studentName.get();
    }

    public void setStudentName(String studentName) {
        this.studentName.set(studentName);
    }

    public double getCgpa() {
        return cgpa.get();
    }

    public void setCgpa(double cgpa) {
        this.cgpa.set(cgpa);
    }

    public int getYear() {
        return year.get();
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public String getSemester() {
        return semester.get();
    }

    public void setSemester(String semester) {
        this.semester.set(semester);
    }

    public String getCourseCode() {
        return courseCode.get();
    }

    public void setCourseCode(String courseCode) {
        this.courseCode.set(courseCode);
    }

    public Double getCreditHour() {
        return creditHour.get();
    }

    public void setCreditHour(Double creditHour) {
        this.creditHour.set(creditHour);
    }

    public String getSubjectName() {
        return subjectName.get();
    }

    public void setSubjectName(String subjectName) {
        this.subjectName.set(subjectName);
    }

    public String getGrade() {
        return grade.get();
    }

    public void setGrade(String grade) {
        this.grade.set(grade);
    }

    public int getTotal() {
        return total.get();
    }

    public void setTotal(int total) {
        this.total.set(total);
    }

    public int getClass_test() {
        return class_test.get();
    }

    public void setClass_test(int class_test) {
        this.class_test.set(class_test);
    }

    public int getAssignment() {
        return assignment.get();
    }

    public void setAssignment(int assignment) {
        this.assignment.set(assignment);
    }

    public int getAttendance() {
        return attendance.get();
    }

    public void setAttendance(int attendance) {
        this.attendance.set(attendance);
    }

    public int getMid() {
        return mid.get();
    }

    public void setMid(int mid) {
        this.mid.set(mid);
    }

    public int get_final() {
        return _final.get();
    }

    public void set_final(int _final) {
        this._final.set(_final);
    }






    public SimpleIntegerProperty idProperty() {
        return id;
    }


    public SimpleIntegerProperty student_idProperty() {
        return student_id;
    }


    public SimpleStringProperty studentNameProperty() {
        return studentName;
    }


    public int yearProperty() {
        return year.get();
    }


    public SimpleStringProperty semesterProperty() {
        return semester;
    }


    public SimpleStringProperty courseCodeProperty() {
        return courseCode;
    }


    public SimpleDoubleProperty creditHourProperty() {
        return creditHour;
    }


    public SimpleDoubleProperty cgpaProperty() {
        return cgpa;
    }


    public SimpleStringProperty subjectNameProperty() {
        return subjectName;
    }


    public SimpleStringProperty gradeProperty() {
        return grade;
    }


    public SimpleIntegerProperty totalProperty() {
        return total;
    }


    public SimpleIntegerProperty class_testProperty() {
        return class_test;
    }


    public SimpleIntegerProperty assignmentProperty() {
        return assignment;
    }


    public SimpleIntegerProperty attendanceProperty() {
        return attendance;
    }


    public SimpleIntegerProperty midProperty() {
        return mid;
    }


    public SimpleIntegerProperty finalProperty() {
        return _final;
    }


    public String toSQL() {
        return "("+null+"" +
                ","+student_id.get()+"" +
                ",'"+studentName.get()+"'" +
                ","+year.get()+"" +
                ",'"+semester.get()+"'" +
                ",'"+courseCode.get()+"'" +
                ","+creditHour.get()+"" +
                ",'"+subjectName.get()+"'" +
                ",'"+grade.get()+"'" +
                ","+ total.get() +"" +
                ","+class_test.get()+"" +
                ","+assignment.get()+"" +
                ","+attendance.get()+"" +
                ","+mid.get()+"" +
                ","+_final.get()+"" +
                ","+cgpa.get()+"" +
                ");";
    }



}
