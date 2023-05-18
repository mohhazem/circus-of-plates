package oop.game.world;

import eg.edu.alexu.csd.oop.game.GameObject;
import static java.lang.Thread.sleep;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ObstacleGenerator extends Thread {
    
    private final List<GameObject> moving;
    private boolean shouldStop = false;
    private final int SPAWN_RATE;

    public void setShouldStop(boolean shouldStop) {
        this.shouldStop = shouldStop;
    }
    
    public ObstacleGenerator(List<GameObject> moving, int difficulty){
        this.moving = moving;
        this.SPAWN_RATE = 3000 - difficulty * 1000;
    }
    
    @Override
    public void run(){
        
        while(true){
            if(shouldStop){
                continue;
            }
            moving.add(ObjectsFactory.getObject("obstacle"));
            try {
                sleep(SPAWN_RATE);
            } catch (InterruptedException ex) {
                Logger.getLogger(PlateGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

