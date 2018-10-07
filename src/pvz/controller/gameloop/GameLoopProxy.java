package pvz.controller.gameloop;

/**
 * GameLoopProxy collects all methods that permits communication with GameLoop
 * from external components.<br>
 * All requests are accepted by the controller that transfers the real execution
 * to the GameLoop.
 */
public interface GameLoopProxy {

    /**
     * This method starts the GameLoop thread.
     */
    void startGameLoop();

    /**
     * It ends the gameLoop .
     * 
     */

    void abortGameLoop();

    /**
     * It resumes the gameLoop after a pause.
     */

    void resumeGameLoop();

    /**
     * It suspends the gameLoop.
     */

    void pauseGameLoop();

    /**
     * It returns true the gameLoop is running.
     * 
     * @return isRunning.
     */

    boolean isRunning();

    /**
     * It returns true if the gameLoop is in pause.
     * 
     * @return isInPause.
     */

    boolean isInPause();

}
