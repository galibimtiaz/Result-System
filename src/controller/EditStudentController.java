package controller;

import database.DatabaseHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Department;
import model.Result;
import model.Subject;
import model.User;
import utils.Constants;
import utils.Utils;

import java.util.ArrayList;

import static utils.Utils.containsId;


/**
 * Created by mtush on 2/23/2017.
 */
public class EditStudentController {


    @FXML
    private ComboBox<User> students;

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

    private User user;

    @FXML
    void editStudent(ActionEvent event) {

        if (!Utils.checkTextError(year, msg, "Please enter Year")
                || !Utils.checkTextError(students, msg, "Please enter Students")
                || !Utils.checkTextError(semester, msg, "Please enter Students Semester")) {
            return;
        }


        if (currentSubjects.isEmpty()) {
            msg.setText("Please Selected Some Subjects");
            return;
        }


        msg.setVisible(false);

        DatabaseHelper.getInstance().updateUser(user);

        ArrayList<Subject> pre = DatabaseHelper.getInstance().getSubjectsbyIdYearsemester(user.getId_number(), semester.getValue(), year.getValue());

        for (Subject item : currentSubjects) {
            if (!containsId(pre, item.getSub_code())) {
                System.out.println("Added " +item.getSub_code());
                DatabaseHelper.getInstance().addUserSubjects(user.getId_number(), item.getId(),
                        year.getValue(), semester.getValue());

                DatabaseHelper.getInstance().addResult(
                        new Result(
                                0,
                                user.getId_number(),
                                user.getName(),
                                year.getValue(),
                                semester.getValue(),
                                item.getSub_code(),
                                item.getCreditHour(),
                                item.getName(),
                                "F",
                                0,
                                0,
                                0,
                                0,
                                0,
                                0,
                                0.0
                        ));
            }
        }

        for (Subject item : pre) {
            if (!containsId(currentSubjects, item.getSub_code())) {
                System.out.println("Removed " +item.getId());
                DatabaseHelper.getInstance().removeUserSubjects(item.getId());
                DatabaseHelper.getInstance().removeUserResult(semester.getValue(),item.getSub_code(),year.getValue());
            }
        }

        close(event);
    }

    @FXML
    void close(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }


    @FXML
    private void initialize() {

        setProperty();
    }


    private void setProperty() {


        currentSubjects = new ArrayList<>();

        semester.getItems().addAll("Spring", "Summer", "Fall");
        year.getItems().addAll(Utils.range(2016, 2100));

        students.getItems().addAll(DatabaseHelper.getInstance().getUsersByType(Constants.STUDENT));

        students.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User object) {
                return object.getName() + " - ID " + object.getId_number();
            }

            @Override
            public User fromString(String string) {
                return null;
            }
        });


        existingSubjects.setCellFactory(param -> new ListCell<Subject>() {
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

        //System.out.println(existingSubjects.getSelectionModel().getSelectedItem());
        if (selected != null) {
            if (!containsId(currentSubjects, selected.getSub_code())) {
                currentSubjects.add(selected);
                addedSubjects.getItems().setAll(currentSubjects);
            }
        }
    }


    @FXML
    void removeSubject(ActionEvent event) {
        currentSubjects.remove(addedSubjects.getSelectionModel().getSelectedItem());
        addedSubjects.getItems().setAll(currentSubjects);
    }


    @FXML
    void semesterChange(ActionEvent event) {
        update();
    }

    @FXML
    void studentChange(ActionEvent event) {
        update();
    }

    @FXML
    void yearChange(ActionEvent event) {
        update();
    }


    private void update() {

        user = students.getSelectionModel().getSelectedItem();
        existingSubjects.getItems().setAll(DatabaseHelper.getInstance().getSubjectsByDepartment(user.getDept()));


        if (user != null && semester.getValue() != null && year.getValue() != null) {
            currentSubjects = DatabaseHelper.getInstance().getSubjectsbyIdYearsemester(user.getId_number(), semester.getValue(), year.getValue());
            addedSubjects.getItems().setAll(currentSubjects);
        } else {
            currentSubjects.clear();
            addedSubjects.getItems().clear();
        }
    }


}
