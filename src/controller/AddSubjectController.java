package controller;

import database.DatabaseHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Department;
import model.Subject;
import utils.Utils;

import java.util.Arrays;
import java.util.List;


/**
 * Created by mtush on 2/23/2017.
 */
public class AddSubjectController {


    @FXML
    private TextField subjectName;

    @FXML
    private TextField subjectCode;

    @FXML
    private ComboBox<Department> departments;

    @FXML
    private Text msg;

    @FXML
    private ComboBox<Double> sub_credit;

    @FXML
    void addSubject(ActionEvent event) {

        if (    !Utils.checkTextError(subjectName, msg, "Please enter Subject Name")
                || !Utils.checkTextError(subjectCode, msg, "Please enter Subject Code")
                || !Utils.checkTextError(departments, msg, "Please enter Subject's Department")
                || !Utils.checkTextError(sub_credit, msg, "Please enter Subject Credit")) {
            return;
        }

        msg.setVisible(false);

        DatabaseHelper.getInstance().addSubject(new Subject(
                0,
                subjectCode.getText(),
                subjectName.getText(),
                departments.getValue().getName(),
                sub_credit.getValue()));
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

        departments.getItems().addAll(DatabaseHelper.getInstance().getDepartments());

        departments.setConverter(new StringConverter<Department>() {
            @Override
            public String toString(Department object) {
                return object.getName();
            }

            @Override
            public Department fromString(String string) {
                return null;
            }
        });

        departments.setCellFactory(param ->  new ListCell<Department>() {
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
        List<Double> list =  Arrays.asList(
                3.0,1.5) ;
        sub_credit.getItems().addAll(list);


        msg.setVisible(false);
    }


}
