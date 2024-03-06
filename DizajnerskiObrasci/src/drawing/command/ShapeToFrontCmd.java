package drawing.command;

import drawing.DrawingModel;
import geometry.Shape;

public class ShapeToFrontCmd implements Command {

	private Shape shape;
	private DrawingModel model;
	private int original;
	private int oneBelow;
	
	public ShapeToFrontCmd(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
	}

	@Override
	public void execute() {
		original = model.getShapes().indexOf(shape);
		oneBelow = model.getShapes().indexOf(shape)+1;
		Shape tmp = shape;
		model.getShapes().set(model.getShapes().indexOf(shape), model.get(oneBelow));
		model.getShapes().set(oneBelow, tmp);
	}

	@Override
	public void unexecute() {
		Shape tmp = model.getShapes().get(original);
		model.getShapes().set(original, model.get(oneBelow));
		model.getShapes().set(oneBelow, tmp);
	}

	public String toString() {
		return "Move_" + shape.toString()+"_ToFrontByOne";
	}

}
