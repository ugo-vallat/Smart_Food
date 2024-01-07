package apiOcaml;

import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class CommandeShell {

	private static CommandeShell instance = new CommandeShell();
public static boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

	public void executerOcaml(String args) {

		if (isWindows) {
			executerOcamlWindow(args);
		} else {
			executerOcamlLinux(args);
		}
	}
 
	private void executerOcamlWindow(String args) {
		ProcessBuilder builder = new ProcessBuilder("CMD", "/C", "Bash ExecuterOcaml.sh "+ args);
		String chemin = "data/fichier_out.csv";
		File plans = new File(chemin);
		try {
			Process process = builder.start();
			System.out.println((read(process)));

		} catch (Exception e) {
			String erreur = "Erreur survenue lors de l'execution des fichiers Ocaml : " + e + "\n"
					+ "Une solution possible est de donner les droits d'ex�cution au fichier ExecuterOcaml.sh avec 'chmod u+v ExecuterOcaml.sh'.";
			System.out.println((erreur));
		}
	}
	
	private void executerOcamlLinux(String args) {
		ProcessBuilder builder = new ProcessBuilder();
		String chemin = "data/fichier_out.csv";
		builder.command(new File("./") + "/ExecuterOcaml.sh" + args);
		File plans = new File(chemin);

		if (plans.exists()) {
			try {
				Process process = builder.start();
				System.out.println((read(process)));

			} catch (Exception e) {
				String erreur = "Erreur survenue lors de l'execution des fichiers Ocaml : " + e + "\n"
						+ "Une solution possible est de donner les droits d'éxécution au fichier ExecuterOcaml.sh avec 'chmod u+v ExecuterOcaml.sh'.";
				System.out.println((erreur));
			}
		} else {
			System.out.println("Les fichiers de donnés n'existent pas.\n");
		}
	}

	private String read(Process process) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line + "\n");
			}
			return builder.toString();
		} catch (Exception e) {
			System.out.println("Erreur survenue pendant la lecture du terminal");
			return "";
		}
	}

	public static CommandeShell getInstance() {
		return instance;
	}

}
