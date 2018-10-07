package pvz.view.menu;

import java.util.stream.Collectors;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
 * This is the class for the HighScores menu.
 *
 */
public class HighscoresMenu extends Scene {

    private static final double BOTTOM_SPACING = MainWindow.getSceneWidth() / 1.33;
    private static final double LABEL_SPACING = MainWindow.getSceneWidth() / 10;

    private static HighscoresMenu mainScene;
    private static Stage mainStage;

    private final Button back = ComponentFactory.getComponentFactory().getButton("Back", true);
    private final Button quit = ComponentFactory.getComponentFactory().getButton("Quit", true);

    private final Label titleArcade = ComponentFactory.getComponentFactory().getLabel("Highscores Mode Arcade");
    private Label arcade = ComponentFactory.getComponentFactory()
            .getLabel(View.getController().getHighScores().get(Mode.ARCADE).stream()
                    .map(s -> "" + s.getPlayer() + ": " + s.getScore()).collect(Collectors.joining("\n")));

    /**
     * Public constructor.
     */
    public HighscoresMenu() {
        super(new StackPane(), MainWindow.getSceneWidth(), MainWindow.getSceneHeight());

        this.back.setOnAction(e -> {
            mainStage.setScene(MainMenu.get(mainStage));
        });

        this.quit.setOnAction(e -> {
            QuitHandler.exitProgram(mainStage);
        });

        final VBox layout1 = new VBox();
        layout1.setAlignment(Pos.TOP_CENTER);
        layout1.setSpacing(LABEL_SPACING / 2);
        layout1.getChildren().addAll(this.titleArcade, this.arcade);

        final HBox centerLayout = new HBox();
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.setSpacing(LABEL_SPACING);
        centerLayout.getChildren().addAll(layout1);

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

    static HighscoresMenu get(final Stage mainWindow) {
        mainStage = mainWindow;
        mainScene = new HighscoresMenu();
        return mainScene;
    }

}
