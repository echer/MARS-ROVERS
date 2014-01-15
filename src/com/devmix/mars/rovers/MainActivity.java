package com.devmix.mars.rovers;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	private int[][] square;
	
	//private Rover[] rovers = new Rover[0];
	
	private static final String leftRotate = "L";
	private static final String rightRotate = "R";
	private static final String move = "M";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        buildSquare(5, 5);
        
        //Rover rover1 = createRover(1,2,Rover.POSITION_NORTH);
        Rover rover1 = new Rover(1,2,Rover.POSITION_NORTH);
        
        rover1 = sendCommands(rover1,new String[]{leftRotate,move,leftRotate,move,leftRotate,move,leftRotate,move,move});
        
        exibeResultado(rover1);
        
        Rover rover2 = new Rover(3,3,Rover.POSITION_EAST);
        
        rover2 = sendCommands(rover2, new String[]{"M","M","R","M","M","R","M","R","R","M"});
        
        exibeResultado(rover2);
    }
    
    public void exibeResultado(Rover r){
    	Log.i("Output", "X: "+r.getX()+" | Y: "+r.getY()+" | Front to: "+getFrontTo(r.getFrontTo()));
    }
    
    private String getFrontTo(int frontTo) {
		switch (frontTo) {
		case Rover.POSITION_NORTH:
			return "N";
		case Rover.POSITION_EAST:
			return "E";
		case Rover.POSITION_SOUTH:
			return "S";
		case Rover.POSITION_WEAST:
			return "W";
		default:
			return "";
		}
	}

	private Rover sendCommands(Rover rover, String... commands) {
		for(String command:commands){
			rover = runCommand(rover,command);
		}
		return rover;
	}

	private Rover runCommand(Rover rover, String command) {
		if(command == leftRotate){
			rover.setFrontTo(rotateLeft(rover.getFrontTo()));
		}else if(command == rightRotate){
			rover.setFrontTo(rotateRight(rover.getFrontTo()));
		}else if(command == move){
			rover.move();
		}
		return rover;
	}

	private int rotateLeft(int frontTo) {
		if(frontTo == Rover.POSITION_NORTH)return Rover.POSITION_WEAST;
		if(frontTo == Rover.POSITION_WEAST)return Rover.POSITION_SOUTH;
		if(frontTo == Rover.POSITION_SOUTH)return Rover.POSITION_EAST;
		if(frontTo == Rover.POSITION_EAST)return Rover.POSITION_NORTH;
		return -1;
	}
	
	private int rotateRight(int frontTo) {
		if(frontTo == Rover.POSITION_NORTH)return Rover.POSITION_EAST;
		if(frontTo == Rover.POSITION_EAST)return Rover.POSITION_SOUTH;
		if(frontTo == Rover.POSITION_SOUTH)return Rover.POSITION_WEAST;
		if(frontTo == Rover.POSITION_WEAST)return Rover.POSITION_NORTH;
		return -1;
	}
	
	

	/*private Rover createRover(int x, int y, int frontTo) {
		Rover r = new Rover(x,y,frontTo);
    	appendRover(new Rover(x,y,frontTo));
    	return r;
	}*/

	/*private void appendRover(Rover rover) {
		Rover[] aux;
		if(rovers.length == 0){
			aux = new Rover[1];
			aux[0] = rover;
		}else{
			aux = new Rover[rovers.length+1];
			for(int i = 0;i<aux.length-1;i++){
				aux[i] = rovers[i];
			}
			aux[rovers.length] = rover;
		}
		rovers = aux;
	}*/

	private boolean buildSquare(int width, int height){
    	if(width > 0 && height > 0){
	    	square = new int[width][height];
	    	return true;
    	}else{
    		return false;
    	}
    }
    
    public class Rover{
    	private static final int POSITION_NORTH = 0;
    	private static final int POSITION_EAST = 1;
    	private static final int POSITION_SOUTH = 2;
    	private static final int POSITION_WEAST = 3;
    	private int frontTo = POSITION_NORTH;
    	private int x = 0,y = 0;
    	
    	public void move(){
    		switch (frontTo) {
			case POSITION_NORTH:
				y++;
				break;
			case POSITION_SOUTH:
				y--;
				break;
			case POSITION_EAST:
				x++;
				break;
			case POSITION_WEAST:
				x--;
				break;
			default:
				break;
			}
    	}
    	
    	public Rover(){}
    	
    	public Rover(int x, int y, int frontTo){
    		this.x = x;
    		this.y = y;
    		this.frontTo = frontTo;
    	}
		public int getFrontTo() {
			return frontTo;
		}
		public void setFrontTo(int frontTo) {
			this.frontTo = frontTo;
		}
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
    }


    
}
