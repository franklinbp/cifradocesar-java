import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileManager {
    public void writeToFile(String nombreArchivo, String contenido) {
        try {
            Path ruta = Paths.get(nombreArchivo);
            Files.write(ruta, contenido.getBytes());
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    public String readFromFile(String nombreArchivo) {
        try {
            Path ruta = Paths.get(nombreArchivo);
            return new String(Files.readAllBytes(ruta));
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return "";
        }
    }

    public int readShiftFromFile(String nombreArchivo) {
        try {
            Path ruta = Paths.get(nombreArchivo);
            List<String> lineas = Files.readAllLines(ruta);
            for (String linea : lineas) {
                if (linea.startsWith("desplazamiento=")) {
                    return Integer.parseInt(linea.split("=")[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de desplazamiento: " + e.getMessage());
        }
        return 0;
    }
}