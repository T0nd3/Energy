package db;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import de.faeuster.eng.energy.EnergyType;
import de.faeuster.eng.energy.Place;
import de.faeuster.eng.energy.PowerPlant;
import de.faeuster.eng.energy.PowerUnit;

public class DBUtil {

	protected EntityManager em;

	public DBUtil(EntityManager em) {
		this.em = em;
	}

	public PowerPlant createTestdata() {
		PowerPlant plant = new PowerPlant(new ArrayList<>());

		LocalDate now = LocalDate.now();
		PowerUnit plant1 = new PowerUnit(EnergyType.PHOTOVOLTAIKANLAGEN, 500, now, "EON", 100,
				new Place("Berlin", "PotsdammerStraße", "12", "04321", "Deuschland"), 6);

		PowerUnit plant2 = new PowerUnit(EnergyType.BLOCKHEIZKRAFTWERK, 22, now, "Evita", 55,
				new Place("Hamburg", "HafenStaße", "3a", "55421", "Deuschland"), 2);

		PowerUnit plant3 = new PowerUnit(EnergyType.WINDRAD, 521, now, "Nexus", 231,
				new Place("Köln", "Dachauerweg", "A", "222", "Deuschland"), 18);

		plant.getUnits().add(plant1);
		plant.getUnits().add(plant2);
		plant.getUnits().add(plant3);

		PowerPlant plant22 = new PowerPlant(new ArrayList<>());

		PowerUnit plant5 = new PowerUnit(EnergyType.PHOTOVOLTAIKANLAGEN, 73, now, "1-2-3 Energie", 100,
				new Place("Bremen", "UbootStraße", "33", "01222", "Deutschland"), 12);

		PowerUnit plant6 = new PowerUnit(EnergyType.BLOCKHEIZKRAFTWERK, 61, now, "FlexGas", 33,
				new Place("Hamburg", "Kaiserring", "3", "55412", "Deutschland"), 4);

		PowerUnit plant7 = new PowerUnit(EnergyType.WINDRAD, 105, now, "Eprimo", 250,
				new Place("Gnarrenburg", "Feldweg", "1", "01233", "Deutschland"), 15);

		plant22.getUnits().add(plant5);
		plant22.getUnits().add(plant6);
		plant22.getUnits().add(plant7);
		em.persist(plant22);
		em.persist(plant);
		return plant;
	}

	public void removeProfessor(int id) {
		PowerUnit emp = findProfessor(id);
		if (emp != null) {
			em.remove(emp);
		}
	}

	public PowerUnit findProfessor(int id) {
		return em.find(PowerUnit.class, id);
	}

	public Collection<PowerUnit> findAllPowerUnits() {
		Query query = em.createQuery("SELECT e FROM PowerUnit e");
		return (Collection<PowerUnit>) query.getResultList();
	}

	public Collection<PowerPlant> findAllPowerPlants() {
		Query query = em.createQuery("SELECT e FROM PowerPlant e");
		System.err.println(query.getResultList());
		for (PowerPlant p : (Collection<PowerPlant>) query.getResultList())
			System.err.println(p);
		System.err.println(query.getResultList().size());
		return (Collection<PowerPlant>) query.getResultList();
	}
}
