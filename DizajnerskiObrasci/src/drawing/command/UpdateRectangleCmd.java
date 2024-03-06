package drawing.command;

import geometry.Rectangle;

public class UpdateRectangleCmd implements Command {

	private Rectangle oldState;
	private Rectangle newState;
	private Rectangle original = new Rectangle();

	public UpdateRectangleCmd(Rectangle oldState, Rectangle newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		original = oldState.clone();
		
		oldState.setUpperLeftPoint(newState.getUpperLeftPoint());
		oldState.setHeight(newState.getHeight());
		oldState.setWidth(newState.getWidth());
		oldState.setColor(newState.getColor());
		oldState.setInnerColor(newState.getInnerColor());
		oldState.setSelected(newState.isSelected());
	}

	@Override
	public void unexecute() {
		oldState.setUpperLeftPoint(original.getUpperLeftPoint());
		oldState.setHeight(original.getHeight());
		oldState.setWidth(original.getWidth());
		oldState.setColor(original.getColor());
		oldState.setInnerColor(original.getInnerColor());
	}
	
	public String toString() {
		return "Update_Rectangle";
	}

}
