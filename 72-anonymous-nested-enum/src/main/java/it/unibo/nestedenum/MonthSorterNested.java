package it.unibo.nestedenum;

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
public final class MonthSorterNested implements MonthSorter {

    private enum Month{
    JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC  
    }
    
    @Override
    public Comparator<String> sortByDays() {
        return (a, b) -> Integer.compare(0, 0) ;
    }

    @Override
    public Comparator<String> sortByOrder() {
        return null;
    }
}
