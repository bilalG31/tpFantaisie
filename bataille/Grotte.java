package bataille;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import attaque.Pouvoir;
import protagoniste.Monstre;
import protagoniste.ZoneDeCombat;
import protagoniste.ZoneDeCombatNonCompatibleException;

public class Grotte {
	
	
	private Map<Salle,List<Salle>> planGrotte = new LinkedHashMap<>();
	private Map<Salle,Bataille> batailles = new HashMap<>();
	private Set<Salle> sallesExplorees = new HashSet<>();
	private int numeroSalleDecisive;
	
	

	public void ajouterSalle(ZoneDeCombat zoneDeCombat, Monstre<? extends Pouvoir>...monstresGardiens) throws ZoneDeCombatNonCompatibleException {
			int numSalle = planGrotte.size()+1;
			Salle salle = new Salle(numSalle,zoneDeCombat);
			Bataille bataille = new Bataille();
			for(Monstre<? extends Pouvoir> m : monstresGardiens) {
				if(m.getZoneDeCombat() != zoneDeCombat) {
					throw new ZoneDeCombatNonCompatibleException(zoneDeCombat, m.getZoneDeCombat());
				}else
				bataille.ajouter(m);
			}
			planGrotte.put(salle,new ArrayList<>());
			batailles.put(salle, bataille);
		
	}
	
	public void setNumeroSalleDecisive(int numeroSalleDecisive) {
		this.numeroSalleDecisive = numeroSalleDecisive;
	}
	
	public String afficherPlanGrotte() {
		  StringBuilder affichage = new StringBuilder();
		  for (Map.Entry<Salle, List<Salle>> entry : planGrotte.entrySet()) {
		   Salle salle = entry.getKey();
		   List<Salle> acces = planGrotte.get(salle);
		   affichage.append("La " + salle + ".\nelle possede " + acces.size() + " acces : ");
		   for (Salle access : acces) {
		    affichage.append(" vers la salle " + access);
		   }
		   Bataille bataille = batailles.get(salle);
		   Camp<Monstre<? extends Pouvoir>> camp = bataille.getCampMonstres();
		   Monstre<? extends Pouvoir> monstre = camp.selectionner();
		   if (camp.nbCombattants() > 1) {
		    affichage.append("\n" + camp.nbCombattants() + " monstres de type ");
		   } else {
		    affichage.append("\nUn monstre de type ");
		   }
		   affichage.append(monstre.getNom() + " la protege.\n");
		   if (salle.getNumSalle() == numeroSalleDecisive) {
		    affichage.append("C'est dans cette salle que se trouve la pierre de sang.\n");
		   }
		   affichage.append("\n");
		  }
		  return affichage.toString();
		}
	
	/**
	 * methode qui permet de retrouver une salle dans planGrotte a partir de son numero
	 */
	public Salle trouverSalle(int numeroSalle) { 
		for(Salle salle:planGrotte.keySet()) {
			if (salle.getNumSalle()==numeroSalle){
				return salle; 
			} 
		} 
		return null; 
	}
	
	public void configurerAcces(int numeroSalle, int...numSallesAccessibles) {
		Salle salleOrigine = trouverSalle(numeroSalle);
		List<Salle> sallesAccessibles = new ArrayList<>();
		for(int i=0; i<numSallesAccessibles.length; i++) {
			sallesAccessibles.add(trouverSalle(numSallesAccessibles[i]));
		}
		planGrotte.put(salleOrigine, sallesAccessibles);
	}
	
	/**
	 * 
	 * @param salle : salle a verifier si elle contient la pierre de sang
	 * @return : true si c'est le cas, false sinon
	 */
	public boolean salleDecisive(Salle salle) {
		return (salle.getNumSalle()==numeroSalleDecisive);
		
	}
	
	/**
	 * 
	 * @return : la premiere salle de la grotte
	 */
	public Salle premiereSalle() {
		Salle sallePremiere = trouverSalle(1);
		sallesExplorees.add(sallePremiere);
		return sallePremiere;
	}
	//TODO
	/* NUMERO SALLE SUIVANTE DOIT ETRE > A 0*/
	public Salle salleSuivante(Salle salle) {
		List<Salle> sallesAccessibles = planGrotte.get(salle);
		sallesAccessibles.removeAll(sallesExplorees);
		if(sallesAccessibles.isEmpty())
			sallesAccessibles = planGrotte.get(salle);
		Random r = new Random();
		int numeroSalleSuivante = r.nextInt(sallesAccessibles.size());
		Salle salleSuivante = sallesAccessibles.get(numeroSalleSuivante);
		sallesExplorees.add(salleSuivante);
		return salleSuivante; 
	}
}
