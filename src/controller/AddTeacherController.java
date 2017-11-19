package controller;

import database.DatabaseHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Department;
import model.Subject;
import model.User;
import utils.Constants;
import utils.Utils;

import java.util.ArrayList;


/**
 * Created by mtush on 2/23/2017.
 */
public class AddTeacherController {

    @FXML
    private TextField teachers_name;

    @FXML
    private TextField teachers_id;

    @FXML
    private ComboBox<Department> dept;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private ListView<Subject> existingSubjects;

    @FXML
    private ListView<Subject> addedSubjects;

    @FXML
    private ComboBox<String> semester;

    @FXML
    private ComboBox<Integer> year;

    @FXML
    private Text msg;

    private ArrayList<Subject> currentSubjects;

    @FXML
    void addTeacher(ActionEvent event) {

        if (    !Utils.checkTextError(teachers_name, msg, "Please enter Teacher name")
                || !Utils.checkTextError(teachers_id, msg, "Please enter Teacher ID")
                || !Utils.checkTextError(year, msg, "Please enter Year")
                || !Utils.checkTextError(username, msg, "Please enter Teacher User Name")
                || !Utils.checkTextError(password, msg, "Please enter Teacher Password")
                || !Utils.checkTextError(dept, msg, "Please enter Teacher Department")
                || !Utils.checkTextError(semester, msg, "Please enter Teacher Semester")) {
            return;
        }

        if(!Utils.isInteger(teachers_id.getText())){
            msg.setText("ID must be a Number");
            return;
        }

        if(currentSubjects.isEmpty()){
            msg.setText("Please Selected Some Subjects");
            return;
        }


        msg.setVisible(false);

        User user = DatabaseHelper.getInstance().newUser(username.getText(),password.getText(),
                new User(0,
                        teachers_name.getText(),
                        0,
                        dept.getValue().getName(),
                        Constants.TEACHER
                        ,Integer.parseInt(teachers_id.getText())));

        for (Subject item : currentSubjects) {
            DatabaseHelper.getInstance().addUserSubjects(user.getId_number(),item.getId(),
                    year.getValue(),semester.getValue());
        }
        
        close(event);
    }

    @FXML
    void close(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }



    @FXML
    private void initialize(){

        setProperty();
    }

    @FXML
    void depChange(ActionEvent event) {
        existingSubjects.getItems().setAll(DatabaseHelper.getInstance().getSubjectsByDepartment(dept.getSelectionModel().getSelectedItem().getName()));
    }

    private void setProperty() {


        currentSubjects = new ArrayList<>();

        semester.getItems().addAll("Spring", "Summer", "Fall");
        year.getItems().addAll(Utils.range(2016,2100));

        dept.getItems().addAll(DatabaseHelper.getInstance().getDepartments());
        dept.setConverter(new StringConverter<Department>() {
            @Override
            public String toString(Department object) {
                return object.getName();
            }

            @Override
            public Department fromString(String string) {
                return null;
            }
        });

        dept.setCellFactory(param ->  new ListCell<Department>() {
            @Override
            protected void updateItem(Department item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });



        existingSubjects.setCellFactory(param ->  new ListCell<Subject>() {
            @Override
            protected void updateItem(Subject item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getSub_code() == null) {
                    setText(null);
                } else {
                    setText(item.getSub_code());
                }
            }
        });
        addedSubjects.setCellFactory(existingSubjects.getCellFactory());


        msg.setVisible(false);
    }

    @FXML
    void addSubject(ActionEvent event) {

        Subject selected = existingSubjects.getSelectionModel().getSelectedItem();

        System.out.println(existingSubjects.getSelectionModel().getSelectedItem());
        if(!currentSubjects.contains(selected) && selected != null){
            currentSubjects.add(selected);
            addedSubjects.getItems().setAll(currentSubjects);
        }
    }


    @FXML
    void removeSubject(ActionEvent event) {
        currentSubjects.remove(addedSubjects.getSelectionModel().getSelectedItem());
        addedSubjects.getItems().setAll(currentSubjects);
    }


}
