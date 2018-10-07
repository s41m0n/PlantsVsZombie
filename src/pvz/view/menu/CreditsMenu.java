package pvz.view.menu;

import java.io.IOException;
import java.util.stream.Collectors;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pvz.view.MainWindow;
import pvz.view.View;
import pvz.view.input.QuitHandler;
import pvz.view.menu.utility.ComponentFactory;

/**
 * The Credits menu Scene.
 *
 */
public class CreditsMenu extends Scene {

    private static final double BOTTOM_SPACING = MainWindow.getSceneWidth() / 1.33;

    private static CreditsMenu mainScene;
    private static Stage mainStage;

    private final Button back = ComponentFactory.getComponentFactory().getButton("Back", true);
    private final Button quit = ComponentFactory.getComponentFactory().getButton("Quit", true);

    private Label credits;

    /**
     * Public constructor.
     */
    public CreditsMenu() {
        super(new StackPane(), MainWindow.getSceneWidth(), MainWindow.getSceneHeight());

        this.back.setOnAction(e -> {
            mainStage.setScene(MainMenu.get(mainStage));
        });

        this.quit.setOnAction(e -> {
            QuitHandler.exitProgram(mainStage);
        });

        try {
            this.credits = ComponentFactory.getComponentFactory()
                    .getLabel(View.getController().getCredits().stream().collect(Collectors.joining("\n")));
        } catch (IOException e) {
            final Alert pa = ComponentFactory.getComponentFactory().getAlert("Unable to get credits");
            pa.show();
            this.credits = ComponentFactory.getComponentFactory().getLabel("ERROR");
        }

        final HBox bottomLayout = new HBox();
        bottomLayout.setAlignment(Pos.BOTTOM_RIGHT);
        bottomLayout.setSpacing(BOTTOM_SPACING);
        bottomLayout.getChildren().addAll(this.back, this.quit);

        final BorderPane root = new BorderPane();
        root.setId("simpleMenu");
        root.setBottom(bottomLayout);
        root.setCenter(this.credits);

        this.setRoot(root);
        this.getStylesheets().add("style.css");
    }

    static CreditsMenu get(final Stage mainWindow) {
        mainStage = mainWindow;
        mainScene = new CreditsMenu();
        return mainScene;
    }
}
