package a8;

public class GOLModel {
	private Cell[][] cells;
	private int generation;
	private boolean alive;
	private int lowThreshold;
	private int highThreshold;
	private int birthLowThreshold;
	private int birthHighThreshold;
	GOLModel() {
		generation = 0;
		lowThreshold = 2;
		highThreshold = 3;
		birthLowThreshold = 3;
		birthHighThreshold = 3;
	}
	
	void setCellDimension(int width, int height) {
		setCells(new Cell[width][height]);
		for (int r = 0; r < getCells().length; r++) {
			for (int c = 0; c < getCells()[r].length; c++) {
				getCells()[r][c] = new Cell();
 
			}
		}
		for (int r = 1; r < getCells().length-1; r++) {
			for (int c = 1; c < getCells()[r].length-1; c++) {
				 cells[r][c].addNeighbor(getCells()[r-1][c]);    
				 cells[r][c].addNeighbor(getCells()[r+1][c]);  
				 cells[r][c].addNeighbor(getCells()[r][c-1]);    
                 cells[r][c].addNeighbor(getCells()[r][c+1]);    
                 cells[r][c].addNeighbor(getCells()[r-1][c-1]);  
                 cells[r][c].addNeighbor(getCells()[r-1][c+1]); 
                 cells[r][c].addNeighbor(getCells()[r+1][c-1]); 
                 cells[r][c].addNeighbor(getCells()[r+1][c+1]);  
			}
		}
	}
	void updateNeighbor() {
		for (int r = 1; r < getCells().length-1; r++) {
			for (int c = 1; c < getCells()[r].length-1; c++) {
				cells[r][c].getNeighbor()[0] = getCells()[r-1][c];
				cells[r][c].getNeighbor()[1] = (getCells()[r+1][c]);  
				cells[r][c].getNeighbor()[2] = (getCells()[r][c-1]);    
				cells[r][c].getNeighbor()[3] = (getCells()[r][c+1]);    
				cells[r][c].getNeighbor()[4] = (getCells()[r-1][c-1]);  
				cells[r][c].getNeighbor()[5] = (getCells()[r-1][c+1]); 
				cells[r][c].getNeighbor()[6] = (getCells()[r+1][c-1]); 
                cells[r][c].getNeighbor()[7] = (getCells()[r+1][c+1]);  
			}
		}
	}
	void clearCell(int w, int h) {
		getCells()[w][h].setDead();
	}
	
	void setCell(int w, int h, boolean a) {
		if (a) {
		getCells()[w][h].setAlive();
		}
		else {
			getCells()[w][h].setDead();

		}
	}
	void checkAlive(Cell cloneCell) {
        cloneCell.setNalive(0);
        for(int i = 0; i < 8; i++) {
        	if (cloneCell.getNeighbor()[i].isAlive()) {
        		cloneCell.addnalive();
         	}
        }
        if(cloneCell.isAlive()) {
        	if(cloneCell.getNalive() < lowThreshold)
            	cloneCell.setNextStatus(false);
        	else if(cloneCell.getNalive() > highThreshold)
            	cloneCell.setNextStatus(false);
        	else {
        		cloneCell.setNextStatus(true);
        	}
        }
        else {
        	 if(cloneCell.getNalive() >= birthLowThreshold && cloneCell.getNalive() <= birthHighThreshold) {
             	cloneCell.setNextStatus(true);
             }
        }
               
	}

//	Cell checkDead(Cell cell) {
//		Cell c = cell;
//        c.setNalive(0);
//        for(int i = 0; i < 8; i++) {
//        	if (c.getNeighbor()[i].isAlive()) {
//        		c.addnalive();
//         	}
//        }
////        if(!c.isAlive()) {
////        if(c.getNalive() == 3) {
////        	c.setAlive();
////        }
//        
////        if(c.isAlive()) {
//                if(c.getNalive() < 2)
//                    c.setDead();
//                if(c.getNalive() > 3)
//                    c.setDead();
////        }
////        else {
//               return c;
////        }	
//	}

	public Cell[][] getCells() {
		return cells;
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}
	
	void setLow(int n) {
		lowThreshold = n;
	}
	
	void setHigh(int n) {
		highThreshold = n;
		
	}

	public void setHighB(int high) {
		// TODO Auto-generated method stub
		birthHighThreshold = high;
	}
	public void setLowB(int high) {
		// TODO Auto-generated method stub
		birthLowThreshold = high;
	}
	void defaultThresholds() {
		lowThreshold = 2;
		highThreshold = 3;
		birthLowThreshold = 3;
		birthHighThreshold = 3;
	}
}
