package pvz.view.menu;

import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pvz.view.MainWindow;
import pvz.view.View;
import pvz.view.input.QuitHandler;
import pvz.view.menu.utility.ComponentFactory;

/**
 * New game menu scene.
 *
 */
public class NewGameMenu extends Scene {

    private static final double BOTTOM_SPACING = MainWindow.getSceneWidth() / 3.5;
    private static final double TEXT_WIDTH = MainWindow.getSceneWidth() / 2.4;

    private static NewGameMenu mainScene;
    private static Stage mainStage;

    private final TextField insertName = ComponentFactory.getComponentFactory().getTextField("--Insert your name--");
    private final Button register = ComponentFactory.getComponentFactory().getButton("Register", true);
    private final Button back = ComponentFactory.getComponentFactory().getButton("Back", true);
    private final Button quit = ComponentFactory.getComponentFactory().getButton("Quit", true);

    /**
     * Public constructor.
     */
    public NewGameMenu() {
        super(new StackPane(), MainWindow.getSceneWidth(), MainWindow.getSceneHeight());

        this.register.setOnAction(e -> {
            if (this.insertName.getText().isEmpty()) {
                final Alert alert = ComponentFactory.getComponentFactory().getAlert("No name insert!");
                alert.show();
            } else {
                if (!View.getController().registerPlayer(this.insertName.getText())) {
                    final Alert alert = ComponentFactory.getComponentFactory().getAlert("Player already exists");
                    alert.show();
                } else {
                    mainStage.setScene(SelectModeMenu.get(mainStage));
                }
            }
        });

        this.back.setOnAction(e -> {
            mainStage.setScene(MainMenu.get(mainStage));
        });

        this.quit.setOnAction(e -> {
            QuitHandler.exitProgram(mainStage);
        });

        this.insertName.setPrefWidth(TEXT_WIDTH);

        final HBox centerLayout = new HBox();
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.getChildren().addAll(this.insertName);

        final HBox bottomLayout = new HBox();
        bottomLayout.setAlignment(Pos.BOTTOM_RIGHT);
        bottomLayout.setSpacing(BOTTOM_SPACING);
        bottomLayout.getChildren().addAll(this.register, this.back, this.quit);

        final BorderPane root = new BorderPane();
        root.setId("simpleMenu");
        root.setBottom(bottomLayout);
        root.setCenter(centerLayout);

        this.setRoot(root);
        this.getStylesheets().add("style.css");
    }

    static NewGameMenu get(final Stage mainWindow) {
        mainStage = mainWindow;
        mainScene = new NewGameMenu();
        return mainScene;
    }
}
