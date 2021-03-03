package attaque;

public abstract class Pouvoir extends ForceDeCombat {

	private int nbUtilisationPouvoir;
	private int nbUtilisationPouvoirInitial;
	
	/**
	 * 
	 * @param pointDeDegat : nb de point de degat cause par le pouvoir
	 * @param nom : nom du pouvoir
	 * @param nbUtilisationPouvoir : nb utilisation possible du pouvoir
	 */
	public Pouvoir(int pointDeDegat, String nom, int nbUtilisationPouvoir) {
		super(pointDeDegat, nom);
		this.nbUtilisationPouvoir = nbUtilisationPouvoir;
	}

	/**
	 * Pouvoir de nouveau operationnel, il peut etre utilise autant
	 * de fois qu'a  l'initialisation 
	 */
	public void regenererPouvoir() {
		this.operationnel = true;
		nbUtilisationPouvoir = nbUtilisationPouvoirInitial;
	}
	
	/**
	 * decremente le nb d'utilisation du pouvoir. S'il est = 0
	 * le pouvoir n'est plus operationnel. Retourne le nb de degat
	 */
	public int utiliser() {
		if(isOperationnel()) {
			nbUtilisationPouvoir--;
			if(nbUtilisationPouvoir < 1)
				operationnel = false;
		}
		return super.utiliser();
	}
}

