package club.manager;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.Member;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import club.classes.Club;
import club.classes.Club_Membre;
import club.classes.Membre;

public class FileManager {

	private String currentPath;
	private StringBuilder sb;

	public FileManager() {
		currentPath = "D:\\Documents\\test";
		this.sb = new StringBuilder();
	}

	/**
	 * Listing folder files
	 * 
	 * @return
	 */
	public List<File> listFiles() {
		File currentFolder = new File(currentPath);

		File[] files = currentFolder.listFiles();

		return files != null ? Arrays.asList(files) : new LinkedList<>();
	}

	/**
	 * Listing folder files
	 * 
	 * @return
	 */
	public void deleteFileFromList(int index) {
		File currentFolder = new File(currentPath);

		currentFolder.listFiles()[index].delete();
	}

	/**
	 * Create a .txt file
	 * 
	 * @param name the name of the file to create (with no extension)
	 * @return
	 */
	public File createTxtFile(String name) {
		File file = new File(currentPath + name + ".txt");

		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return file;
	}

	/**
	 * Move the path to a folder
	 * 
	 * @param index of the folder to move into
	 */
	public void enterFolder(int index) {
		File currentFolder = new File(currentPath);
		List<File> folders = new LinkedList<>();

		for (File file : currentFolder.listFiles()) {
			if (!file.isFile()) {
				folders.add(file);
			}
		}

		currentPath += folders.get(index).getName() + "\\";
	}

	/**
	 * Go back one folder from the current path
	 */
	public void backOneFolder() {
		List<String> paths = Arrays.asList(currentPath.split(Pattern.quote("\\")));

		if (paths.size() > 0) {
			ConsoleManager.getInstance().printToConsole("" + paths.size(), true);
			paths = paths.subList(0, paths.size() - 1);

			currentPath = String.join("\\", paths);
			currentPath += "\\";
		}

		if (currentPath.isEmpty()) {
			currentPath = "\\";
		}
	}

	public void createFolder(String name) {
		File file = new File(currentPath + name);

		file.mkdir();
	}

	/**
	 * Get the current path
	 *
	 * @return
	 */
	public String getCurrentPath() {
		return currentPath;
	}

	public void readFile(int index) throws IOException {
		File currentFolder = new File(currentPath);
		List<File> folders = new LinkedList<>();

		for (File file : currentFolder.listFiles()) {
			folders.add(file);
		}

		String content = "";

		File fileToRead = new File(currentPath + "\\" + folders.get(index).getName());
		System.out.println(fileToRead.getAbsolutePath());
		if (fileToRead.isFile()) {
			try (FileInputStream fis = new FileInputStream(fileToRead)) {
				int data;

				if ((data = fis.read()) < 0) {
					content += "Le fichier est vide !";
				} else {
					while ((data = fis.read()) >= 0) {
						content += (char) data;
					}
				}

				ConsoleManager.getInstance().printLine();
				ConsoleManager.getInstance().printToConsole("Contenu du fichier", true);
				ConsoleManager.getInstance().printLine();

				ConsoleManager.getInstance().printToConsole(content, true);

				ConsoleManager.getInstance().consoleLineBreak();

			} catch (FileNotFoundException ex) {
				Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
			} catch (EOFException exception) {
				// Output expected EOFExceptions.
				// Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null,
				// exception);
			}
		} else {
			ConsoleManager.getInstance().printToConsole("Le fichier choisi n'est pas un fichier", true);
		}

	}

