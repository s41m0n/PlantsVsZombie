package pvz.view.menu;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pvz.controller.constants.Mode;
import pvz.model.GameStatus;
import pvz.model.entity.Entity;
import pvz.model.entity.plant.PlantType;
import pvz.utility.Pair;
import pvz.view.MainWindow;
import pvz.view.View;
import pvz.view.input.Input;
import pvz.view.input.InputInterface;
import pvz.view.input.InputManager;
import pvz.view.input.InputType;
import pvz.view.input.QuitHandler;
import pvz.view.menu.render.EntityDrawer;
import pvz.view.menu.utility.ComponentFactory;
import pvz.view.menu.utility.ImageMapper;
import pvz.view.menu.utility.ComponentFactory.ImageButton;

/**
 * Typical gaming screen.
 *
 */
public class GamingMenu extends Scene {

    private static Stage mainStage;

    private static final double TOP_SPACING = MainWindow.getSceneWidth() / 13;
    private static final double SHOVEL_HEIGHT = MainWindow.getSceneHeight() / 9.3;
    private static final double BUTTON_WIDTH = MainWindow.getSceneWidth() / 12;
    private static final double PLANT_Y = SHOVEL_HEIGHT * 1.18;

    private InputManager manager = new InputManager();
    private EntityDrawer drawer;

    private int energy = Integer.MAX_VALUE;

    private final Pair<Double, Double> fieldPosition = new Pair<>(MainWindow.getSceneWidth() * 0.2975,
            MainWindow.getSceneHeight() * 0.2536);
    private final Pair<Double, Double> fieldDimension = new Pair<>(MainWindow.getSceneWidth() * 0.6738,
            MainWindow.getSceneHeight() * 0.6254);

    private Optional<Button> buttonSelected = Optional.empty();
    private boolean shovelClicked = false;

    private final ImageView sun = new ImageView(
            new Image(this.getClass().getResourceAsStream("/entity/resource/Sun.png")));

    private final Button hide = new Button();
    private final Button pause = ComponentFactory.getComponentFactory().getButton("pause", true);
    private final Button shovel = ComponentFactory.getComponentFactory().getImageButton(
            new Image(this.getClass().getResourceAsStream("/images/Shovel.png")), SHOVEL_HEIGHT, Optional.empty(),
            false);
    private List<Button> plants = new ArrayList<>();

    private Label sunLabel = ComponentFactory.getComponentFactory().getLabel(null);
    private Label levelLabel = ComponentFactory.getComponentFactory().getLabel(null);

    private final Group root = new Group();
    private final Pane battlefield = new Pane();

