package livre;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Fichier implements Livre{
	
	/**
	 * ecrire le texte passe en parametre d'entree dans le fichier
	 * histoire.txt
	 */
	@Override
	public void ecrire(String texte) {
		try {
			String chemin = "./src/livre/histoire.txt";
			File writer = new File(chemin);
			FileWriter fichier = null;
			try {
				fichier = new FileWriter(writer, true);
				fichier.write(texte);
			}finally {
				fichier.close();
			}
		}
		 catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
