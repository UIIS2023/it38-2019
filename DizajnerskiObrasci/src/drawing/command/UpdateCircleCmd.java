package drawing.command;

import geometry.Circle;

public class UpdateCircleCmd implements Command {

	private Circle oldState;
	private Circle newState;
	private Circle original = new Circle();

	public UpdateCircleCmd(Circle oldState, Circle newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		original = oldState.clone();
		
		oldState.setCenter(newState.getCenter());
		oldState.setRadius(newState.getRadius());
		oldState.setColor(newState.getColor());
		oldState.setInnerColor(newState.getInnerColor());
		oldState.setSelected(newState.isSelected());

	}

	@Override
	public void unexecute() {
		oldState.setCenter(original.getCenter());
		oldState.setRadius(original.getRadius());
		oldState.setColor(original.getColor());
		oldState.setInnerColor(original.getInnerColor());
	}
	
	public String toString() {
		return "Update_Circle";
	}

}
