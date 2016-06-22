package com.cooksys.ftd.kata.model;

public class Catterfly implements Comparable<Catterfly> {

	private double wingspan;
	private double weight;

	public Catterfly() {
		super();
	}

	public Catterfly(double wingspan, double weight) {
		super();
		this.wingspan = wingspan;
		this.weight = weight;
	}
	

	@Override
	public int compareTo(Catterfly that) {
		if (this.wingspan < that.wingspan) {
			return -1;
		} else if (this.wingspan > that.wingspan) {
			return +1;
		} else if (this.weight < that.weight) {
			return -1;
		} else if (this.weight > that.weight) {
			return +1;
		} else {
			return 0;
		}
	}

	public double getWingspan() {
		return wingspan;
	}

	public void setWingspan(double wingspan) {
		this.wingspan = wingspan;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Catterfly [wingspan=" + wingspan + ", weight=" + weight + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(wingspan);
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
		Catterfly other = (Catterfly) obj;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		if (Double.doubleToLongBits(wingspan) != Double.doubleToLongBits(other.wingspan))
			return false;
		return true;
	}

}
