package protagoniste;

import bataille.Bataille;

public abstract class EtreVivant implements Comparable<EtreVivant> {

	private String nom;
	private int forceDeVie;
	protected Bataille bataille;
	
	/**
	 * 
	 * @param nom : nom de l'etre vivant
	 * @param forceDeVie : sa force de vie
	 */
	public EtreVivant(String nom, int forceDeVie) {
		this.nom = nom;
		this.forceDeVie = forceDeVie;
	}
	
	public int getForceDeVie() {
		return forceDeVie;
	}
	
	public String getNom() {
		return nom;
	}

	@Override
	public String toString() {
		return "EtreVivant [nom=" + nom + ", forceDeVie=" + forceDeVie + "]\n";
	}
	
	/**
	 * 
	 * @param b : la bataille a rejoindre 
	 */
	public void rejointBataille(Bataille b) {
		bataille = b;
	}
	
	/**
	 * methode a implementer dans les classes filles Homme et Monstre
	 */
	public abstract void mourir();
	
	/**
	 * 2 etre vivants sont considere identique ssi ils ont le meme nom
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof EtreVivant) {
			EtreVivant e = (EtreVivant) obj;
			return getNom().equals(e.getNom());
		}
		return false;
	}
	
	/**
	 * @return : -1 si this < other, 0 si this = other, +1 si this > other
	 * compareTo existe deja et fait cela
	 */
	@Override
	public int compareTo(EtreVivant other) {
		/*if(this.equals(other))
			return 0;
		else {
			if(this.getNom().length() < other.getNom().length())
				return -1;
			else
				return 1;
		}*/
		return this.nom.compareTo(other.getNom());
	}
}
