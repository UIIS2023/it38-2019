package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

public class DrawingFrame extends JFrame {
	private DrawingView view = new DrawingView();
	private DrawingController controller;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	protected JToggleButton point;
	protected JToggleButton rectangle;
	protected JToggleButton line;
	protected JToggleButton circle;
	protected JToggleButton donut;
	protected JToggleButton select;
	protected JToggleButton hexagon;
	protected JList<String> logListView;
	protected DefaultListModel<String> dlm = new DefaultListModel<String>();
	protected JButton modify;
	protected JButton remove;
	protected Color outerColor;
	protected Color innerColor;
	protected JButton outerColorChooser;
	protected JButton innerColorChooser;
	protected JButton undo;
	protected JButton redo;
	protected JButton bringToFront;
	protected JButton bringToBack;
	protected JButton next;
	protected JButton toFront;
	protected JButton toBack;
	protected ArrayList<String> logList = new ArrayList<String>();

	public DrawingFrame() {
		setTitle("Drawing App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.mouseClicked(e);
			}
		});
		
		setSize(800, 800);
		outerColor = Color.BLACK;
		innerColor = Color.WHITE;
		getContentPane().add(view, BorderLayout.CENTER);
		view.setLayout(new BorderLayout(0, 0));
		
		
		JPanel panelEast = new JPanel();
		view.add(panelEast, BorderLayout.EAST);
		
		JToggleButton tglbtnPoint = new JToggleButton("Point");
		tglbtnPoint.setHorizontalAlignment(SwingConstants.RIGHT);
		buttonGroup.add(tglbtnPoint);
				
		JToggleButton tglbtnLine = new JToggleButton("Line");
		buttonGroup.add(tglbtnLine);
						
		JToggleButton tglbtnRectangle = new JToggleButton("Rectangle");
		buttonGroup.add(tglbtnRectangle);
								
		JToggleButton tglbtnCircle = new JToggleButton("Circle");
		buttonGroup.add(tglbtnCircle);
										
		JToggleButton tglbtnDonut = new JToggleButton("Donut");
		buttonGroup.add(tglbtnDonut);
												
		JToggleButton tglbtnHexagon = new JToggleButton("Hexagon");
		buttonGroup.add(tglbtnHexagon);
												
		JToggleButton tglbtnSelect = new JToggleButton("Select");
		buttonGroup.add(tglbtnSelect);
														
		JButton btnModify = new JButton("Modify");
		buttonGroup.add(btnModify);
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.modify();
			}
		});
																
		JButton btnRemove = new JButton("Remove");
		buttonGroup.add(btnRemove);
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.remove();
			}
		});


		JFrame frame = new JFrame("Choose a color");
		JButton btnOuterColor = new JButton("Outer color");
		buttonGroup.add(btnOuterColor);
		
		
		
		point = tglbtnPoint;
		line = tglbtnLine;
		rectangle = tglbtnRectangle;
		circle = tglbtnCircle;
		donut = tglbtnDonut;
		select = tglbtnSelect;
		hexagon = tglbtnHexagon;
		modify = btnModify;
		remove = btnRemove;
		outerColorChooser = btnOuterColor;
		outerColorChooser.setBackground(outerColor);
		outerColorChooser.setForeground(Color.LIGHT_GRAY);
		
		
		remove.setEnabled(false);
		modify.setEnabled(false);
		
		JButton btnUndo = new JButton("Undo");
		buttonGroup.add(btnUndo);
		JButton btnRedo = new JButton("Redo");
		buttonGroup.add(btnRedo);
		
		undo = btnUndo;
		redo = btnRedo;
		undo.setEnabled(false);
		redo.setEnabled(false);
		
		JButton btnBringToFront = new JButton("Bring to front");
		buttonGroup.add(btnBringToFront);
		JButton btnBringToBack = new JButton("Bring to back");
		buttonGroup.add(btnBringToBack);
		
		bringToFront = btnBringToFront;
		bringToBack = btnBringToBack;
		bringToFront.setEnabled(false);
		bringToBack.setEnabled(false);
		
		JButton btnSaveDrawing = new JButton("Save drawing");

		
		JButton btnLoadDrawing = new JButton("Load drawing");

		JButton btnLoadLog = new JButton("Load Log");

		
		JButton btnNext = new JButton("Next");
		
		next = btnNext;
		next.setEnabled(false);
		
		JButton btnToFront = new JButton("To front");
		toFront = btnToFront;
		JButton btnToBack = new JButton("To back");
		toBack = btnToBack;
		toFront.setEnabled(false);
		toBack.setEnabled(false);
		
		JButton btnInnerColor = new JButton("Inner color");
		innerColorChooser = btnInnerColor;
		innerColorChooser.setBackground(innerColor);
		innerColorChooser.setForeground(Color.LIGHT_GRAY);
		
		
		GroupLayout gl_panelSouth = new GroupLayout(panelEast);
		gl_panelSouth.setHorizontalGroup(
			gl_panelSouth.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelSouth.createSequentialGroup()
					.addContainerGap(986, Short.MAX_VALUE)
					.addGroup(gl_panelSouth.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelSouth.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_panelSouth.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelSouth.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panelSouth.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panelSouth.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_panelSouth.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panelSouth.createSequentialGroup()
													.addGap(65)
													.addGroup(gl_panelSouth.createParallelGroup(Alignment.TRAILING)
														.addGroup(gl_panelSouth.createParallelGroup(Alignment.LEADING)
															.addComponent(tglbtnDonut, Alignment.TRAILING)
															.addGroup(gl_panelSouth.createParallelGroup(Alignment.LEADING)
																.addGroup(gl_panelSouth.createSequentialGroup()
																	.addGap(94)
																	.addGroup(gl_panelSouth.createParallelGroup(Alignment.TRAILING)
																		.addGroup(gl_panelSouth.createParallelGroup(Alignment.LEADING)
																			.addGroup(gl_panelSouth.createParallelGroup(Alignment.LEADING)
																				.addComponent(btnRemove)
																				.addComponent(tglbtnLine, Alignment.TRAILING))
																			.addComponent(tglbtnPoint, Alignment.TRAILING))
																		.addComponent(tglbtnHexagon)))
																.addComponent(tglbtnRectangle, Alignment.TRAILING)))
														.addComponent(tglbtnCircle)))
												.addComponent(btnModify, Alignment.TRAILING))
											.addComponent(tglbtnSelect, Alignment.TRAILING))
										.addGroup(Alignment.TRAILING, gl_panelSouth.createSequentialGroup()
											.addComponent(btnInnerColor)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnOuterColor)))
									.addComponent(btnUndo, Alignment.TRAILING))
								.addComponent(btnRedo, Alignment.TRAILING))
							.addGroup(gl_panelSouth.createSequentialGroup()
								.addComponent(btnToFront)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnBringToFront))
							.addGroup(gl_panelSouth.createSequentialGroup()
								.addComponent(btnToBack)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnBringToBack))
							.addComponent(btnSaveDrawing, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_panelSouth.createSequentialGroup()
								.addComponent(btnLoadLog)
								.addGap(11)
								.addComponent(btnNext)))
						.addComponent(btnLoadDrawing, Alignment.TRAILING))
					.addContainerGap())
		);
		gl_panelSouth.setVerticalGroup(
			gl_panelSouth.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSouth.createSequentialGroup()
					.addContainerGap()
					.addComponent(tglbtnPoint)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tglbtnLine)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tglbtnRectangle)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tglbtnCircle)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tglbtnDonut)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tglbtnHexagon)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tglbtnSelect)
					.addGap(11)
					.addComponent(btnModify)
					.addGap(13)
					.addComponent(btnRemove)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelSouth.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnOuterColor)
						.addComponent(btnInnerColor))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnUndo)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnRedo)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelSouth.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnBringToFront)
						.addComponent(btnToFront))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelSouth.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnBringToBack)
						.addComponent(btnToBack))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnSaveDrawing)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelSouth.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnLoadLog)
						.addComponent(btnNext))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnLoadDrawing)
					.addContainerGap(70, Short.MAX_VALUE))
		);
		panelEast.setLayout(gl_panelSouth);
		
		JScrollPane scrollPane = new JScrollPane();
		view.add(scrollPane, BorderLayout.SOUTH);
		
		JList<String> jLogList = new JList<String>();
		jLogList.setVisibleRowCount(10);
		scrollPane.add(jLogList);
		jLogList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(jLogList);
		jLogList.setModel(dlm);
		logListView = jLogList;
		
		JButton btnSaveLog = new JButton("Save Log");
		scrollPane.setRowHeaderView(btnSaveLog);
		
		btnUndo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.undo();
				
			}
		});
		
		btnRedo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.redo();
			}
		});
		
		btnBringToFront.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.bringToFront();
			}
		});
		
		btnBringToBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.bringToBack();
			}
		});
		
		btnLoadDrawing.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.loadDrawing();
				
			}
		});
		
		btnSaveDrawing.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveDrawing();
			}
		});
		
		btnLoadLog.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.loadLog();
			}
		});
		
		btnSaveLog.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveLog();
			}
		});
		
		btnNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.next();				
			}
		});
		
		btnToFront.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.toFront();
				
			}
		});
		
		btnToBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.toBack();
			}
		});
		
		btnOuterColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color temp = new Color(outerColor.getRed(), outerColor.getGreen(),outerColor.getBlue());
				outerColor = JColorChooser.showDialog(frame, "Chose Color", Color.BLACK);
				if(outerColor == null) {
					outerColor = temp;
				}
				btnOuterColor.setBackground(outerColor);
			}
		});
		
		btnInnerColor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Color temp = new Color(innerColor.getRed(), innerColor.getGreen(),innerColor.getBlue());
				innerColor = JColorChooser.showDialog(frame, "Chose Color", Color.BLACK);
				if(innerColor == null) {
					innerColor = temp;
				}
				btnInnerColor.setBackground(innerColor);
			}
		});
	}

	public DrawingView getView() {
		return view;
	}
	
	
	
	public ArrayList<String> getLogList() {
		return logList;
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
	}
	
	public void log(String s) {
		dlm.addElement(s);
		logList.add(s);
	}
}