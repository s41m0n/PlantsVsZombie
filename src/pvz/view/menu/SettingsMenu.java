package pvz.view.menu;

import java.util.Arrays;

import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pvz.controller.constants.GameConstants;
import pvz.view.MainWindow;
import pvz.view.View;
import pvz.view.input.QuitHandler;
import pvz.view.menu.utility.ComponentFactory;

/**
 * Public class for Settings menu.
 */
public class SettingsMenu extends Scene {

    private static final double BOTTOM_SPACING = MainWindow.getSceneWidth() / 3.1;
    private static final double LAYOUT_SPACING = MainWindow.getSceneWidth() / 20.0;

    private static SettingsMenu mainScene;
    private static Stage mainStage;

    private ObservableList<Integer> comboValues = FXCollections.observableArrayList(GameConstants.AVAILABLE_FPS);
    private ObservableList<String> musicValues = FXCollections.observableArrayList(Arrays.asList("yes", "no"));

    private final Label musicLabel = ComponentFactory.getComponentFactory().getLabel("Background Music :");
    private final Label fpsLabel = ComponentFactory.getComponentFactory().getLabel("Frame per second :");
    private final ComboBox<Integer> fpsCombo = ComponentFactory.getComponentFactory().getComboBox(this.comboValues);
    private final ComboBox<String> musicCombo = ComponentFactory.getComponentFactory().getComboBox(this.musicValues);

    private final Button save = ComponentFactory.getComponentFactory().getButton("Save", true);
    private final Button back = ComponentFactory.getComponentFactory().getButton("Back", true);
    private final Button quit = ComponentFactory.getComponentFactory().getButton("Quit", true);
    private BorderPane root = new BorderPane();

    /**
     * Public constructor.
     */
    public SettingsMenu() {
        super(new StackPane(), MainWindow.getSceneWidth(), MainWindow.getSceneHeight());

        this.save.setOnAction(e -> {
            this.save();
        });

        this.back.setOnAction(e -> {
            mainStage.setScene(MainMenu.get(mainStage));
        });

        this.quit.setOnAction(e -> {
            QuitHandler.exitProgram(mainStage);
        });

        this.fpsCombo.setValue(View.getController().getFPS());
        this.musicCombo.setValue(MainWindow.isMusicDisabled() ? this.musicValues.get(1) : this.musicValues.get(0));

        final HBox fpsLayout = new HBox();
        fpsLayout.setAlignment(Pos.CENTER);
        fpsLayout.setSpacing(LAYOUT_SPACING);
        fpsLayout.getChildren().addAll(this.fpsLabel, this.fpsCombo);

        final HBox musicLayout = new HBox();
        musicLayout.setAlignment(Pos.CENTER);
        musicLayout.setSpacing(LAYOUT_SPACING);
        musicLayout.getChildren().addAll(this.musicLabel, this.musicCombo);

        final HBox bottomLayout = new HBox();
        bottomLayout.setAlignment(Pos.BOTTOM_RIGHT);
        bottomLayout.setSpacing(BOTTOM_SPACING);
        bottomLayout.getChildren().addAll(this.save, this.back, this.quit);

        final VBox centerLayout = new VBox();
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.getChildren().addAll(fpsLayout, musicLayout);

        root = new BorderPane();
        root.setId("simpleMenu");
        root.setBottom(bottomLayout);
        root.setCenter(centerLayout);

        this.setRoot(root);
        this.getStylesheets().add("style.css");

    }

    private void save() {
        if (this.fpsCombo.getValue() == null) {
            final Alert alert = ComponentFactory.getComponentFactory().getAlert("Save failed!");
            alert.show();
        } else {
            View.getController().setFPS((int) this.fpsCombo.getValue());
            final Alert alert = ComponentFactory.getComponentFactory().getAlert("Save success!");
            alert.show();
        }
        if (this.musicCombo.getValue().equals(this.musicValues.get(1))) {
            MainWindow.disableMusic();
        } else {
            MainWindow.resumeMusic();
        }
    }

    static SettingsMenu get(final Stage mainWindow) {
        mainStage = mainWindow;
        mainScene = new SettingsMenu();
        return mainScene;
    }

}
