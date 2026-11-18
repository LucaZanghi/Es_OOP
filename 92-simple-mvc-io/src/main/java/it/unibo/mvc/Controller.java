package it.unibo.mvc;

import java.io.File;
import java.io.IOException;

/**
 * Application controller. Performs the I/O.
 */
public class Controller {
    private static final String FILE_DEFAULT_NAME ="output.txt"; 
    private final String path_user = System.getProperty("user.home");
    private final String path_separator = System.getProperty("file.separator");
    private File FILE_DEFAULT;//Dobbiamo cambairlo quindi non possiamo metterlo final

    //Builder
    public Controller(){
        this.FILE_DEFAULT =new File(path_user,FILE_DEFAULT_NAME);
    }
    //Metodo1
    public void setfile(File file){
        //Dobbiamo settare il file corrente
        this.FILE_DEFAULT =file;
    }
    //Metodo2
    public File getfile(){
        return this.FILE_DEFAULT;
    }
    //Metodo3
    public String getpath(File file){
        String path = this.FILE_DEFAULT.getAbsolutePath();
        return path;
    }
    //Metodo4
    public void savestring(String string){
        //Deve salvare la stringa nel file corrente
        /*if(string ==null){
            throw  IOException();
        }*/
        File newpath = new File(string);//Creiamo un nuovo file con il path in questione
        this.FILE_DEFAULT=newpath;//E lo associamo a quell del nostro oggetto
    }
    
}
