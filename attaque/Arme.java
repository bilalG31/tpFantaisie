package attaque;

import java.util.HashSet;
import java.util.Set;

import protagoniste.ZoneDeCombat;

public abstract class Arme extends ForceDeCombat implements Orderable<Arme> /*Comparable<Arme>*/ {
	
	private Set<ZoneDeCombat> zoneDeCombat = new HashSet<>();
	
	public Arme(int pointDeDegat, String nom, ZoneDeCombat...zoneDeCombat) {
		super(pointDeDegat, nom);
		for(ZoneDeCombat z : zoneDeCombat)
			this.zoneDeCombat.add(z);
	}
	
	public Set<ZoneDeCombat> getZoneDeCombat() {
		return zoneDeCombat;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Arme) {
			Arme armes = (Arme) obj;
			return (this.getNom().equals(armes.getNom()) && this.getPointDeDegat()==armes.getPointDeDegat() && this.isOperationnel() == armes.isOperationnel());
		}
		return false;
	}
	
	@Override
	public int compareTo(Arme armeToCompare) {
		Integer pdd = this.getPointDeDegat();
		Integer pddArmeToCompare = armeToCompare.getPointDeDegat();
		
		if(this.isOperationnel() && armeToCompare.isOperationnel()) {
			if(this.getPointDeDegat() != armeToCompare.getPointDeDegat())
				return pdd.compareTo(pddArmeToCompare);
			else  
				return this.getNom().compareTo(armeToCompare.getNom());
		}else if(this.isOperationnel()) {
			return 1;
		}
		else return -1;
	}


}
