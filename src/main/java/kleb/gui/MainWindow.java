package kleb.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import kleb.Kleb;

/**
 * Controller for the MainWindow.
 * This provides the logic for the main GUI window of the application.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Kleb kleb;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image klebImage = new Image(this.getClass().getResourceAsStream("/images/kleb.png"));

    /**
     * Initializes the controller class.
     * This method is automatically called after the FXML file has been loaded.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Sets the Kleb instance for the controller.
     * This links the GUI to the main application logic.
     *
     * @param k The Kleb instance to be used.
     */
    public void setKleb(Kleb k) {
        kleb = k;
    }

    /**
     * Handles the user input event.
     * It reads user input, gets a response from Kleb, and updates the dialog container.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = kleb.handleCommand(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getKlebDialog(response, klebImage)
        );
        userInput.clear();
    }
}

