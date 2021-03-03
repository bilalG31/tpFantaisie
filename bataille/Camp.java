package bataille;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import protagoniste.EtreVivant;

public class Camp <E extends EtreVivant> implements Iterable<E>{
	
	private List<E> liste; //liste d'etre vivants (homme ou monstre)
	
	public Camp() {
		liste = new LinkedList<>();
	}
	/**
	 * 
	 * @param etreVivant : homme ou monstre a ajouter s'il existe pas deja dans la liste
	 */
	public void ajouter(E etreVivant) {
		if (!liste.contains(etreVivant))
			liste.add(etreVivant);
	}

	/**
	 * 
	 * @param etreVivant : homme ou monstre a supprimer de la liste (s'il existe)
	 */
	public void eliminer(E etreVivant) {
		if(liste.contains(etreVivant))
			liste.remove(etreVivant);
	}
	
	public String toString() {
		return liste.toString();
	}

	@Override
	public Iterator<E> iterator() {
		return liste.iterator();
	}

	public int nbCombattants() {
		  return liste.size();
		}

	public E selectionner() {
		Random randomGenerateur = new Random();
		int numeroCombattant = randomGenerateur.nextInt(liste.size());
		return liste.get(numeroCombattant);
		}

	
	/*
	 * public boolean equals(Object obj) {
		if(obj instanceof Camp) {
			int i = 0;
			for(Iterator<?> iter = iterator(); iter.hasNext();) {
				if(!liste.get(i).equals(iter.next())) return false;
				i++;
			}
			return true;
		}
		return false;
	}
	 */
}
