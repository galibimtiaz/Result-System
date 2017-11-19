package controller;

import database.DatabaseHelper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import model.User;
import utils.Constants;
import utils.Utils;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by mtush on 1/26/2017.
 */


public class LoginController {


    @FXML
    private Text error_msg;

    @FXML
    private TextField username_txt;

    @FXML
    private PasswordField password_txt;

    @FXML
    private ComboBox<String> role_combo;

    @FXML
    private Button login_btn;

    private DatabaseHelper helper;

    @FXML
    void attemptLogin(ActionEvent event) throws IOException {

        //Loading the Stage from where the fxml of this controller is loaded to.
        User user = helper.LogIn(username_txt.getText(), password_txt.getText());

        if(user == null){
            error_msg.setText("Wrong Username or Password");
            error_msg.setVisible(true);
            return;
        }

        if (role_combo.getValue()== null || !Objects.equals(user.getUserType(), role_combo.getValue().toLowerCase())){
            error_msg.setText("Wrong Role Selected");
            error_msg.setVisible(true);
            return;
        }

        switch (user.getUserType()) {
            case Constants.TEACHER: {
                Utils.changeScreen("/views/teacher_dash.fxml","Teacher Panel");
                break;
            }
            case Constants.STUDENT: {
                Utils.changeScreen("/views/student_dash.fxml","Student Panel");

                break;
            }
            case Constants.ADMIN:
                Utils.changeScreen("/views/admin_dash.fxml","Admin Panel");
                break;
        }

    }

    @FXML
    private void initialize() {

        error_msg.setVisible(false);
        role_combo.getItems().setAll("Student","Teacher","Admin");
        helper =  DatabaseHelper.getInstance();

    }


}
