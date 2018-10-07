package pvz.view.input;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import pvz.view.View;
import pvz.view.menu.utility.ComponentFactory;

/**
 * Public class for closing correctly the program.
 * 
 * 
 */
public final class QuitHandler {

    private static final QuitHandler QUIT_HANDLER = new QuitHandler();

    private QuitHandler() {

    }

    static QuitHandler get() {
        return QuitHandler.QUIT_HANDLER;
    }

    /**
     * Method to safely exit the program.
     * 
     * @param mainWindow
     *            the main stage.
     */
    public static void exitProgram(final Stage mainWindow) {
        final Alert box = ComponentFactory.getComponentFactory().getConfirmBox("Are you sure you want to quit?");
        final Boolean answer = box.showAndWait().get().equals(ButtonType.YES);

        try {
            if (View.getController().isInPause()) {
                if (answer) {
                    View.getController().abortGameLoop();
                    mainWindow.close();
                    View.getController().quit();
                } else {
                    View.getController().resumeGameLoop();
                }
            }
        } catch (IllegalStateException e) {
        }
        if (answer) {
            mainWindow.close();
            View.getController().quit();
        }
    }
}