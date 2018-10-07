package pvz.controller.application;

import pvz.view.ViewInterface;


import java.util.Locale;
import pvz.controller.Controller;
import pvz.controller.ControllerInterface;
import pvz.view.View;

/**
 * This class has the task to launching the application.
 */

public final class Application {

    /**
     * Application's main.
     * 
     * @param args
     *            command line arguments
     */
    public static void main(final String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        final ControllerInterface controller = new Controller();
        final ViewInterface view = new View(controller);
        ((Controller) controller).setView(view);
        view.init();

    }

    /**
     * Application constructor.
     */
    private Application() {
    }
}
