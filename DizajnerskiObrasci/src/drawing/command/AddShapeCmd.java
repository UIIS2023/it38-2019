package drawing.command;

import java.util.Stack;

import drawing.DrawingModel;
import geometry.Shape;

public class AddShapeCmd implements Command{
	
	private Shape shape;
	private DrawingModel model;

	public AddShapeCmd(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
	}

	@Override
	public void execute() {
		model.add(shape);
	}

	@Override
	public void unexecute() {
		model.remove(shape);
	}
	
	public String toString() {
		return "Add_Shape";
	}

}