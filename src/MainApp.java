import java.io.IOException;
import club.manager.ConsoleManager;
import club.manager.FileManager;

public class MainApp {

	private static void menu() throws IOException, ClassNotFoundException {
		FileManager file = new FileManager();
		System.out.println("Que voulez-vous faire ?");
		System.out.println("  1 - Lister les Clubs");
		System.out.println("  2 - Lister les Membres d'un club");
		System.out.println("  3 - Ajouter des membres dans un club");
		System.out.println("  4 - Supprimer des membres d'un club");
		System.out.println("");
		int cpt1 = ConsoleManager.getInstance().readUserInputInteger();
		if (cpt1 == 1) {
			file.readClubs(0);
			menu();
		} else if (cpt1 == 2) {
			System.out.println("Quel Club choisissez-vous ?");
			System.out.println("(Donnez un id de Club)");
			ConsoleManager.getInstance().printLine();
			
			int idClub = ConsoleManager.getInstance().readUserInputInteger();
			
			file.readMembers(0,idClub);
			menu();
		} else if (cpt1 == 3) {
			file.writeMember(0);
			menu();
		} else if (cpt1 == 4) {

		} else {

		}
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		FileManager file = new FileManager();
		file.startData(0);
		menu();
	}
}
