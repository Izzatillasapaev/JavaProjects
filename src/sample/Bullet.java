package sample;

import javafx.scene.Node;

public class Bullet {
    //bullet logic
    private char direction;
    private Node bullet;
    private boolean visible;
    private boolean needDelete = false;
    private boolean activated = false;
    private double speed;
    private int time;


    Bullet(char direction, Node bullet, boolean activated, double speed) {
        this.activated = activated;
        this.bullet = bullet;
        this.direction = direction;
        this.visible = true;
        this.speed=speed;
        this.time = 0;
        bullet.setVisible(visible);
    }

    public Node getBullet() {
        return bullet;
    }

    public int getTime() {
        return time;
    }

    public boolean isActive() {
        return activated;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setPosition(double x, double y) {
        bullet.relocate(x, y);
    }

    public void activate() {
        this.activated = true;
    }

    public void makeVisible() {
        bullet.setVisible(true);
        this.visible = true;
    }

    public void makeNotVisible() {
        bullet.setVisible(false);
        this.visible = false;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void update() {
        if (!activated) return;
        makeVisible();
        double bulletX = bullet.getLayoutX();
        double bulletY = bullet.getLayoutY();

        if (direction == 'N') {
            bulletY -= speed;
        }
        if (direction == 'E') {
            bulletX += speed;
        }
        if (direction == 'W') {
            bulletX -= speed;
        }
        if (direction == 'S') {
            bulletY += speed;
        }
        time += speed;
        if (bulletX < 0 || bulletX > 1225 || bulletY < 0 || bulletY > 680) {
            this.needDelete = true;
            this.activated = false;
            makeNotVisible();
            return;
        }
        bullet.relocate(bulletX, bulletY);


    }

    public boolean getNeedDelete() {
        return needDelete;
    }


}

