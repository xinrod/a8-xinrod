package a8;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GOLController {
	private GOLView view;
	private GOLModel model;
	private boolean torus;
	private boolean mouseHeld;
	
	GOLController(GOLView v, GOLModel m) {
		view = v;
		model = m;
		torus = false;
		model.setCellDimension(view.getBoardRow()+2, view.getBoardCol()+2);
		view.addMouseListener(new MouseActionListener());
		view.addClearListener(new ClearActionListener());
		view.addPauseListener(new PauseActionListener());
		view.addStartListener(new StartActionListener());
		view.addRandomListener(new RandomActionListener());
		view.addAdvanceListener(new AdvanceActionListener());
		view.addResizeListener(new ResizeActionListener());
		view.addThresholdListener(new ThresholdActionListener());
		view.addTimerListener(new TimerActionListener());
		view.addTorusListener(new TorusActionListener());
		view.addManualListener(new ManualActionListener());
	}
	void torusM() {
		if (torus) {
			for (int r = 1; r < view.getBoardRow(); r++) {
				model.getCells()[r][0] = model.getCells()[r][view.getBoardCol()];
				model.getCells()[r][view.getBoardRow()+1] = model.getCells()[r][1];
			}
			for (int c = 1; c < view.getBoardCol(); c++) {
				model.getCells()[0][c] = model.getCells()[view.getBoardCol()][c];
				model.getCells()[view.getBoardRow()+1][c] = model.getCells()[1][c];
			}
		}
			else {
				for (int r = 1; r < view.getBoardRow(); r++) {
					model.getCells()[r][0].clear();
					model.getCells()[r][view.getBoardRow()+1].clear();
				}
				for (int c = 1; c < view.getBoardCol(); c++) {
					model.getCells()[0][c].clear();
					model.getCells()[view.getBoardRow()+1][c].clear();
				}
			}
		model.updateNeighbor();
		
	
	}
	class ManualActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JOptionPane.showMessageDialog(new JFrame(), "Click your mouse on individual cells to make them alive/dead"
					+ " (make sure your mouse is not moving!). You can also click and drag your mouse to quickly make cells "
					+ "alive/dead.");
		}
		
	}
	class TorusActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (torus) {
				torus = false;
				view.getTorusB().setText("Toggle Torus Mode (Currently Off)");
			}
			else {
				torus = true;
				view.getTorusB().setText("Toggle Torus Mode (Currently On)");
			}
			torusM();
		}
		
	}
	class ThresholdActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int low = -1;
			int high = 9;
			String l = JOptionPane.showInputDialog("Enter the new desired low survive threshold. Range: 0-8");
			String h = JOptionPane.showInputDialog("Enter the new desired high survive threshold. Range: 0-8");
			if (l == null || h == null) {
				JOptionPane.showMessageDialog(new JFrame(), "You entered nothing, resetting to default thresholds.");
				model.defaultThresholds();
				return;
			}
			try {
				low = Integer.parseInt(l);
				high = Integer.parseInt(h);
				
			}
			catch (NumberFormatException n) {
				JOptionPane.showMessageDialog(new JFrame(), "You did not enter an Integer between 0 - 8, resetting to default thresholds.");
				model.defaultThresholds();
				return;
			}
			finally {
				if (low < 0 || high > 8) {
					JOptionPane.showMessageDialog(new JFrame(), "You did not enter an Integer between 0 - 8, resetting to default thresholds.");
					model.defaultThresholds();				
					return;
				}
				else {
				model.setHigh(high);
				model.setLow(low);
				}
			}
			setBirthThreshold();
		}
		private void setBirthThreshold() {
			int low = -1;
			int high = 9;
			String l = JOptionPane.showInputDialog("Enter the new desired low birth threshold. Range: 0-8");
			String h = JOptionPane.showInputDialog("Enter the new desired high birth threshold. Range: 0-8");
			if (l == null || h == null) {
				JOptionPane.showMessageDialog(new JFrame(), "You entered nothing, resetting to default thresholds.");
				model.defaultThresholds();
				return;
			}
			try {
				low = Integer.parseInt(l);
				high = Integer.parseInt(h);
				
			}
			catch (NumberFormatException n) {
				JOptionPane.showMessageDialog(new JFrame(), "You did not enter an Integer between 0 - 8, resetting to default thresholds.");
				model.defaultThresholds();
				return;
			}
			finally {
				if (low < 0 || high > 8) {
					JOptionPane.showMessageDialog(new JFrame(), "You did not enter an Integer between 0 - 8, resetting to default thresholds.");
					model.defaultThresholds();

					return;
				}
				else {
				model.setHighB(high);
				model.setLowB(low);
				}
			}
		}
		
	}
	class ClearActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			view.getTimer().stop();   
			view.setRunStatus(false);  
			view.getPauseB().setEnabled(false);   
			view.getStartB().setEnabled(true);   
			for(int r = 0; r < view.getCellHouse().length; r++) {
				for(int c = 0; c < view.getCellHouse()[r].length; c++) {
					view.getCellHouse()[r][c].clear();
					model.getCells()[r][c].clear();
				}
			}
			view.setGenerationCount(0);
			view.getGenerationLabel().setText("Generation: 0");
			return;
		}
	}
	class PauseActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			view.getTimer().stop();  
			view.setRunStatus(false); 
			view.getPauseB().setEnabled(false);   
			view.getStartB().setEnabled(true);    
			return;
		}
	}

	class StartActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			view.getPauseB().setEnabled(true);    //Enable the pause button
			view.getStartB().setEnabled(false);   //Disable the start button
			view.setRunStatus(true);   //Set game as running
			view.getTimer().setDelay(view.getGenerationS().getValue());  //Adjust the speed
			view.getTimer().start();
			return;
		}
	}
	
	class TimerActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			updateGeneration();
		}
		
	}
	class AdvanceActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			//Update generation count and display
		updateGeneration();
		}
	}
	class ResizeActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int width = 10; int height = 10;
	
			String w = JOptionPane.showInputDialog("Enter your desired field width, 10-500");
			String h = JOptionPane.showInputDialog("Enter your desired field width, 10-500");
			if (w == null || h == null || w.trim() == "" || h.trim() == "") {
				JOptionPane.showMessageDialog(new JFrame(), "You did not enter anything");
				return;
			}
			try {
				width = Integer.parseInt(w);
				height = Integer.parseInt(h);
				
			}
			catch (NumberFormatException n) {
				JOptionPane.showMessageDialog(new JFrame(), "You did not enter an Integer between 10 - 500");

				return;
			}
			finally {
				if (width > 500 || width < 10 || height > 500 || height < 10) {
					JOptionPane.showMessageDialog(new JFrame(), "You did not enter an Integer between 10 - 500");
					return;
				}
				GOLView newview = new GOLView(width, height);
				GOLModel newmodel = new GOLModel();
				GOLController newcontroller = new GOLController(newview, newmodel);
				
				newview.setTitle("MVC Game of Life");
				newview.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				Dimension maximumSize = new Dimension(1280,960);
				newview.setMinimumSize(maximumSize);
				newview.pack();
				newview.setVisible(true);
				Menu mnu = new Menu(model, view, GOLController.this);
				mnu.closeAllInMenu();
					
			}
