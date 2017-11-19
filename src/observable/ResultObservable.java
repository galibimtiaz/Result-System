package observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Result;

/**
 * Created by mtush on 2/13/2017.
 */
public class ResultObservable {

    public ObservableList<Result> results;

    public ResultObservable(){
        this.results = FXCollections.observableArrayList();
    }
}
