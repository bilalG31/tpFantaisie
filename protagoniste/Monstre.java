package protagoniste;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import attaque.Pouvoir;
import bataille.Bataille;

public class Monstre <P extends Pouvoir> extends EtreVivant {

	private P[] attaques;
	private ZoneDeCombat zoneDeCombat;
	private Domaine domaine;
	private GestionAttaque gestionAttaque;
	
	/**
	 * 
	 * @param nom : nom du monstre
	 * @param forceDeVie : sa force de vie
	 * @param zoneDeCombat: sa zone de combat
	 * @param domaine : domaine de combat
	 * @param attaques : les attaques que le monstre peut produire
	 */
	@SafeVarargs
	public Monstre(String nom, int forceDeVie, ZoneDeCombat zoneDeCombat, Domaine domaine, P... attaques) {
		super(nom, forceDeVie);
		this.zoneDeCombat = zoneDeCombat;
		this.domaine = domaine;
		this.attaques = attaques;
	}
	
	/**
	 * 
	 * @return : zone de combat du monstre
	 */
	public ZoneDeCombat getZoneDeCombat() {
		return zoneDeCombat;
	}
	
	/**
	 * 
	 * @return : le domaine de combat du monstre 
	 */
	public Domaine getDomaine() {
		return domaine;
	}

	@Override
	public String toString() {
		return "Monstre [getNom()="+ getNom() + ", attaques="
				+ Arrays.toString(attaques)
				+"Zone de combat="+zoneDeCombat
				+", getForceDeVie()="+getForceDeVie()+"]\n";
	}
	
	/**
	 * 
	 * classe interne pour la gestion des attaques des monstres
	 *
	 */
	public class GestionAttaque implements Iterator<P>{

		private P[] attaquesPossibles;
		private int nbAttaquesPossibles; 
		
		/**
		 * 
		 * @param attaques : liste d'attaques d'un monstre 
		 */
		private GestionAttaque (P[] attaques){
			attaquesPossibles = attaques;
			nbAttaquesPossibles = attaques.length;
		}

		@Override
		public boolean hasNext() {
				for(int i=1; i<nbAttaquesPossibles; i++){
					if(!attaquesPossibles[i].isOperationnel()){
						attaquesPossibles[i] = attaquesPossibles[i-1];
						nbAttaquesPossibles--;
					}
				}
				if(nbAttaquesPossibles>0)
					return true;
				return false;
			}

		@Override
		public P next() {
				Random rand = new Random();
				int indiceAleatoire = rand.nextInt(nbAttaquesPossibles-1);
				return attaquesPossibles[indiceAleatoire];
			}	
	}

	/**
	 * regenere tous les pouvoirs contenus dans le tableau attaques,
	 * puis affecte un nouvel obj a  l'attribut gestionAttaque
	 */
	public void entrerEnCombat(){
		for(int i=0; i<attaques.length; i++){
			attaques[i].regenererPouvoir();
		}
		gestionAttaque = new GestionAttaque(attaques);
	}
	
	/**
	 * 
	 * @return : attaque suivante s'il en reste sinon retourne null
	 */
	public Pouvoir attaque(){
		Pouvoir resultat = null;
		if(gestionAttaque.hasNext())
			resultat = gestionAttaque.next();
		return resultat;
		
	}
	
	/**
	 * Dans la bataille b on ajoute le monstre actuel a cette bataille
	 * ensuite on affecter b a l'attribut bataille
	 */
	public void rejointBataille(Bataille b) {
		b.ajouter(this);
		super.rejointBataille(b); //bataille = b;
	}

	/**
	 * mort d'un monstre => l'elimination de ce monstre de la bataille
	 */
	@Override
	public void mourir() {
		bataille.eliminer(this);	
	}
	
}
