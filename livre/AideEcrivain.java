package livre;

import java.util.Iterator;

import java.util.LinkedList;
import java.util.NavigableSet;
import java.util.TreeSet;

import attaque.*;
import bataille.*;
import protagoniste.*;

import java.util.Comparator;

public class AideEcrivain {
	
	private Bataille bataille;
	//liste de monstres trie selon leur domaine puis selon leur ordre naturel 
	private NavigableSet<Monstre<?>> monstresDomaineSet = new TreeSet<>(new CompareDomaineMonstre());
	//liste monstres trie selon leur zone de combat, puis force de vie et enfin ordre naturel
	private NavigableSet<Monstre<?>> monstresZoneSet = new TreeSet<>(new CompareZoneCombat());
	
	private NavigableSet<Monstre<?>> monstresDeFeu = new TreeSet<>();
	private NavigableSet<Monstre<?>> monstresDeGlace = new TreeSet<>();
	private NavigableSet<Monstre<?>> monstresTranchants = new TreeSet<>();
	
	public AideEcrivain(Bataille bataille) {
		this.bataille = bataille;
	}
	
	/**
	 * 
	 * @return : les monstres de domaine feu
	 */
	public NavigableSet<Monstre<?>> getMonstresDeFeu() {
		initMonstresDeFeu();
		return monstresDeFeu;
	}
	
	/**
	 * 
	 * @return : les monstres de domaine glace
	 */
	public NavigableSet<Monstre<?>> getMonstresDeGlace() {
		initMonstresDeGlace();
		return monstresDeGlace;
	}
	
	/**
	 * 
	 * @return : les monstres de domaine tranchant
	 */
	public NavigableSet<Monstre<?>> getMonstresTranchants() {
		initMonstresTranchant();
		return monstresTranchants;
	}
	/**
	 * cette methode ajoute un a un les hommes du camp humain
	 * dans une variable locale listeTriee de type LinkedList :
	 * D'abord les heros dans l'ordre donne dans la liste du camp
	 * puis les hommes dans l'ordre donne dans la liste du camp
	 */
	//Avec iterateur
	public String visualiserForcesHumaines() {
		LinkedList<Homme> listeTriee = new LinkedList<>();
		Camp<Homme> listeCamp = bataille.getCampHumains();
		int lastHeros = 0;
		for ( Iterator<Homme> iter = listeCamp.iterator() ; iter.hasNext() ; ) {
			Homme hACompare = iter.next();
			if ( hACompare instanceof Heros ) {
				listeTriee.add(lastHeros, hACompare);
				lastHeros++;
			}
			else
				listeTriee.add(hACompare);
		}
		return listeTriee.toString();
	}
	/*
	 // ne marche qu'au debut, une fois rentre dans le combat les forceDeVie changent de valeur
	public List<Homme> visualiserForcesHumaines() {
		LinkedList<Homme> listeTriee = new LinkedList();
		Camp<Homme> listeCamp = bataille.getCampHumains();
		int indiceDernierHeros = 0;
		for(Homme humain: listeCamp) {
			if(humain.getForceDeVie()==100) {
				listeTriee.add(indiceDernierHeros, humain);
				indiceDernierHeros++;
			}
			else
				listeTriee.add(humain);
		}
		return listeTriee;
	}*/
	
	/**
	 * methode qui recupere l'iterateur sur les monstres du camp des monstres
	 * et retourne la concatenation des noms de ces monstres tries dans l'ORDRE
	 * ALPHABETIQUE(NavigableSet) et separe par une virgule
	 */
	public String ordreNaturelMonstre() {
		NavigableSet <Monstre<?>> ensemble = new TreeSet<>();
		Camp<Monstre<?>> listeCampMonstres = bataille.getCampMonstres();
		String result = "";
		for(Iterator<Monstre<?>> iter = listeCampMonstres.iterator(); iter.hasNext();) {
			Monstre<?> m = iter.next();
			ensemble.add(m);
		}
		for(Iterator<Monstre<?>> iter = ensemble.iterator(); iter.hasNext();) {
			Monstre<?> m = iter.next();
			result += m.getNom();
			if(iter.hasNext())
				result += ", ";
		}
		return result;
	}
	
	/**
	 * trie selon domaine puis selon ordre naturel
	 *
	 */
	private class CompareDomaineMonstre implements Comparator<Monstre<?>> {
		@Override
		public int compare(Monstre<?> m1, Monstre<?> m2) {
			int result;
			Domaine d1 = m1.getDomaine();
			Domaine d2 = m2.getDomaine();
			if(/*d1 == d2*/d1.equals(d2))
				result = m1.compareTo(m2);
			else
				result = d1.compareTo(d2);
			return result;
		}
		
	}
	
	/**
	 * ajouter un a un les monstres du camp des monstres a l'ensemble monstresDomaineSet
	 */
	public void updateMonstresDomaine() {
		Camp<Monstre<?>> listeCampMonstres = bataille.getCampMonstres();
		for(Iterator<Monstre<?>> iter = listeCampMonstres.iterator(); iter.hasNext();) {
			monstresDomaineSet.add(iter.next());
		}
	}
	
