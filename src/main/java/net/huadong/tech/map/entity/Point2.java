package net.huadong.tech.map.entity;


public class Point2  {

	private static final long serialVersionUID = 1L;

	public Point2() {}
	public Point2( double x,double y) {
		this.x=x;
		this.y=y;
	}
	private double x;
	private double y;
	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}
	

}