	public List<Club> readClubs(int index) throws IOException, ClassNotFoundException {
		File currentFolder = new File(currentPath);
		List<File> folders = new LinkedList<>();

		for (File file : currentFolder.listFiles()) {
			folders.add(file);
		}

		File fileToRead = new File(currentPath + "\\" + folders.get(index).getName());
		System.out.println(fileToRead.getAbsolutePath());
		List<Club> listClubs = new LinkedList<>();
		if (fileToRead.isFile()) {
			try (FileInputStream fis = new FileInputStream(fileToRead);
					ObjectInputStream ois = new ObjectInputStream(fis)) {
				Object obj;
				ConsoleManager.getInstance().printLine();
				ConsoleManager.getInstance().printToConsole("Listing des Clubs", true);
				ConsoleManager.getInstance().printLine();
				while ((obj = ois.readObject()) != null) {
					if (obj instanceof Club) {
						Club club = (Club) obj;
						System.out.println(club.toString());
						listClubs.add(club);
					}
				}
				ConsoleManager.getInstance().printLine();
				ConsoleManager.getInstance().consoleLineBreak();

			} catch (FileNotFoundException ex) {
				Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
			} catch (EOFException exception) {
				// Output expected EOFExceptions.
				// Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null,
				// exception);
			}
		} else {
			ConsoleManager.getInstance().printToConsole("Le fichier choisi n'est pas un fichier", true);
		}
		return listClubs;
	}

	public List<Membre> readMembers(int index, int idClub) throws IOException, ClassNotFoundException {
		File currentFolder = new File(currentPath);
		List<File> folders = new LinkedList<>();

		for (File file : currentFolder.listFiles()) {
			folders.add(file);
		}
		String content = "";

		File fileToRead = new File(currentPath + "\\" + folders.get(index).getName());
		System.out.println(fileToRead.getAbsolutePath());
		List<Membre> listMembers = new LinkedList<>();
		if (fileToRead.isFile()) {
			try (FileInputStream fis = new FileInputStream(fileToRead);
					ObjectInputStream ois = new ObjectInputStream(fis)) {
				Object obj;
				ConsoleManager.getInstance().printLine();
				ConsoleManager.getInstance().printToConsole("Listing des Membre du Club", true);
				ConsoleManager.getInstance().printLine();
				while ((obj = ois.readObject()) != null) {
					if (obj instanceof Membre) {
						Membre mem = (Membre) obj;
						if (mem.getIdClub() == idClub) {
							System.out.println(mem.toString());

						}
						listMembers.add(mem);
					}
				}

				ConsoleManager.getInstance().printLine();
				ConsoleManager.getInstance().consoleLineBreak();

			} catch (FileNotFoundException ex) {
				Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
			} catch (EOFException exception) {
				// Output expected EOFExceptions.
				// Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null,
				// exception);
			}
		} else {
			ConsoleManager.getInstance().printToConsole("Le fichier choisi n'est pas un fichier", true);
		}
		return listMembers;
	}

	public void writeFile(int index) throws IOException{
		File currentFolder = new File(currentPath);
		List<File> folders = new LinkedList<>();

		for (File file : currentFolder.listFiles()) {
			folders.add(file);
		}

		ConsoleManager.getInstance().printToConsole("What do you want to write ?", true);
		String content = ConsoleManager.getInstance().readUserInput();

		File fileToWrite = new File(currentPath + folders.get(index).getName());

		if (fileToWrite.isFile()) {
			try (FileOutputStream fop = new FileOutputStream(fileToWrite)) {
				byte[] contentInBytes = content.getBytes();

				fop.write(contentInBytes);
				fop.flush();

			} catch (FileNotFoundException ex) {
				Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
			} catch (EOFException exception) {
				// Output expected EOFExceptions.
				// Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null,
				// exception);
			}
		} else {
			ConsoleManager.getInstance().printToConsole("Le fichier choisi n'est pas un fichier", true);
		}
	}

