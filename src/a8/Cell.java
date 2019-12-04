package a8;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Cell {
	
	private Cell[] neighbor;
    private boolean alive;
    private int n;
	private boolean nextStatus;
	
   private int nalive;
	public Cell() {
		alive = false;
		neighbor = new Cell[8];
		n = 0;
		nalive = 0;
		nextStatus = false;
	}
	void clear() {
		alive = false;
		nextStatus = false;
	}
	boolean isAlive() {
		return alive;
	}
	void setAlive() {
		alive = true;
	}
	void setDead() {
		alive = false;
	}
	void addNeighbor(Cell c) {
		if (n > 7)
			return;
		
		neighbor[n] = c;
		n++;
	}
	Cell[] getNeighbor() {
		return neighbor;
	}
	void addnalive() {
		nalive++;
	}
	int getNalive() {
		return nalive;
	}
	void setNalive(int n) {
		nalive = n;
	}
	void setNextStatus (boolean b) {
		nextStatus = b;
	}
	boolean getNextStatus () {
		return nextStatus;
	}
}
