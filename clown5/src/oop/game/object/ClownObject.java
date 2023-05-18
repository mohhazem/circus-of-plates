package oop.game.object;

import eg.edu.alexu.csd.oop.game.GameObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import oop.game.myfirstgame.AudioPlayer;
import oop.game.world.Circus;

public class ClownObject extends Shapes{
    
    private boolean scored = false;
    
    final static String imagePath = "clown.png";
    static ClownObject instance = null;
    private Stack stackOne, stackTwo;
    private List<GameObject> constant;
    
    private ClownObject(int x, int y, List<GameObject> constant){
        super(x, y, imagePath);
        stackOne = new Stack();
        stackTwo = new Stack();
        this.constant = constant;
    }
    
    public static ClownObject getInstance(int x, int y, List<GameObject> constant) {
        if(instance == null){
            instance = new ClownObject(x, y, constant);
        }
        
        if (constant != null){
            instance.clear();
            instance.constant = constant;
        }
        
        return instance;
    }

    public void blink(){
        Blinker blinker = new Blinker();
        blinker.start();
    }
    
    public boolean scored() {
        return scored;
    }
    
    public int getStackOneHieght(){
        return stackOne.height;
    }
    
    public int getStackTwoHieght(){
        return stackTwo.height;
    }
    
    public void addToStackOne(Shapes plate){
        stackOne.add(plate);
    }
    
    public void addToStackTwo(Shapes plate){
        stackTwo.add(plate);
    }
    
    @Override
    public void setX(int x){
        super.setX(x);
        for (Shapes plate: stackOne){
            plate.setX(x + Constants.STACK_ONE_OFFSET - plate.getWidth()/2);
        }
        for (Shapes plate: stackTwo){
            plate.setX(x + Constants.STACK_TWO_OFFSET - plate.getWidth()/2);
        }
    }
    
    @Override
    public void setY(int y){}

    public void popStackOne() {
        stackOne.pop();
    }
    
    public void popStackTwo() {
        stackTwo.pop();
    }

    private void clear() {
        stackOne = new Stack();
        stackTwo = new Stack();
    }
            
    private class Stack extends ArrayList<Shapes>{
        
        private int height, similarPlates;
        
        public Stack(){
            super();
            height = getY();
            similarPlates = 1;
        }
        
        private void checkStack(){
            if (similarPlates == 3){
                AudioPlayer audio = AudioPlayer.getInstance("src\\resources\\el3ab3.wav");
                audio.play();
                for (int i = 0; i < 3; i++){
                    Shapes plate = get(size() - 1);
                    constant.remove(plate);
                    pop();
                }
                
                Circus.score++;
                scored = true;
            }
            else 
                scored = false;
        }
        
        @Override
        public boolean add(Shapes plate){
            if(!contains(plate)){
                super.add(plate);
                height -= Constants.PLATE_HEIGHT;
                plate.setY(height);
                setX(getX());
               
                if(size() > 1){
                    if (plate.getType() == get(size() - 2).getType()){
                        similarPlates++;
                    }
                    else{
                        similarPlates = 1;
                    }
                    checkStack();
                }
                else 
                    scored = false;
            }
            return false;
        }

        private void pop() {
            if(!isEmpty()){
                GameObject plate = get(size() - 1);
                constant.remove(plate);
                remove(plate);
                height += Constants.PLATE_HEIGHT;
                
                similarPlates = 1;
                
                if(size() >= 2)
                    if (get(size() - 1).getType() == get(size() - 2).getType())
                        similarPlates++;
            }
        }
    }
    
    private class Blinker extends Thread{
        
        @Override
        public void run(){
            try {
                for(int i = 0; i < 3;i++){
                    setVisible(false);
                    sleep(300);
                    setVisible(true);
                    sleep(300);
                }
            } catch (Exception e) {
                Logger.getLogger(ClownObject.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
   