	public void writeMember(int index) throws IOException, ClassNotFoundException  {

		File currentFolder = new File(currentPath);
		List<File> folders = new LinkedList<>();

		for (File file : currentFolder.listFiles()) {
			folders.add(file);
		}

		ConsoleManager.getInstance().printToConsole("Donnez l'id du membre", true);
		int id = ConsoleManager.getInstance().readUserInputInteger();

		ConsoleManager.getInstance().printToConsole("Quel est son nom ?", true);
		String nom = ConsoleManager.getInstance().readUserInput();
		ConsoleManager.getInstance().printToConsole("Quel est son prenom ?", true);
		String prenom = ConsoleManager.getInstance().readUserInput();
		ConsoleManager.getInstance().printToConsole("Quel age a le membre ?", true);
		int age = ConsoleManager.getInstance().readUserInputInteger();
		ConsoleManager.getInstance().printToConsole("Quel est son type de Licence ?", true);
		String licence = ConsoleManager.getInstance().readUserInput();

		ConsoleManager.getInstance().printToConsole("Inscrire le membre dans  ?", true);
		int idClub = ConsoleManager.getInstance().readUserInputInteger();

		Membre m1 = new Membre(id, idClub, nom, prenom, age, licence);

		File fileToWrite = new File(currentPath + "\\" + folders.get(index).getName());
		if (fileToWrite.isFile()) {

			try (FileOutputStream fop = new FileOutputStream(fileToWrite);

					ObjectOutputStream oop = new ObjectOutputStream(fop)) {
				
				
				List<Club> listClubs = this.readClubs(index);
				List<Membre> listMembres = this.readMembers(index, 1);
				
				listMembres.add(m1);
				
				
				for (Club club : listClubs) {
					oop.writeObject(club);
				}
				for (Membre membre : listMembres) {
					oop.writeObject(membre);
				}

				oop.flush();

			} catch (FileNotFoundException ex) {
				Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
			} catch (EOFException exception) {
				// Output expected EOFExceptions.
				// Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null,
				// exception);
			}
		} else {
			ConsoleManager.getInstance().printToConsole("Le fichier choisi n'est pas un fichier", true);
		}
	}

	public void startData(int index) throws IOException {
		File currentFolder = new File(currentPath);
		List<File> folders = new LinkedList<>();

		for (File file : currentFolder.listFiles()) {
			folders.add(file);
		}

		File fileToWrite = new File(currentPath + "\\" + folders.get(index).getName());
		System.out.println(fileToWrite);
		if (fileToWrite.isFile()) {
			try (FileOutputStream fop = new FileOutputStream(fileToWrite);
					ObjectOutputStream oop = new ObjectOutputStream(fop)) {
				Club club1 = new Club(1, "Club de Fesse");
				Club club2 = new Club(2, "Club de Ping-Pong");
				Club club3 = new Club(3, "Club de Verres d'Alcool");
				Club club4 = new Club(4, "Club de G�teaux");

				Membre m1 = new Membre(1, 1, "RODGERS", "Dimitri", 17, "1er Age");
				Membre m2 = new Membre(2, 1, "ROGER", "JP", 40, "3eme Age");
				Membre m3 = new Membre(3, 2, "NIKITA", "THIKITA", 12, "0eme Age");
				Membre m4 = new Membre(4, 3, "ERROR", "CLONE 1", 0, "CLONE");
				Membre m5 = new Membre(5, 3, "ERROR", "CLONE 3", 0, "CLONE");
				Membre m6 = new Membre(6, 3, "ERROR", "CLONE 2", 0, "CLONE");

				if (!fileToWrite.exists()) {
					fileToWrite.createNewFile();
				}



				oop.writeObject(club1);
				oop.writeObject(club2);
				oop.writeObject(club3);
				oop.writeObject(club4);
				
				oop.writeObject(m1);
				oop.writeObject(m2);
				oop.writeObject(m3);
				oop.writeObject(m4);
				oop.writeObject(m5);
				oop.writeObject(m6);

				oop.flush();

			} catch (FileNotFoundException ex) {
				Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
			} catch (EOFException exception) {
				// Output expected EOFExceptions.
				// Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null,
				// exception);
			}
		} else {
			ConsoleManager.getInstance().printToConsole("Le fichier choisi n'est pas un fichier", true);
		}
	}

