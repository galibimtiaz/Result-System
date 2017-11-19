package controller;

import database.DatabaseHelper;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import model.Result;
import model.User;
import observable.ResultObservable;
import utils.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static utils.Utils.printNode;
import static utils.Utils.shortenDouble;

public class StudentDashController {

    @FXML
    private Label heading;

    @FXML
    private VBox print_area;

    @FXML
    private Text designation;

    @FXML
    private Text student_roll;

    @FXML
    private Text cgpaTotal;

    @FXML
    private Text name;

    @FXML
    private VBox tableField;

    private ArrayList<String> tablename;
    private ArrayList<ObservableList<Result>> resultData;

    private int semesterSum = 0;
    private Double gpaSum = 0.0;

    User user = null;

    @FXML
    void changeProfile(ActionEvent event) {

    }

    @FXML
    void editProfile(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {
        Utils.changeScreen("/views/login.fxml","Log In");
    }

    @FXML
    void print_result(ActionEvent event) {

        Text student_name = new Text("Name : " +user.getName());
        Text student_id = new Text("ID : " +user.getId_number());
        Text student_cgpa = new Text("Cumulative GPA : "+  shortenDouble(gpaSum/semesterSum));

        tableField.getChildren().add(0,student_name);
        tableField.getChildren().add(1,student_id);
        tableField.getChildren().add(2,student_cgpa);



        try {
            printNode(tableField);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        tableField.getChildren().remove(student_name);
        tableField.getChildren().remove(student_id);
        tableField.getChildren().remove(student_cgpa);

    }

    @FXML
    private void initialize() {
        user = DatabaseHelper.getInstance().getCurrentUser();
        name.setText(user.getName());
        designation.setText("Student of "+user.getDept()+" Department");
        student_roll.setText("Student ID "+user.getId_number());
        populateResult();
    }






    //Ready Result....
    private void getResult(){

        resultData  = new ArrayList<>();
        tablename   = new ArrayList<>();
        int i = 0;
        int j = 0;

        ArrayList<Integer> years = DatabaseHelper.getInstance().getYearsByUserId(user.getId_number());

        for (int year : years) {

            ArrayList<String> semesters = DatabaseHelper.getInstance().getSemesterByYear(year,user.getId_number());

            for (String semester : semesters) {
                semesterSum++;
                tablename.add(i, year+" " + semester);
                i++;

                ResultObservable r = new ResultObservable();
                ArrayList<Result> results = DatabaseHelper.getInstance().getResultsByYearSemester(semester,year,user.getId_number());

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

                tableField.getChildren().addAll(tname, table, textBox);

            }

        cgpaTotal.setText("Cumulative GPA : "+ shortenDouble(gpaSum/semesterSum));
    }





}
