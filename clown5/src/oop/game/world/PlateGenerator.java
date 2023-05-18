package oop.game.world;

import eg.edu.alexu.csd.oop.game.GameObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlateGenerator extends Thread {
    
    private final List<GameObject> moving;
    private boolean shouldStop = false;

    public void setShouldStop(boolean shouldStop) {
        this.shouldStop = shouldStop;
    }
    
    public PlateGenerator(List<GameObject> moving){
        this.moving = moving;
    }
    
    @Override
    public void run(){
        
        while(true){
            if(shouldStop){
                continue;
            }
            moving.add(ObjectsFactory.getObject("plate"));
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(PlateGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

