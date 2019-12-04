package a8;

import java.awt.Dimension;

import javax.swing.JFrame;

public class GameOfLife {

	public static void main(String[] args) {
		GOLModel model = new GOLModel();
		GOLView view = new GOLView();
		GOLController controller = new GOLController(view, model);
		
		view.setTitle("MVC Game of Life");
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension maximumSize = new Dimension(1280,960);
		view.setMinimumSize(maximumSize);
		view.pack();
		view.setVisible(true);
	}
}
