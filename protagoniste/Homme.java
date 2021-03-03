package protagoniste;

import java.util.Comparator;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

import attaque.Arme;
import attaque.Pouvoir;
import bataille.Bataille;

public class Homme extends EtreVivant {

	private Map<ZoneDeCombat,List<Arme>> armes = new EnumMap<>(ZoneDeCombat.class);
	
	private Arme armeChoisie;
	
	/**
	 * 
	 * @param nom : nom de l'humain 
	 * chaque humain a une forceDeVie de 70 points
	 */
	public Homme(String nom) {
		super(nom, 70);
	}
	
	public Homme(String nom, int forceDeVie) {
		super(nom,forceDeVie);
	}
	
	/**
	 * 
	 * @return : l'arme choisie
	 */
	public Arme getArmeChoisie() {
		return armeChoisie;
	}
	
	/**
	 * Dans la bataille b on ajoute l'homme actuel a cette bataille
	 * ensuite on affecter b a l'attribut bataille
	 */
	public void rejointBataille(Bataille b) {
		b.ajouter(this);
		super.rejointBataille(b); //bataille = b;
	}

	/**
	 * mort d'un homme => l'elimination de cet homme de la bataille 
	 */
	@Override
	public void mourir() {
		bataille.eliminer(this);
	}

	@Override
	public String toString() {
		return "Homme [ " + super.toString() + "]";
	}

	/**
	 * 
	 * @param armes : les armes a ajouter dans les listes ou elles sont efficaces 
	 */
	public void ajouterArmes(Arme...armesAajouter) {
		for(Arme arme : armesAajouter) {
			//pr chaq arme recuperer les zones de combat ou elle est efficace
			for(ZoneDeCombat zdc : arme.getZoneDeCombat()) {
				//pr chaque zone de combat ajouter dans sa liste d'armes l'arme qui est efficace 
				List<Arme> listeArmes = armes.get(zdc);
				listeArmes.add(arme);
				armes.put(zdc,listeArmes);
			}
		
		}
	}
	
	/**
	 * 
	 * @param arme : l'arme a supprimer
	 */
	public void supprimerArme(Arme arme) {
		//pr chaq zone de combat recuperer sa liste d'armes et verifier si l'arme a supprime
		//est contenu dedans si c le cas la supprimer et modifier la liste d'arme associe a cette 
		// zone dans la Map armes
		List<Arme> listeArmes;
		for(ZoneDeCombat zdc : armes.keySet()) {
			listeArmes = armes.get(zdc); //recuperer les armes dans zdc
			if(listeArmes.contains(arme)) {
				listeArmes.remove(arme);
				armes.put(zdc, listeArmes);
			}		
		}
	}
	
	public Arme choisirArme(Monstre<? extends Pouvoir> monstre) {
		ZoneDeCombat zdc = monstre.getZoneDeCombat();
		if(armes.containsKey(zdc)) {
			List<Arme> listeArmes = armes.get(zdc);
			int i=0;
			//retirer les armes non operationnelles
			while(i<listeArmes.size()) {
				if(!listeArmes.get(i).isOperationnel())
					listeArmes.remove(i);
				else
					i++;
			}
			if(listeArmes.isEmpty())
				return null;
			else {
				NavigableSet<Arme> armesTriees = new TreeSet<>( new Comparator<Arme>() {
					public int compare(Arme arme1, Arme arme2) {
						if(arme1.isOperationnel() && arme2.isOperationnel()) {
							if(arme1.getPointDeDegat() != arme2.getPointDeDegat()) {
								Integer pointDeDegat1 = arme1.getPointDeDegat();
								Integer pointDeDegat2 = arme2.getPointDeDegat();
								return pointDeDegat2.compareTo(pointDeDegat1);
							}
						}
						return arme1.getNom().compareTo(arme2.getNom());
					}
				});
				armesTriees.addAll(listeArmes);
				if(armesTriees.isEmpty())
					return null;
				int forceDeVieMonstre = monstre.getForceDeVie();
				NavigableSet<Arme> armesAdaptees = armesTriees.tailSet(new KeyArme(forceDeVieMonstre, ""), true);
				if(!armesAdaptees.isEmpty()) 
					armeChoisie = armesAdaptees.first();
				else
					armeChoisie = armesAdaptees.last();
				return armeChoisie;
			}
		}	
		return null ;
	}
	
}
