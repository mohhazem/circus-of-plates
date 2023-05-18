package oop.game.world;

import eg.edu.alexu.csd.oop.game.GameObject;
import oop.game.myfirstgame.AudioPlayer;
import oop.game.object.ClownObject;
import oop.game.object.Constants;
import oop.game.object.Shapes;
import static oop.game.world.Circus.score;

public class Strategy {
    
    private static int type;

    public static int getType() {
        return type;
    }

    public static void setType(int type) {
        Strategy.type = type;
    }
    
    public static int checkObject(GameObject clown, GameObject object){
        if (type > 10){
            return ObstacleFunction(clown, object);
        }
        else if (type < 11){
            return plateFunction(clown, object);
        }
        return 0;
    }
    
    private static int plateFunction(GameObject clown, GameObject plate){
        int stackOne = clown.getX() + Constants.STACK_ONE_OFFSET;
        int stackTwo = clown.getX() + Constants.STACK_TWO_OFFSET;
        
        ClownObject cascadedClown = (ClownObject) clown;
        
        int horizontalDistance = plate.getY() - cascadedClown.getStackOneHieght();
        if(horizontalDistance > -2 && horizontalDistance  < 2){
            int distanceFromStackOne = stackOne - plate.getX();
            if (distanceFromStackOne <= plate.getWidth() && distanceFromStackOne >= 0){
                cascadedClown.addToStackOne((Shapes) plate);
                return 1;
            }
        }
           
        horizontalDistance = plate.getY() - cascadedClown.getStackTwoHieght();
        if(horizontalDistance > -2 && horizontalDistance  < 2){
            
            int distanceFromStackTwo = stackTwo - plate.getX();
            if (distanceFromStackTwo <= plate.getWidth() && distanceFromStackTwo >= 0){
                cascadedClown.addToStackTwo((Shapes) plate);
                return 1;
            }
        }
        return 0;
    }

    private static int ObstacleFunction(GameObject clown, GameObject obstacle) {
        
        int stackOne = clown.getX() + Constants.STACK_ONE_OFFSET;
        int stackTwo = clown.getX() + Constants.STACK_TWO_OFFSET;
        ClownObject cascadedClown = (ClownObject) clown;
        
        int horizontalDistance = obstacle.getY() + obstacle.getHeight() - cascadedClown.getStackOneHieght();
        if(horizontalDistance > -2 && horizontalDistance < 2){
            
            int distanceFromStackOne = stackOne - obstacle.getX();
            
            if (distanceFromStackOne <= obstacle.getWidth() && distanceFromStackOne >= 0){
                if(type == 11){
                    AudioPlayer audio = AudioPlayer.getInstance("src\\resources\\break.wav");
                    audio.play();
                    cascadedClown.popStackOne();
                }
                else{
                    cascadedClown.blink();
                    AudioPlayer audio = AudioPlayer.getInstance("src\\resources\\boom.wav");
                    audio.play();
                    if(score > 2)
                        score -= 3;
                    else
                        score = 0;
                }
                return 2;
            }
        }
            
        horizontalDistance = obstacle.getY() + obstacle.getHeight() - cascadedClown.getStackTwoHieght();
        if(horizontalDistance > -2 && horizontalDistance < 2){

            int distanceFromStackTwo = stackTwo - obstacle.getX();
            if (distanceFromStackTwo <= obstacle.getWidth() && distanceFromStackTwo >= 0){

                if(type == 11){
                    AudioPlayer audio = AudioPlayer.getInstance("C:\\Users\\Omar\\Desktop\\clown5\\src\\resources\\break.wav");
                    audio.play();
                    cascadedClown.popStackTwo();
                }
                else{
                    cascadedClown.blink();
                    AudioPlayer audio = AudioPlayer.getInstance("C:\\Users\\Omar\\Desktop\\clown5\\src\\resources\\boom.wav");
                    audio.play();
                    if(score > 2)
                        score -= 3;
                    else
                        score = 0;
                }
                return 2;
            }
        }
        return 0;
    }
}
