package oop.game.object;

public class Plates {
    
    private int x, y, type;
    private static Plates instance; 
    
    private Plates(int x, int y, int type){
        this.x = x;
        this.y = y;
        this.type = type;
    }
    
    public static Plates getInstance(int x, int y, int type){
        if(instance == null){
            instance = new Plates(x, y, type);
        }else{
            instance.x = x;
            instance.y = y;
            instance.type = type;
        }
            
        return instance;
    }
    
    public Shapes getShape(){
        return new Shapes(x, y, type % Constants.PLATE_COLORS, "Plate" + type + ".png");
    }
}