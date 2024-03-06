package drawing.save;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import drawing.DrawingFrame;
import drawing.DrawingModel;

public class SaveLogDrawing implements Save {


	@Override
	public void saveDrawing(DrawingModel model) {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("src/resources"));
			chooser.setDialogTitle("Choose save location");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setAcceptAllFileFilterUsed(false);
			if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				FileOutputStream fileOut = new FileOutputStream(chooser.getCurrentDirectory()+"/" + chooser.getSelectedFile().getName()+".ser");
		         ObjectOutputStream out = new ObjectOutputStream(fileOut);
		         out.writeObject(model.getShapes());
		         out.close();
		         fileOut.close();
				JOptionPane.showConfirmDialog(null, "Drawing saved as: " + chooser.getSelectedFile().getName() + ".ser", "Info", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);

			}
	         
	      } catch (IOException i) {
	         i.printStackTrace();
	      }
		
	}

	@Override
	public void saveLog(DrawingFrame frame) {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new File("src/resources"));
			chooser.setDialogTitle("Choose save location");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setAcceptAllFileFilterUsed(false);
			if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				FileWriter fw = new FileWriter(chooser.getCurrentDirectory()+"/" + chooser.getSelectedFile().getName()+".txt");
				for(String s : frame.getLogList()) {
					fw.append(s+"\n");
				}
				fw.close();
				JOptionPane.showConfirmDialog(null, "Log saved as: " + chooser.getSelectedFile().getName() + ".txt", "Info", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);

			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}




}
