package kleb.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import kleb.Kleb;

/**
 * The main entry point for the Kleb GUI application.
 * This class sets up the JavaFX stage and loads the main window.
 */
public class Main extends Application {
    private Kleb kleb = new Kleb("./data/tasks.txt");

    /**
     * Starts and shows the primary stage of the application.
     * This method is called by the JavaFX runtime to launch the GUI.
     *
     * @param stage The primary stage for this application.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            stage.setMaxWidth(417);
            fxmlLoader.<MainWindow>getController().setKleb(kleb);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
