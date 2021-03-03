package livre;

public interface Livre {
	
	/* JAVA 7 */
	 /* public void ecrire(String message); */
	
	/* java 8 */
	public default void ecrire(String message) {
		System.out.println(message);
	}
	
	
}
