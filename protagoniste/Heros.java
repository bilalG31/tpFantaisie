package protagoniste;

public class Heros extends Homme {

	/**
	 * 
	 * @param nom : nom de l'heros
	 * chaque heros a une forceDeVie de 100 points
	 */
	public Heros(String nom) {
		super(nom,100);
	}

	@Override
	public String toString() {
		return "Heros [" + super.toString() + "]";
	}
	
	
}
