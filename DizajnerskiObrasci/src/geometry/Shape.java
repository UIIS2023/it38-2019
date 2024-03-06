package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import drawing.save.Save;

public abstract class Shape implements Moveable, Comparable, Cloneable, Serializable {

	private boolean selected;
	private Color color;
	
	private PropertyChangeSupport propertyChangeSupport;

	public Shape() {
		propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public Shape(Color color) {
		this.color = color;
	}

	public Shape(Color color, boolean selected) {
		this(color);
		this.selected = selected;
	}
	@Override
	public Shape clone() {
		try {
			return (Shape) super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

	public abstract boolean contains(int x, int y);

	public abstract void draw(Graphics g);

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		boolean selectedOld = this.selected;
		this.selected = selected;
		propertyChangeSupport.firePropertyChange("selected", selectedOld, this.selected);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}

	public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
	}
	
	

}
