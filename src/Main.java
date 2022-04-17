import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String[] args) {
        openZip("src\\savegames\\zipFile.zip", "src\\savegames");
        openProgress("src\\savegames\\OpennedZip\\save1.dat");
    }

    static void openProgress(String pathToFileSave) {
        GameProgress gameProgress = null;

        try (FileInputStream fileInputStream = new FileInputStream(pathToFileSave);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            gameProgress = (GameProgress) objectInputStream.readObject();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(gameProgress);
    }

    static void openZip(String pathToArchiveFolder, String pathToFolderForUnzippedFiles) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(pathToArchiveFolder))) {

            File dirOpennedZip = new File(pathToFolderForUnzippedFiles + "/OpennedZip/");
            dirOpennedZip.mkdir();

            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();// получим название файла
                // распаковка
                FileOutputStream fout = new FileOutputStream(pathToFolderForUnzippedFiles + "/OpennedZip/" + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                System.out.println("Распакован файл " + name);
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
