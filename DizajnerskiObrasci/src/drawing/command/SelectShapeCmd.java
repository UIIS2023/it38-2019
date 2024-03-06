package drawing.command;

import drawing.DrawingModel;
import geometry.Shape;

public class SelectShapeCmd implements Command {
	
	private Shape shape;
	private DrawingModel model;
	private boolean selected;

	public SelectShapeCmd(Shape shape, DrawingModel model, boolean selected) {
		this.shape = shape;
		this.model = model;
		this.selected = selected;
	}

	@Override
	public void execute() {
		for(Shape s : model.getShapes()) {
			if(s.equals(shape)) {
				s.setSelected(selected);
			}
		}

	}

	@Override
	public void unexecute() {
		for(Shape s : model.getShapes()) {
			if(s.equals(shape)) {
				s.setSelected(!selected);
			}
		}

	}
	
	public String toString() {
		return selected ? "Select_Shape" : "Deselect_Shape";
	}

}
