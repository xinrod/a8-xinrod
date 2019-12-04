package a8;
import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;
import javax.swing.*;
import java.awt.event.MouseListener;

public class GOLView extends JFrame {
    private CellJLabel[][] cellHouse;
	private JLabel j;
    private Timer timer;

    private int generationCount = 0;
    private JLabel generationLabel = new JLabel("Generation: 0");

    private JButton randomB = new JButton("Random Fill");
    private JButton startB = new JButton("Start");
    private JButton pauseB = new JButton("Pause");
    private JButton clearB = new JButton("Clear");
    private JButton advanceB = new JButton("Advance One Generation");
    private JButton resizeB = new JButton("Resize Field");
    private JButton thresholdB = new JButton("Set Low/High Threshold");
    private JButton torusB = new JButton("Toggle Torus Mode (Currently Off)");
    private JButton manualB = new JButton("How do I manually set/clear cells?");
    

    private static final int minSpeed = 10;
    private static final int maxSpeed = 1000;

    private JSlider generationS = new JSlider(0,getMaxspeed());

    private boolean runStatus = false;

    private JPanel panel;
    
    private int boardRow;
    private int boardCol;
    
    public GOLView(int w, int h) {
        super("Game Of Life");
        j = new JLabel();
    	setBoardRow(w);
    	setBoardCol(h);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cellHouse = new CellJLabel[getBoardRow()+2][getBoardCol()+2];
        for(int r = 0; r < getBoardRow()+2; r++) {
            for(int c = 0; c < getBoardCol()+2; c++) {
                cellHouse[r][c] = new CellJLabel();
            }
    }

        panel = new JPanel(new GridLayout(getBoardRow(), getBoardCol(), 1, 1));
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        for(int r = 1; r < getBoardRow() + 1; r++) {
                for(int c = 1; c < getBoardCol() + 1; c++) {
                        panel.add(cellHouse[r][c]);
                       
                }
        }

//         setG(new GridsCanvas(1100, 900, boardRow, boardCol));
//        getG().setMaximumSize(new Dimension(1100, 900));
//        getG().setMinimumSize(new Dimension(1100, 900));
        add(panel, BorderLayout.CENTER);
        JPanel panelBottom = new JPanel();
        JPanel eastPanel = new JPanel(new GridLayout(8,1));
        JPanel buttonPanel = new JPanel();
        eastPanel.add(randomB);
        eastPanel.add(advanceB);
        eastPanel.add(clearB);
        pauseB.setEnabled(false);
        panelBottom.add(pauseB);
        panelBottom.add(startB);
        eastPanel.add(resizeB);
        eastPanel.add(thresholdB);
        eastPanel.add(getTorusB());
        eastPanel.add(manualB);

        JPanel speedPanel = new JPanel();
        JLabel speedText = new JLabel("Set ms Per Generation:");
        generationS.setPaintTrack(true); 
        generationS.setPaintTicks(true); 
        generationS.setPaintLabels(true);
        generationS.setMajorTickSpacing(990);
        generationS.setMinimum(10);
        



        speedPanel.add(speedText);
        speedPanel.add(getGenerationS());

        eastPanel.add(buttonPanel);
        panelBottom.add(speedPanel);
        panelBottom.add(getGenerationLabel());

        add(panelBottom, BorderLayout.SOUTH);
        add(eastPanel, BorderLayout.EAST);
        setLocation(20, 20);
        pack();
        setVisible(true);
    }
    public GOLView() {
        super("Game Of Life");
        j = new JLabel();
    	setBoardRow(10);
    	setBoardCol(10);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cellHouse = new CellJLabel[getBoardRow()+2][getBoardCol()+2];
        for(int r = 0; r < getBoardRow()+2; r++) {
            for(int c = 0; c < getBoardCol()+2; c++) {
                cellHouse[r][c] = new CellJLabel();
            }
    }

        panel = new JPanel(new GridLayout(getBoardRow(), getBoardCol(), 1, 1));
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        for(int r = 1; r < getBoardRow() + 1; r++) {
                for(int c = 1; c < getBoardCol() + 1; c++) {
                        panel.add(cellHouse[r][c]);
                       
                }
        }

//         setG(new GridsCanvas(1100, 900, boardRow, boardCol));
//        getG().setMaximumSize(new Dimension(1100, 900));
//        getG().setMinimumSize(new Dimension(1100, 900));
        add(panel, BorderLayout.CENTER);
        JPanel panelBottom = new JPanel();
        JPanel eastPanel = new JPanel(new GridLayout(8,1));
        JPanel buttonPanel = new JPanel();
        eastPanel.add(manualB);

        eastPanel.add(randomB);
        eastPanel.add(advanceB);
        eastPanel.add(clearB);
        pauseB.setEnabled(false);
        panelBottom.add(pauseB);
        panelBottom.add(startB);
        eastPanel.add(resizeB);
        eastPanel.add(thresholdB);
        eastPanel.add(getTorusB());

        JPanel speedPanel = new JPanel();
        JLabel speedText = new JLabel("Set ms Per Generation:");
        generationS.setPaintTrack(true); 
        generationS.setPaintTicks(true); 
        generationS.setPaintLabels(true);
        generationS.setMajorTickSpacing(990);
        generationS.setMinimum(10);
        



        speedPanel.add(speedText);
        speedPanel.add(getGenerationS());

        eastPanel.add(buttonPanel);
        panelBottom.add(speedPanel);
        panelBottom.add(getGenerationLabel());

        add(panelBottom, BorderLayout.SOUTH);
        add(eastPanel, BorderLayout.EAST);
        setLocation(20, 20);
        pack();
        setVisible(true);
    }

