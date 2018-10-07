package pvz.view.menu;

import java.util.HashSet;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pvz.controller.constants.Mode;
import pvz.model.entity.plant.PlantType;
import pvz.view.MainWindow;
import pvz.view.View;
import pvz.view.input.QuitHandler;
import pvz.view.menu.utility.ComponentFactory;
import pvz.view.menu.utility.ImageMapper;

/**
 * Menu previous the story mode.
 *
 */
public class PreArcadeMenu extends Scene {

    private static final int MAX_PLANT = 5;
    private static final double BOTTOM_SPACING = MainWindow.getSceneWidth() / 3.1;
    private static final double PLANT_HEIGHT = MainWindow.getSceneHeight() / 10;
    private static PreArcadeMenu mainScene;
    private static Stage mainStage;

    private Set<PlantType> plantsSelected = new HashSet<>();

    private final Button quit = ComponentFactory.getComponentFactory().getButton("Quit", true);
    private final Button back = ComponentFactory.getComponentFactory().getButton("Back", true);

    private final Button play = ComponentFactory.getComponentFactory().getButton("Play", true);
    private final Label titleArcade = ComponentFactory.getComponentFactory().getLabel(" Highscores Mode Arcade");
    private Label highscores = ComponentFactory.getComponentFactory()
            .getLabel(View.getController().getHighScores().get(Mode.ARCADE).stream()
                    .map(s -> "" + s.getPlayer() + ": " + s.getScore()).collect(Collectors.joining("\n")));;

    /**
     * Public constructor.
     */
    public PreArcadeMenu() {
        super(new StackPane(), MainWindow.getSceneWidth(), MainWindow.getSceneHeight());

        this.quit.setOnAction(e -> {
            QuitHandler.exitProgram(mainStage);
        });

        this.back.setOnAction(e -> {
            mainStage.setScene(SelectModeMenu.get(mainStage));
        });

        this.play.setOnAction(e -> {
            if (!this.plantsSelected.isEmpty()) {
                final GamingMenu game = new GamingMenu(this.plantsSelected);
                View.setGamingScreen(game);
                View.getController().startGame(Optional.empty(), this.plantsSelected);
                mainStage.setScene(game.get(mainStage));
            } else {
                final Alert alert = ComponentFactory.getComponentFactory().getAlert("No Plant selected!");
                alert.show();
            }
        });

        final FlowPane plantsLayout = new FlowPane();

        final HBox topLayout = new HBox();
        topLayout.setAlignment(Pos.TOP_RIGHT);
        View.getController().getPlantsUnlocked().stream()
                .sorted((p1, p2) -> Integer.compare(p1.getCost(), p2.getCost())).forEach(p -> {
                    final Button button = ComponentFactory.getComponentFactory()
                            .getImageButton(ImageMapper.get().getImage(p), PLANT_HEIGHT, Optional.of(p), false);
                    button.setOnAction(e -> {
                        if (this.plantsSelected.size() >= MAX_PLANT) {
                            final Alert alert = ComponentFactory.getComponentFactory().getAlert("Max plant reached!");
                            alert.show();
                        } else if (!this.plantsSelected.contains(p)) {
                            this.plantsSelected.add(p);
                            final Button butt = ComponentFactory.getComponentFactory()
                                    .getImageButton(ImageMapper.get().getImage(p), PLANT_HEIGHT, Optional.of(p), false);
                            butt.setOnAction(k -> {
                                this.plantsSelected.remove(p);
                                plantsLayout.getChildren().remove(butt);
                            });
                            plantsLayout.getChildren().add(butt);
                        }
                    });
                    topLayout.getChildren().add(button);
                });

        final Label plantInfo = ComponentFactory.getComponentFactory().getLabel("Plants selected");

        final VBox centerLayout = new VBox();
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.getChildren().addAll(plantInfo, plantsLayout);

        final VBox leftLayout = new VBox();
        leftLayout.setAlignment(Pos.CENTER);
        leftLayout.getChildren().addAll(this.titleArcade, this.highscores);

        final HBox bottomLayout = new HBox();
        bottomLayout.getChildren().addAll(this.play, this.back, this.quit);
        bottomLayout.setSpacing(BOTTOM_SPACING);
        bottomLayout.setAlignment(Pos.BOTTOM_RIGHT);

        final BorderPane root = new BorderPane();
        root.setId("simpleMenu");
        root.setBottom(bottomLayout);
        root.setTop(topLayout);
        root.setLeft(leftLayout);
        root.setCenter(centerLayout);

        this.setRoot(root);
        this.getStylesheets().add("style.css");
    }

    static PreArcadeMenu get(final Stage mainWindow) {
        mainStage = mainWindow;
        mainScene = new PreArcadeMenu();
        return mainScene;
    }
}
