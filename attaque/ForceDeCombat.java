package attaque;

public abstract class ForceDeCombat {

	private int pointDeDegat;
	private String nom;
	protected boolean operationnel = true;
	
	/**
	 * 
	 * @param pointDeDegat : point de degats cause par une attaque
	 * @param nom : nom de la force d'attaque
	 */
	public ForceDeCombat(int pointDeDegat, String nom) {
		this.pointDeDegat = pointDeDegat;
		this.nom = nom;
	}

	/**
	 * 
	 * @return : le nombre de point de degats
	 */
	public int getPointDeDegat() {
		return pointDeDegat;
	}

	/**
	 * 
	 * @return : le nom de la force d'attque
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * 
	 * @return : indique si l'attaque est operationnelle
	 */
	public boolean isOperationnel() {
		return operationnel;
	}

	
	
	@Override
	public String toString() {
		return "ForceDeCombat [ nom=" + nom + ", pointDeDegat=" + pointDeDegat + "]\n";
	}

	/**
	 * 
	 * @return : le nombre de point de degats cause par l'attaque si elle est operationnelle
	 *           sinon retourne 0
	 */
	public int utiliser() {
		if(isOperationnel())
			return pointDeDegat;
		else
			return 0;
	}
	
}
