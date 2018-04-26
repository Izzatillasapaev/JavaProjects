package sample;

import javafx.scene.Node;

public class Zombie {
    private Node zombie;
    private boolean visible;
    private boolean needDelete = false;

    Zombie(Node zombie) {
        this.zombie = zombie;
    }

    public boolean isNear(double x, double y) {
        if (x - zombie.getLayoutX() < 100 && x - zombie.getLayoutX() > 9 && y - zombie.getLayoutY() > 9 && y - zombie.getLayoutY() < 100)
            return true;
        return false;
    }

    public boolean isNearHero(double x, double y) {
        if (x - zombie.getLayoutX() < 80 && zombie.getLayoutX() - x < 50
                && y - zombie.getLayoutY() < 80 && zombie.getLayoutY() - y < 50
                )
            return true;
        return false;
    }

    public void update(double x, double y) {
        double rotation = Math.atan2(x - zombie.getLayoutX(), zombie.getLayoutY() - y) / Math.PI * 180;
        rotation -= 100;
        zombie.setRotate(rotation);
        zombie.relocate((x - zombie.getLayoutX()) / 300 + zombie.getLayoutX(), (y - zombie.getLayoutY()) / 300 + zombie.getLayoutY());
    }

    public Node getZombie() {
        return zombie;
    }
}