//			GOLView view = new GOLView();
//			GOLModel model = new GOLModel();
//			GOLController controller = new GOLController(view, model);
//			
//			view.setTitle("MVC Game of Life");
//			view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			
//			Dimension maximumSize = new Dimension(1280,960);
//			view.setMinimumSize(maximumSize);
//			view.pack();
//			view.setVisible(true);
//			Menu mnu = new Menu(model, view, GOLController.this);
//			mnu.closeAllInMenu();
			
		}

	}
	class RandomActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			for (int r = 1; r < model.getCells().length-1; r++) {
				for (int c = 1; c < model.getCells()[r].length-1; c++) {
					model.getCells()[r][c].setDead();
					view.getCellHouse()[r][c].setBackground(Color.WHITE);

				}
			}

			for (int r = 1; r < model.getCells().length-1; r++) {
				for (int c = 1; c < model.getCells()[r].length-1; c++) {
					double rand = Math.random();
					if (rand > .5 ) {
						model.getCells()[r][c].setAlive();
						view.getCellHouse()[r][c].setBackground(Color.BLACK);
					}
				}
			}

		}

	}
	class MouseActionListener implements MouseListener {        
		public void mouseClicked(MouseEvent arg0) {
			CellJLabel temp = (CellJLabel) arg0.getSource();
			int row = -1;
			int column = -1;

			for (int r = 1; r < model.getCells().length-1; r++) {
				for (int c = 1; c < model.getCells()[r].length-1; c++) {
					if (view.getCellHouse()[r][c] == temp) {
						row = r;
						column = c;
					}
				}
			}	           
			if (temp.getBackground() == temp.getColor()[0]) {
				temp.setBackground(temp.getColor()[1]);
				model.setCell(row, column, true);
			}
			else {
				temp.setBackground(temp.getColor()[0]);
				model.setCell(row, column, false);

			}

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			mouseHeld = true;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			mouseHeld = false;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			if(mouseHeld == true) {
				CellJLabel temp = (CellJLabel) e.getComponent();
				int row = -1;
				int column = -1;

				for (int r = 1; r < model.getCells().length-1; r++) {
					for (int c = 1; c < model.getCells()[r].length-1; c++) {
						if (view.getCellHouse()[r][c] == temp) {
							row = r;
							column = c;
						}
					}
				}	           
				if (temp.getBackground() == temp.getColor()[0]) {
					temp.setBackground(temp.getColor()[1]);
					model.setCell(row, column, true);
				}
				else {
					temp.setBackground(temp.getColor()[0]);
					model.setCell(row, column, false);

				}

			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}
		public void updateGeneration() {
			view.setGenerationCount(view.getGenerationCount() + 1);
			view.getGenerationLabel().setText("Generation: " + view.getGenerationCount());
			torusM();

			for (int r = 1; r < model.getCells().length -1; r++) {
				for (int c = 1; c < model.getCells()[r].length -1; c++) {
					model.checkAlive(model.getCells()[r][c]);
				}
			}
			for (int r = 1; r < model.getCells().length -1; r++) {
				for (int c = 1; c < model.getCells()[r].length -1; c++) {
					if (model.getCells()[r][c].getNextStatus()) {
					model.getCells()[r][c].setAlive();
					}
					else {
						model.getCells()[r][c].setDead();

					}
				}
			}
			torusM();
			for (int r = 1; r < view.getCellHouse().length - 1; r++) {
				for (int c = 1; c < view.getCellHouse()[r].length - 1; c++) {
					if (model.getCells()[r][c].isAlive()) {
						view.getCellHouse()[r][c].setBackground(Color.BLACK);
					}
					else {
						view.getCellHouse()[r][c].setBackground(Color.WHITE);
					}
				}
			}
		}
}

//timer.setDelay(getMaxspeed() - getGenerationS().getValue());
//
//if(!runStatus) return;
//
//setGenerationCount(getGenerationCount() + 1);
//getGenerationLabel().setText("Generation: " + getGenerationCount());
//

//
//for(int r = 0; r < cellHouse.length; r++) {
//        for(int c = 0; c < cellHouse[r].length; c++) {
////                cellHouse[r][c].updateGeneration();
//        }
//}