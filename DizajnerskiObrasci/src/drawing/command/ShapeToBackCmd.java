package drawing.command;

import drawing.DrawingModel;
import geometry.Shape;

public class ShapeToBackCmd implements Command {

	private Shape shape;
	private DrawingModel model;
	private int original;
	private int oneAbove;
	
	public ShapeToBackCmd(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
	}

	@Override
	public void execute() {
		original = model.getShapes().indexOf(shape);
		oneAbove = model.getShapes().indexOf(shape)-1;
		Shape tmp = shape;
		model.getShapes().set(model.getShapes().indexOf(shape), model.get(oneAbove));
		model.getShapes().set(oneAbove, tmp);
	}

	@Override
	public void unexecute() {
		Shape tmp = model.getShapes().get(original);
		model.getShapes().set(original, model.get(oneAbove));
		model.getShapes().set(oneAbove, tmp);
	}

	public String toString() {
		return "Move_" + shape.toString()+"_ToBackByOne";
	}

}
