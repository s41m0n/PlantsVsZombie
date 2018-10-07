package pvz.view.input;

import java.util.LinkedList;

import java.util.List;

/**
 * Class for managing inputs.
 *
 */
public class InputManager {

    private List<InputInterface> inputList = new LinkedList<>();

    /**
     * Public constructor.
     */
    public InputManager() {
    }

    /**
     * Method to return the list of inputs.
     * 
     * @return the list of inputs.
     */
    public List<InputInterface> getList() {
        return this.inputList;
    }

    /**
     * Method to clear the inputs list.
     */
    public void clearList() {
        this.inputList.clear();
    }

    /**
     * Public method to add an input to this list.
     * 
     * @param input
     *            the input.
     */
    public void addInput(final InputInterface input) {
        this.inputList.add(input);
    }
}
