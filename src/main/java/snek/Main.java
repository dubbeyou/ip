package snek;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import snek.ui.MainWindow;

/**
 * A GUI for Snek using FXML.
 */
public class Main extends Application {

    private Snek snek = new Snek("data/tasks.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setTitle("snek");
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setSnek(snek);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
