package com.devmix.mars.rovers;

import java.io.Serializable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author echer
 *
 */
@SuppressLint("DefaultLocale")
public class MainActivity extends Activity {

	private int squareWidh = 0,squareHeight = 0;
	
	
	public static final char leftRotate = 'L';
	public static final char rightRotate = 'R';
	public static final char move = 'M';
	
	private TextView txtOutput;
	private EditText edSquareW,edSquareH,edRoverX,edRoverY,edRoverP,edComando;
	
	private Rover roverInterface;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        txtOutput = (TextView)findViewById(R.id.txtOutput);
        edSquareW = (EditText)findViewById(R.id.edSquareW);
        edSquareH = (EditText)findViewById(R.id.edSquareH);
        edRoverX = (EditText)findViewById(R.id.edRoverX);
        edRoverY = (EditText)findViewById(R.id.edRoverY);
        edRoverP = (EditText)findViewById(R.id.edRoverP);
        edComando = (EditText)findViewById(R.id.edComando);
        
        
        edSquareW.setText("5");
        edSquareH.setText("5");
        
        btnBuildSquare_onClick(null);
        
        
        edRoverX.setText("1");
        edRoverY.setText("2");
        edRoverP.setText(String.valueOf(Rover.POSITION_NORTH));
        
        btnAddRover_onClick(null);
        
        edComando.setText(new String(new char[]{leftRotate,move,leftRotate,move,leftRotate,move,leftRotate,move,move}));
        
        btnRunCommand_onClick(null);
        
        edRoverX.setText("3");
        edRoverY.setText("3");
        edRoverP.setText(String.valueOf(Rover.POSITION_EAST));
        
        btnAddRover_onClick(null);
        
        edComando.setText(new String(new char[]{move,move,rightRotate,move,move,rightRotate,move,rightRotate,rightRotate,move}));
        
        btnRunCommand_onClick(null);
    }
    
    private void appenOutputText(String string) {
    	txtOutput.setText(txtOutput.getText()+"\n"+string);	
    	Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}
    
    public void btnBuildSquare_onClick(View v){
    	buildSquare(Integer.parseInt(edSquareW.getText().toString()), Integer.valueOf(edSquareH.getText().toString()));
    }
    
    public void btnAddRover_onClick(View v){
    	roverInterface = new Rover(Integer.parseInt(edRoverX.getText().toString()),Integer.parseInt(edRoverY.getText().toString()),getPositionTo());
    }
    
    @SuppressLint("DefaultLocale")
	private int getPositionTo() {
    	String upper = edRoverP.getText().toString().toUpperCase();
    	if("N".equals(upper))return Rover.POSITION_NORTH;
    	if("E".equals(upper))return Rover.POSITION_EAST;
    	if("S".equals(upper))return Rover.POSITION_SOUTH;
    	if("W".equals(upper))return Rover.POSITION_WEAST;
		return -1;
	}

	@SuppressLint("DefaultLocale")
	public void btnRunCommand_onClick(View v){
    	roverInterface = sendCommands(roverInterface,  edComando.getText().toString().toUpperCase().toCharArray());
    	exibeResultado();
    }

	/**
     * EXIBE RESULTADO NO LOG DO ANDROID
     * @param r
     */
    public void exibeResultado(){
    	String saida = "X: "+roverInterface.getX()+" | Y: "+roverInterface.getY()+" | Front to: "+getFrontTo(roverInterface.getFrontTo());
    	appenOutputText(saida);
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
	private Rover sendCommands(Rover rover, char[] commands) {
		String strCommandos = "";
		for(int i = 0;i<commands.length;i++){
			rover = runCommand(rover,commands[i]);
			strCommandos += commands[i];
		}
		appenOutputText("Comandos Enviados: "+strCommandos);
		return rover;
	}

	/**
	 * EXECUTA COMANDO
	 * @param rover
	 * @param command COMANDO A SER EXECUTADO
	 * @return
	 */
	private Rover runCommand(Rover rover, char command) {
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
    		appenOutputText("Square criado: "+width+" X "+height);
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
    public class Rover implements Serializable{
    	/**
		 * 
		 */
		private static final long serialVersionUID = 1283763773881936508L;
		public static final int POSITION_NORTH = 0;
    	public static final int POSITION_EAST = 1;
    	public static final int POSITION_SOUTH = 2;
    	public static final int POSITION_WEAST = 3;
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
    		appenOutputText("Rover criado: X: "+this.x+" Y: "+this.y+" "+MainActivity.this.getFrontTo(this.frontTo));
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
