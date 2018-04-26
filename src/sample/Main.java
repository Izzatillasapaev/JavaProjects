package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import static java.lang.System.*;

import java.lang.Exception;
import java.util.Random;

public class Main extends Application {

    private static final double W = 1250, H = 700;

    private static final String HERO_IMAGE_LOC =
            "/Users/izzatilla/Desktop/Final project/img/main_hero.png";
    private static final String ZOMBIE_IMAGE_LOC =
            "/Users/izzatilla/Desktop/Final project/img/zom0.gif";
    private static final String SHOOT_IMAGE_LOC =
            "/Users/izzatilla/Desktop/Final project/img/shoot.png";

    Pane dungeon;
    Group endGroup;
    private Image heroImage;
    private Image zombieImage;
    private Image shootImage;
    private Node hero;
    private Node zombie;
    private Node shoot;
    private TextArea text;
    private static int scoreValue;
    private static Integer bestScore;
    private Line line;
    private static AnimationTimer timer;
    private double bulletSpeed = 10;
    private boolean running, goNorth, goSouth, goEast, goWest, shooting;
    private Scene scene;
    private Scene endScene;
    private boolean gameIsOver;

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        heroImage = new Image(new FileInputStream(HERO_IMAGE_LOC));
        zombieImage = new Image(new FileInputStream(ZOMBIE_IMAGE_LOC), 110, 133, false, false);
        shootImage = new Image(new FileInputStream(SHOOT_IMAGE_LOC));
        hero = new ImageView(heroImage);
        zombie = new ImageView(zombieImage);
        shoot = new ImageView(shootImage);
        text = new TextArea();
        line = new Line();
        gameIsOver = false;
        readFile();
        Label score = new Label("Score: 0");
        Label bestScoreLabel = new Label("Best Score: " + bestScore);

        dungeon = new Pane(hero);
        dungeon.setId("pane0");
        moveHeroTo(W / 2, H / 2);
        dungeon.getChildren().addAll(score, bestScoreLabel);
        score.relocate(1100, 20);
        score.setFont(new Font("Paintdrips",20));
        score.setTextFill(Color.rgb(250, 50, 0));
        bestScoreLabel.relocate(20, 20);
        bestScoreLabel.setFont(new Font("Paintdrips",20));
        bestScoreLabel.setTextFill(Color.rgb(250, 50, 0));
        scene = new Scene(dungeon, W, H);

        GridPane gridPane = new GridPane();
        Image image = new Image ("sample/ground.png");

