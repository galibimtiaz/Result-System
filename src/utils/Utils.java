package utils;

import app.Main;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Subject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utils {

    public static void showDialog(String filePath, String windowName) {

        Stage addStudentWindow = new Stage();
        addStudentWindow.initModality(Modality.WINDOW_MODAL);
        addStudentWindow.initOwner(Main.getPrimaryStage());
        addStudentWindow.setTitle(windowName);

        Parent addStudentLayout = null;
        try {
            addStudentLayout = FXMLLoader.load(filePath.getClass().getResource(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene addStudentScene = new Scene(addStudentLayout);
        addStudentWindow.setScene(addStudentScene);
        addStudentWindow.showAndWait();
    }

    public static void changeScreen(String filePath, String windowName){
        Stage main_stage        = Main.getPrimaryStage();
        Parent parent      = null;
        try {
            parent = FXMLLoader.load(filePath.getClass().getResource(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene loginScene        = new Scene(parent);
        main_stage.setTitle(windowName);
        main_stage.setScene(loginScene);

    }


    public static Boolean checkTextError(TextField testText, Text error, String message){
        if(testText.getText() == null || Objects.equals(testText.getText(), "")){
            error.setVisible(true);
            error.setText(message);
            return false;
        } else {
            return true;
        }
    }

    public static Boolean checkTextError(ComboBox testText, Text error, String message){
        if(testText.getValue() == null ||testText.getValue() == ""){
            error.setVisible(true);
            error.setText(message);
            return false;
        } else {
            return true;
        }
    }


    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }



    public static List<Integer> range(int startYear, int endYear) {
        int cur = startYear;
        List<Integer> list = new ArrayList<>();
        while (cur <= endYear) {
            list.add(cur++);
        }
        return list;
    }


    public static String getGrade(int cgpa) {

        String grade = "--";

        if(cgpa >= 80) {
            grade = "A+";
        } else if((cgpa >= 75)) {
            grade = "A";
        } else if((cgpa >= 70) ) {
            grade = "A-";
        } else if((cgpa >= 65)) {
            grade = "B+";
        }else if((cgpa >= 60)) {
            grade = "B";
        } else if((cgpa >= 55)) {
            grade = "B-";
        }else if((cgpa >= 50)) {
            grade = "C+";
        }else if((cgpa >= 45)) {
            grade = "C";
        }else if((cgpa >= 40)) {
            grade = "D";
        }else if((cgpa >= 0)) {
            grade = "F";
        }


        return grade;
    }


    public static double getPoint(int cgpa) {

        Double point;

        if(cgpa >= 80) {
            point = 4.00;
        } else if(cgpa >= 75) {
            point = 3.75;
        } else if(cgpa >= 70) {
            point = 3.50;
        } else if(cgpa >= 65) {
            point = 3.25;
        }else if(cgpa >= 60) {
            point = 3.00;
        } else if(cgpa >= 55) {
            point = 2.75;
        }else if(cgpa >= 50) {
            point = 2.50;
        }else if(cgpa >= 45) {
            point = 2.25;
        }else if(cgpa >= 40) {
            point = 2.00;
        }else if(cgpa >= 0) {
            point = 0.00;
        } else {
            point = 0.00;
        }


        return point;
    }

    public static boolean containsId(ArrayList<Subject> list, String id) {
        for (Subject object : list) {
            if (Objects.equals(object.getSub_code(), id)) {
                return true;
            }
        }
        return false;
    }

    public  static String shortenDouble(Double value){

        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(value);

    }


    public static void printNode(final Node node) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout
                = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        PrinterAttributes attr = printer.getPrinterAttributes();
        PrinterJob job = PrinterJob.createPrinterJob();
        double scaleX
                = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
        double scaleY
                = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
        Scale scale = new Scale(.65, .65);

        node.getTransforms().add(scale);


        if (job != null && job.showPrintDialog(node.getScene().getWindow())) {
            boolean success = job.printPage(pageLayout, node);
            if (success) {
                job.endJob();

            }
        }
        node.getTransforms().remove(scale);
    }


}
