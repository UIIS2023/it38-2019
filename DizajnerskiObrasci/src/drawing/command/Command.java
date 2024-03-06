package drawing.command;

public interface Command {
	void execute();

	void unexecute();
}
