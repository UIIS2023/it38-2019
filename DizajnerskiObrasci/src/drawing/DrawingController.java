package drawing;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import drawing.command.AddShapeCmd;
import drawing.command.Command;
import drawing.command.RemoveShapeCmd;
import drawing.command.SelectShapeCmd;
import drawing.command.ShapeBringToBackCmd;
import drawing.command.ShapeBringToFrontCmd;
import drawing.command.ShapeToBackCmd;
import drawing.command.ShapeToFrontCmd;
import drawing.command.UpdateCircleCmd;
import drawing.command.UpdateDonutCmd;
import drawing.command.UpdateHexagonCmd;
import drawing.command.UpdateLineCmd;
import drawing.command.UpdatePointCmd;
import drawing.command.UpdateRectangleCmd;
import drawing.dialogs.DlgCircle;
import drawing.dialogs.DlgDonut;
import drawing.dialogs.DlgHexagon;
import drawing.dialogs.DlgLine;
import drawing.dialogs.DlgPoint;
import drawing.dialogs.DlgRectangle;
import drawing.save.Save;
import drawing.save.SaveLogDrawing;
import drawing.save.SaveManager;
import geometry.Circle;
import geometry.Donut;
import geometry.HexagonAdapter;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;

public class DrawingController implements PropertyChangeListener {
	private DrawingModel model;
	private DrawingFrame frame;
	private boolean isFirstClick = true;
	private int x, y;
	private boolean isShapeSelected= false;
	private Command command;
	private Stack<Command> undoStack;
	private Stack<Command> redoStack;
	private Save save;
	private File logFile;
	private BufferedReader readLog;
	private FileReader logReader;
	

	public DrawingController(DrawingModel model, DrawingFrame frame) {
		save = new SaveLogDrawing();
		save = new SaveManager(save);
		this.model = model;
		this.frame = frame;
		this.undoStack = new Stack<Command>();
		this.redoStack = new Stack<Command>();

	}
	

