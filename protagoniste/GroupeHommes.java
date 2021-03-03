package protagoniste;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;

import attaque.Arme;
import attaque.Pouvoir;
import bataille.Bataille;

public class GroupeHommes {
	private TreeSet<Homme> groupe = new TreeSet<>();
	
	/**
	 * 
	 * @param hommes : liste des hommes a ajouter au groupe d'hommes 
	 */
	public void ajouterHommes(Homme...hommes) {
		for(Homme h : hommes)
			groupe.add(h);
	}
	
	
	/*private class ComparateurHommes implements Comparator<Homme>{
		@Override
		public int compare(Homme homme1, Homme homme2) {
			Integer a = homme1.getForceDeVie();
			Integer b = homme2.getForceDeVie();
			if(homme1.getForceDeVie() == homme2.getForceDeVie())
				return homme1.getNom().compareTo(homme2.getNom());
			return b.compareTo(a);
		}	
	}*/
	
	
	private class ComparateurArmes implements Comparator<Arme>{
		Monstre<? extends Pouvoir> monstre;
		public ComparateurArmes(Monstre<?> monstre) {
			this.monstre = monstre;
		}

		@Override
		public int compare(Arme arme1, Arme arme2) {
			int forcedevie = monstre.getForceDeVie();
			if(arme1 == null)
				return 0;
			int pdd1 = arme1.getPointDeDegat();
			int pdd2 = arme2.getPointDeDegat();
			if(pdd1 != pdd2) {
				Map<Integer,Arme> classementForce = new TreeMap<>();
				classementForce.put(pdd1, arme1);
				classementForce.put(pdd2, arme2);
				NavigableSet<Integer> meilleurPointDegat =(NavigableSet<Integer>) classementForce.keySet();
				Integer degat = meilleurPointDegat.floor(forcedevie);
				if(degat==null)
					return arme1.compareTo(arme2);
				else {
					if(degat == arme1.getPointDeDegat())
						return -1;
					else 
						return 1;
				}
			}else
				return arme1.compareTo(arme2);
		}
	}

	/**
	 * 
	 * @param bataille : bataille dans laquelle les hommes entreront
	 * @return : la liste des hommes allant combattre
	 */
	public List<Homme> choixParticipants(Bataille bataille) {
		List<Homme> listHommes= new ArrayList<>();
		Monstre<? extends Pouvoir> monstre = bataille.getCampMonstres().selectionner();
		Map<Arme,TreeSet<Homme>>hommesArmes = new TreeMap<>(new ComparateurArmes(monstre));
		for(Homme homme:groupe) {
			// TODO A VERIFIER 
			TreeSet<Homme> ensembleHomme=new TreeSet<Homme>(/*new ComparateurHommes()*/
					(homme1, homme2) -> (homme1.getForceDeVie() == homme2.getForceDeVie()) 
					? homme1.compareTo(homme2) : (homme2.getForceDeVie() - homme1.getForceDeVie())
					);
			if(hommesArmes.containsKey(homme.choisirArme(monstre))){
				ensembleHomme=hommesArmes.get(homme.choisirArme(monstre));
				ensembleHomme.add(homme);
			}else {
				ensembleHomme.add(homme);
				hommesArmes.put(homme.choisirArme(monstre),ensembleHomme);
			}		
		}
		for(Arme arme : hommesArmes.keySet()) {
			TreeSet<Homme> ensembleHomme = hommesArmes.get(arme);
			for(Homme homme:ensembleHomme) {
				if (listHommes.size() < 3) 
					listHommes.add(homme);	
				}
			}
		return listHommes;
	}
	
}
