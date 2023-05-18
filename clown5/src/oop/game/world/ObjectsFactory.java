package oop.game.world;

import oop.game.object.Shapes;
import oop.game.object.Constants;
import oop.game.object.Constants.*;
import oop.game.object.Plates;

public class ObjectsFactory {
    
    private static State currentPlate; 
    
    public static Shapes getObject(String type){
        
        if(currentPlate == null){
            currentPlate = new State(1);
        }
        
        switch (type) {
            case "plate" -> {
                int x = (int) (Math.random() * (Constants.SCREEN_WIDTH - Constants.PLATE_WIDTH));
                int imgNum = currentPlate.nextPlate();
                Plates plate = Plates.getInstance(x, 10, imgNum);
                imgNum = imgNum % Constants.PLATE_COLORS;
                currentPlate = new State(imgNum);
                return plate.getShape();
            }
            case "obstacle" -> {
                int x = (int) (Math.random() * (Constants.SCREEN_WIDTH - Constants.OBSTACLE_WIDTH));
                int y = 10;
                int imgNum = (int) ((Math.random() * 2 + 1)) ;
                String imagePath = "Obstacle" + imgNum + ".png";
                return new Shapes(x, y, imgNum + 10, imagePath);
            }
            default -> {
                return null;
            }
        }
    }
    
    public static class State{
        private int type;
        private int next;
        
        public State(int type){
            this.type = type;
            next = type + 1;
            if(type >=  Constants.PLATE_COLORS){
                type = Constants.PLATE_COLORS;
                next = 1;
            }
        }

        public int getType() {
            return type;
        }
        
        public int nextPlate(){
            
            if (Math.random() > 0.5){
                next += Constants.PLATE_COLORS;
            }
            
            return next;
        }
    }
}
