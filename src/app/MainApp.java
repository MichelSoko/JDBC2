package app;

import java.util.List;

import dao.*;
import dao.exception.*;
import bo.*;

public class MainApp {

	public static void main(String[] args) {
		try {
			
			/*
			 * 
			 * 
			 * 
			 * REGLER problème sur PersonDAO.getAll()
			 * 
			 * 
			 * REGLER problème pour générer entre 6 et 7 avec 50% de chance pour chaque
			 * 
			 * 
			 */
			
			//populate();
			display();
		} catch (CityDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PersonDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void populate() throws CityDAOException, PersonDAOException {
		CityDAO cityDAO = new CityDAO();
		PersonDAO personDAO = new PersonDAO();
		CityBO cityBO = null;
		PersonBO personBO = null;

		// Construire 10 villes
		for (int i = 1; i < 11; i++) {
			String name = "ville" + i;
			String mayor = "maire" + i;
			int inhabitants = (int) (100000 + Math.random() * 900000);
			String postalcode = "" + (int) (10000 + Math.random() * 89999);
			cityBO = new CityBO(name, mayor, inhabitants, postalcode);

			cityDAO.add(cityBO); // Insertion en BDD
			cityBO = cityDAO.getByName(name); // Récupérer l'ID pour l'insertion des personnes

			// Construire 2 personnes
			for (int j = 1; j < 3; j++) {
				String firstname = name + "prénom" + j;
				String lastname = name + "nom" + j;
				String emails = "prénom" + j + ".nom" + j + "@" + name + ".fr";
				String phone = "0" + (int) (6 + Math.random()) + (int) (10000000 + Math.random() * 90000000);
				personBO = new PersonBO(cityBO, firstname, lastname, emails, phone);

				personDAO.add(personBO); // Insertion en BDD
			}
		}
	}

	public static void display() throws CityDAOException, PersonDAOException {
		CityDAO cityDAO = new CityDAO();
		PersonDAO personDAO = new PersonDAO();
		List<CityBO> listCityBO = cityDAO.getAll();

		if (listCityBO.isEmpty() || listCityBO == null) {
			System.out.println("Aucune ville dans la base de données !");
		} else {
			for (CityBO cityBO : listCityBO) {
				cityBO.toString();
				List<PersonBO> listPersonBO = personDAO.getAll();
				for (PersonBO personBO : listPersonBO) {
					personBO.toString();
				}
			}
		}
	}

}
