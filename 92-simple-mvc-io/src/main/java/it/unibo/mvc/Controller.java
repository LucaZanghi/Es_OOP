package it.unibo.mvc;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Application controller. Performs the I/O.
 */
public class Controller {
    private static final String FILENAME = "output.txt";
    private final String pathUser = System.getProperty("user.home");
    private File FILEBASE; // Dobbiamo cambairlo quindi non possiamo metterlo final

    // Builder
    public Controller() {
        this.FILEBASE = new File(pathUser, FILENAME);
    }

    // Metodo1
    public void setfile(File file) {
        // Dobbiamo settare il file corrente
        this.FILEBASE = file;
    }

    // Metodo2
    public File getfile() {
        return this.FILEBASE;
    }

    // Metodo3
    public String getpath(File file) {
        final String path = this.FILEBASE.getAbsolutePath();
        return path;
    }

    // Metodo4
    public void save(final String text) throws IOException {    //Se il metodo fallisce lancia gia la IOException
        // Deve salvare la stringa nel file corrente
        try(PrintStream out = new PrintStream(FILEBASE)){//try gestisce il caso che potrebbe lanciare la exception
                                                         //PrintStream e' un oggetto con il quale e possibile scrivere sui file 

            out.print(text);//Stampa il testo nel file
        }
    }

}
