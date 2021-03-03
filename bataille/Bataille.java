package bataille;

import attaque.Pouvoir;
import protagoniste.Homme;
import protagoniste.Monstre;

public class Bataille {

	private Camp<Homme> campHumains = new Camp<>();
	private Camp<Monstre<? extends Pouvoir>> campMonstres = new Camp<>();
	
	/**
	 * 
	 * @param homme : l'humain a ajouter dans la bataille
	 */
	public void ajouter (Homme homme) {
		campHumains.ajouter(homme);
	}
	
	/**
	 * 
	 * @param monstre : le monstre a ajouter dans la bataille
	 */
	public void ajouter (Monstre<? extends Pouvoir> monstre) {
		campMonstres.ajouter(monstre);
	}

	/**
	 * 
	 * @param homme : l'humain a eliminer de la bataille
	 */
	public void eliminer (Homme homme) {
		campHumains.eliminer(homme);
	}
	
	/**
	 * 
	 * @param monstre : le monstre a eliminer de la bataille
	 */
	public void eliminer (Monstre<? extends Pouvoir> monstre) {
		campMonstres.eliminer(monstre);
	}
	
	/**
	 * 
	 * @return : les camps humain de la bataille
	 */
	public Camp<Homme> getCampHumains() {
		return campHumains;
	}
	
	/**
	 * 
	 * @return : les camps monstre de la bataille
	 */
	public Camp<Monstre<? extends Pouvoir>> getCampMonstres() {
		return campMonstres;
	}
	
}
