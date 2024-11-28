import java.io.*;
import java.time.Year;

public class TextFile {

    public static void writeClientToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/assets/text_files/Clients.txt"))) {
            for (Client item : Client.clients) {
                writer.write(item.toStringFile());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readClientFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/assets/text_files/Clients.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 8) {
                    Client client = new Client(parts[1], parts[2], parts[3], parts[4], parts[5], parts[6],
                            Long.parseLong(parts[7]));
                    client.setId(Long.parseLong(parts[0]));
                    Client.clients.add(client);
                }
            }
            long maxId = 0;
            for (Client client1 : Client.clients) {
                if (client1.getId() > maxId)
                    maxId = client1.getId();
            }
            Client.setID_GENERATOR(maxId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeRealtorToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/assets/text_files/Realtors.txt"))) {
            for (Realtor item : Realtor.realtors) {
                writer.write(item.toStringFile());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readRealtorFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/assets/text_files/Realtors.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 7) {
                    Realtor realtor = new Realtor(parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
                    realtor.setId(Long.parseLong(parts[0]));
                    Realtor.realtors.add(realtor);
                }
            }
            long maxId = 0;
            for (Realtor realtor1 : Realtor.realtors) {
                if (realtor1.getId() > maxId)
                    maxId = realtor1.getId();
            }
            Realtor.setID_GENERATOR(maxId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeRealEstateToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/assets/text_files/RealEstates.txt"))) {
            for (RealEstate item : RealEstate.realEstates) {
                writer.write(item.toStringFile());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readRealEstateFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/assets/text_files/RealEstates.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 8) {
                    RealEstate realEstate = new RealEstate(parts[1], Double.parseDouble(parts[2]),
                            Integer.parseInt(parts[3]),
                            Year.parse(parts[4]), Integer.parseInt(parts[5]), Double.parseDouble(parts[6]));
                    realEstate.setClientId(Long.parseLong(parts[7]));
                    realEstate.setId(Long.parseLong(parts[0]));
                    RealEstate.realEstates.add(realEstate);
                }
            }
            long maxId = 0;
            for (RealEstate realEstate1 : RealEstate.realEstates) {
                if (realEstate1.getId() > maxId)
                    maxId = realEstate1.getId();
            }
            RealEstate.setID_GENERATOR(maxId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadFile() {
        readClientFromFile();
        readRealtorFromFile();
        readRealEstateFromFile();
    }
}
