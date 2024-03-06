package drawing.save;

import drawing.DrawingFrame;
import drawing.DrawingModel;

public class SaveManager implements Save {
	
	private Save save;
	
	public SaveManager(Save save) {
		this.save = save;
	}

	@Override
	public void saveLog(DrawingFrame frame) {
		save.saveLog(frame);
	}
	
	@Override
	public void saveDrawing(DrawingModel model) {
		save.saveDrawing(model);
	}

}
