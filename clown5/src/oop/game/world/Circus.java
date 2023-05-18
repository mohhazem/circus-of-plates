package oop.game.world;

import eg.edu.alexu.csd.oop.game.GameEngine;
import oop.game.object.Constants;
import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;
import oop.game.object.ClownObject;
import java.util.LinkedList;
import java.util.List;
import oop.game.myfirstgame.AudioPlayer;
import oop.game.object.Shapes;

public class Circus implements World{
    
    private static final int MAX_TIME = 1 * 60 * 1000; // 1 minute
    
    private boolean soundPlayed = false;
    
    private static int DEFAULT_SEPEED = 10;
    private static int DEFAULT_CONTROL_SEPEED = 20;

    private static long startTime;
    private static long pauseTime;
    public static int score;
    private final int DIFFICULTY;
    private final int SPEED;
    
    public static PlateGenerator plateEngine;
    public static ObstacleGenerator obstacleEngine; 

    private final List<GameObject> constant;
    private final List<GameObject> moving;
    private final List<GameObject> control;
    private static boolean musicPlaying;
    
    public static void pause(){
        plateEngine.setShouldStop(true);
        obstacleEngine.setShouldStop(true);
        pauseTime = System.currentTimeMillis();
    }

    public static void resume() {
        plateEngine.setShouldStop(false);
        obstacleEngine.setShouldStop(false);
        startTime += System.currentTimeMillis() - pauseTime;
        pauseTime = 0;
    }
    
    public Circus(int difficulty) {
        
        this.startTime = System.currentTimeMillis();
        this.score = 0;
        
        this.DIFFICULTY = difficulty;
        this.SPEED = difficulty + 1;
        
        this.constant = new LinkedList<>();
        this.moving = new LinkedList<>();
        this.control = new LinkedList<>();

        createGameObject();
    }

    private void createGameObject() {
        
        if (!musicPlaying){
            AudioPlayer audio = AudioPlayer.getInstance("src\\resources\\song2.wav");
            audio.play();
            musicPlaying = true;
        }
        
        //Add backgroud
        constant.add(new Shapes(0, 0, "circus.jpg"));

        // creat plate dropping thread
        plateEngine = new PlateGenerator(moving);
        plateEngine.start();
        obstacleEngine = new ObstacleGenerator(moving, DIFFICULTY);
        obstacleEngine.start();
        
        //Add clown
        ClownObject clown = ClownObject.getInstance(Constants.SCREEN_WIDTH / 3, (int) (Constants.SCREEN_HEIGHT * 0.7), constant);
        control.add(clown);       
    }

    @Override
    public List<GameObject> getConstantObjects() {
        return this.constant;
    }

    @Override
    public List<GameObject> getMovableObjects() {
        return this.moving;
    }

    @Override
    public List<GameObject> getControlableObjects() {
        return this.control;
    }

    @Override
    public int getWidth() {
        return Constants.SCREEN_WIDTH;
    }

    @Override
    public int getHeight() {
        return Constants.SCREEN_HEIGHT;
    }

    @Override
    public boolean refresh() {

        boolean timeout = System.currentTimeMillis() - startTime > MAX_TIME; // time end and game over
        
        if (!timeout){
            // movable objectss 
            ClownObject clown = (ClownObject) control.get(0);
            GameObject fallenPlate = null;
            GameObject caughtPlate = null;

            if (moving.isEmpty())
                return timeout;
                        
            for (GameObject fallingObject : moving) {

                Shapes cascadedFallingObject = (Shapes) fallingObject;

                Strategy.setType(cascadedFallingObject.getType());
                int caught = Strategy.checkObject(clown, fallingObject);

                if (caught == 1){
                    // caught plate
                    if (!clown.scored())
                        constant.add(fallingObject);
                    caughtPlate = fallingObject;
                }
                else if (caught == 2){
                    // caught obstacle
                    caughtPlate = fallingObject;
                }
                else if(fallingObject.getY() >= Constants.SCREEN_HEIGHT){
                    // object fell of the map
                    fallenPlate = fallingObject;
                }
                else{
                    // move object
                    fallingObject.setY(fallingObject.getY() + SPEED);
                }
            }
            moving.remove(fallenPlate);
            moving.remove(caughtPlate);
        }
        
        if (timeout && !soundPlayed){
            AudioPlayer audio = AudioPlayer.getInstance("C:\\Users\\Omar\\Desktop\\clown5\\src\\resources\\GameOver.wav");
            audio.play();
            plateEngine.setShouldStop(true);
            obstacleEngine.setShouldStop(true);
            soundPlayed = true;
        }
        
        return !timeout;
    }

    @Override
    public String getStatus() {
        return "Score=" + score + "   |   Time=" + Math.max(0, (MAX_TIME - (System.currentTimeMillis() - startTime)) / 1000);// update status
    }

    @Override
    public int getSpeed() {
        return DEFAULT_SEPEED;
    }

    @Override
    public int getControlSpeed() {
        return DEFAULT_CONTROL_SEPEED;
    }
}
