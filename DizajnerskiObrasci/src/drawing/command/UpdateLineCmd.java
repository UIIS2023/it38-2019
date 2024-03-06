package drawing.command;

import geometry.Line;

public class UpdateLineCmd implements Command {

	private Line oldState;
	private Line newState;
	private Line original = new Line();

	public UpdateLineCmd(Line oldState, Line newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		original = oldState.clone();
		
		oldState.setStartPoint(newState.getStartPoint());
		oldState.setEndPoint(newState.getEndPoint());
		oldState.setColor(newState.getColor());
		oldState.setSelected(newState.isSelected());
	}

	@Override
	public void unexecute() {
		oldState.setStartPoint(original.getStartPoint());
		oldState.setEndPoint(original.getEndPoint());
		oldState.setColor(original.getColor());
	}
	
	public String toString() {
		return "Update_Line";
	}

}
