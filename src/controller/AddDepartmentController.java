package controller;

import database.DatabaseHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Department;
import model.User;
import utils.Constants;
import utils.Utils;

public class AddDepartmentController {

    @FXML
    private TextField department_name;

    @FXML
    private TextField about_department;

    @FXML
    private Text msg;

    @FXML
    void addDepartment(ActionEvent event) {

        if (    !Utils.checkTextError(department_name, msg, "Please enter Department name")
                || !Utils.checkTextError(about_department, msg, "Please enter about Department")){
            return;
        }
        msg.setVisible(false);

        DatabaseHelper.getInstance().addDepartment(new Department(
                0,
                department_name.getText(),
                about_department.getText()
        ));

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

    private void setProperty() {
        msg.setVisible(false);
    }
}
