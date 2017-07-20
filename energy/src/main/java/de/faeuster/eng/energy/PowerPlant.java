package de.faeuster.eng.energy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity

public class PowerPlant implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	public int getId() {
		return id;
	}

	@Cascade(value = { CascadeType.ALL })
	@OneToMany
	private List<PowerUnit> units = new ArrayList<PowerUnit>();

	public PowerPlant() {
	}

	public PowerPlant(List<PowerUnit> units) {
		super();
		this.units = units;
	}

	public List<PowerUnit> getUnits() {
		return units;
	}

	public void setUnits(List<PowerUnit> units) {
		this.units = units;
	}

}
