package attaque;

public class Arc extends Arme {
	
	private int nbFlechesRestantes;
	
	public Arc(int nbFlechesRestantes) {
		super(50, "Arc");
		this.nbFlechesRestantes = nbFlechesRestantes;
	}
	
	/**
	 * Decremente le nb de fleches. S'il y en a plus
	 * l'arme n'est plus operationnelle. Retourne le nb de degat
	 */
	public int utiliser() {
		if (isOperationnel()) {
			nbFlechesRestantes--;
			if (nbFlechesRestantes < 1)
				operationnel = false;
		}
		return super.utiliser();
	}
}
