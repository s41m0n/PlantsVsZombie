package pvz.view.input;

import java.util.Optional;

import pvz.model.entity.plant.PlantType;

/**
 * Public Class for Inputs (remove,add and harvest).
 * 
 */
public class Input implements InputInterface {

    private final InputType inputType;
    private final double x;
    private final double y;
    private final Optional<PlantType> plantType;

    /**
     * Public constructor.
     * 
     * @param iType
     *            the type of the input
     * @param mouseX
     *            the x position of the mouse click
     * @param mouseY
     *            the y position of the mouse click
     */
    public Input(final InputType iType, final double mouseX, final double mouseY) {
        this.inputType = iType;
        this.x = mouseX;
        this.y = mouseY;
        this.plantType = Optional.empty();
    }

    /**
     * Public constructor.
     * 
     * @param iType
     *            the input's type
     * @param mouseX
     *            the x position of the mouse click
     * @param mouseY
     *            the y position of the mouse click
     * @param pType
     *            the plant's type
     */
    public Input(final InputType iType, final double mouseX, final double mouseY, final PlantType pType) {
        this.inputType = iType;
        this.x = mouseX;
        this.y = mouseY;
        this.plantType = Optional.of(pType);
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public InputType getInputType() {
        return this.inputType;
    }

    @Override
    public Optional<PlantType> getPlant() {
        return this.plantType;
    }
}
