package pvz.view.menu.render;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import pvz.model.WorldConstants;
import pvz.model.entity.AttackerEntity;
import pvz.model.entity.Entity;
import pvz.model.entity.child.projectile.AbstractProjectile;
import pvz.model.entity.child.resource.Sun;
import pvz.model.entity.defence.Lawnmower;
import pvz.model.entity.plant.Plant;
import pvz.model.entity.zombie.Zombie;
import pvz.utility.Pair;
import pvz.view.MainWindow;
import pvz.view.menu.utility.ImageMapper;

/**
 * Public class to draw entities into the game screen.
 *
 */
public class EntityDrawer {

    private static final double FIT_CELL = MainWindow.getSceneHeight() / 8.5;
    private static final double FIT_PROJECTILE = MainWindow.getSceneHeight() / 20;
    private static final double FIT_ZOMBIE = MainWindow.getSceneHeight() / 6;
    private static final double HIGHER_CONST = 12;
    private double higherEntityOffset;
    private final double fieldX;
    private final double fieldY;
    private final double fieldHeight;
    private final double fieldWidth;

    private Map<Long, Pair<Boolean, ImageView>> entityMap;
    private Map<Long, Boolean> checkMap;

    /**
     * Public constructor.
     * 
     * @param position
     *            the position x,y of the battlefield
     * @param dimension
     *            the width and height of the battlefield
     */
    public EntityDrawer(final Pair<Double, Double> position, final Pair<Double, Double> dimension) {
        this.fieldX = position.getX();
        this.fieldY = position.getY();
        this.fieldWidth = dimension.getX();
        this.fieldHeight = dimension.getY();
        this.entityMap = new HashMap<>();
        this.checkMap = new HashMap<>();
        higherEntityOffset = this.fieldHeight / HIGHER_CONST;
    }

    /**
     * Public method to render.
     * 
     * @param layout
     *            the pane where entities are drawn.
     * @param list
     *            the list of entities.
     */
    public void render(final Pane layout, final List<Entity> list) {
        layout.getChildren().clear();

        /* Rendering Lawnmower */
        list.stream().filter(e -> e instanceof Lawnmower).forEach(e -> {
            if (((AttackerEntity) e).isAttacking()) {
                this.checkAndUpdateEntity(layout, e, true, false, FIT_CELL);
            } else {
                this.checkAndUpdateEntity(layout, e, false, false, FIT_CELL);
            }
        });

        /* Rendering PLANTS */
        list.stream().filter(e -> e instanceof Plant).sorted((e1, e2) -> Double.compare(e2.getX(), e1.getX()))
                .forEach(e -> {
                    if (e instanceof AttackerEntity && ((AttackerEntity) e).isAttacking()) {
                        this.checkAndUpdateEntity(layout, e, true, false, FIT_CELL);
                    } else {
                        this.checkAndUpdateEntity(layout, e, false, false, FIT_CELL);
                    }
                });

        /* Rendering ZOMBIES */
        list.stream().filter(e -> e instanceof Zombie).sorted((e1, e2) -> Double.compare(e1.getY(), e2.getY()))
                .forEach(e -> {
                    if (((AttackerEntity) e).isAttacking()) {
                        this.checkAndUpdateEntity(layout, e, true, true, FIT_ZOMBIE);
                    } else {
                        this.checkAndUpdateEntity(layout, e, false, true, FIT_ZOMBIE);
                    }
                });

        /* Rendering Sun */
        list.stream().filter(e -> e instanceof Sun).forEach(e -> {
            this.checkAndUpdateEntity(layout, e, false, false, FIT_CELL);
        });

        /* Rendering PROJECTILES */
        list.stream().filter(e -> e instanceof AbstractProjectile).forEach(e -> {
            this.checkAndUpdateEntity(layout, e, false, false, FIT_PROJECTILE);
        });

        this.refreshMap();
    }

    private void checkAndUpdateEntity(final Pane layout, final Entity entity, final boolean isAttacking,
            final boolean isBigger, final double fit) {
        ImageView iv;
        final Long id = entity.getID();
        if (!this.entityMap.containsKey(id)) {
            iv = new ImageView();
            iv.setImage(ImageMapper.get().getImage(entity));
            iv.setPreserveRatio(true);
            iv.setFitHeight(fit);
            iv.setX(this.fieldX + (this.fieldWidth * (entity.getX() / WorldConstants.BACKYARD_WIDTH)));
            if (isBigger) {
                iv.setY(this.fieldY - this.higherEntityOffset
                        + (this.fieldHeight * (entity.getY() / WorldConstants.BACKYARD_HEIGHT)));
            } else {
                iv.setY(this.fieldY + (this.fieldHeight * (entity.getY() / WorldConstants.BACKYARD_HEIGHT)));
            }
            iv.toFront();
            this.entityMap.put(id, new Pair<Boolean, ImageView>(isAttacking, iv));
            this.checkMap.put(id, true);
        } else {
            iv = this.entityMap.get(id).getY();
            iv.setX(this.fieldX + (this.fieldWidth * (entity.getX() / WorldConstants.BACKYARD_WIDTH)));
            if (isBigger) {
                iv.setY(this.fieldY - this.higherEntityOffset
                        + (this.fieldHeight * (entity.getY() / WorldConstants.BACKYARD_HEIGHT)));
            } else {
                iv.setY(this.fieldY + (this.fieldHeight * (entity.getY() / WorldConstants.BACKYARD_HEIGHT)));
            }

            if (this.entityMap.get(id).getX() != isAttacking) {
                iv.setImage(ImageMapper.get().getImage(entity));
            }
            this.entityMap.replace(id, new Pair<Boolean, ImageView>(isAttacking, iv));
            this.checkMap.replace(id, true);
        }
        layout.getChildren().add(iv);
    }

    private void refreshMap() {
        List<Long> entityToRemove = this.checkMap.keySet().stream().filter(k -> !this.checkMap.get(k))
                .collect(Collectors.toList());

        entityToRemove.stream().forEach(k -> {
            this.entityMap.remove(k);
            this.checkMap.remove(k);
        });

        this.checkMap.keySet().stream().forEach(k -> {
            this.checkMap.replace(k, false);
        });
    }
}