	/**
	 * 
	 * @return : noms des monstres structures comme suit :
	 * le nom du domaine, un saut de ligne, les noms des monstres par ordre alphabetique
	 * separe par une virgule puis le domaine suivant
	 */
	public String ordreMonstreDomaine () {
		String result = "";
		this.updateMonstresDomaine();
		Domaine previousDomaine = null;
		for(Iterator<Monstre<?>> iter = monstresDomaineSet.iterator(); iter.hasNext();) {
			Monstre<?> m = iter.next();
			if(m.getDomaine() != previousDomaine) {
				result += "\n" + m.getDomaine() + " :\n" + m.getNom() + ", ";
				previousDomaine = m.getDomaine();
			}else
				result += m.getNom();
		}
		return result;
	}
	
	/**
	 * trie selon zone de combat, puis selon force de vie et enfin selon ordre naturel
	 */
	//TODO A VERIFIER
	private class CompareZoneCombat implements Comparator<Monstre<?>> {
		@Override
		public int compare(Monstre<?> m1, Monstre<?> m2) {
			ZoneDeCombat z1 = m1.getZoneDeCombat();
			ZoneDeCombat z2 = m2.getZoneDeCombat();
			if(z1.equals(z2)) {
				if(m1.getForceDeVie()==m2.getForceDeVie())
					return m1.compareTo(m2);
				else
					return m2.getForceDeVie()-m1.getForceDeVie();
			}else
				return z1.compareTo(z2);
		}
		
	}
	
	/**
	 * ajout des monstres du campMonstre a l'ensemble monstresZoneSet
	 */
	public void updateMonstresZone () {
		Camp<Monstre<?>> campMonstres = bataille.getCampMonstres();
		for(Iterator<Monstre<?>> iter = campMonstres.iterator(); iter.hasNext();) {
			Monstre<?> m = iter.next();
			monstresZoneSet.add(m);
		}
	}
	

	/**
	 * 
	 * @return : une chaine contenant les noms des monstres structuree comme suit :
	 * nom de la zone de combat, saut de ligne, noms des monstres, :, leurs forces
	 * separes par une virgule puis la zone de combat suivante
	 */
	public String ordreMonstreZone() {
		String result = "";
		this.updateMonstresZone();
		ZoneDeCombat previousZone = null;
		for(Iterator<Monstre<?>> iter = monstresZoneSet.iterator(); iter.hasNext();) {
			Monstre<?> m = iter.next();
			if(m.getZoneDeCombat() != previousZone) {
				result += "\n" + m.getZoneDeCombat() + "\n" + m.getNom() + " : " + m.getForceDeVie();
				previousZone = m.getZoneDeCombat();
			}else
				result +=  ", " + m.getNom() + " : " + m.getForceDeVie() + ", ";
		}
		return result;
	}
	
	/**
	 * @param domaine : le domaine du monstre voulu 
	 * @return : retourne le 1er monstre de l'ensemble monstresDomaineSet dont le domaine est
	 * celui donne en parametre d'entree de la fct
	 */
//	public Monstre<?> firstMonstreDomaine( Domaine domaine) {
//		this.updateMonstresDomaine();
//		for(Iterator<Monstre<?>> iter = monstresDomaineSet.iterator(); iter.hasNext();) {
//			Monstre<?> m = iter.next();
//			if(m.getDomaine().equals(domaine))
//				return m;
//		}
//		return null;
//	}
	
	
	/**
	 * initialise la vue monstresDeFeu
	 */
//	public void initMonstresDeFeu() {
//		updateMonstresDomaine();
//		Monstre<?> firstMonstreFeu = firstMonstreDomaine(Domaine.FEU);
//		Monstre<?> firstMonstreGlace = firstMonstreDomaine(Domaine.GLACE);
//		monstresDeFeu = monstresDomaineSet.subSet(firstMonstreFeu, true, firstMonstreGlace, false);
//	}
	
	//avec utilisation d'un objet degenere
	public void initMonstresDeFeu() {
		updateMonstresDomaine();
		monstresDeFeu = monstresDomaineSet.subSet(new Monstre<>("!", 0, ZoneDeCombat.TERRESTRE, Domaine.FEU, new Morsure(10)),
				true,
				new Monstre<>("~", 0, ZoneDeCombat.TERRESTRE, Domaine.FEU, new Morsure(10)),
				true);
	}
	
	public void initMonstresDeGlace() {
		updateMonstresDomaine();
		monstresDeGlace = monstresDomaineSet.subSet(new Monstre<>("!", 0, ZoneDeCombat.AQUATIQUE, Domaine.GLACE, new PicsDeGlace(2)),
				true,
				new Monstre<>("~",0, ZoneDeCombat.AQUATIQUE, Domaine.GLACE, new PicsDeGlace(2)),
				true);
	}
	
	public void initMonstresTranchant() {
		updateMonstresDomaine();
		monstresTranchants = monstresDomaineSet.subSet(new Monstre<>("!", 0, ZoneDeCombat.TERRESTRE, Domaine.TRANCHANT, new LameAcier(10)),
				true,
				new Monstre<>("~", 0, ZoneDeCombat.TERRESTRE, Domaine.TRANCHANT, new LameAcier(10)),
				true);
	}
}
