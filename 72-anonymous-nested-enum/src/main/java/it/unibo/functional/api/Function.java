package it.unibo.functional.api;

/**
 * This class models a function of a single parameter as an Object.
 *
 * @param <I> the input type
 * @param <O> the output type
 */
    //Nota: Quelli sopra sono commmenti in javadoc
    //@Param <nomeParametro> --> descrive a cosa serve il parmetro in ingresso passato al metodo 
    //@Return descrive il valore restituito dal metodo alla fine della esecuzione 
public interface Function<I, O> {

    /**
     * Calls the function.
     *
     * @param input the input of the function
     * @return the result of the function evaluation(Valutata) on the provided input(con l'input fornito)
     */
    O call(I input);

    /**
     * Returns the identity function, which returns the same object that is provided as input.
     *
     * @param <T> the input (and output) type of the function
     * @return A new {@code Function} that implements the identity function with the provided type
     */
    static <T> Function<T, T> identity() {
        //ANALISI DELLA DICHIARAZIONE:
        
        //Static --> Metodo statico si invoca direttamente sulla interfaccia(ma si potrebbe fare anche su classi) senza dover fare una istanza del ogetto 
        
        //<T> --> e la dichiarazione del paramaetro generico , "In questo metodo usero la lettera T come segnaposto per un tipo che vera stabilito in una volta al momento della chiamata"
        
        //Function<T, T> --> e' il tipo di ritorno , significa in se che il metodo restituisce una funzione
        //che accetta un valore di tipo T in input e restituisce un valore sempre T come ritorno.
        //Ma il metodo restiuisce la funzione che fa questo non la T

        
        //CONSEGNA:
        //deve restituire la funzione identita --> cioe l'input passato in ingresso senza nessuna modifica
        //Attraverso una classe anonima 
        //Una classe anonima e' una classe usa e getta o per cui e 
        
        //la sintassi per una classe anonima 
        //Quando la classe anonima implementa un'interfaccia, le parentesi tonde dopo il nome dell'interfaccia rimangono **vuote** `()`
        return new Function<T,T>(){
            @Override 
            public T call(final T input)
            {
                return input;
            }
        };
        

        //Per un esempio di classe anonima che implemento funzioni guarda TestFuncionalLibrery 
        
    }

}
