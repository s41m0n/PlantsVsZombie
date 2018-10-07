package pvz.view.menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pvz.view.MainWindow;
import pvz.view.View;
import pvz.view.input.QuitHandler;
import pvz.view.menu.utility.ComponentFactory;

/**
 * Main menu scene.
 * 
 */
public class MainMenu extends Scene {

    private static final double VBOX_SPACING = MainWindow.getSceneHeight() / 7.5;
    private static final double HBOX_SPACING = MainWindow.getSceneWidth() / 3.85;
    private static final double LOGO_WIDTH = MainWindow.getSceneWidth() / 1.5;

    private static MainMenu mainScene;
    private static Stage mainStage;

    private ObservableList<String> playersValue = FXCollections
            .observableArrayList(View.getController().getRegisteredPlayers());
    private final ComboBox<String> players = ComponentFactory.getComponentFactory().getComboBox(this.playersValue);

    private Image logo = new Image(this.getClass().getResourceAsStream("/images/Logo.png"));
    private final Button newGame = ComponentFactory.getComponentFactory().getButton("New Game", true);
    private final Button loadGame = ComponentFactory.getComponentFactory().getButton("LoadGame", true);
    private final Button highscores = ComponentFactory.getComponentFactory().getButton("Highscores", true);
    private final Button credits = ComponentFactory.getComponentFactory().getButton("Credits", true);
    private final Button settings = ComponentFactory.getComponentFactory().getButton("Settings", true);
    private final Button remove = ComponentFactory.getComponentFactory().getButton("Remove Player", true);
    private final Button quit = ComponentFactory.getComponentFactory().getButton("Quit", true);

    /**
     * Public constructor.
     */
    public MainMenu() {
        super(new StackPane(), MainWindow.getSceneWidth(), MainWindow.getSceneHeight());

        this.newGame.setOnAction(e -> {
            mainStage.setScene(NewGameMenu.get(mainStage));
        });

        this.loadGame.setOnAction(e -> {
            if (this.players.getValue() == null) {
                final Alert alert = ComponentFactory.getComponentFactory().getAlert("No Player selected!");
                alert.show();
            } else {
                View.getController().loadPlayer((String) this.players.getValue());
                mainStage.setScene(SelectModeMenu.get(mainStage));
            }
        });

        this.highscores.setOnAction(e -> {
            mainStage.setScene(HighscoresMenu.get(mainStage));
        });

        this.credits.setOnAction(e -> {
            mainStage.setScene(CreditsMenu.get(mainStage));
        });

        this.settings.setOnAction(e -> {
            mainStage.setScene(SettingsMenu.get(mainStage));
        });

        this.remove.setOnAction(e -> {
            if (this.players.getValue() == null) {
                final Alert alert = ComponentFactory.getComponentFactory().getAlert("No Player selected!");
                alert.show();
            } else {
                View.getController().removePlayer(this.players.getValue());
                final Alert alert = ComponentFactory.getComponentFactory().getAlert("Player removed currectly!");
                alert.show();
                this.playersValue = FXCollections.observableArrayList(View.getController().getRegisteredPlayers());
                this.players.setItems(this.playersValue);
            }
        });

        this.quit.setOnAction(e -> {
            QuitHandler.exitProgram(mainStage);
        });

        final ImageView iv = new ImageView(this.logo);
        iv.setPreserveRatio(true);
        iv.setFitWidth(LOGO_WIDTH);

        this.players.setValue(null);
        this.players.setPromptText("--Select player--");

        final HBox bottomLayout = new HBox();
        bottomLayout.getChildren().addAll(this.credits, this.settings, this.quit);
        bottomLayout.setSpacing(HBOX_SPACING);
        bottomLayout.setAlignment(Pos.BOTTOM_LEFT);

        final HBox topLayout = new HBox();
        topLayout.getChildren().addAll(this.players, this.remove);
        topLayout.setAlignment(Pos.TOP_RIGHT);

        final VBox leftLayout = new VBox();
        leftLayout.getChildren().addAll(this.newGame, this.loadGame, this.highscores);
        leftLayout.setSpacing(VBOX_SPACING);

        final BorderPane root = new BorderPane();
        root.setId("simpleMenu");
        root.setLeft(leftLayout);
        root.setTop(topLayout);
        root.setBottom(bottomLayout);
        root.setCenter(iv);

        this.setRoot(root);
        this.getStylesheets().add("style.css");
    }

    /**
     * Public getter of this Scene.
     * 
     * @param mainWindow
     *            the mainWindow to set in in this class.
     * @return the main menu completed.
     */
    public static MainMenu get(final Stage mainWindow) {
        mainStage = mainWindow;
        mainScene = new MainMenu();
        return mainScene;
    }
}
