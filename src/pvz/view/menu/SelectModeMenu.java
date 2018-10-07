package pvz.view.menu;

import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pvz.controller.constants.Mode;
import pvz.view.MainWindow;
import pvz.view.View;
import pvz.view.input.QuitHandler;
import pvz.view.menu.utility.ComponentFactory;

/**
 * Class for the selection of the mode (story or arcade).
 *
 */
public class SelectModeMenu extends Scene {

    private static final double BOTTOM_SPACING = MainWindow.getSceneWidth() / 1.33;
    private static final double LABEL_SPACING = MainWindow.getSceneWidth() / 10;

    private static SelectModeMenu mainScene;
    private static Stage mainStage;

    private final Button back = ComponentFactory.getComponentFactory().getButton("Back", true);
    private final Button quit = ComponentFactory.getComponentFactory().getButton("Quit", true);
    private final Button storyMode = ComponentFactory.getComponentFactory().getButton("Story Mode", true);
    private final Button arcadeMode = ComponentFactory.getComponentFactory().getButton("Arcade Mode", true);

    /**
     * Public constructor.
     */
    public SelectModeMenu() {
        super(new StackPane(), MainWindow.getSceneWidth(), MainWindow.getSceneHeight());

        this.storyMode.setOnAction(e -> {
            View.getController().setMode(Mode.HISTORY);
            mainStage.setScene(PreStoryMenu.get(mainStage));
        });

        this.arcadeMode.setOnAction(e -> {
            View.getController().setMode(Mode.ARCADE);
            mainStage.setScene(PreArcadeMenu.get(mainStage));
        });

        this.back.setOnAction(e -> {
            mainStage.setScene(MainMenu.get(mainStage));
        });

        this.quit.setOnAction(e -> {
            QuitHandler.exitProgram(mainStage);
        });

        final VBox centerLayout = new VBox();
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.setSpacing(LABEL_SPACING);
        centerLayout.getChildren().addAll(this.storyMode, this.arcadeMode);

        final HBox bottomLayout = new HBox();
        bottomLayout.setAlignment(Pos.BOTTOM_RIGHT);
        bottomLayout.setSpacing(BOTTOM_SPACING);
        bottomLayout.getChildren().addAll(this.back, this.quit);

        final BorderPane root = new BorderPane();
        root.setId("simpleMenu");
        root.setBottom(bottomLayout);
        root.setCenter(centerLayout);

        this.setRoot(root);
        this.getStylesheets().add("style.css");
    }

    static SelectModeMenu get(final Stage mainWindow) {
        mainStage = mainWindow;
        mainScene = new SelectModeMenu();
        return mainScene;
    }
}
