package com.devmix.mars.rovers;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * @author echer
 *
 */
public class MainActivity extends Activity {

	private int squareWidh = 0,squareHeight;
	
	
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
    
    /**
     * EXIBE RESULTADO NO LOG DO ANDROID
     * @param r
     */
    public void exibeResultado(Rover r){
    	Log.i("Output", "X: "+r.getX()+" | Y: "+r.getY()+" | Front to: "+getFrontTo(r.getFrontTo()));
    }
    
    /**
     * CONVERTE POSICAO EM STRING REPRESENTANDO A CARDINALIDADE
     * @param frontTo CARDINALIDADE A SER CONVERTIDA
     * @return CARDINALIDADE
     */
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

    /**
     * ENVIA COMANDO PARA O ROVER E RETORNA O OBJETO
     * @param rover
     * @param commands ARRAY DE COMANDOS A SEREM EXECUTADOS
     * @return ROVER QUE FORAM EXECUTADOS OS COMANDOS
     */
	private Rover sendCommands(Rover rover, String... commands) {
		for(String command:commands){
			rover = runCommand(rover,command);
		}
		return rover;
	}

	/**
	 * EXECUTA COMANDO
	 * @param rover
	 * @param command COMANDO A SER EXECUTADO
	 * @return
	 */
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

	/**
	 * RODA PARA A ESQUERDA E RETORNA A POSICAO
	 * @param frontTo
	 * @return
	 */
	private int rotateLeft(int frontTo) {
		if(frontTo == Rover.POSITION_NORTH)return Rover.POSITION_WEAST;
		if(frontTo == Rover.POSITION_WEAST)return Rover.POSITION_SOUTH;
		if(frontTo == Rover.POSITION_SOUTH)return Rover.POSITION_EAST;
		if(frontTo == Rover.POSITION_EAST)return Rover.POSITION_NORTH;
		return -1;
	}
	
	/**
	 * RODA PARA A DIREITA E RETORNA A POSICAO
	 * @param frontTo
	 * @return
	 */
	private int rotateRight(int frontTo) {
		if(frontTo == Rover.POSITION_NORTH)return Rover.POSITION_EAST;
		if(frontTo == Rover.POSITION_EAST)return Rover.POSITION_SOUTH;
		if(frontTo == Rover.POSITION_SOUTH)return Rover.POSITION_WEAST;
		if(frontTo == Rover.POSITION_WEAST)return Rover.POSITION_NORTH;
		return -1;
	}
	
	/**
	 * CONSTROI PLANICE PARA O ROVER ANDAR
	 * @param width
	 * @param height
	 * @return
	 */
	private boolean buildSquare(int width, int height){
    	if(width > 0 && height > 0){
    		squareWidh = width;
    		squareHeight = height;
	    	return true;
    	}else{
    		return false;
    	}
    }
    
	/**
	 * OBJETO ROVER
	 * @author echer
	 *
	 */
    public class Rover{
    	private static final int POSITION_NORTH = 0;
    	private static final int POSITION_EAST = 1;
    	private static final int POSITION_SOUTH = 2;
    	private static final int POSITION_WEAST = 3;
    	private int frontTo = POSITION_NORTH;
    	private int x = 0,y = 0;
    	
    	/**
    	 * MOVE O ROVER DE ACORDO COM A POSICAO RESPEITANDO A PLANICE DO SQUARE
    	 */
    	public void move(){
    		switch (frontTo) {
			case POSITION_NORTH:
				if((y+1) <= squareHeight)
				y++;
				break;
			case POSITION_SOUTH:
				if((y-1) >= 0)
				y--;
				break;
			case POSITION_EAST:
				if((x+1) <= squareWidh)
				x++;
				break;
			case POSITION_WEAST:
				if((x-1) >= 0)
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
