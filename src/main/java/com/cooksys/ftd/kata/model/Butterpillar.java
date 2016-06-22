package com.cooksys.ftd.kata.model;

public class Butterpillar implements Comparable<Butterpillar> {

	private double length;
	private double leavesEaten;

	public Butterpillar() {
		super();
	}

	public Butterpillar(double length, double leavesEaten) {
		super();
		this.length = length;
		this.leavesEaten = leavesEaten;
	}
	
	@Override
	public int compareTo(Butterpillar that) {
		if (this.length < that.length) {
			return -1;
		} else if (this.length > that.length) {
			return +1;
		} else if (this.leavesEaten < that.leavesEaten) {
			return -1;
		} else if (this.leavesEaten > that.leavesEaten) {
			return +1;
		} else {
			return 0;
		}
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getLeavesEaten() {
		return leavesEaten;
	}

	public void setLeavesEaten(double leavesEaten) {
		this.leavesEaten = leavesEaten;
	}

	@Override
	public String toString() {
		return "Butterpillar [length=" + length + ", leavesEaten=" + leavesEaten + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(leavesEaten);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(length);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Butterpillar other = (Butterpillar) obj;
		if (Double.doubleToLongBits(leavesEaten) != Double.doubleToLongBits(other.leavesEaten))
			return false;
		if (Double.doubleToLongBits(length) != Double.doubleToLongBits(other.length))
			return false;
		return true;
	}

	

}
