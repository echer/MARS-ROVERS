package com.devmix.mars.rovers;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	private int[][] square;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        buildSquare(5, 5);
    }
    
    private boolean buildSquare(int width, int height){
    	if(width > 0 && height > 0){
	    	square = new int[width][height];
	    	return true;
    	}else{
    		return false;
    	}
    }


    
}
