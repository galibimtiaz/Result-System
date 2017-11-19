package app;

import javafx.application.Application;
import javafx.stage.Stage;
import utils.Utils;


public class Main extends Application {

    private static Stage pStage;


    @Override
    public void start(Stage primaryStage) throws Exception{



        pStage = primaryStage;

        Utils.changeScreen("/views/login.fxml","Log In");

        primaryStage.show();

    }

    public static Stage getPrimaryStage() {
        return pStage;
    }


   /* public static void main(String[] args) {
        launch(args);
    }*/
}
