package drawing.save;

import javax.swing.JList;

import drawing.DrawingFrame;
import drawing.DrawingModel;

public interface Save {
	void saveLog(DrawingFrame frame);
	void saveDrawing(DrawingModel model);
}
