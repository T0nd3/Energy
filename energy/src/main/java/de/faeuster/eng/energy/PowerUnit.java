package de.faeuster.eng.energy;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PowerUnit implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private EnergyType type;
	private double power;
	private LocalDate date;
	private String producer;
	private double price;
	private Place place;
	private int workingduration;

	public PowerUnit() {
	}

	public PowerUnit(EnergyType type, double power, LocalDate date, String producer, double price, Place place,
			int workingduration) {
		super();
		this.type = type;
		this.power = power;
		this.date = date;
		this.producer = producer;
		this.price = price;
		this.place = place;
		this.workingduration = workingduration;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EnergyType getType() {
		return type;
	}

	public void setType(EnergyType type) {
		this.type = type;
	}

	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = power;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public int getWorkingduration() {
		return workingduration;
	}

	public void setWorkingduration(int workingduration) {
		this.workingduration = workingduration;
	}

}