        dungeon.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));
        scene.getStylesheets().add("sample/style.css");

        gridPane.setStyle("-fx-background-color: rgba(0,4,255,0.33);");
        endScene = new Scene(gridPane, W, H, Color.GREENYELLOW);
        endScene.getStylesheets().add("sample/style.css");

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        goNorth = true;
                        break;
                    case DOWN:
                        goSouth = true;
                        break;
                    case LEFT:
                        goWest = true;
                        break;
                    case RIGHT:
                        goEast = true;
                        break;
                    case SHIFT:
                        running = true;
                        break;
                    case SPACE:
                        shooting = true;
                        break;

                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        goNorth = false;
                        break;
                    case DOWN:
                        goSouth = false;
                        break;
                    case LEFT:
                        goWest = false;
                        break;
                    case RIGHT:
                        goEast = false;
                        break;
                    case SHIFT:
                        running = false;
                        break;
                    case SPACE:
                        shooting = false;
                        break;

                }
            }
        });

        stage.setScene(scene);
        stage.show();

        scoreValue = 0;
        timer = new AnimationTimer() {
            Bullet bullet;
            double minTime = 0;


            double shootFrequency = 30;
            double runningSpeed = 5;
            double walkingSpeed = 1;
            double bulletSpeed = 10;
            int timer = 0; //timer for zombies generate
            int genFrequency = 200; //zombies generating frequency
            List<Bullet> bullets = new ArrayList<Bullet>();
            char faceSide = 'S';
            List<Zombie> zombies = new ArrayList<Zombie>();

            @Override
            public void handle(long now) {
                double dx = 0;
                double dy = 0;
                if (timer % genFrequency == 0) {
                    if (genFrequency > 30) genFrequency -= 2;
                    if (shootFrequency > 5) shootFrequency -= 0.5;
                    if (runningSpeed < 8)
                        runningSpeed += 0.05;
                    if (bulletSpeed < 10)
                        bulletSpeed += 0.1;
                    if (walkingSpeed < 5)
                        walkingSpeed += 0.03;
                    zombies.add(getNewZombie(getZombieRandomPosX(), getZombieRandomPosY()));
                }
                for (Zombie z : zombies) {
                    z.update(hero.getLayoutX(), hero.getLayoutY());
                    if (z.isNearHero(hero.getLayoutX(), hero.getLayoutY())) {
                        //dungeon.getChildren().removeAll();
                        gameIsOver = true;
                        stopGame(dungeon, gridPane, stage, endScene);
                      /*  dungeon.getChildren().clear();
                        EndWindow endWindow = new EndWindow(gridPane);
                        endWindow.start();
                       // stage.setScene(endScene);
                       timer.stop();
                        new MainWindow(endWindow);
                        */
                        return;

                    }
                }

                if (goNorth) {
                    faceSide = 'N';
                    dy -= 1;
                    hero.setRotate(202);
                } else if (goSouth) {
                    dy += 1;
                    faceSide = 'S';
                    hero.setRotate(22);
                } else if (goEast) {
                    dx += 1;
                    faceSide = 'E';
                    hero.setRotate(292);

                } else if (goWest) {
                    hero.setRotate(112);
                    dx -= 1;
                    faceSide = 'W';
                }

                if (running) {
                    dx *= runningSpeed / walkingSpeed;
                    dy *= runningSpeed / walkingSpeed;
                }
                if (shooting && minTime > shootFrequency) {


                    if (faceSide == 'N') {
                        bullets.add(getNewBullet(hero.getLayoutX() + 45, hero.getLayoutY() - 35, 0, 'N'));
                    } else if (faceSide == 'S') {
                        bullets.add(getNewBullet(hero.getLayoutX() + 27, hero.getLayoutY() + 100, 0, 'S'));
                    } else if (faceSide == 'E') {
                        bullets.add(getNewBullet(hero.getLayoutX() + 101, hero.getLayoutY() + 42, 90, 'E'));
                    } else if (faceSide == 'W') {
                        bullets.add(getNewBullet(hero.getLayoutX() - 34, hero.getLayoutY() + 24, 90, 'W'));
                    }
                    minTime = 0;
                }
                minTime += 1.0;
                moveHeroBy(dx * walkingSpeed, dy * walkingSpeed);


                for (Bullet b : bullets) {
                    b.update();

                    if (b.getNeedDelete()) {
                        dungeon.getChildren().removeAll(b.getBullet());
                        bullets.remove(b);
                    }
                    List<Zombie> killedZombies = new ArrayList<>();
                    for (Zombie z : zombies) {
                        if (z.isNear(b.getBullet().getLayoutX(), b.getBullet().getLayoutY())) {
                            dungeon.getChildren().removeAll(b.getBullet());
                            dungeon.getChildren().removeAll(z.getZombie());
                            killedZombies.add(z);
                            bullets.remove(b);
                            scoreValue++;
                            score.setText("Score: " + scoreValue);

                            if (scoreValue > bestScore) {
                                bestScore = scoreValue;
                                bestScoreLabel.setText("Best score: " + bestScore);
                                writeFile();

                            }

                          //  System.out.println("Deleted :: " + bullets.size());
                        }
                    }

                    for (Zombie killedZombie  : killedZombies){
                        zombies.remove(killedZombie);

                    }
                    killedZombies.clear();
                }

                timer++;


            }


        };
        //System.out.println(gameIsOver);
        timer.start();

        //  System.out.println(timer.minTime);
        if (gameIsOver) {
            timer.stop();
            dungeon.getChildren().clear();
            System.out.println("done");
        }
        System.out.println(gameIsOver);

    }

    private void moveHeroBy(double dx, double dy) {
        final double cx = hero.getBoundsInLocal().getWidth() / 2;
        final double cy = hero.getBoundsInLocal().getHeight() / 2;

        double x = cx + hero.getLayoutX() + dx;
        double y = cy + hero.getLayoutY() + dy;

        moveHeroTo(x, y);
    }

    private void moveHeroTo(double x, double y) {
        final double cx = hero.getBoundsInLocal().getWidth() / 2;
        final double cy = hero.getBoundsInLocal().getHeight() / 2;

        if (x - cx >= 0 &&
                x + cx <= W &&
                y - cy >= 0 &&
                y + cy <= H) {
            hero.relocate(x - cx, y - cy);
        }
    }

    public static void stopGame(Pane dungeon, GridPane gridPane, Stage stage, Scene endScene) {
        timer.stop();
        dungeon.getChildren().clear();
        EndWindow endWindow = new EndWindow(gridPane, scoreValue);
        endWindow.start();
        stage.setScene(endScene);
        //  timer.stop();
    }

    public static void writeFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
                    "/Users/izzatilla/Desktop/Final project/logs/best.txt"), false));
            bw.write(String.valueOf(bestScore));
          //  bw.newLine();
            bw.close();
        } catch (Exception e) {
            System.err.println("Exception error");
            e.printStackTrace();
        }
    }

    public static void readFile() {

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("/Users/izzatilla/Desktop/Final project/logs/best.txt"));
            String line = "0";
            if ((line = br.readLine()) != null) {
                 bestScore = Integer.valueOf(line);
                // System.out.println(Integer.valueOf(line.substring(0,line.length()-1)));
            }
        } catch (IOException e) {
            System.out.println("You have forgotten to write some information\nPlease complete input");
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    private void moveShootTo(double x, double y) {

        shoot.relocate(x, y);
    }

    private Bullet getNewBullet(double x, double y, double rotation, char diraction) {
        Node bullet;

        bullet = new ImageView(shootImage);
        bullet.relocate(x, y);
        bullet.setRotate(rotation);
        dungeon.getChildren().addAll(bullet);
        return new Bullet(diraction, bullet, true, bulletSpeed);
    }

    public int getZombieRandomPosX() {
        Random rand = new Random();
        int x = rand.nextInt(1000);
        if ((x - hero.getLayoutX()) > 500 || hero.getLayoutX() - x > 500)
            return x;
        return getZombieRandomPosX();
    }


    public int getZombieRandomPosY() {
        Random rand = new Random();
        int y = rand.nextInt(700);
        if ((y - hero.getLayoutY() > 300 || hero.getLayoutY() - y > 300))
            return y;
        return getZombieRandomPosY();
    }

    private Zombie getNewZombie(double x, double y) {

        Node zom;
        zom = new ImageView(zombieImage);
        zom.relocate(x, y);
        dungeon.getChildren().addAll(zom);
        return new Zombie(zom);

    }


//     public static void main(String[] args) {
//        launch(args);
//    }
}
