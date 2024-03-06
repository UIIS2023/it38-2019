package drawing.command;

import drawing.DrawingModel;
import geometry.Shape;

public class ShapeBringToFrontCmd implements Command {
	
	private Shape shape;
	private DrawingModel model;
	private int original;
	private int oneBelow;
	
	public ShapeBringToFrontCmd(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
	}

	@Override
	public void execute() {
		original = model.getShapes().indexOf(shape);
		for(int i = 0; i<model.getShapes().size()-1;i++) {
			oneBelow = model.getShapes().indexOf(shape)+1;
			if(oneBelow >= model.getShapes().size()) {
				break;
			}
			Shape tmp = shape;
			model.getShapes().set(model.getShapes().indexOf(shape), model.get(oneBelow));
			model.getShapes().set(oneBelow, tmp);
		}
	}

	@Override
	public void unexecute() {
		for(int i = 0; i<model.getShapes().size()-1;i++) {
			oneBelow = model.getShapes().indexOf(shape)-1;
			if(oneBelow < 0 || oneBelow < original) {
				break;
			}
			Shape tmp = shape;
			model.getShapes().set(model.getShapes().indexOf(shape), model.get(oneBelow));
			model.getShapes().set(oneBelow, tmp);
		}
	}

	public String toString() {
		return "Move_" + shape.toString()+"_ToFront";
	}
}