    /**
     * Public constructor.
     * 
     * @param availablePlant
     *            the list of available plants.
     */
    public GamingMenu(final Set<PlantType> availablePlant) {
        super(new StackPane(), MainWindow.getSceneWidth(), MainWindow.getSceneHeight());

        this.shovel.setFocusTraversable(false);

        this.pause.setOnAction(e -> {
            View.getController().pauseGameLoop();
            this.pause();
        });

        this.shovel.setOnAction(e -> {
            if (this.shovelClicked) {
                this.hide.requestFocus();
                this.shovelClicked = false;
            } else {
                this.shovel.requestFocus();
                this.shovelClicked = true;
            }
            this.buttonSelected = Optional.empty();
        });

        this.battlefield.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            if (e.isPrimaryButtonDown()) {
                if (this.isInBound(e.getX(), e.getY())) {
                    double x = (e.getX() - this.fieldPosition.getX()) / this.fieldDimension.getX();
                    double y = (e.getY() - this.fieldPosition.getY()) / this.fieldDimension.getY();
                    if (this.shovelClicked) {
                        this.manager.addInput(new Input(InputType.REMOVE_PLANT, x, y));
                    } else if (this.buttonSelected.isPresent()) {
                        this.manager.addInput(new Input(InputType.ADD_PLANT, x, y,
                                ((ImageButton) this.buttonSelected.get()).getPlantType()));
                    } else {
                        this.manager.addInput(new Input(InputType.HARVEST_ENERGY, x, y));
                    }
                    this.shovelClicked = false;
                    this.buttonSelected = Optional.empty();
                    this.hide.requestFocus();
                }
            } else {
                this.shovelClicked = false;
                this.buttonSelected = Optional.empty();
                this.hide.requestFocus();
            }
        });

        availablePlant.stream().sorted((p1, p2) -> Integer.compare(p1.getCost(), p2.getCost())).forEach(e -> {
            final Button button = ComponentFactory.getComponentFactory().getImageButton(ImageMapper.get().getImage(e),
                    BUTTON_WIDTH, Optional.of(e), true);
            button.setOnAction(p -> {
                this.shovelClicked = false;
                if (this.buttonSelected.isPresent() && ((ImageButton) this.buttonSelected.get())
                        .getPlantType() == ((ImageButton) button).getPlantType()) {
                    this.hide.requestFocus();
                    this.buttonSelected = Optional.empty();
                } else {
                    this.buttonSelected = Optional.of(button);
                }
            });
            button.setDisable(false);
            this.plants.add(button);
        });

        this.drawer = new EntityDrawer(this.fieldPosition, this.fieldDimension);

        this.sun.setFitHeight(SHOVEL_HEIGHT);
        this.sun.setPreserveRatio(true);

        final HBox topLayout = new HBox();
        topLayout.setAlignment(Pos.TOP_LEFT);
        topLayout.setId("menuBar");
        topLayout.setSpacing(TOP_SPACING);
        topLayout.setMaxHeight(SHOVEL_HEIGHT);
        topLayout.getChildren().addAll(this.pause, this.sun, this.sunLabel, this.shovel, this.levelLabel);

        final VBox plantLayout = new VBox();
        plantLayout.setAlignment(Pos.TOP_LEFT);
        plantLayout.setLayoutY(PLANT_Y);
        plantLayout.setId("menuBar");
        plantLayout.setMaxWidth(BUTTON_WIDTH);
        this.plants.forEach(p -> plantLayout.getChildren().add(p));

        this.battlefield.setPrefHeight(MainWindow.getSceneHeight());
        this.battlefield.setPrefWidth(MainWindow.getSceneWidth());
        this.battlefield.setId("gamingScene");
        this.root.getChildren().addAll(this.battlefield, topLayout, plantLayout, this.hide);

        this.setRoot(root);
        this.getStylesheets().add("style.css");
    }

    /**
     * Method to draw every entity.
     * 
     * @param listEntities
     *            the list of entities to draw.
     * @param buttonsAffordable
     *            the list of buttons (plant) the user can click.
     * @param currentEnergy
     *            the current energy.
     * @param currentLevel
     *            the current level (useful mainly in arcade mode)
     */
    public void render(final List<Entity> listEntities, final List<PlantType> buttonsAffordable,
            final int currentEnergy, final int currentLevel) {

        if (this.energy < currentEnergy) {
            MainWindow.playSun();
        }
        this.energy = currentEnergy;

        this.plants.stream().forEach(p -> {
            if (buttonsAffordable.contains(((ImageButton) p).getPlantType())) {
                p.setDisable(false);
            } else {
                p.setDisable(true);
            }
        });

        this.levelLabel.setText("Level " + currentLevel);
        this.sunLabel.setText("  " + currentEnergy);
        this.drawer.render(this.battlefield, listEntities);
    }

    /**
     * Method to return all inputs.
     * 
     * @return the list of inputs.
     */
    public List<InputInterface> getInputList() {
        final List<InputInterface> list = new ArrayList<>(Collections.unmodifiableList(this.manager.getList()));
        this.manager.clearList();
        return list;
    }

    private boolean isInBound(final double mouseX, final double mouseY) {
        if (mouseX >= this.fieldPosition.getX() && mouseX <= this.fieldPosition.getX() + this.fieldDimension.getX()) {
            if (mouseY >= this.fieldPosition.getY()
                    && mouseY <= this.fieldPosition.getY() + this.fieldDimension.getY()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to show an alert when a level if finished.
     * 
     * @param state
     *            the state of the level, won or lost.
     * @param mode
     *            the mode, history or arcade.
     */
    public void showLevelEnd(final GameStatus state, final Mode mode) {
        final Stage infoStage = new Stage();
        final Scene infoScene = new Scene(new StackPane(), MainWindow.getSceneWidth() / 2.3,
                MainWindow.getSceneHeight() / 2.5);

        final Label infoLabel = ComponentFactory.getComponentFactory().getLabel("You " + state);

        final Button back = mode == Mode.ARCADE
                ? ComponentFactory.getComponentFactory().getButton("Back to Arcade Menu", true)
                : ComponentFactory.getComponentFactory().getButton("Back to Level Select", true);

        infoStage.initStyle(StageStyle.UNDECORATED);

        back.setOnAction(e -> {
            infoStage.close();
            if (mode == Mode.ARCADE) {
                mainStage.setScene(PreArcadeMenu.get(mainStage));
            } else {
                mainStage.setScene(PreStoryMenu.get(mainStage));
            }
        });

        final HBox bottomLayout = new HBox();
        bottomLayout.setAlignment(Pos.BOTTOM_CENTER);
        bottomLayout.getChildren().addAll(back);

        final BorderPane root = new BorderPane();
        root.setId("simpleMenu");
        root.setCenter(infoLabel);
        root.setBottom(bottomLayout);

        infoScene.setRoot(root);
        infoScene.getStylesheets().add("style.css");

        infoStage.setResizable(false);
        infoStage.initOwner(mainStage);
        infoStage.initModality(Modality.WINDOW_MODAL);
        infoStage.setScene(infoScene);
        infoStage.sizeToScene();
        infoStage.show();
    }

    private void pause() {
        final Stage pauseStage = new Stage();
        final Scene pauseScene = new Scene(new StackPane(), MainWindow.getSceneWidth() / 1.5,
                MainWindow.getSceneHeight() / 1.5);

        final double spacing = pauseScene.getWidth() / 7.5;

        final Label textLabel = ComponentFactory.getComponentFactory()
                .getLabel("The game has been paused.\n Select what you want to do!");
        final Button resume = ComponentFactory.getComponentFactory().getButton("Resume", false);
        final Button mainMenu = ComponentFactory.getComponentFactory().getButton("Main Menu", true);
        final Button quit = ComponentFactory.getComponentFactory().getButton("Quit", false);

        pauseStage.initStyle(StageStyle.UNDECORATED);

        quit.setOnAction(e -> {
            QuitHandler.exitProgram(mainStage);
        });

        resume.setOnAction(e -> {
            pauseStage.close();
            View.getController().resumeGameLoop();
        });

        mainMenu.setOnAction(e -> {
            pauseStage.close();
            View.getController().abortGameLoop();
            mainStage.setScene(MainMenu.get(mainStage));
        });

        final HBox bottomLayout = new HBox();
        bottomLayout.setAlignment(Pos.BOTTOM_RIGHT);
        bottomLayout.setSpacing(spacing);
        bottomLayout.getChildren().addAll(resume, mainMenu, quit);

        final BorderPane root = new BorderPane();
        root.setId("simpleMenu");
        root.setCenter(textLabel);
        root.setBottom(bottomLayout);

        pauseScene.setRoot(root);
        pauseScene.getStylesheets().add("style.css");

        pauseStage.setResizable(false);
        pauseStage.initOwner(mainStage);
        pauseStage.initModality(Modality.WINDOW_MODAL);
        pauseStage.setScene(pauseScene);
        pauseStage.show();
    }

    GamingMenu get(final Stage mainWindow) {
        mainStage = mainWindow;
        mainStage.sizeToScene();
        return this;
    }
}
