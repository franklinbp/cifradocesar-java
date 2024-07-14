import java.util.Scanner;
public class CaesarCipher {
    // Alfabeto utilizado para el cifrado
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.,;:!? ";
    private final FileManager fileManager;
    private final Scanner scanner;

    public CaesarCipher() {
        this.fileManager = new FileManager();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println("\n1. Cifrar");
            System.out.println("2. Descifrar");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea

            switch (opcion) {
                case 1:
                    encryptFlow();
                    break;
                case 2:
                    decryptFlow();
                    break;
                case 3:
                    System.out.println("¡Hasta pronto!");
                    return;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private void encryptFlow() {
        System.out.print("Ingrese el texto a cifrar: ");
        String textoOriginal = scanner.nextLine();

        System.out.print("Ingrese el nombre del archivo para guardar el texto cifrado: ");
        String nombreArchivoCifrado = scanner.nextLine();

        System.out.print("Ingrese el número de desplazamientos: ");
        int desplazamiento = scanner.nextInt();
        scanner.nextLine(); // Consumir nueva línea

        String textoCifrado = encrypt(textoOriginal, desplazamiento);
        fileManager.writeToFile(nombreArchivoCifrado, textoCifrado);
        fileManager.writeToFile("cambio.txt", "desplazamiento=" + desplazamiento);

        System.out.println("Texto cifrado guardado en: " + nombreArchivoCifrado);
    }

    private void decryptFlow() {
        System.out.print("Ingrese el nombre del archivo cifrado: ");
        String nombreArchivoCifrado = scanner.nextLine();

        String textoCifrado = fileManager.readFromFile(nombreArchivoCifrado);
        int desplazamiento = fileManager.readShiftFromFile("cambio.txt");

        String textoDescifrado = decrypt(textoCifrado, desplazamiento);
        fileManager.writeToFile("texto_descifrado.txt", textoDescifrado);

        System.out.println("Texto descifrado guardado en: texto_descifrado.txt");
    }

    private String encrypt(String texto, int desplazamiento) {
        StringBuilder resultado = new StringBuilder();
        for (char caracter : texto.toCharArray()) {
            int indice = ALPHABET.indexOf(caracter);
            if (indice != -1) {
                int nuevoIndice = (indice + desplazamiento) % ALPHABET.length();
                resultado.append(ALPHABET.charAt(nuevoIndice));
            } else {
                resultado.append(caracter);
            }
        }
        return resultado.toString();
    }

    private String decrypt(String texto, int desplazamiento) {
        return encrypt(texto, ALPHABET.length() - (desplazamiento % ALPHABET.length()));
    }
}