	public void mouseClicked(MouseEvent e) {
		if (frame.point.isSelected()) {
			Point p = new Point(e.getX(), e.getY(), false, frame.outerColor);
			command = new AddShapeCmd(p, model);
			command.execute();
			undoStack.push(command);
			frame.redo.setEnabled(false);
			redoStack.clear();
			frame.log("Draw_"+p.toString());
		} else if (frame.line.isSelected()) {
			if (isFirstClick) {
				x = e.getX();
				y = e.getY();
				isFirstClick = false;
			} else {
				Line l = new Line(new Point(x, y), new Point(e.getX(), e.getY()), false, frame.outerColor);
				command = new AddShapeCmd(l, model);
				command.execute();
				undoStack.push(command);
				frame.log("Draw_"+l.toString());
				frame.redo.setEnabled(false);
				redoStack.clear();
				isFirstClick = true;
			}
		} else if (frame.rectangle.isSelected()) {
			DlgRectangle dlg = new DlgRectangle(e.getX(), e.getY());
			dlg.setOuter(frame.outerColor);
			dlg.setInner(frame.innerColor);
			dlg.setVisible(true);
			if (dlg.isCancel()) {
				return;
			} else {
				try {
					int height = Integer.parseInt(dlg.getTxtHeight().getText());
					int width = Integer.parseInt(dlg.getTxtWidth().getText());
					Point p = new Point(Integer.parseInt(dlg.getTxtTopLeftX().getText()),
							Integer.parseInt(dlg.getTxtTopLeftY().getText()));
					Rectangle r = new Rectangle(p, width, height, false, dlg.getOuter(), dlg.getInner());
					command = new AddShapeCmd(r, model);
					command.execute();
					undoStack.push(command);
					frame.redo.setEnabled(false);
					redoStack.clear();
					frame.log("Draw_"+r.toString());
				} catch (NumberFormatException ex) {
			         JOptionPane.showConfirmDialog(null, "Only integers can be accepted in the text fields", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
					return;
				}

			}
		} else if (frame.circle.isSelected()) {
			DlgHexagon dlg = new DlgHexagon(e.getX(), e.getY());
			dlg.setOuter(frame.outerColor);
			dlg.setInner(frame.innerColor);
			dlg.setVisible(true);
			if (dlg.isCancel())
				return;
			else {
				try {
					int radius = Integer.parseInt(dlg.getTxtRadius().getText());
					Point center = new Point(Integer.parseInt(dlg.getTxtX().getText()),
							Integer.parseInt(dlg.getTxtY().getText()));
					Circle c = new Circle(center, radius, false, dlg.getOuter(), dlg.getInner());
					command = new AddShapeCmd(c, model);
					command.execute();
					undoStack.push(command);
					frame.redo.setEnabled(false);
					redoStack.clear();
					frame.log("Draw_"+c.toString());
				} catch (NumberFormatException ex) {
			         JOptionPane.showConfirmDialog(null, "Only integers can be accepted in the text fields", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		} else if (frame.donut.isSelected()) {
			DlgDonut dlg = new DlgDonut(e.getX(), e.getY());
			dlg.setOuter(frame.outerColor);
			dlg.setInner(frame.innerColor);
			dlg.setVisible(true);
			if (dlg.isCancel())
				return;
			else {
					try {
						int innerRadius = Integer.parseInt(dlg.getTxtInnerRadius().getText());
						int outerRadius = Integer.parseInt(dlg.getTxtOuterRadius().getText());
						Point center = new Point(Integer.parseInt(dlg.getTxtX().getText()),
								Integer.parseInt(dlg.getTxtY().getText()));
						Donut d = new Donut(center, outerRadius, innerRadius, false, dlg.getOuter(), dlg.getInner());
						command = new AddShapeCmd(d, model);
						command.execute();
						undoStack.push(command);
						frame.redo.setEnabled(false);
						redoStack.clear();
						frame.log("Draw_"+d.toString());
					} catch (NumberFormatException ex) {
				         JOptionPane.showConfirmDialog(null, "Only integers can be accepted in the text fields", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				         return;
					}

			}
		} else if (frame.hexagon.isSelected()) {
			DlgHexagon dlg = new DlgHexagon(e.getX(), e.getY());
			dlg.setOuter(frame.outerColor);
			dlg.setInner(frame.innerColor);
			dlg.setVisible(true);
			if(dlg.isCancel())
				return;
			else {
				try {
					int radius = Integer.parseInt(dlg.getTxtRadius().getText());
					int x = Integer.parseInt(dlg.getTxtX().getText());
					int y = Integer.parseInt(dlg.getTxtY().getText());
					Shape hexagonAdapter = new HexagonAdapter(x,y,radius, false, dlg.getOuter(),dlg.getInner());
					command = new AddShapeCmd(hexagonAdapter, model);
					command.execute();
					undoStack.push(command);
					frame.redo.setEnabled(false);
					redoStack.clear();
					frame.log("Draw_"+hexagonAdapter.toString());
				} catch (NumberFormatException ex) {
			        JOptionPane.showConfirmDialog(null, "Only integers can be accepted in the text fields", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			
		} else if (frame.select.isSelected()) {
			int counter = 0;
			ListIterator<Shape> iterator = model.getShapes().listIterator(model.getShapes().size());
			while(iterator.hasPrevious()) {
				Shape shape = iterator.previous();
				shape.addPropertyChangeListener(this);
				if(shape.contains(e.getX(), e.getY())) {
					if (shape.isSelected()) {
						command = new SelectShapeCmd(shape, model, false);
						command.execute();
						undoStack.push(command);
						frame.log("Deselect_"+shape.toString());
						frame.redo.setEnabled(false);
						redoStack.clear();
						break;
					} else {
						command = new SelectShapeCmd(shape, model, true);
						command.execute();
						undoStack.push(command);
						frame.redo.setEnabled(false);
						redoStack.clear();
						frame.log("Select_"+shape.toString());
						break;
					}
				}
			}
			
		}
		frame.repaint();
		if(!undoStack.isEmpty())
			frame.undo.setEnabled(true);
	}

	public void modify() {
		for (Shape s : model.getShapes()) {
			if (s.isSelected()) {
				if (s instanceof Point) {
					try {
						Point oldPoint = new Point(((Point) s).getX(), ((Point) s).getY(), false, s.getColor());
						DlgPoint dlg = new DlgPoint(((Point) s).getX(), ((Point) s).getY());
						dlg.setColor(s.getColor());
						dlg.setVisible(true);
						Point newPoint = new Point(Integer.parseInt(dlg.getTxtX().getText()),Integer.parseInt(dlg.getTxtY().getText()), false, dlg.getColor());
						command = new UpdatePointCmd((Point)s, newPoint);
						command.execute();
						undoStack.push(command);
						frame.redo.setEnabled(false);
						redoStack.clear();
						frame.log("Modify_"+oldPoint.toString()+"_New_"+newPoint.toString());
					} catch (NumberFormatException e) {
				        JOptionPane.showConfirmDialog(null, "Only integers can be accepted in the text fields", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				if (s instanceof Line) {
					try {
						Line oldLine = new Line(((Line) s).getStartPoint(), ((Line) s).getEndPoint(), false, s.getColor());
						DlgLine dlg = new DlgLine(((Integer) ((Line) s).getStartPoint().getX()),
								((Integer) ((Line) s).getStartPoint().getY()), ((Integer) ((Line) s).getEndPoint().getX()),
								((Integer) ((Line) s).getEndPoint().getY()));
						dlg.setColor(s.getColor());
						dlg.setVisible(true);
						Point start = new Point(Integer.parseInt(dlg.getTxtStartX().getText()),
								Integer.parseInt(dlg.getTxtStartY().getText()));
						Point end = new Point(Integer.parseInt(dlg.getTxtEndX().getText()),
								Integer.parseInt(dlg.getTxtEndY().getText()));
						
						Line newLine = new Line(start, end, false, dlg.getColor());
						
						command = new UpdateLineCmd((Line) s, newLine);
						command.execute();
						undoStack.push(command);
						frame.redo.setEnabled(false);
						redoStack.clear();
						frame.log("Modify_"+oldLine.toString()+"_New_"+newLine.toString());
					} catch (NumberFormatException e) {
						JOptionPane.showConfirmDialog(null, "Only integers can be accepted in the text fields", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				if (s instanceof Rectangle) {
					try {
						Rectangle oldRectangle = new Rectangle(((Rectangle) s).getUpperLeftPoint(),
								((Rectangle) s).getWidth(), ((Rectangle) s).getHeight(), false, s.getColor(), ((Rectangle) s).getInnerColor());
						x = ((Rectangle) s).getUpperLeftPoint().getX();
						y = ((Rectangle) s).getUpperLeftPoint().getY();
						DlgRectangle dlg = new DlgRectangle((Integer) x, (Integer) y);
						Integer height = ((Rectangle) s).getHeight();
						Integer width = ((Rectangle) s).getWidth();
						dlg.getTxtTopLeftX().setText(((Integer) x).toString());
						dlg.getTxtTopLeftY().setText(((Integer) y).toString());
						dlg.getTxtHeight().setText(height.toString());
						dlg.getTxtWidth().setText(width.toString());
						dlg.setInner(((Rectangle) s).getInnerColor());
						dlg.setOuter(s.getColor());
						dlg.setVisible(true);
						Point p = new Point(Integer.parseInt(dlg.getTxtTopLeftX().getText()),
								Integer.parseInt(dlg.getTxtTopLeftY().getText()));
						Rectangle newRectangle = new Rectangle(p, Integer.parseInt(dlg.getTxtWidth().getText()),
								Integer.parseInt(dlg.getTxtHeight().getText()), false, dlg.getOuter(), dlg.getInner());
						command = new UpdateRectangleCmd((Rectangle) s, newRectangle);
						command.execute();
						undoStack.push(command);
						frame.redo.setEnabled(false);
						redoStack.clear();
						frame.log("Modify_"+oldRectangle.toString()+"_New_"+newRectangle.toString());
					} catch (NumberFormatException e) {
						JOptionPane.showConfirmDialog(null, "Only integers can be accepted in the text fields", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				if (s instanceof Circle && !(s instanceof Donut)) {
					try {
						Circle oldCircle = new Circle(((Circle) s).getCenter(), ((Circle) s).getRadius(),
								false, s.getColor(), ((Circle) s).getInnerColor());
						DlgCircle dlg = new DlgCircle(((Circle) s).getCenter().getX(), ((Circle) s).getCenter().getY());
						dlg.getTxtRadius().setText(((Integer) ((Circle) s).getRadius()).toString());
						dlg.setInner(((Circle) s).getInnerColor());
						dlg.setOuter(((Circle) s).getColor());
						dlg.setVisible(true);
						Point p = new Point(Integer.parseInt(dlg.getTxtX().getText()),
								Integer.parseInt(dlg.getTxtY().getText()));
						Circle newCircle = new Circle(p, Integer.parseInt(dlg.getTxtRadius().getText()), false, dlg.getOuter(), dlg.getInner());
						command = new UpdateCircleCmd((Circle) s, newCircle);
						command.execute();
						undoStack.push(command);
						frame.redo.setEnabled(false);
						redoStack.clear();
						frame.log("Modify_"+oldCircle.toString()+"_New_"+newCircle.toString());
					} catch (NumberFormatException e) {
						JOptionPane.showConfirmDialog(null, "Only integers can be accepted in the text fields", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				if (s instanceof Donut) {
					try {
						Donut oldDonut = new Donut(((Donut ) s).getCenter(), ((Donut )s ).getRadius(), ((Donut)s).getInnerRadius(),
								false, s.getColor(), ((Donut)s ).getInnerColor());
						DlgDonut dlg = new DlgDonut(((Donut) s).getCenter().getX(), ((Donut) s).getCenter().getY());
						dlg.getTxtOuterRadius().setText(((Integer) ((Donut) s).getRadius()).toString());
						dlg.getTxtInnerRadius().setText(((Integer) ((Donut) s).getInnerRadius()).toString());
						dlg.setOuter(s.getColor());
						dlg.setInner(((Donut) s).getInnerColor());
						dlg.setVisible(true);
						Point center = new Point(Integer.parseInt(dlg.getTxtX().getText()),
									Integer.parseInt(dlg.getTxtY().getText()));
						Donut newDonut = new Donut(center, Integer.parseInt(dlg.getTxtOuterRadius().getText()),
								Integer.parseInt(dlg.getTxtInnerRadius().getText()), false, dlg.getOuter(), dlg.getInner());
						command = new UpdateDonutCmd((Donut) s, newDonut);
						command.execute();
						undoStack.push(command);
						frame.redo.setEnabled(false);
						redoStack.clear();
						frame.log("Modify_"+oldDonut.toString()+"_New_"+newDonut.toString());
					} catch (NumberFormatException e) {
						JOptionPane.showConfirmDialog(null, "Only integers can be accepted in the text fields", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				if (s instanceof HexagonAdapter) {
					try {
						HexagonAdapter oldHexagon = new HexagonAdapter(((HexagonAdapter)s).getX(), ((HexagonAdapter)s).getY(),((HexagonAdapter) s).getR(),
								false, s.getColor(), ((HexagonAdapter) s).getInnerColor());
						DlgHexagon dlg = new DlgHexagon(((HexagonAdapter) s).getX(), (((HexagonAdapter) s).getY()));
						dlg.getTxtRadius().setText(((Integer) ((HexagonAdapter) s).getR()).toString());
						dlg.setOuter(s.getColor());
						dlg.setInner(((HexagonAdapter) s).getInnerColor());
						dlg.setVisible(true);
						HexagonAdapter newHexagon = new HexagonAdapter(Integer.parseInt(dlg.getTxtX().getText()),
								Integer.parseInt(dlg.getTxtY().getText()), Integer.parseInt(dlg.getTxtRadius().getText()), false, dlg.getOuter(), dlg.getInner());
						command = new UpdateHexagonCmd((HexagonAdapter) s, newHexagon);
						command.execute();
						undoStack.push(command);
						frame.redo.setEnabled(false);
						redoStack.clear();
						frame.log("Modify_" + oldHexagon.toString()+"_New_" + newHexagon.toString());
					} catch (NumberFormatException e) {
						JOptionPane.showConfirmDialog(null, "Only integers can be accepted in the text fields", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			}
		}
		frame.repaint();
	}

	public void remove() {
		if (JOptionPane.showConfirmDialog(null, "Are you sure you want to remove the selected shape(s)?", "Warning",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			for (int i = model.getShapes().size() - 1; i >= 0; i--) {
				if (model.get(i).isSelected()) {
					command = new RemoveShapeCmd(model.get(i), model);
					model.get(i).setSelected(false);
					frame.log("Remove_"+model.get(i).toString());
					undoStack.push(command);
					command.execute();
					frame.redo.setEnabled(false);
					redoStack.clear();
				}
			}
			frame.repaint();
		}
	}
	
	public void undo() {
		undoStack.peek().unexecute();
		frame.log("Undo_"+undoStack.peek().toString());
		redoStack.push(undoStack.pop());
		frame.redo.setEnabled(true);
		if(undoStack.isEmpty()) {
			frame.undo.setEnabled(false);
		}
		if(!redoStack.isEmpty()) {
			frame.redo.setEnabled(true);
		}
		frame.repaint();
	}

	public void redo() {
		redoStack.peek().execute();
		frame.log("Redo_"+redoStack.peek().toString());
		undoStack.push(redoStack.pop());
		if(redoStack.isEmpty()) {
			frame.redo.setEnabled(false);
		}
		if(!undoStack.isEmpty()) {
			frame.undo.setEnabled(true);
		}
		frame.repaint();
	}
	public void toFront() {
		for(Shape s : model.getShapes()) {
			if(s.isSelected()) {
				if(s.equals(model.getShapes().get(model.getShapes().size()-1))) {
					frame.toFront.setEnabled(false);
					frame.bringToFront.setEnabled(false);
			         JOptionPane.showConfirmDialog(null, "Target shape is already in front of all shapes!", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
					break;
				}
				command = new ShapeToFrontCmd(s, model);
				command.execute();
				undoStack.push(command);
				frame.log(command.toString());
				frame.repaint();
				frame.toBack.setEnabled(true);
				frame.bringToBack.setEnabled(true);
				frame.redo.setEnabled(false);
				redoStack.clear();
				break;
			}
		}
	}
	
	public void toBack() {
		for(Shape s : model.getShapes()) {
			if(s.isSelected()) {
				if(s.equals(model.getShapes().get(0))) {
					frame.toBack.setEnabled(false);
					frame.bringToBack.setEnabled(false);
			        JOptionPane.showConfirmDialog(null, "Target shape is already behind all shapes!", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			        break;
				}
				command = new ShapeToBackCmd(s, model);
				command.execute();
				undoStack.push(command);
				frame.log(command.toString());
				frame.repaint();
				frame.toFront.setEnabled(true);
				frame.bringToFront.setEnabled(true);
				frame.redo.setEnabled(false);
				redoStack.clear();
				break;
			}
		}
	}
	
	public void bringToFront() {
		for(Shape s : model.getShapes()) {
			if(s.isSelected()) {
				if(s.equals(model.getShapes().get(model.getShapes().size()-1))) {
					frame.bringToFront.setEnabled(false);
					frame.toFront.setEnabled(false);
			        JOptionPane.showConfirmDialog(null, "Target shape is already in front of all shapes!", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
					break;
				}
				command = new ShapeBringToFrontCmd(s, model);
				command.execute();
				undoStack.push(command);
				frame.log(command.toString());
				frame.repaint();
		        frame.bringToBack.setEnabled(true);
				frame.bringToFront.setEnabled(false);
				frame.toBack.setEnabled(true);
				frame.toFront.setEnabled(false);
				frame.redo.setEnabled(false);
				redoStack.clear();
				break;
			}
		}
	}

	public void bringToBack() {
		for(Shape s : model.getShapes()) {
			if(s.isSelected()) {
				if(s.equals(model.getShapes().get(0))) {
					frame.bringToBack.setEnabled(false);
					frame.toBack.setEnabled(false);
			         JOptionPane.showConfirmDialog(null, "Target shape is already behind all shapes!", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
					break;
				}
				command = new ShapeBringToBackCmd(s, model);
				command.execute();
				undoStack.push(command);
				frame.log(command.toString());
				frame.repaint();
				frame.bringToBack.setEnabled(false);
				frame.bringToFront.setEnabled(true);
				frame.toBack.setEnabled(false);
				frame.toFront.setEnabled(true);
				break;
			}
		}
	}
	
	public void loadDrawing() {
		ArrayList<Shape> s;
		try {
			model.getShapes().clear();
			undoStack.clear();
			redoStack.clear();
			frame.dlm.clear();
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("src/resources"));
			chooser.setDialogTitle("Choose save location");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setAcceptAllFileFilterUsed(false);
			if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				FileInputStream fileIn = new FileInputStream(chooser.getCurrentDirectory()+"/" + chooser.getSelectedFile().getName());
		         ObjectInputStream in = new ObjectInputStream(fileIn);
		         s = (ArrayList<Shape>) in.readObject();
		         for(Shape p : s) {
		        	 model.getShapes().add(p);
		         }
		         in.close();
		         fileIn.close();
				JOptionPane.showConfirmDialog(null, "Loaded drawing: " + chooser.getSelectedFile().getName(), "Info", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
		         frame.repaint();
			} else {
				return;
			}
	         
	      } catch (IOException i) {
	         JOptionPane.showConfirmDialog(null, "Wrong file type!", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
	         return;
	      } catch (ClassNotFoundException c) {
	    	  JOptionPane.showConfirmDialog(null, "Shapes not found!", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
	         return;
	      }
		
	}
	
	public void saveDrawing() {
		save.saveDrawing(model);
	}

	public void loadLog() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("src/resources"));
		chooser.setDialogTitle("Open");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			String extension = "";
			int i = chooser.getSelectedFile().getName().toString().lastIndexOf('.');
			if(i > 0) {
				extension = chooser.getSelectedFile().getName().toString().substring(i+1);
			}
			if(!extension.equals("txt")) {
				JOptionPane.showConfirmDialog(null, "Wrong file type!", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				return;
			}
			logFile = new File(chooser.getCurrentDirectory()+"/" + chooser.getSelectedFile().getName());
			frame.next.setEnabled(true);
			
			try {
				logReader = new FileReader(logFile);
			} catch (FileNotFoundException e1) {
				JOptionPane.showConfirmDialog(null, "File doesnt exist", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				return;
			}
			readLog = new BufferedReader(logReader);
			model.getShapes().clear();
			undoStack.clear();
			redoStack.clear();
			frame.dlm.clear();
			JOptionPane.showConfirmDialog(null, "Loaded log: " + chooser.getSelectedFile().getName(), "Info", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
			frame.repaint();
		}
	}
	
	public void saveLog() {
		save.saveLog(frame);

	}
	
	public void next() {
		char[] array = new char[200];
		try {
			String s;
			s = readLog.readLine();
			if(s == null || s.equals("")) {
				JOptionPane.showConfirmDialog(null, "End of log", "Info", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);

			}
			if(s!=null) {
				frame.dlm.addElement(s.toString());
			} 
			if(s != null && !s.isEmpty()) {
				String[] str = s.split("_");
				String[] pos;
				String[] pos1;
				String[] size;
				String[] color;
				String[] innerColor;
				if(str[0].equals("Draw")) {
					switch(str[1]){
					case "POINT":
						pos = str[2].split(",");
						Point p = new Point();
						p.setX(Integer.parseInt(pos[0]));
						p.setY(Integer.parseInt(pos[1]));
						color = str[4].split(",");
						p.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						command = new AddShapeCmd(p, model);
						command.execute();
						undoStack.push(command);
						frame.repaint();
						break;
					case "LINE":
						pos = str[3].split(",");
						Line l = new Line();
						Point startPoint = new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
						pos1 = str[5].split(",");
						Point endPoint = new Point(Integer.parseInt(pos1[0]),Integer.parseInt(pos1[1]));
						l.setStartPoint(startPoint);
						l.setEndPoint(endPoint);
						color = str[7].split(",");
						l.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						command = new AddShapeCmd(l, model);
						command.execute();
						undoStack.push(command);
						frame.repaint();
						break;
					case "RECTANGLE":
						pos = str[2].split(",");
						Rectangle r = new Rectangle();
						Point upperLeftPoint = new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
						r.setUpperLeftPoint(upperLeftPoint);
						size = str[3].split("x");
						r.setHeight(Integer.parseInt(size[0]));
						r.setWidth(Integer.parseInt(size[1]));
						innerColor = str[5].split(",");
						r.setInnerColor(new Color(Integer.parseInt(innerColor[0]),
								Integer.parseInt(innerColor[1]), Integer.parseInt(innerColor[2])));
						color = str[7].split(",");
						r.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						command = new AddShapeCmd(r, model);
						command.execute();
						undoStack.push(command);
						frame.repaint();
						break;
					case "CIRCLE":
						pos = str[2].split(",");
						Circle c = new Circle();
						Point center = new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
						c.setCenter(center);
						c.setRadius(Integer.parseInt(str[4]));
						innerColor = str[6].split(",");
						c.setInnerColor(new Color(Integer.parseInt(innerColor[0]),
								Integer.parseInt(innerColor[1]), Integer.parseInt(innerColor[2])));
						color = str[8].split(",");
						c.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						command = new AddShapeCmd(c, model);
						command.execute();
						undoStack.push(command);
						frame.repaint();
						break;
					case "DONUT":
						pos = str[2].split(",");
						Donut d = new Donut();
						Point centerDonut = new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
						d.setCenter(centerDonut);
						d.setRadius(Integer.parseInt(str[4]));
						d.setInnerRadius(Integer.parseInt(str[6]));
						innerColor = str[8].split(",");
						d.setInnerColor(new Color(Integer.parseInt(innerColor[0]),
								Integer.parseInt(innerColor[1]), Integer.parseInt(innerColor[2])));
						color = str[10].split(",");
						d.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						command = new AddShapeCmd(d, model);
						command.execute();
						undoStack.push(command);
						frame.repaint();
						break;
					case "HEXAGON":
						pos = str[2].split(",");
						HexagonAdapter h = new HexagonAdapter();
						h.setX(Integer.parseInt(pos[0]));
						h.setY(Integer.parseInt(pos[1]));
						h.setR(Integer.parseInt(str[4]));
						innerColor = str[6].split(",");
						h.setInnerColor(new Color(Integer.parseInt(innerColor[0]),
								Integer.parseInt(innerColor[1]), Integer.parseInt(innerColor[2])));
						color = str[8].split(",");
						h.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						command = new AddShapeCmd(h, model);
						command.execute();
						undoStack.push(command);
						frame.repaint();
						break;
					}
				} else if(str[0].equals("Undo")) {
					undoStack.peek().unexecute();
					redoStack.push(undoStack.pop());
					frame.repaint();
				} else if(str[0].equals("Redo")) {
					redoStack.peek().execute();
					undoStack.push(redoStack.pop());
					frame.repaint();
				} else if(str[0].equals("Select")) {
					switch(str[1]){
					case "POINT":
						pos = str[2].split(",");
						Point p = new Point();
						p.setX(Integer.parseInt(pos[0]));
						p.setY(Integer.parseInt(pos[1]));
						color = str[4].split(",");
						p.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						for(Shape shape : model.getShapes()) {
							if(shape.equals(p)) {
								command = new SelectShapeCmd(shape, model, true);
								command.execute();
								undoStack.push(command);
								break;
							}
						}
						frame.repaint();
						break;
					case "LINE":
						pos = str[3].split(",");
						Line l = new Line();
						Point startPoint = new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
						pos1 = str[5].split(",");
						Point endPoint = new Point(Integer.parseInt(pos1[0]),Integer.parseInt(pos1[1]));
						l.setStartPoint(startPoint);
						l.setEndPoint(endPoint);
						color = str[7].split(",");
						l.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						for(Shape shape : model.getShapes()) {
							if(shape.equals(l)) {
								command = new SelectShapeCmd(shape, model, true);
								command.execute();
								undoStack.push(command);
								break;
							}
						}
						frame.repaint();
						break;
					case "RECTANGLE":
						pos = str[2].split(",");
						Rectangle r = new Rectangle();
						Point upperLeftPoint = new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
						r.setUpperLeftPoint(upperLeftPoint);
						size = str[3].split("x");
						r.setHeight(Integer.parseInt(size[0]));
						r.setWidth(Integer.parseInt(size[1]));
						innerColor = str[5].split(",");
						r.setInnerColor(new Color(Integer.parseInt(innerColor[0]),
								Integer.parseInt(innerColor[1]), Integer.parseInt(innerColor[2])));
						color = str[7].split(",");
						r.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						for(Shape shape : model.getShapes()) {
							if(shape.equals(r)) {
								command = new SelectShapeCmd(shape, model, true);
								command.execute();
								undoStack.push(command);
								break;
							}
						}
						frame.repaint();
						break;
					case "CIRCLE":
						pos = str[2].split(",");
						Circle c = new Circle();
						Point center = new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
						c.setCenter(center);
						c.setRadius(Integer.parseInt(str[4]));
						innerColor = str[6].split(",");
						c.setInnerColor(new Color(Integer.parseInt(innerColor[0]),
								Integer.parseInt(innerColor[1]), Integer.parseInt(innerColor[2])));
						color = str[8].split(",");
						c.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						for(Shape shape : model.getShapes()) {
							if(shape.equals(c)) {
								command = new SelectShapeCmd(shape, model, true);
								command.execute();
								undoStack.push(command);
								break;
							}
						}
						frame.repaint();
						break;
					case "DONUT":
						pos = str[2].split(",");
						Donut d = new Donut();
						Point centerDonut = new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
						d.setCenter(centerDonut);
						d.setRadius(Integer.parseInt(str[4]));
						d.setInnerRadius(Integer.parseInt(str[6]));
						innerColor = str[8].split(",");
						d.setInnerColor(new Color(Integer.parseInt(innerColor[0]),
								Integer.parseInt(innerColor[1]), Integer.parseInt(innerColor[2])));
						color = str[10].split(",");
						d.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						for(Shape shape : model.getShapes()) {
							if(shape.equals(d)) {
								command = new SelectShapeCmd(shape, model, true);
								command.execute();
								undoStack.push(command);
								break;
							}
						}
						frame.repaint();
						break;
					case "HEXAGON":
						pos = str[2].split(",");
						HexagonAdapter h = new HexagonAdapter();
						h.setX(Integer.parseInt(pos[0]));
						h.setY(Integer.parseInt(pos[1]));
						h.setR(Integer.parseInt(str[4]));
						innerColor = str[6].split(",");
						h.setInnerColor(new Color(Integer.parseInt(innerColor[0]),
								Integer.parseInt(innerColor[1]), Integer.parseInt(innerColor[2])));
						color = str[8].split(",");
						h.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						for(Shape shape : model.getShapes()) {
							if(shape.equals(h)) {
								command = new SelectShapeCmd(shape, model, true);
								command.execute();
								undoStack.push(command);
								break;
							}
						}
						frame.repaint();
						break;
					}
				} else if (str[0].equals("Deselect")) {
					switch(str[1]){
					case "POINT":
						pos = str[2].split(",");
						Point p = new Point();
						p.setX(Integer.parseInt(pos[0]));
						p.setY(Integer.parseInt(pos[1]));
						color = str[4].split(",");
						p.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						for(Shape shape : model.getShapes()) {
							if(shape.equals(p)) {
								command = new SelectShapeCmd(shape, model, false);
								command.execute();
								undoStack.push(command);
								break;
							}
						}
						frame.repaint();
						break;
					case "LINE":
						pos = str[3].split(",");
						Line l = new Line();
						Point startPoint = new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
						pos1 = str[5].split(",");
						Point endPoint = new Point(Integer.parseInt(pos1[0]),Integer.parseInt(pos1[1]));
						l.setStartPoint(startPoint);
						l.setEndPoint(endPoint);
						color = str[7].split(",");
						l.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						for(Shape shape : model.getShapes()) {
							if(shape.equals(l)) {
								command = new SelectShapeCmd(shape, model, false);
								command.execute();
								undoStack.push(command);
								break;
							}
						}
						frame.repaint();
						break;
					case "RECTANGLE":
						pos = str[2].split(",");
						Rectangle r = new Rectangle();
						Point upperLeftPoint = new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
						r.setUpperLeftPoint(upperLeftPoint);
						size = str[3].split("x");
						r.setHeight(Integer.parseInt(size[0]));
						r.setWidth(Integer.parseInt(size[1]));
						innerColor = str[5].split(",");
						r.setInnerColor(new Color(Integer.parseInt(innerColor[0]),
								Integer.parseInt(innerColor[1]), Integer.parseInt(innerColor[2])));
						color = str[7].split(",");
						r.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						for(Shape shape : model.getShapes()) {
							if(shape.equals(r)) {
								command = new SelectShapeCmd(shape, model, false);
								command.execute();
								undoStack.push(command);
								break;
							}
						}
						frame.repaint();
						break;
					case "CIRCLE":
						pos = str[2].split(",");
						Circle c = new Circle();
						Point center = new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
						c.setCenter(center);
						c.setRadius(Integer.parseInt(str[4]));
						innerColor = str[6].split(",");
						c.setInnerColor(new Color(Integer.parseInt(innerColor[0]),
								Integer.parseInt(innerColor[1]), Integer.parseInt(innerColor[2])));
						color = str[8].split(",");
						c.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						for(Shape shape : model.getShapes()) {
							if(shape.equals(c)) {
								command = new SelectShapeCmd(shape, model, false);
								command.execute();
								undoStack.push(command);
								break;
							}
						}
						frame.repaint();
						break;
					case "DONUT":
						pos = str[2].split(",");
						Donut d = new Donut();
						Point centerDonut = new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
						d.setCenter(centerDonut);
						d.setRadius(Integer.parseInt(str[4]));
						d.setInnerRadius(Integer.parseInt(str[6]));
						innerColor = str[8].split(",");
						d.setInnerColor(new Color(Integer.parseInt(innerColor[0]),
								Integer.parseInt(innerColor[1]), Integer.parseInt(innerColor[2])));
						color = str[10].split(",");
						d.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						for(Shape shape : model.getShapes()) {
							if(shape.equals(d)) {
								command = new SelectShapeCmd(shape, model, false);
								command.execute();
								undoStack.push(command);
								break;
							}
						}
						frame.repaint();
						break;
					case "HEXAGON":
						pos = str[2].split(",");
						HexagonAdapter h = new HexagonAdapter();
						h.setX(Integer.parseInt(pos[0]));
						h.setY(Integer.parseInt(pos[1]));
						h.setR(Integer.parseInt(str[4]));
						innerColor = str[6].split(",");
						h.setInnerColor(new Color(Integer.parseInt(innerColor[0]),
								Integer.parseInt(innerColor[1]), Integer.parseInt(innerColor[2])));
						color = str[8].split(",");
						h.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						for(Shape shape : model.getShapes()) {
							if(shape.equals(h)) {
								command = new SelectShapeCmd(shape, model, false);
								command.execute();
								undoStack.push(command);
								break;
							}
						}
						frame.repaint();
						break;
					}
				} else if(str[0].equals("Remove")) {
					switch(str[1]){
					case "POINT":
						pos = str[2].split(",");
						Point p = new Point();
						p.setX(Integer.parseInt(pos[0]));
						p.setY(Integer.parseInt(pos[1]));
						color = str[4].split(",");
						p.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						for(Shape shape : model.getShapes()) {
							if(shape.equals(p)) {
								command = new RemoveShapeCmd(shape, model);
								command.execute();
								undoStack.push(command);
								break;
							}
						}
						frame.repaint();
						break;
					case "LINE":
						pos = str[3].split(",");
						Line l = new Line();
						Point startPoint = new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
						pos1 = str[5].split(",");
						Point endPoint = new Point(Integer.parseInt(pos1[0]),Integer.parseInt(pos1[1]));
						l.setStartPoint(startPoint);
						l.setEndPoint(endPoint);
						color = str[7].split(",");
						l.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						for(Shape shape : model.getShapes()) {
							if(shape.equals(l)) {
								command = new RemoveShapeCmd(shape, model);
								command.execute();
								undoStack.push(command);
								break;
							}
						}
						frame.repaint();
						break;
					case "RECTANGLE":
						pos = str[2].split(",");
						Rectangle r = new Rectangle();
						Point upperLeftPoint = new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
						r.setUpperLeftPoint(upperLeftPoint);
						size = str[3].split("x");
						r.setHeight(Integer.parseInt(size[0]));
						r.setWidth(Integer.parseInt(size[1]));
						innerColor = str[5].split(",");
						r.setInnerColor(new Color(Integer.parseInt(innerColor[0]),
								Integer.parseInt(innerColor[1]), Integer.parseInt(innerColor[2])));
						color = str[7].split(",");
						r.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						for(Shape shape : model.getShapes()) {
							if(shape.equals(r)) {
								command = new RemoveShapeCmd(shape, model);
								command.execute();
								undoStack.push(command);
								break;
							}
						}
						frame.repaint();
						break;
					case "CIRCLE":
						pos = str[2].split(",");
						Circle c = new Circle();
						Point center = new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
						c.setCenter(center);
						c.setRadius(Integer.parseInt(str[4]));
						innerColor = str[6].split(",");
						c.setInnerColor(new Color(Integer.parseInt(innerColor[0]),
								Integer.parseInt(innerColor[1]), Integer.parseInt(innerColor[2])));
						color = str[8].split(",");
						c.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						for(Shape shape : model.getShapes()) {
							if(shape.equals(c)) {
								command = new RemoveShapeCmd(shape, model);
								command.execute();
								undoStack.push(command);
								break;
							}
						}
						frame.repaint();
						break;
					case "DONUT":
						pos = str[2].split(",");
						Donut d = new Donut();
						Point centerDonut = new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
						d.setCenter(centerDonut);
						d.setRadius(Integer.parseInt(str[4]));
						d.setInnerRadius(Integer.parseInt(str[6]));
						innerColor = str[8].split(",");
						d.setInnerColor(new Color(Integer.parseInt(innerColor[0]),
								Integer.parseInt(innerColor[1]), Integer.parseInt(innerColor[2])));
						color = str[10].split(",");
						d.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						for(Shape shape : model.getShapes()) {
							if(shape.equals(d)) {
								command = new RemoveShapeCmd(shape, model);
								command.execute();
								undoStack.push(command);
								break;
							}
						}
						frame.repaint();
						break;
					case "HEXAGON":
						pos = str[2].split(",");
						HexagonAdapter h = new HexagonAdapter();
						h.setX(Integer.parseInt(pos[0]));
						h.setY(Integer.parseInt(pos[1]));
						h.setR(Integer.parseInt(str[4]));
						innerColor = str[6].split(",");
						h.setInnerColor(new Color(Integer.parseInt(innerColor[0]),
								Integer.parseInt(innerColor[1]), Integer.parseInt(innerColor[2])));
						color = str[8].split(",");
						h.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						for(Shape shape : model.getShapes()) {
							if(shape.equals(h)) {
								command = new RemoveShapeCmd(shape, model);
								command.execute();
								undoStack.push(command);
								break;
							}
						}
						frame.repaint();
						break;
					}
				} else if (str[0].equals("Modify")) {
					String[] posModify;
					String[] pos1Modify;
					String[] sizeModify;
					String[] colorModify;
					String[] innerColorModify;
					switch(str[1]){
					case "POINT":
						pos = str[2].split(",");
						Point p = new Point();
						Point p1 = new Point();
						p.setX(Integer.parseInt(pos[0]));
						p.setY(Integer.parseInt(pos[1]));
						color = str[4].split(",");
						p.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						posModify = str[7].split(",");
						p1.setX(Integer.parseInt(posModify[0]));
						p1.setY(Integer.parseInt(posModify[1]));
						colorModify = str[9].split(",");
						p1.setColor(new Color(Integer.parseInt(colorModify[0]),
								Integer.parseInt(colorModify[1]), Integer.parseInt(colorModify[2])));
						for(Shape shape : model.getShapes()) {
							if(shape.equals(p)) {
								command = new UpdatePointCmd((Point)shape, p1);
								command.execute();
								undoStack.push(command);
								break;
							}
						}
						
						frame.repaint();
						break;
					case "LINE":
						pos = str[3].split(",");
						Line l = new Line();
						Line l1 = new Line();
						Point startPoint = new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
						pos1 = str[5].split(",");
						Point endPoint = new Point(Integer.parseInt(pos1[0]),Integer.parseInt(pos1[1]));
						l.setStartPoint(startPoint);
						l.setEndPoint(endPoint);
						color = str[7].split(",");
						l.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						posModify = str[11].split(",");
						pos1Modify = str[13].split(",");
						Point startPointModify = new Point(Integer.parseInt(posModify[0]),Integer.parseInt(posModify[1]));
						Point endPointModify = new Point(Integer.parseInt(pos1Modify[0]),Integer.parseInt(pos1Modify[1]));
						colorModify = str[15].split(",");
						l1.setStartPoint(startPointModify);
						l1.setEndPoint(endPointModify);
						l1.setColor(new Color(Integer.parseInt(colorModify[0]),
								Integer.parseInt(colorModify[1]), Integer.parseInt(colorModify[2])));
						for(Shape shape : model.getShapes()) {
							if(shape.equals(l)) {
								command = new UpdateLineCmd((Line)shape, l1);
								command.execute();
								undoStack.push(command);
								break;
							}
						}
						frame.repaint();
						break;
					case "RECTANGLE":
						pos = str[2].split(",");
						Rectangle r = new Rectangle();
						Rectangle r1 = new Rectangle();
						Point upperLeftPoint = new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
						r.setUpperLeftPoint(upperLeftPoint);
						size = str[3].split("x");
						r.setHeight(Integer.parseInt(size[0]));
						r.setWidth(Integer.parseInt(size[1]));
						innerColor = str[5].split(",");
						r.setInnerColor(new Color(Integer.parseInt(innerColor[0]),
								Integer.parseInt(innerColor[1]), Integer.parseInt(innerColor[2])));
						color = str[7].split(",");
						r.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						posModify = str[10].split(",");
						sizeModify = str[11].split("x");
						innerColorModify = str[13].split(",");
						colorModify = str[15].split(",");
						Point upperLeftPointModify = new Point(Integer.parseInt(posModify[0]),Integer.parseInt(posModify[1]));
						r1.setUpperLeftPoint(upperLeftPointModify);
						r1.setHeight(Integer.parseInt(sizeModify[0]));
						r1.setWidth(Integer.parseInt(sizeModify[1]));
						r1.setInnerColor(new Color(Integer.parseInt(innerColorModify[0]),
								Integer.parseInt(innerColorModify[1]), Integer.parseInt(innerColorModify[2])));
						r1.setColor(new Color(Integer.parseInt(colorModify[0]),
								Integer.parseInt(colorModify[1]), Integer.parseInt(colorModify[2])));
						for(Shape shape : model.getShapes()) {
							if(shape.equals(r)) {
								command = new UpdateRectangleCmd((Rectangle)shape, r1);
								command.execute();
								undoStack.push(command);
								break;
							}
						}
						frame.repaint();
						break;
					case "CIRCLE":
						pos = str[2].split(",");
						Circle c = new Circle();
						Circle c1 = new Circle();
						Point center = new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
						c.setCenter(center);
						c.setRadius(Integer.parseInt(str[4]));
						innerColor = str[6].split(",");
						c.setInnerColor(new Color(Integer.parseInt(innerColor[0]),
								Integer.parseInt(innerColor[1]), Integer.parseInt(innerColor[2])));
						color = str[8].split(",");
						c.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						posModify = str[11].split(",");
						Point centerModify = new Point(Integer.parseInt(posModify[0]),Integer.parseInt(posModify[1]));
						c1.setCenter(centerModify);
						c1.setRadius(Integer.parseInt(str[13]));
						innerColorModify = str[15].split(",");
						c1.setInnerColor(new Color(Integer.parseInt(innerColorModify[0]),
								Integer.parseInt(innerColorModify[1]), Integer.parseInt(innerColorModify[2])));
						colorModify = str[17].split(",");
						c1.setColor(new Color(Integer.parseInt(colorModify[0]),
								Integer.parseInt(colorModify[1]), Integer.parseInt(colorModify[2])));
						for(Shape shape : model.getShapes()) {
							if(shape.equals(c)) {
								command = new UpdateCircleCmd((Circle)shape, c1);
								command.execute();
								undoStack.push(command);
								break;
							}
						}
						frame.repaint();
						break;
					case "DONUT":
						pos = str[2].split(",");
						Donut d = new Donut();
						Donut d1 = new Donut();
						Point centerDonut = new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
						d.setCenter(centerDonut);
						d.setRadius(Integer.parseInt(str[4]));
						d.setInnerRadius(Integer.parseInt(str[6]));
						innerColor = str[8].split(",");
						d.setInnerColor(new Color(Integer.parseInt(innerColor[0]),
								Integer.parseInt(innerColor[1]), Integer.parseInt(innerColor[2])));
						color = str[10].split(",");
						d.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						posModify = str[13].split(",");
						Point centerDonutModify = new Point(Integer.parseInt(posModify[0]),Integer.parseInt(posModify[1]));
						d1.setCenter(centerDonutModify);
						d1.setRadius(Integer.parseInt(str[15]));
						d1.setInnerRadius(Integer.parseInt(str[17]));
						innerColorModify = str[19].split(",");
						d1.setInnerColor(new Color(Integer.parseInt(innerColorModify[0]),
								Integer.parseInt(innerColorModify[1]), Integer.parseInt(innerColorModify[2])));
						colorModify = str[21].split(",");
						d1.setColor(new Color(Integer.parseInt(colorModify[0]),
								Integer.parseInt(colorModify[1]), Integer.parseInt(colorModify[2])));
						for(Shape shape : model.getShapes()) {
							if(shape.equals(d)) {
								command = new UpdateDonutCmd((Donut)shape, d1);
								command.execute();
								undoStack.push(command);
								break;
							}
						}
						frame.repaint();
						break;
					case "HEXAGON":
						pos = str[2].split(",");
						HexagonAdapter h = new HexagonAdapter();
						HexagonAdapter h1 = new HexagonAdapter();
						h.setX(Integer.parseInt(pos[0]));
						h.setY(Integer.parseInt(pos[1]));
						h.setR(Integer.parseInt(str[4]));
						innerColor = str[6].split(",");
						h.setInnerColor(new Color(Integer.parseInt(innerColor[0]),
								Integer.parseInt(innerColor[1]), Integer.parseInt(innerColor[2])));
						color = str[8].split(",");
						h.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						posModify = str[11].split(",");
						h1.setX(Integer.parseInt(posModify[0]));
						h1.setY(Integer.parseInt(posModify[1]));
						h1.setR(Integer.parseInt(str[13]));
						innerColorModify = str[15].split(",");
						colorModify = str[17].split(",");
						h1.setInnerColor(new Color(Integer.parseInt(innerColorModify[0]),
								Integer.parseInt(innerColorModify[1]), Integer.parseInt(innerColorModify[2])));
						h1.setColor(new Color(Integer.parseInt(colorModify[0]),
								Integer.parseInt(colorModify[1]), Integer.parseInt(colorModify[2])));
						for(Shape shape : model.getShapes()) {
							if(shape.equals(h)) {
								command = new UpdateHexagonCmd((HexagonAdapter)shape, h1);
								command.execute();
								undoStack.push(command);
								break;
							}
						}
						frame.repaint();
						break;
					}
				} else if(str[0].equals("Move")) {
					switch(str[1]){
					case "POINT":
						pos = str[2].split(",");
						Point p = new Point();
						p.setX(Integer.parseInt(pos[0]));
						p.setY(Integer.parseInt(pos[1]));
						color = str[4].split(",");
						p.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						if(str[str.length-1].equals("ToFront")) {
							for(Shape shape : model.getShapes()) {
								if(shape.equals(p)) {
									command = new ShapeBringToFrontCmd(shape, model);
									command.execute();
									undoStack.push(command);
									break;
								}
							}
						} else if(str[str.length-1].equals("ToBack")) {
							for(Shape shape : model.getShapes()) {
								if(shape.equals(p)) {
									command = new ShapeBringToBackCmd(shape, model);
									command.execute();
									undoStack.push(command);
									break;
								}
							}
						} else if(str[str.length-1].equals("ToFrontByOne")) {
							for(Shape shape : model.getShapes()) {
								if(shape.equals(p)) {
									command = new ShapeToFrontCmd(shape, model);
									command.execute();
									undoStack.push(command);
									break;
								}
							}
						} else if(str[str.length-1].equals("ToBackByOne")) {
							for(Shape shape : model.getShapes()) {
								if(shape.equals(p)) {
									command = new ShapeToBackCmd(shape, model);
									command.execute();
									undoStack.push(command);
									break;
								}
							}
						} 
						frame.repaint();
						break;
					case "LINE":
						pos = str[3].split(",");
						Line l = new Line();
						Point startPoint = new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
						pos1 = str[5].split(",");
						Point endPoint = new Point(Integer.parseInt(pos1[0]),Integer.parseInt(pos1[1]));
						l.setStartPoint(startPoint);
						l.setEndPoint(endPoint);
						color = str[7].split(",");
						l.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						if(str[str.length-1].equals("ToFront")) {
							for(Shape shape : model.getShapes()) {
								if(shape.equals(l)) {
									command = new ShapeBringToFrontCmd(shape, model);
									command.execute();
									undoStack.push(command);
									break;
								}
							}
						} else if(str[str.length-1].equals("ToBack")) {
							for(Shape shape : model.getShapes()) {
								if(shape.equals(l)) {
									command = new ShapeBringToBackCmd(shape, model);
									command.execute();
									undoStack.push(command);
									break;
								}
							}
						} else if(str[str.length-1].equals("ToFrontByOne")) {
							for(Shape shape : model.getShapes()) {
								if(shape.equals(l)) {
									command = new ShapeToFrontCmd(shape, model);
									command.execute();
									undoStack.push(command);
									break;
								}
							}
						} else if(str[str.length-1].equals("ToBackByOne")) {
							for(Shape shape : model.getShapes()) {
								if(shape.equals(l)) {
									command = new ShapeToBackCmd(shape, model);
									command.execute();
									undoStack.push(command);
									break;
								}
							}
						} 
						frame.repaint();
						break;
					case "RECTANGLE":
						pos = str[2].split(",");
						Rectangle r = new Rectangle();
						Point upperLeftPoint = new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
						r.setUpperLeftPoint(upperLeftPoint);
						size = str[3].split("x");
						r.setHeight(Integer.parseInt(size[0]));
						r.setWidth(Integer.parseInt(size[1]));
						innerColor = str[5].split(",");
						r.setInnerColor(new Color(Integer.parseInt(innerColor[0]),
								Integer.parseInt(innerColor[1]), Integer.parseInt(innerColor[2])));
						color = str[7].split(",");
						r.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						if(str[str.length-1].equals("ToFront")) {
							for(Shape shape : model.getShapes()) {
								if(shape.equals(r)) {
									command = new ShapeBringToFrontCmd(shape, model);
									command.execute();
									undoStack.push(command);
									break;
								}
							}
						} else if(str[str.length-1].equals("ToBack")) {
							for(Shape shape : model.getShapes()) {
								if(shape.equals(r)) {
									command = new ShapeBringToBackCmd(shape, model);
									command.execute();
									undoStack.push(command);
									break;
								}
							}
						} else if(str[str.length-1].equals("ToFrontByOne")) {
							for(Shape shape : model.getShapes()) {
								if(shape.equals(r)) {
									command = new ShapeToFrontCmd(shape, model);
									command.execute();
									undoStack.push(command);
									break;
								}
							}
						} else if(str[str.length-1].equals("ToBackByOne")) {
							for(Shape shape : model.getShapes()) {
								if(shape.equals(r)) {
									command = new ShapeToBackCmd(shape, model);
									command.execute();
									undoStack.push(command);
									break;
								}
							}
						} 
						frame.repaint();
						break;
					case "CIRCLE":
						pos = str[2].split(",");
						Circle c = new Circle();
						Point center = new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
						c.setCenter(center);
						c.setRadius(Integer.parseInt(str[4]));
						innerColor = str[6].split(",");
						c.setInnerColor(new Color(Integer.parseInt(innerColor[0]),
								Integer.parseInt(innerColor[1]), Integer.parseInt(innerColor[2])));
						color = str[8].split(",");
						c.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						if(str[str.length-1].equals("ToFront")) {
							for(Shape shape : model.getShapes()) {
								if(shape.equals(c)) {
									command = new ShapeBringToFrontCmd(shape, model);
									command.execute();
									undoStack.push(command);
									break;
								}
							}
						} else if(str[str.length-1].equals("ToBack")) {
							for(Shape shape : model.getShapes()) {
								if(shape.equals(c)) {
									command = new ShapeBringToBackCmd(shape, model);
									command.execute();
									undoStack.push(command);
									break;
								}
							}
						} else if(str[str.length-1].equals("ToFrontByOne")) {
							for(Shape shape : model.getShapes()) {
								if(shape.equals(c)) {
									command = new ShapeToFrontCmd(shape, model);
									command.execute();
									undoStack.push(command);
									break;
								}
							}
						} else if(str[str.length-1].equals("ToBackByOne")) {
							for(Shape shape : model.getShapes()) {
								if(shape.equals(c)) {
									command = new ShapeToBackCmd(shape, model);
									command.execute();
									undoStack.push(command);
									break;
								}
							}
						} 
						frame.repaint();
						break;
					case "DONUT":
						pos = str[2].split(",");
						Donut d = new Donut();
						Point centerDonut = new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
						d.setCenter(centerDonut);
						d.setRadius(Integer.parseInt(str[4]));
						d.setInnerRadius(Integer.parseInt(str[6]));
						innerColor = str[8].split(",");
						d.setInnerColor(new Color(Integer.parseInt(innerColor[0]),
								Integer.parseInt(innerColor[1]), Integer.parseInt(innerColor[2])));
						color = str[10].split(",");
						d.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						if(str[str.length-1].equals("ToFront")) {
							for(Shape shape : model.getShapes()) {
								if(shape.equals(d)) {
									command = new ShapeBringToFrontCmd(shape, model);
									command.execute();
									undoStack.push(command);
									break;
								}
							}
						} else if(str[str.length-1].equals("ToBack")) {
							for(Shape shape : model.getShapes()) {
								if(shape.equals(d)) {
									command = new ShapeBringToBackCmd(shape, model);
									command.execute();
									undoStack.push(command);
									break;
								}
							}
						} else if(str[str.length-1].equals("ToFrontByOne")) {
							for(Shape shape : model.getShapes()) {
								if(shape.equals(d)) {
									command = new ShapeToFrontCmd(shape, model);
									command.execute();
									undoStack.push(command);
									break;
								}
							}
						} else if(str[str.length-1].equals("ToBackByOne")) {
							for(Shape shape : model.getShapes()) {
								if(shape.equals(d)) {
									command = new ShapeToBackCmd(shape, model);
									command.execute();
									undoStack.push(command);
									break;
								}
							}
						} 
						frame.repaint();
						break;
					case "HEXAGON":
						pos = str[2].split(",");
						HexagonAdapter h = new HexagonAdapter();
						h.setX(Integer.parseInt(pos[0]));
						h.setY(Integer.parseInt(pos[1]));
						h.setR(Integer.parseInt(str[4]));
						innerColor = str[6].split(",");
						h.setInnerColor(new Color(Integer.parseInt(innerColor[0]),
								Integer.parseInt(innerColor[1]), Integer.parseInt(innerColor[2])));
						color = str[8].split(",");
						h.setColor(new Color(Integer.parseInt(color[0]),
								Integer.parseInt(color[1]), Integer.parseInt(color[2])));
						if(str[str.length-1].equals("ToFront")) {
							for(Shape shape : model.getShapes()) {
								if(shape.equals(h)) {
									command = new ShapeBringToFrontCmd(shape, model);
									command.execute();
									undoStack.push(command);
									break;
								}
							}
						} else if(str[str.length-1].equals("ToBack")) {
							for(Shape shape : model.getShapes()) {
								if(shape.equals(h)) {
									command = new ShapeBringToBackCmd(shape, model);
									command.execute();
									undoStack.push(command);
									break;
								}
							}
						} else if(str[str.length-1].equals("ToFrontByOne")) {
							for(Shape shape : model.getShapes()) {
								if(shape.equals(h)) {
									command = new ShapeToFrontCmd(shape, model);
									command.execute();
									undoStack.push(command);
									break;
								}
							}
						} else if(str[str.length-1].equals("ToBackByOne")) {
							for(Shape shape : model.getShapes()) {
								if(shape.equals(h)) {
									command = new ShapeToBackCmd(shape, model);
									command.execute();
									undoStack.push(command);
									break;
								}
							}
						} 
						frame.repaint();
						break;
					}
				}
			} else {
				frame.next.setEnabled(false);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		isShapeSelected = false;
		int counter = 0;
		for (Shape shape : model.getShapes()) {
			if(shape.isSelected()) {
				counter++;
				this.isShapeSelected = true;
			}
		}

		if(this.isShapeSelected && counter == 1) {
			frame.modify.setEnabled(true);
			frame.bringToFront.setEnabled(true);
			frame.bringToBack.setEnabled(true);
			frame.toFront.setEnabled(true);
			frame.toBack.setEnabled(true);
			frame.remove.setEnabled(true);
		} else if(this.isShapeSelected && counter != 1) {
			frame.modify.setEnabled(false);
			frame.remove.setEnabled(true);
			frame.bringToFront.setEnabled(false);
			frame.bringToBack.setEnabled(false);
			frame.toFront.setEnabled(false);
			frame.toBack.setEnabled(false);
		} else {
			frame.modify.setEnabled(false);
			frame.remove.setEnabled(false);
			frame.bringToFront.setEnabled(false);
			frame.bringToBack.setEnabled(false);
			frame.toFront.setEnabled(false);
			frame.toBack.setEnabled(false);
		}
		
	}

}
