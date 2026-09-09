import java.io.*;
import java.net.*;
import java.util.Properties;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 5003;

        // 1. Loeme seaded failist
        try {
            Properties prop = new Properties();
            FileInputStream ip = new FileInputStream("config.properties");
            prop.load(ip);

            host = prop.getProperty("server.ip"); // Siin oli suur P, muudetud väikseks p-ks
            port = Integer.parseInt(prop.getProperty("server.port"));
            System.out.println("[+] Seaded laetud! Ühendun: " + host + ":" + port);
        } catch (Exception e) {
            System.out.println("[!] config.properties puudub, kasutan vaikimisi sätteid.");
        }

        // 2. Luuakse tegelik võrguühendus (See osa oli sul puudu!)
        try {
            Socket socket = new Socket(host, port);
            System.out.println("[+] Ühendus õnnestus!");

            // Seadistame voogude muutujad, mida sinu kood allpool kasutab
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);


            // --- SIIT EDASI JÄTKUB SINU OLEMASOLEV KOOD ---
            while (true) {
                System.out.print("Java_RAT_Konsool $> ");
                String command = scanner.nextLine();

                if (command.trim().isEmpty()) continue;

                out.println(command);

                if (command.equalsIgnoreCase("exit")) {
                    break;
                }

                String line;
                while ((line = in.readLine()) != null) {
                    if (line.equals("---END_OF_RESP---")) {
                        break;
                    }
                    System.out.println(line);
                }
            }

            // Suleme ühenduse tsükli lõpus
            socket.close();
            System.out.println("[*] Ühendus lõpetatud.");

        } catch (IOException e) {
            System.out.println("Ühenduse viga: " + e.getMessage());
        }
    }
}
