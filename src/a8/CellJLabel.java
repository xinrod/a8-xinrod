package a8;

import java.awt.*;

import javax.swing.*;

public class CellJLabel extends JLabel {
    private static final Color[] color = {Color.WHITE, Color.BLACK};
  
    //Size of cells
    private static final int cellSize = 15;
    private static final Dimension citySize = new Dimension(cellSize, cellSize);

    //checks if the mouse is still pressed or not
    private boolean mouseHold = false;

    private int nbNeighbor;
   

    CellJLabel() {
            setOpaque(true);    //Show color
            setBackground(color[0]); 
            this.setPreferredSize(citySize);   //Set the dimension of the board
    }
    
    public void clear() {
    	  if(getBackground() == color[1]) {
              setBackground(color[0]);
      }
    }
    Color[] getColor() {
    	return color;
    }
}
