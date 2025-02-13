package ui.fx;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Main window for JavaFx Ui.
 */
public class DukeMainWindowController {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;

    private Image anakinImage;
    private Image yodaImage;
    private Image vaderImage;

    private List<FxDukeInput> fxDukeInputs;

    /**
     * Constructor for MainWindow component of the JavaFx application.
     */
    public DukeMainWindowController() {
        this.anakinImage = new Image(getClass().getResourceAsStream("/images/anakin.png"));
        this.yodaImage = new Image(getClass().getResourceAsStream("/images/yoda.png"));
        this.vaderImage = new Image(getClass().getResourceAsStream("/images/vader.png"));

        this.fxDukeInputs = new ArrayList<>();
    }

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing duke.Duke's reply and then appends
     * them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();

        if (!input.equals("")) {
            printUserMessage(input);
        }

        if (!input.equals("") && this.fxDukeInputs.size() > 0) {
            this.fxDukeInputs.forEach(fxDukeInput -> fxDukeInput.receiveInput(input));
        }

        userInput.clear();
    }

    /**
     * Sends user input to listeners on Enter key press.
     * @param keyEvent user key press
     */
    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            handleUserInput();
        }
    }

    /**
     * Prints message from Duke's perspective.
     * @param message message to be printed
     */
    void printDukeMessage(String message) {
        if (message.contains("\n/vader/")) {
            message = message.replace("\n/vader/", "");
            dialogContainer.getChildren().addAll(FxDialogBox.getDukeDialog(message, vaderImage));
            return;
        }

        dialogContainer.getChildren().addAll(FxDialogBox.getDukeDialog(message, yodaImage));
    }

    /**
     * Prints message from user's perspective.
     * @param message message to be printed
     */
    private void printUserMessage(String message) {
        dialogContainer.getChildren().addAll(
                FxDialogBox.getUserDialog(message, anakinImage)
        );
    }

    /**
     * Adds a fxDukeInput instance to the list of listeners. This fxDukeInput will be notified through its
     * receiveInput(String input) method each time the user enters a input.
     * @param fxDukeInput fxDukeInput to be added as a listener.
     */
    void addInputListener(FxDukeInput fxDukeInput) {
        this.fxDukeInputs.add(fxDukeInput);
    }

    /**
     * Removes a fxDukeInput instance from the list of listeners. This fxDukeInput instance will no longer be notified
     * of any user inputs.
     * @param fxDukeInput fxDukeInput to be removed as a listener.
     */
    void removeInputListener(FxDukeInput fxDukeInput) {
        this.fxDukeInputs.remove(fxDukeInput);
    }
}

