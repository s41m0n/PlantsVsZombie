package pvz.view.menu.utility;

import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;
import pvz.model.entity.plant.PlantType;
import pvz.view.MainWindow;

/**
 * This class is responsible of creating specified objects with a certain style.
 * 
 *
 */
public final class ComponentFactory {

    private static final ComponentFactory FACTORY = new ComponentFactory();

    private Font font;

    private ComponentFactory() {
        this.setFont();
    }

    private void setFont() {
        if (System.getProperty("os.name").startsWith("Mac") || System.getProperty("os.name").startsWith("Linux")) {
            this.font = Font.loadFont(
                    this.getClass().getResourceAsStream(System.getProperty("file.separator") + "PixelModeX.ttf"),
                    MainWindow.getSceneWidth() / 32);
        } else {
            this.font = Font.loadFont(this.getClass().getClassLoader().getResourceAsStream("PixelModeX.ttf"),
                    MainWindow.getSceneWidth() / 32);
        }
    }

    /**
     * Public getter.
     * 
     * @return this component factory.
     */
    public static ComponentFactory getComponentFactory() {
        return FACTORY;
    }

    /**
     * Class for button with image.
     *
     */
    public class ImageButton extends Button {
        private final Optional<PlantType> plantType;

        ImageButton(final Image image, final double length, final Optional<PlantType> type, final boolean fitWidth) {
            super();
            if (fitWidth) {
                plantType = type;
                ImageView iv = new ImageView(image);
                iv.setPreserveRatio(true);
                iv.setFitWidth(length);
                this.setGraphic(iv);
            } else {
                plantType = type;
                ImageView iv = new ImageView(image);
                iv.setPreserveRatio(true);
                iv.setFitHeight(length);
                this.setGraphic(iv);
            }
            this.setId("imageButton");
        }

        /**
         * Getter of plant type if present.
         * 
         * @return the plant type of the button.
         */
        public PlantType getPlantType() {
            return plantType.get();
        }
    }

    /**
     * Method to return a Personalized Label.
     * 
     * @param text
     *            the text the label have to show.
     * @return the personalized Label.
     */
    public Label getLabel(final String text) {
        Label label = new Label();
        label.setText(text);
        label.setFont(this.font);
        label.setWrapText(true);
        return label;
    }

    /**
     * Method to return a Personalized Button.
     * 
     * @param name
     *            the name of the Button.
     * @param soundOn
     *            true if when button is clicked crows should be played.
     * @return the personalized Button.
     */
    public Button getButton(final String name, final boolean soundOn) {
        Button button = new Button(name);
        button.setFont(this.font);
        if (soundOn) {
            button.addEventHandler(ActionEvent.ACTION, e -> {
                MainWindow.playCrows();
            });
        }
        return button;
    }

    /**
     * Method to return a Personalized Button with Image.
     * 
     * @param image
     *            the image of the Button.
     * @param length
     *            the height or width.
     * @param type
     *            the plant type if it's a button with an image of a plant.
     * @param fitWidth
     *            true if the button should be fit with width.
     * @return the personalized Button.
     */
    public Button getImageButton(final Image image, final double length, final Optional<PlantType> type,
            final boolean fitWidth) {

        return new ImageButton(image, length, type, fitWidth);
    }

    /**
     * Method to return a Personalized Alert.
     * 
     * @param message
     *            the message the alert should show.
     * @return the personalized alert.
     */
    public Alert getAlert(final String message) {
        final String title = "Attention!";
        final String header = "Message for you:";

        Label label = this.getLabel(message);

        Alert dialog = new Alert(AlertType.NONE);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.getButtonTypes().add(ButtonType.OK);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.getDialogPane().setContent(label);
        dialog.getDialogPane().getStylesheets().add("style.css");

        dialog.getDialogPane().getChildren().stream().filter(node -> node instanceof Label)
                .forEach(node -> ((Label) node).setFont(this.font));

        dialog.getDialogPane().lookup(".header-panel")
                .setStyle("-fx-font-family: \"Pixel ModeX\"" + ";\n-fx-font-size: " + this.font.getSize() + ";");

        ((ButtonBar) dialog.getDialogPane().lookup(".button-bar")).getButtons().stream().forEach(
                b -> b.setStyle("-fx-font-family: \"Pixel ModeX\"" + ";\n-fx-font-size: " + this.font.getSize() + ";"));

        return dialog;

    }

    /**
     * Method to return a Personalized ConfirmBox.
     * 
     * @param message
     *            the message the confirm box should show.
     * @return the personalized Confirm Box.
     */
    public Alert getConfirmBox(final String message) {
        final String title = "Attention!";
        final Alert dialog = this.getAlert(message);

        dialog.getButtonTypes().clear();
        dialog.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        dialog.setTitle(title);

        ((ButtonBar) dialog.getDialogPane().lookup(".button-bar")).getButtons().stream().forEach(
                b -> b.setStyle("-fx-font-family: \"Pixel ModeX\"" + ";\n-fx-font-size: " + this.font.getSize() + ";"));

        return dialog;
    }

    /**
     * Method to return a Personalized Combo Box.
     * 
     * @param <E>
     *            the type of the comboBox
     * @param values
     *            the list of items that this box should have.
     * @return the personalized Combo Box
     */
    public <E> ComboBox<E> getComboBox(final ObservableList<E> values) {
        ComboBox<E> box = new ComboBox<>(values);

        box.setStyle("-fx-font-family: \"Pixel ModeX\"" + ";\n-fx-font-size: " + this.font.getSize() + ";");
        return box;
    }

    /**
     * Method to return a Personalized Text Field.
     * 
     * @param promptText
     *            the prompted Text.
     * @return the personalized Text Field
     */
    public TextField getTextField(final String promptText) {
        TextField text = new TextField();
        text.setPromptText(promptText);
        text.setFont(this.font);

        return text;
    }

    /**
     * Method to return a Personalized Check Box.
     * 
     * @return the personalized Check Box
     */
    public CheckBox getCheckBox() {
        CheckBox box = new CheckBox();
        box.setFont(this.font);

        return box;
    }
}