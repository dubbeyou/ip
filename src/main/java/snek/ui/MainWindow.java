package snek.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import snek.Snek;
import snek.commands.Command;
import snek.data.exception.SnekException;
import snek.parser.Parser;

/**
 * Controller for the main GUI.
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

    private Snek snek;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/rat.jpg"));
    private Image snekImage = new Image(this.getClass().getResourceAsStream("/images/snek.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Snek instance */
    public void setSnek(Snek s) {
        snek = s;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing
     * Snek's reply and then appends them to the dialog container. Clears the
     * user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        Command command = null;
        String response;

        try {
            command = Parser.parse(input);
            response = snek.getResponse(command);
        } catch (SnekException e) {
            response = e.getMessage();
        }

        dialogContainer.getChildren().addAll(DialogBox.getUserDialog(input, userImage),
                                        DialogBox.getSnekDialog(response, snekImage));

        if (command != null && command.isExit()) {
            Platform.exit();
        }

        userInput.clear();
    }
}