    public void addMouseListener(MouseListener m) {
    	for (int r = 0; r < cellHouse.length; r++) {
    		for (int c = 0; c < cellHouse[r].length; c++) {
    			cellHouse[r][c].addMouseListener(m);
    		}
    	}
    }
    void addAdvanceListener(ActionListener e) {
    	advanceB.addActionListener(e);
    }
    
    void addResizeListener(ActionListener e) {
        resizeB.addActionListener(e);

    }
    public void addClearListener(ActionListener e) {
        clearB.addActionListener(e);
    }
    
    void addPauseListener(ActionListener e) {
        pauseB.addActionListener(e);
        timer = new Timer(getMaxspeed() - getGenerationS().getValue(), e);

    }
    void addStartListener(ActionListener e) {
        startB.addActionListener(e);
        timer = new Timer(getMaxspeed() - getGenerationS().getValue(), e);

    }
    
    void addRandomListener(ActionListener e) {
    	randomB.addActionListener(e);
    }
    
    void addThresholdListener(ActionListener e) {
    	thresholdB.addActionListener(e);
    }
    void addTimerListener(ActionListener e) {
    	timer.addActionListener(e);
    }
    void addTorusListener(ActionListener e) {
    	getTorusB().addActionListener(e);
    }
    void addManualListener(ActionListener e) {
    	manualB.addActionListener(e);
    }
	public Timer getTimer() {
		// TODO Auto-generated method stub
		return timer;
	}

	public void setRunStatus(boolean b) {
		// TODO Auto-generated method stub
		runStatus = b;
	}

	public JButton getPauseB() {
		// TODO Auto-generated method stub
		return pauseB;
	}

	public JButton getStartB() {
		// TODO Auto-generated method stub
		return startB;
	}

	public CellJLabel[][] getCellHouse() {
		// TODO Auto-generated method stub
		return cellHouse;
	}

	public int getGenerationCount() {
		return generationCount;
	}

	public void setGenerationCount(int generationCount) {
		this.generationCount = generationCount;
	}

	public JLabel getGenerationLabel() {
		return generationLabel;
	}

	public void setGenerationLabel(JLabel generationLabel) {
		this.generationLabel = generationLabel;
	}

	public static int getMaxspeed() {
		return maxSpeed;
	}

	public JSlider getGenerationS() {
		return generationS;
	}

	public void setGenerationS(JSlider generationS) {
		this.generationS = generationS;
	}


	public int getBoardRow() {
		return boardRow;
	}


	public void setBoardRow(int boardRow) {
		this.boardRow = boardRow;
	}


	public int getBoardCol() {
		return boardCol;
	}


	public void setBoardCol(int boardCol) {
		this.boardCol = boardCol;
		
	}


	public JButton getTorusB() {
		return torusB;
	}


	public void setTorusB(JButton torusB) {
		this.torusB = torusB;
	}
	
	
	

}