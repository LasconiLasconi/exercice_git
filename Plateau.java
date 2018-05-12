/* Cette classe est utilisee pour definir le plateau de jeu
	methode pour l'afficher (pour simplifier, l'affichage est un String, 
		pour un affichage correct, limite a 20 colonnes,
	methode pour retirer ou recupere les jetons restants en jeu
	methode pour obtenir ou mettre un string dans une case,
	methode pour changer le joueur,
	methodes pour calculer s'il y a un gain ou pas (4 jetons identiques alignes)
*/


public class Plateau {
	private String[][] plateau;
	private int size;
	private int jetons;

	public Plateau (int size) {
		// En fonction de la taille indique, le tableau de String est initialise, le nombre de jetons calcule
		this.size = size;
		this.jetons = this.size*this.size;
		this.plateau = new String[size][size];
		for (int i = 0; i < size ; i++) {
			for (int j = 0; j < size ; j++) {
				plateau[i][j]= " ";
			}
		}
	}

	public void jetonsMoins() {
		this.jetons--;
	}
	public int getJetons() {
		return this.jetons;
	}

	public String toString() {
		// pour l'affichage il est considere que le tableau est inferieur a 20 colonnes

		String resultat = "";
		String deplacement = "";
		for (int ligne = 0 ; ligne < plateau.length ; ligne++) {
			for (int colonne = 0 ; colonne < plateau[0].length ; colonne++) {
				resultat = resultat + "!" + plateau[ligne][colonne];
			}
			resultat = resultat + "!" + "\n";
		}
		resultat = resultat + "!";
		for (int colonne = 0 ; colonne < plateau[0].length ; colonne++){
			resultat = resultat + "=" + "!";
		}
		resultat = resultat + "\n" + "!";
		for (int colonne = 0 ; colonne < plateau[0].length ; colonne++) {
			if (colonne < 9) {
				resultat= resultat + (colonne+1) + "!";
				deplacement = deplacement + "  ";
			} else if (colonne < 19) {
				resultat = resultat + 1 + "!";
			}
		}
		if (plateau[0].length > 9) {
			resultat = resultat + "\n";
			resultat = resultat + deplacement + "!";
			for (int colonne = 9 ; colonne < plateau[0].length ; colonne++) {
				resultat = resultat + (colonne +1 -10) +"!";
			}
		}
		return resultat;
	}

	public String getCase(int ligne, int colonne) {
		return plateau[ligne][colonne];
	}

	public void setCase(int ligne, int colonne, String symbole) {
		this.plateau[ligne][colonne] = symbole;
	}

	public boolean colonnePleine(int colonne) {
		if (plateau[0][colonne].equals(" ")) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public int ligneJeton(int colonne, String logo) {
		// avant d'utiliser cette methode, il faut s'assurer que la colonne n'est pas pleine
		// car il doit être trouve case avec " "
		String vide = "";
		int ligne = plateau.length ;
		while (!(vide.equals(" "))) {
			ligne--;
			vide = getCase(ligne, colonne);
		}
		setCase(ligne, colonne, logo);
		return ligne;
	}

	private int calculAli(int ligne, int colonne, String logo, int dligne, int dcolonne) {
		// methode de "base" pour determiner s'il y a des String d'aligne
		while (colonne >= 0 && colonne < plateau[0].length && ligne >= 0 && ligne < plateau.length){
			if((getCase(ligne, colonne)).equals(logo)) {
				return (1 + calculAli(ligne+dligne, colonne+dcolonne, logo, dligne, dcolonne));
			}else {
				return 0;
			}
		}
		return 0;
	}
	private int calculHorizontal(int ligne, int colonne, String logo) {
		int horizontal = (calculAli(ligne, colonne, logo, 0, -1)
					 + calculAli(ligne, colonne, logo, 0, 1));
		return horizontal;
	}
	private int calculVertical(int ligne, int colonne, String logo) {
		int vertical = calculAli(ligne, colonne, logo, 1, 0);
		return vertical;
	}
	private int diagonaldroite (int ligne, int colonne, String logo) {
		int diagonal = calculAli(ligne, colonne, logo, -1,1) + calculAli(ligne, colonne, logo, 1, -1);
		return diagonal;
	}

	private int diagonalgauche (int ligne, int colonne, String logo) {
		int diagonal = calculAli(ligne, colonne, logo, -1,-1) + calculAli(ligne, colonne, logo, 1, 1);
		return diagonal;
	}
	public boolean gain(int ligne, int colonne, String logo) {
		if (diagonalgauche(ligne, colonne, logo) > 4 || diagonaldroite(ligne, colonne, logo) >4 || calculHorizontal(ligne, colonne, logo) >4 || calculVertical(ligne, colonne, logo) > 4) {
			return true;
		}
		return false;
	}

	public int changeJoueur(int nombre) {
		return ((nombre + 1) % 2);
	}
	public static void main (String[] args) {

		Plateau plateauDeJeu= new Plateau(10);
		System.out.println(plateauDeJeu);
	}
}
