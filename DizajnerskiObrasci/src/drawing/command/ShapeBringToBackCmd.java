package drawing.command;

import drawing.DrawingModel;
import geometry.Shape;

public class ShapeBringToBackCmd implements Command {

	private Shape shape;
	private DrawingModel model;
	private int original;
	private int oneAbove;

	public ShapeBringToBackCmd(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
	}

	@Override
	public void execute() {
		original = model.getShapes().indexOf(shape);
		for(int i = 0; i<model.getShapes().size(); i++) {
			oneAbove = model.getShapes().indexOf(shape)-1;
			if(oneAbove < 0) {
				break;
			}
			Shape tmp = shape;
			model.getShapes().set(model.getShapes().indexOf(shape), model.get(oneAbove));
			model.getShapes().set(oneAbove, tmp);
		}
	}

	@Override
	public void unexecute() {
		for(int i = 0; i<model.getShapes().size(); i++) {
			oneAbove = model.getShapes().indexOf(shape)+1;
			if(oneAbove == model.getShapes().size() || oneAbove > original ) {
				break;
			}
			Shape tmp = shape;
			model.getShapes().set(model.getShapes().indexOf(shape), model.get(oneAbove));
			model.getShapes().set(oneAbove, tmp);
		}
	}

	public String toString() {
		return "Move_" + shape.toString()+"_ToBack";
	}

}
