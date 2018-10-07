package pvz.view.menu;

import java.util.HashSet;

import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

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
import pvz.controller.constants.GameConstants;
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
public class PreStoryMenu extends Scene {

    private static final double BOTTOM_SPACING = MainWindow.getSceneWidth() / 3.1;
    private static final double PLANT_HEIGHT = MainWindow.getSceneHeight() / 10;

    private static PreStoryMenu mainScene;
    private static Stage mainStage;

    private Optional<Integer> levelSelected = Optional.empty();
    private Set<PlantType> plantsSelected = new HashSet<>();

    private final Button quit = ComponentFactory.getComponentFactory().getButton("Quit", true);
    private final Button back = ComponentFactory.getComponentFactory().getButton("Back", true);
    private final Button play = ComponentFactory.getComponentFactory().getButton("Play", true);

    /**
     * Public constructor.
     */
    public PreStoryMenu() {
        super(new StackPane(), MainWindow.getSceneWidth(), MainWindow.getSceneHeight());

        this.quit.setOnAction(e -> {
            QuitHandler.exitProgram(mainStage);
        });

        this.back.setOnAction(e -> {
            mainStage.setScene(SelectModeMenu.get(mainStage));
        });

        this.play.setOnAction(e -> {
            if (this.levelSelected.isPresent() && !this.plantsSelected.isEmpty()) {
                final GamingMenu game = new GamingMenu(this.plantsSelected);
                View.setGamingScreen(game);
                View.getController().startGame(this.levelSelected, this.plantsSelected);
                mainStage.setScene(game.get(mainStage));
            } else {
                if (!this.levelSelected.isPresent()) {
                    final Alert alert = ComponentFactory.getComponentFactory().getAlert("No level selected!");
                    alert.show();
                } else {
                    final Alert alert = ComponentFactory.getComponentFactory().getAlert("No Plant selected!");
                    alert.show();
                }
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
                        if (this.plantsSelected.contains(p)) {
                            final Alert alert = ComponentFactory.getComponentFactory()
                                    .getAlert("Plant already selected");
                            alert.show();
                        } else {
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

        final Label levelInfo = ComponentFactory.getComponentFactory().getLabel("  Level selected:        ");
        leftLayout.getChildren().add(levelInfo);

        IntStream.range(1, GameConstants.STORY_LEVELS + 1).forEach(i -> {
            final Button pb = ComponentFactory.getComponentFactory().getButton("Level " + i, false);
            pb.setDisable(View.getController().getLevelsUnlocked() - i < 0);
            pb.setOnAction(e -> {
                if (!pb.isDisabled()) {
                    this.levelSelected = Optional
                            .of(Integer.parseInt(pb.getText().substring(pb.getText().length() - 1)));
                    levelInfo.setText(levelInfo.getText().substring(0, levelInfo.getText().lastIndexOf(":") + 2)
                            + "Level " + this.levelSelected.get());
                }
            });
            leftLayout.getChildren().add(pb);
        });

        final HBox bottomLayout = new HBox();
        bottomLayout.getChildren().addAll(this.play, this.back, this.quit);
        bottomLayout.setSpacing(BOTTOM_SPACING);
        bottomLayout.setAlignment(Pos.BOTTOM_RIGHT);

        final BorderPane root = new BorderPane();
        root.setId("simpleMenu");
        root.setLeft(leftLayout);
        root.setBottom(bottomLayout);
        root.setTop(topLayout);
        root.setCenter(centerLayout);

        this.setRoot(root);
        this.getStylesheets().add("style.css");
    }

    static PreStoryMenu get(final Stage mainWindow) {
        mainStage = mainWindow;
        mainScene = new PreStoryMenu();
        return mainScene;
    }
}
