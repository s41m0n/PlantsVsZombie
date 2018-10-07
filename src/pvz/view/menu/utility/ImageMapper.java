package pvz.view.menu.utility;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import pvz.model.entity.AttackerEntity;
import pvz.model.entity.Entity;
import pvz.model.entity.child.ChildEntity;
import pvz.model.entity.defence.Lawnmower;
import pvz.model.entity.plant.PlantType;
import pvz.model.entity.zombie.Zombie;

/**
 * Public class where are saved all entities types and paths.
 *
 */
public final class ImageMapper {

    private static ImageMapper im;
    private Map<String, List<Image>> imageMap;

    /**
     * Private constructor.
     */
    private ImageMapper() {
        this.imageMap = new HashMap<>();
    }

    /**
     * Static method to get this class.
     * 
     * @return the imageMapper.
     */
    public static ImageMapper get() {
        if (im == null) {
            im = new ImageMapper();
        }
        return im;
    }

    /**
     * Method that returns the image of the entity giving the class.
     * 
     * @param entity
     *            the entity to return the image.
     * @return the corresponding image
     */
    public Image getImage(final Entity entity) {
        try {
            final String name = entity.getClass().getName().substring(entity.getClass().getName().lastIndexOf(".") + 1);
            final String key = name.toLowerCase();
            if (!this.imageMap.containsKey(key)) {
                String inPackage = entity.getClass().getPackage().getName()
                        .substring(entity.getClass().getPackage().getName().lastIndexOf(".") + 1).trim();
                if (entity instanceof Zombie) {
                    this.imageMap.put(key,
                            new ArrayList<>(Arrays.asList(
                                    new Image(this.getClass()
                                            .getResourceAsStream("/entity/" + inPackage + "/" + name + "1.gif")),
                                    new Image(this.getClass()
                                            .getResourceAsStream("/entity/" + inPackage + "/" + name + "2.gif")))));
                } else if (entity instanceof AttackerEntity && !(entity instanceof Lawnmower)) {
                    this.imageMap.put(key, new ArrayList<>(Arrays.asList(
                            new Image(
                                    this.getClass().getResourceAsStream("/entity/" + inPackage + "/" + name + ".png")),
                            new Image(
                                    this.getClass().getResourceAsStream("/entity/" + inPackage + "/" + name + "1.gif")),
                            new Image(this.getClass()
                                    .getResourceAsStream("/entity/" + inPackage + "/" + name + "2.gif")))));
                } else if (entity instanceof ChildEntity) {
                    this.imageMap.put(key, new ArrayList<>(Arrays.asList(new Image(
                            this.getClass().getResourceAsStream("/entity/" + inPackage + "/" + name + "1.png")))));
                } else {
                    this.imageMap.put(key,
                            new ArrayList<>(Arrays.asList(
                                    new Image(this.getClass()
                                            .getResourceAsStream("/entity/" + inPackage + "/" + name + ".png")),
                                    new Image(this.getClass()
                                            .getResourceAsStream("/entity/" + inPackage + "/" + name + "1.gif")))));
                }
            }
            if (entity instanceof Zombie) {
                if (((Zombie) entity).isAttacking()) {
                    return this.imageMap.get(key).get(1);
                } else {
                    return this.imageMap.get(key).get(0);
                }
            }
            if (entity instanceof AttackerEntity && !(entity instanceof Lawnmower)) {
                if (((AttackerEntity) entity).isAttacking()) {
                    return this.imageMap.get(key).get(2);
                } else {
                    return this.imageMap.get(key).get(1);
                }
            } else if (entity instanceof Lawnmower) {
                if (((Lawnmower) entity).isAttacking()) {
                    return this.imageMap.get(key).get(1);
                } else {
                    return this.imageMap.get(key).get(0);
                }
            } else if (entity instanceof ChildEntity) {
                return this.imageMap.get(key).get(0);
            }
            return this.imageMap.get(key).get(1);
        } catch (Exception e) {
            final Alert pa = ComponentFactory.getComponentFactory().getAlert("Error while loading image\n"
                    + entity.getClass().getName().substring(entity.getClass().getName().lastIndexOf(".") + 1));
            pa.show();
        }
        return null;
    }

    /**
     * Method that returns the image of the entity giving the plantType. (useful
     * for example when user is in a menu and there aren't any entity yet)
     * 
     * @param type
     *            the type of the plant.
     * @return the corresponding image
     */
    public Image getImage(final PlantType type) {
        try {
            final String key = type.name().toLowerCase().replace("_", "");
            if (!this.imageMap.containsKey(key)) {
                String className = key.substring(0, 1).toUpperCase() + key.substring(1);
                if (!type.canAttack()) {
                    this.imageMap.put(key, Arrays.asList(
                            new Image(this.getClass().getResourceAsStream("/entity/plants/" + className + ".png")),
                            new Image(this.getClass().getResourceAsStream("/entity/plants/" + className + "1.gif"))));
                } else {
                    this.imageMap.put(key, Arrays.asList(
                            new Image(this.getClass().getResourceAsStream("/entity/plants/" + className + ".png")),
                            new Image(this.getClass().getResourceAsStream("/entity/plants/" + className + "1.gif")),
                            new Image(this.getClass().getResourceAsStream("/entity/plants/" + className + "2.gif"))));
                }
            }
            return this.imageMap.get(key).get(0);
        } catch (Exception e) {
            final Alert pa = ComponentFactory.getComponentFactory().getAlert("Error while loading image\n" + type);
            pa.show();
        }
        return null;
    }
}
