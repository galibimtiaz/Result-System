package controller;

import database.DatabaseHelper;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import model.Result;
import model.Subject;
import model.User;
import model.User_Subjects;
import observable.ResultObservable;
import utils.Constants;
import utils.Utils;

import java.util.ArrayList;

public class TeacherDashController implements EventHandler<Event> {


    @FXML
    private Label heading;

    @FXML
    private Text name;

    @FXML
    private Text designation;

    @FXML
    private Text dep_year;

    @FXML
    private TabPane subtabs;

    @FXML
    private TableView<Result> student_table;

    @FXML
    private TableColumn<Result, Integer> classTest;

    @FXML
    private TableColumn<Result, Integer> assignment;

    @FXML
    private TableColumn<Result, Integer> attendence;

    @FXML
    private TableColumn<Result, Integer> mid;

    @FXML
    private TableColumn<Result, Integer> _final;

    @FXML
    private TableColumn<Result, Double> creditHour;

    @FXML
    private TableColumn<Result, Integer> gpa;

    @FXML
    private TableColumn<Result, String> grade;

    @FXML
    private ComboBox<String> semester;

    @FXML
    private ComboBox<Integer> year;


    private User user;

    private ResultObservable resultData;
    private ArrayList<Subject> subjects;

    @FXML
    void changePassword(ActionEvent event) {

    }

    @FXML
    void editTeacherProfile(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {
        Utils.changeScreen("/views/login.fxml", "Log In");
    }

   /* @FXML
    void showResultForm(ActionEvent event) {
        Utils.showDialog("/views/result_form.fxml", "Result Form");
        showStudents();
    }*/

    @FXML
    private void initialize() {
        user = DatabaseHelper.getInstance().getCurrentUser();


        year.getItems().addAll(DatabaseHelper.getInstance().getSubjectYearsByUserId(user.getId_number()));
        year.getSelectionModel().selectFirst();
        semester.getItems().addAll(DatabaseHelper.getInstance().getSubjectSemesterByYear(year.getValue(), user.getId_number()));
        semester.getSelectionModel().selectFirst();


        classTest.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        assignment.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        attendence.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        mid.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        _final.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        creditHour.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));



        name.setText(user.getName());
        designation.setText("Lecturer of " + user.getDept() + " Department");
        resultData = new ResultObservable();
        student_table.setItems(resultData.results);
        student_table.getSelectionModel().cellSelectionEnabledProperty().set(true);


        update(null);
    }


    private void showStudents() {

        resultData.results.clear();
        resultData.results.addAll(DatabaseHelper.getInstance().getResultsBySubject(subjects.get(0).getSub_code(), year.getValue()));
    }

    @Override
    public void handle(Event event) {

        for (Subject item : subjects) {
            if (item.getName().equals(((Tab) event.getSource()).getText())) {
                resultData.results.clear();
                resultData.results.addAll(DatabaseHelper.getInstance().getResultsBySubject(item.getSub_code(), year.getValue()));
            }
        }

    }

    @FXML
    void onEditCommit(TableColumn.CellEditEvent event) {

        //event.getTableColumn()
        Result current = ((Result) event.getTableView().getItems().get(event.getTablePosition().getRow()));

        switch (event.getTableColumn().getId()) {

            case "classTest": {
                System.out.println(event.getTableColumn().getId());
                current.setClass_test((Integer) event.getNewValue());
            }
            break;

            case "assignment": {
                System.out.println(event.getTableColumn().getId());
                current.setAssignment((Integer) event.getNewValue());
            }
            break;

            case "attendence": {
                System.out.println(event.getTableColumn().getId());
                current.setAttendance((Integer) event.getNewValue());
            }
            break;

            case "mid": {
                System.out.println(event.getTableColumn().getId());
                current.setMid((Integer) event.getNewValue());
            }
            break;

            case "_final": {
                System.out.println(event.getTableColumn().getId());
                current.set_final((Integer) event.getNewValue());
            }
            break;

            case "creditHour": {
                System.out.println(event.getTableColumn().getId());
                current.setCreditHour((Double) event.getNewValue());
            }
            break;
        }


        current.setTotal(calculateTotal(current.getClass_test(), current.getAssignment(), current.getAttendance(), current.getMid(), current.get_final()));
        current.setGrade(Utils.getGrade(current.getTotal()));
        current.setCgpa(Utils.getPoint(current.getTotal()) * current.getCreditHour());

        DatabaseHelper.getInstance().updateResult(current);

        System.out.println(current.getId());
    }

    @FXML
    void update(ActionEvent event) {

        if (year.getValue() != null && semester.getValue() != null) {

            dep_year.setText(semester.getValue() + " " + year.getValue());
            subtabs.getTabs().clear();
            subjects = DatabaseHelper.getInstance().getSubjectsbyIdNumber(user.getId_number(), semester.getValue(), year.getValue());
            for (Subject item : subjects) {
                Tab subjectTab = new Tab(item.getName());
                subtabs.getTabs().add(subjectTab);
                subjectTab.setOnSelectionChanged(this);
            }

            showStudents();

        }


    }

    private String getGrade(int cgpa) {

        String grade;

        if (cgpa >= 12) {
            grade = "A+";
        } else if (cgpa >= 10) {
            grade = "A";
        } else if (cgpa >= 8) {
            grade = "A-";
        } else if (cgpa >= 6) {
            grade = "B+";
        } else if (cgpa >= 4) {
            grade = "B";
        } else if (cgpa >= 2) {
            grade = "B-";
        } else {
            grade = "F";
        }


        return grade;
    }


    private int calculateTotal(int classTest, int assignment, int attendance, int mid, int final_) {

        return (classTest + assignment + attendance + mid + final_);
    }

    ;


}
