package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Donut extends Circle {

	private int innerRadius;

	public Donut() {

	}

	public Donut(Point center, int radius, int innerRadius) {
		super(center, radius);
		this.innerRadius = innerRadius;
	}

	public Donut(Point center, int radius, int innerRadius, boolean selected) {
		this(center, radius, innerRadius);
		setSelected(selected);
	}

	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color) {
		this(center, radius, innerRadius, selected);
		this.setColor(color);
	}

	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color, Color innerColor) {
		this(center, radius, innerRadius, selected, color);
		this.setInnerColor(innerColor);
	}
	
	private Shape transparentDonut() {
		Ellipse2D inner = new Ellipse2D.Double(this.getCenter().getX()- this.innerRadius, this.getCenter().getY() - this.innerRadius,
				this.innerRadius * 2, this.innerRadius * 2);
		Ellipse2D outer = new Ellipse2D.Double(super.getCenter().getX() - super.getRadius(), super.getCenter().getY() - super.getRadius(),
				super.getRadius() * 2, super.getRadius() * 2);
		Area area = new Area(outer);
		area.subtract(new Area(inner));
		return area;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Donut) {
			return (int) (this.area() - ((Donut) o).area());
		}
		return 0;
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		super.fill(g);
		g.setColor(Color.LIGHT_GRAY);
		g.fillOval(this.getCenter().getX() - this.innerRadius, this.getCenter().getY() - this.innerRadius,
				this.innerRadius * 2, this.innerRadius * 2);
	}

	@Override
	public void draw(Graphics g) {
		Shape donut = transparentDonut();
		g.setColor(getInnerColor());
		((Graphics2D) g).fill(donut);
		g.setColor(getColor());
		((Graphics2D) g).draw(donut);
		
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.getCenter().getX()-3, this.getCenter().getY()-3, 6, 6);
			g.drawRect(this.getCenter().getX()-this.getRadius()-3, this.getCenter().getY()-3, 6, 6);
			g.drawRect(this.getCenter().getX()+this.getRadius()-3, this.getCenter().getY()-3, 6, 6);
			g.drawRect(this.getCenter().getX()-3, this.getCenter().getY()-this.getRadius()-3, 6, 6);
			g.drawRect(this.getCenter().getX()-3, this.getCenter().getY()+this.getRadius()-3, 6, 6);
		}
	}

	public double area() {
		return super.area() - innerRadius * innerRadius * Math.PI;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Donut) {
			Donut pomocni = (Donut) obj;
			if (this.innerRadius == pomocni.innerRadius && getCenter().equals(pomocni.getCenter())
					&& getRadius() == pomocni.getRadius()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean contains(int x, int y) {
		double dFromCenter = getCenter().distance(x, y);
		return super.contains(x, y) && dFromCenter > innerRadius;
	}

	public boolean contains(Point p) {
		double dFromCenter = getCenter().distance(p.getX(), p.getY());
		return super.contains(p.getX(), p.getY()) && dFromCenter > innerRadius;
	}

	public int getInnerRadius() {
		return innerRadius;
	}

	public void setInnerRadius(int innerRadius) {
		this.innerRadius = innerRadius;
	}

	public String toString() {
		return String.format("DONUT_%1$s,%2$s_OuterRadius_%3$s_InnerRadius_%4$s_InnerColor_%5$s,%6$s,%7$s_OuterColor_%8$s,%9$s,%10$s",
				this.getCenter().getX(),this.getCenter().getY(), this.getRadius(),this.getInnerRadius(),this.getInnerColor().getRed(),
				this.getInnerColor().getGreen(),this.getInnerColor().getBlue(),this.getColor().getRed(),
				this.getColor().getGreen(),this.getColor().getBlue());
	}
	
	@Override
	public Donut clone() {
		return (Donut) super.clone();
	}

}
