package controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import database.DatabaseHelper;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Result;
import model.User;
import observable.ResultObservable;
import utils.Constants;
import utils.Utils;

import static utils.Utils.printNode;
import static utils.Utils.shortenDouble;

/**
 * Created by mtush on 1/28/2017.
 */
public class AdminController {

    @FXML
    private Label heading;

    @FXML
    private Text name;

    @FXML
    private Text designation;

    @FXML
    private TextField student_serach_id;

    @FXML
    private VBox printable_area;

    @FXML
    private Text student_name;

    @FXML
    private Text student_id;

    @FXML
    private Text student_dep;

    @FXML
    private Text student_cgpa;

    @FXML
    private VBox resultTableContainer;



    User user;
    User student;

    private ArrayList<String> tablename;
    private ArrayList<ObservableList<Result>> resultData;

    private int semesterSum = 0;
    private Double gpaSum = 0.0;

    @FXML
    void addStudent(ActionEvent event) {
        Utils.showDialog("/views/add_student.fxml","Add Student");
    }

    @FXML
    void addDepartment(ActionEvent event) {
        Utils.showDialog("/views/add_department.fxml","Add Department");
        System.out.println("Just Closed");
    }

    @FXML
    void addSubject(ActionEvent event) {
        Utils.showDialog("/views/add_subject.fxml","Add Subject");
    }

    @FXML
    void addTeacher(ActionEvent event) {
        Utils.showDialog("/views/add_teacher.fxml", "Add Teacher");
    }

    @FXML
    void changeProfile(ActionEvent event) {

    }

    @FXML
    void editProfile(ActionEvent event) {

    }

    @FXML
    void Print(ActionEvent event) {

        Text student_name = new Text("Name : " +student.getName());
        Text student_id = new Text("ID : " +student.getId_number());
        Text student_cgpa = new Text("Cumulative GPA : "+  shortenDouble(gpaSum/semesterSum));

        resultTableContainer.getChildren().add(0,student_name);
        resultTableContainer.getChildren().add(1,student_id);
        resultTableContainer.getChildren().add(2,student_cgpa);

        try {
            printNode(resultTableContainer);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        resultTableContainer.getChildren().remove(student_name);
        resultTableContainer.getChildren().remove(student_id);
        resultTableContainer.getChildren().remove(student_cgpa);

    }

    @FXML
    void editStudent(ActionEvent event) {
        Utils.showDialog("/views/edit_student.fxml", "Edit Student");
    }

    @FXML
    void editTeacher(ActionEvent event) {
        Utils.showDialog("/views/edit_teacher.fxml", "Edit Teacher");
    }

    @FXML
    void logout(ActionEvent event) {
        Utils.changeScreen("/views/login.fxml","Log In");
    }

    @FXML
    private void initialize() {

        user = DatabaseHelper.getInstance().getCurrentUser();
        name.setText(user.getName());

    }

    @FXML
    void findStudent(ActionEvent event) {

        student = DatabaseHelper.getInstance().getUserByIDNumber(Integer.parseInt(student_serach_id.getText()));

        if( student != null && student.getUserType().equals(Constants.STUDENT)){
            student_name.setText(student.getName());
            student_id.setText("ID : "+student.getId_number());
            student_dep.setText("Department : "+student.getDept());
            resultTableContainer.getChildren().clear();
            populateResult();
        }

    }





    //Ready Result....
    private void getResult(){

        resultData  = new ArrayList<>();
        tablename   = new ArrayList<>();
        int i = 0;
        int j = 0;

        ArrayList<Integer> years = DatabaseHelper.getInstance().getYearsByUserId(student.getId_number());
        for (int year : years) {
            ArrayList<String> semesters = DatabaseHelper.getInstance().getSemesterByYear(year,student.getId_number());

            for (String semester : semesters) {
                semesterSum++;
                tablename.add(i, year+" " + semester);
                i++;

                ResultObservable r = new ResultObservable();
                ArrayList<Result> results = DatabaseHelper.getInstance().getResultsByYearSemester(semester,year,student.getId_number());

                r.results.addAll(results);
                resultData.add(j , r.results);
                j++;
            }
        }
    }


    private void populateResult(){

        // preparing result.....
        getResult();

        // Generating tables
        for (int i = 0; i<resultData.size(); i++){

            // Course Code Column
            TableColumn<Result, String> courseCodeColumn = new TableColumn<>("Course Code");
            courseCodeColumn.setMinWidth(100);
            courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));

            // Subject Name Column
            TableColumn<Result, String> subjectNameColumn = new TableColumn<>("Subject Name");
            subjectNameColumn.setMinWidth(260);
            subjectNameColumn.setCellValueFactory(new PropertyValueFactory<>("subjectName"));

            // Grade Column
            TableColumn<Result, String> gradeColumn = new TableColumn<>("Grade");
            gradeColumn.setMinWidth(100);
            gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));

            // CGPA Column
            TableColumn<Result, String> cgpaColumn = new TableColumn<>("GP");
            cgpaColumn.setMinWidth(100);
            cgpaColumn.setCellValueFactory(new PropertyValueFactory<>("cgpa"));

            // Credit Hour Column
            TableColumn<Result, String> creditHourColumn = new TableColumn<>("Credit hour");
            creditHourColumn.setMinWidth(110);
            creditHourColumn.setCellValueFactory(new PropertyValueFactory<>("creditHour"));


            TableView table = new TableView<>();
            table.setItems(resultData.get(i));

            table.getColumns().addAll(
                    courseCodeColumn,
                    subjectNameColumn,
                    creditHourColumn,
                    gradeColumn,
                    cgpaColumn
            );

            Double cgpa = 0.0 ;
            Double credit_hour = 0.0 ;
            for (Result result : resultData.get(i)) {
                cgpa += result.getCgpa();
                credit_hour += result.getCreditHour();
            }

            table.setFixedCellSize(25);
            table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(28));

            Label tname = new Label(tablename.get(i));
            tname.setPadding(new Insets(24, 0, 8, 0));

            Double gpa = cgpa/credit_hour;
            gpaSum += gpa;
            Text t = new Text("GPA : " +gpa);

            VBox textBox = new VBox();
            textBox.getChildren().addAll(t);
            textBox.setAlignment(Pos.CENTER_LEFT);

            resultTableContainer.getChildren().addAll(tname, table, textBox);

        }

        student_cgpa.setText("Cumulative GPA : "+ shortenDouble(gpaSum/semesterSum));
    }



}
