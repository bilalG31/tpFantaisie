package testsfonctionnels;

import attaque.*;
import protagoniste.*;

public class TestGestionAttaque {
	
 public static void main(String[] args) {
	
	Monstre<Feu> dragotenebre = new Monstre<>("dragotenebre", 200, ZoneDeCombat.AERIEN, Domaine.FEU, new Lave(100), new Eclair(100), new BouleDeFeu(100));
	System.out.println(dragotenebre.toString());

	dragotenebre.entrerEnCombat();
		
	System.out.println(dragotenebre.attaque());
	System.out.println(dragotenebre.attaque());
	System.out.println(dragotenebre.attaque());
		
//		RESULTAT ATTENDU
//		Monstre [getNom()=dragotenebre, attaques=[ForceDeCombat [nom=une boule de feu, pointDeDegat=20], ForceDeCombat [nom=un jet de lave, pointDeDegat=80], ForceDeCombat [nom=un �clair, pointDeDegat=50]], zoneDeCombat=AERIEN, getForceDeVie()=200]
//		ForceDeCombat [nom=un �clair, pointDeDegat=50]
//		ForceDeCombat [nom=une boule de feu, pointDeDegat=20]
//		ForceDeCombat [nom=un jet de lave, pointDeDegat=80]
 }
 
}
