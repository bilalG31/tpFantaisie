package protagoniste;

public class ZoneDeCombatNonCompatibleException extends Exception {

	public ZoneDeCombatNonCompatibleException(ZoneDeCombat zdcSalle, ZoneDeCombat zdcMonstre) {
		System.out.println("La zone de combat de la salle est de type " + zdcSalle + ", alors que celle du monstre est " + zdcMonstre);
	}
	
}