	public void copyFile(int index, int index2) throws IOException {
		File currentFolder = new File(currentPath);
		List<File> folders = new LinkedList<>();

		for (File file : currentFolder.listFiles()) {
			folders.add(file);
		}

		String content = "";

		File fileToRead = new File(currentPath + folders.get(index).getName());
		File fileToCopy = new File(currentPath + folders.get(index2).getName());

		FileOutputStream fop = new FileOutputStream(fileToCopy);

		if (fileToRead.isFile()) {
			try (FileInputStream fis = new FileInputStream(fileToRead)) {
				int data;

				while ((data = fis.read()) >= 0) {
					content += (char) data;
				}

				byte[] contentInBytes = content.getBytes();

				fop.write(contentInBytes);
				fop.flush();

				ConsoleManager.getInstance().printLine();
				ConsoleManager.getInstance().printToConsole("Fichier copiey", true);
				ConsoleManager.getInstance().printLine();

			} catch (FileNotFoundException ex) {
				Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
			} finally {
				try {
					if (fop != null) {
						fop.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			ConsoleManager.getInstance().printToConsole("Le fichier choisi n'est pas un fichier", true);
		}
	}

	public void testPerf(int index) throws IOException {
		File currentFolder = new File(currentPath);
		List<File> folders = new LinkedList<>();

		for (File file : currentFolder.listFiles()) {
			folders.add(file);
		}

		File fileToRead = new File(currentPath + folders.get(index).getName());

		Date startFis = new Date();
		if (fileToRead.isFile()) {
			try (FileInputStream fis = new FileInputStream(fileToRead)) {
				int data;

				while ((data = fis.read()) >= 0) {
					fis.read();
				}

			} catch (FileNotFoundException ex) {
				Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			ConsoleManager.getInstance().printToConsole("Le fichier choisi n'est pas un fichier", true);
		}
		Date stopFis = new Date();

		Date startBis = new Date();
		if (fileToRead.isFile()) {
			try (FileInputStream fis = new FileInputStream(fileToRead)) {
				BufferedInputStream bis = new BufferedInputStream(fis);
				int data;

				while ((data = bis.read()) >= 0) {
					bis.read();
				}

			} catch (FileNotFoundException ex) {
				Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			ConsoleManager.getInstance().printToConsole("Le fichier choisi n'est pas un fichier", true);
		}
		Date stopBis = new Date();

		long timeDiffFis = stopFis.getTime() - startFis.getTime();
		long timeDiffBis = stopBis.getTime() - startBis.getTime();

		ConsoleManager.getInstance().printToConsole("Durée InputString : " + Long.toString(timeDiffFis), true);
		ConsoleManager.getInstance().printToConsole("Durée BufferedReader : " + Long.toString(timeDiffBis), true);
	}

	public void writeType(int index) throws IOException {
		File currentFolder = new File(currentPath);
		List<File> folders = new LinkedList<>();

		for (File file : currentFolder.listFiles()) {
			folders.add(file);
		}

		File file = new File(currentPath + folders.get(index).getName());

		if (file.isFile()) {
			try (FileInputStream fis = new FileInputStream(file);
					FileOutputStream fop = new FileOutputStream(file);
					DataInputStream ois = new DataInputStream(fis);
					DataOutputStream oop = new DataOutputStream(fop)) {

				char myChar = 'a';
				double myDouble = 42.42;
				int myInt = 5;
				boolean myBool = true;

				oop.writeChar(myChar);
				oop.writeDouble(myDouble);
				oop.writeInt(myInt);
				oop.writeBoolean(myBool);

				oop.flush();

				int data;
				String content = "";

				char myCharRead = ois.readChar();
				double myDoubleRead = ois.readDouble();
				int myIntRead = ois.readInt();
				boolean myBoolRead = ois.readBoolean();

				ConsoleManager.getInstance().printToConsole(
						"Contenu du fichier : " + myCharRead + myDoubleRead + myIntRead + myBoolRead, true);

			} catch (FileNotFoundException ex) {
				Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			ConsoleManager.getInstance().printToConsole("Le fichier choisi n'est pas un fichier", true);
		}
	}

}
