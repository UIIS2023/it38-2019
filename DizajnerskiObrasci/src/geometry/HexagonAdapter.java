package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import hexagon.Hexagon;

public class HexagonAdapter extends SurfaceShape {
	
	private Hexagon hexagon;
	private PropertyChangeSupport propertyChangeSupport;
	
	public HexagonAdapter() {
		this.hexagon = new Hexagon(0, 0, 0);
		propertyChangeSupport = new PropertyChangeSupport(this);
	}
	
	public HexagonAdapter(int x, int y, int r, boolean selected, Color color, Color innerColor) {
		propertyChangeSupport = new PropertyChangeSupport(this);
		this.hexagon = new Hexagon(x, y, r);
		this.hexagon.setSelected(selected);
		this.hexagon.setBorderColor(color);
		this.hexagon.setAreaColor(innerColor);
	}
	
	public int getX() {
		return this.hexagon.getX();
	}
	
	public int getY() {
		return this.hexagon.getY();
	}
	
	public int getR() {
		return this.hexagon.getR();
	}
	
	public Color getInnerColor() {
		return this.hexagon.getAreaColor();
	}
	
	@Override
	public Color getColor() {
		return this.hexagon.getBorderColor();
	}
	
	public void setX(int x) {
		this.hexagon.setX(x);
	}
	
	public void setY(int y) {
		this.hexagon.setY(y);
	}
	
	public void setR(int r) {
		this.hexagon.setR(r);
	}
	
	public void setInnerColor(Color c) {
		this.hexagon.setAreaColor(c);
	}
	
	@Override
	public void setColor(Color c) {
		this.hexagon.setBorderColor(c);
	}
	
	
	@Override
	public void moveBy(int byX, int byY) {
		this.hexagon.setX(this.hexagon.getX() + byX);
		this.hexagon.setY(this.hexagon.getY() + byY);
	}

	@Override
	public int compareTo(Object o) {
		if(o instanceof Hexagon) {
			return this.hexagon.getR() - ((Hexagon) o).getR();
		}
		return 0;
	}
	
	@Override
	public boolean isSelected() {
		return this.hexagon.isSelected();
	}
	
	@Override
	public void setSelected(boolean selected) {
		boolean selectedOld = this.hexagon.isSelected();
		this.hexagon.setSelected(selected);
		propertyChangeSupport.firePropertyChange("selected", selectedOld, this.hexagon.isSelected());
	}

	@Override
	public boolean contains(int x, int y) {
		return this.hexagon.doesContain(x, y);
	}

	@Override
	public void draw(Graphics g) {
		this.fill(g);
	}
	
	public String toString() {
		
		return String.format("HEXAGON_%1$s,%2$s_Radius_%3$s_InnerColor_%4$s,%5$s,%6$s_OuterColor_%7$s,%8$s,%9$s",
				this.hexagon.getX(),this.hexagon.getY(),this.hexagon.getR(),
				this.hexagon.getAreaColor().getRed(),this.hexagon.getAreaColor().getGreen(),this.hexagon.getAreaColor().getBlue(),
				this.hexagon.getBorderColor().getRed(),this.hexagon.getBorderColor().getGreen(),this.hexagon.getBorderColor().getBlue());
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof HexagonAdapter) {
			HexagonAdapter pomocni = (HexagonAdapter) obj;
			if (this.getX() == pomocni.getX() && this.getY() == pomocni.getY() &&
					this.getR() == pomocni.getR()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}


	@Override
	public boolean contains(Point p) {
		return this.hexagon.doesContain(p.getX(), p.getY());
	}

	@Override
	public void fill(Graphics g) {
		this.hexagon.paint(g);
	}
	
	@Override
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}
	@Override
	public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
	}
	
	@Override
	public HexagonAdapter clone() {
		HexagonAdapter ha = new HexagonAdapter(this.getX(),this.getY(),this.getR(),this.isSelected(),this.getColor(),this.getInnerColor());
		return ha;
	}

}
