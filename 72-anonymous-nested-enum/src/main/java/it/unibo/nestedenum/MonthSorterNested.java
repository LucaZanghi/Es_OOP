package it.unibo.nestedenum;

import java.time.Month;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
//Enum --> Enumerection --> in italiano elenco numerato
//e. Questa enum deve avere un metodo Month fromString(String) 
// che, data una stringa di testo, restituisce il Month che meglio la rappresenta. 
// A tal proposito, si legga con molta attenzione la Javadoc di MonthSorter.

/**
 * Implementation of {@link MonthSorter}.
 */
//Comparator e un oggetto che dice come confrontare due cose.
//Un comparetor a solo du un metodo compare(a,b) che restituisce un int --> negativo se a va prima di b , 0 se sono uguale , positivo se a va dopo b
public final class MonthSorterNested implements MonthSorter {

    private static final Comparator<String> ByDays = new SortByDays(); //Inizializzazione del comparatore che deve ritornaere
    private static final Comparator<String> ByOrder = new SortByOrder();
    public MonthSorterNested(){//Costruttore della classe

    }


    @Override
    public Comparator<String> sortByDays() {
        return ByDays;
    }

    @Override
    public Comparator<String> sortByOrder() {
        return ByOrder;
    }

    private enum Month{
        JAN(31), FEB(28), MAR(31), APR(30), MAY(31), JUN(30), JUL(31), AUG(31), SEP(30), OCT(31), NOV(30), DEC(31);

        //Campi
        private final int days;
        //Costruttore
        Month( final int days)
        {
            this.days =days;
        }

        public int getDays(){
            return days;
        }
        
        
        //Siccome in sotybydays deve confrontare due stringe noi per confrontarle le dobbiamo convertire in enum di month
        static Month fromString(final String name){
            //Controlliamo che name non sia NULL
            Objects.requireNonNull(name);
            try{
                return valueOf(name);//E un metodo di base degli enum che cerca il risultato piu simile al interno del enum e lo asoccia (FEB-->feb)
            }catch(final IllegalArgumentException e){//se non troviamo nulla con quel nome prendiamo quelle exception e la intercettiamo con e
            //Sotto verra rifatto un secondo value of pero non far interrompere il metodo dove daremmo un errore piu specifico 
            //olte a quello per nomi non completti il ciclo qua sotto funziona mentre il valueof no tipo ge -> gen
            //valueof controlla il nome esatto
             
                //Partita la exeption facciamo:
                Month match =null; //Creiami una var per salvarci il mese
                
                for(final Month month: values()){//Creiamo una var temporanea 
                // e scorriamo values che e un array di base che contiene tutti gli argomenti del enum
                    
                    if(month.toString().toLowerCase(Locale.ROOT)//Il mese nella var temp lo mettiamo in lower case(FEB->feb) , locale.ROOT come in ogni paese
                        .startsWith(name.toLowerCase(Locale.ROOT))){//E chiediamo inizia come name quindi la stringa che abbiamo inserito(GEN --> GEA=TRUE)
                        if(match !=null){
                            throw new IllegalArgumentException(name+"is ambigus : both "+match+"and"+month+" would be valid matches",e);
                            //es Ju e ambiguo perche sia june che july sarebbero dei match validi 
                            //e con il ,e ci dice la causa della exeption 
                            //Successivamente si blocca il codice
                        }
                        match = month;//La prima volta che questo if e vero associamo a match month
                        //ma se per altri mesi si riferifica la medesima condizione lanciamo la eception    
                    }                    
                }
                
                if(match == null){//Se alla fine non abbiamo trovato nessun mese valido rimarà null
                        throw new IllegalArgumentException("No matching months for "+name,e);//causa e
                }
                return match;
            }
        }
    }

    //classe innestate(inner) --> annidate
    private static final class SortByDays implements Comparator<String>{
        @Override 
        public int compare(String a , String b){
            final var m1 = Month.fromString(a);
            final var m2 = Month.fromString(b);
            return Integer.compare(m1.getDays(),m2.getDays());
        }
    }

    private static final class SortByOrder implements Comparator<String>{
        @Override 
        public int compare(String a, String b){
            final var m1 = Month.fromString(a);
            final var m2 = Month.fromString(b);
            return m1.compareTo(m2);
        }
    }
}
    



