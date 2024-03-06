package drawing.command;

import drawing.DrawingModel;
import geometry.Shape;

public class RemoveShapeCmd implements Command{
	
	private Shape shape;
	private DrawingModel model;

	public RemoveShapeCmd(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
	}

	@Override
	public void execute() {
		model.remove(shape);
	}

	@Override
	public void unexecute() {
		model.add(shape);
		shape.setSelected(true);
	}
	
	public String toString() {
		return "Remove_Shape";
	}

}
