package it.unibo.functional;

import it.unibo.functional.api.Function;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * A special utility class with methods that transform collections using {@link Function}s provided as parameters.
 * Una speciale class utility con metodi che transformano le collezione che usano la clase Function come paramentro 
 */
public final class Transformers {

    private Transformers() { }//E un costruttore privato che viene inserito nelle classi utility
    //- ovvero classi che contengono esclusivamente metodi statici 
    //Si usa per evitare la creazione di oggetti inutili e disabilita il costruttore di default  

    /**
     * A function that applies a transformation to each element of an {@link Iterable}, obtaining multiple elements,
     * and then builds a "flat" list of these transformed elements.
     * 
     * Una funzione che applica la transoformazione (il metodo) su ogni elemento di Iterable , ottenendo multipli elementi 
     * e dopo costruire un flat lista(lista fatta da singoli elementi) di queste elementi trasformati 
     * 
     * For instance, {@code [1, 2, 3, 4, 5]} could use {@code flattenTransform} to transform only the odd numbers into
     * squares, passing a function that returns an empty {@link List} for even numbers and a List with the square of
     * the input otherwise, obtaining as output {@code [1, 9, 25]}.
     * 
     * Per l'istanza [1,2,3,4] puo trasformarla solo nella somma dei numeri in quadrati
     * passando una funzione che ritorna nulla per numeri pari e in caso contrario(per i dispari) da il quadrato --> [1,9,25] 
     *
     * @param base the elements on which to operate
     * @param transformer the {@link Function} to apply to each element. It must transform the elements into a
     *     (possibly empty) collection of other elements.
     * @param <I> input elements type
     * @param <O> output elements type
     * @return A "flattened" list of the produced elements
     */
    public static <I, O> List<O> flattenTransform(
        final Iterable<? extends I> base, //elementi con cui puo operare
        final Function<I, ? extends Collection<? extends O>> transformer 
        //la classe funzione va applicata ad ogni elemento possibilmente vuoto in una collezione di altri elementi 

    ) {
        final var result = new ArrayList<O>();//Creiamo una arraylist con elementi di tipo O(Output)
        for (final I input : Objects.requireNonNull(base, "The base iterable cannot be null")) {
            result.addAll(transformer.call(input));
        }
        return result;
    }
    /*
    Prima di entrare nel ciclo si controlla che base non sia null.
    Poi, ad ogni giro, input diventa il prossimo elemento di base.
    Su quell’elemento si fa transformer.call(input), che restituisce un gruppo di risultati.
    Quel gruppo viene aggiunto a result, che quindi cresce giro dopo giro.
    Quando base è finito, il ciclo si ferma da solo e si fa return result.
    */

    /**
     * A function that applies a transformation to each element of an {@link Iterable},
     * returning a list of these transformed elements.
     * For instance, {@code [1, 2, 3, 4, 5]} could use {@code transform} to produce a list of squares(quadrati) by passing
     * a function that computes the square(il quadrato) of the input, thus obtaining(ottenendo cosi) {@code [1, 4, 9, 16, 25]}.
     * <b>NOTE:</b> this function is a special flattenTransform whose function always return a list with a single
     * element
     * (Restituisce sempre un elenco singolo)
     *
     * @param base the elements on which to operate
     * @param transformer the {@link Function} to apply to each element.
     * @param <I> input elements type
     * @param <O> output elements type
     * @return A transformed list where each input element is replaced with the produced elements
     */
    public static <I, O> List<O> transform(final Iterable<I> base, final Function<I, O> transformer) {
        O element;
        final var list = new ArrayList<O>();
        for(final I input : base){
            element = transformer.call(input);
            list.add(element);
        }
        //scorrere iterable 
        //apllicare il metodo per ogni elemltno 

        return list;
    }

    /**
     * A function that takes an iterable of collections, and returns a flatten list of the elements of the inner(interne)
     * collections.
     * For instance, {@code [[1], [2, 3], [4, 5], []]} could use {@code flatten} to produce a flat list, thus obtaining
     * {@code [1, 2, 3, 4, 5]}.
     * <b>NOTE:</b> this function is a special flattenTransform whose input is an iterable of collections,
     * and whose function simply returns each collection (identity).
     *
     * @param base the collections on which to operate
     * @param <I> type of the collection elements
     * @return A flattened list with the elements of each collection in the input
     */
    public static <I> List<? extends I> flatten(final Iterable<? extends Collection<? extends I>> base) {
        
        return flattenTransform(base,pippo->pippo);//deve fare il return di una list di I di una sua sottoclasse
        //con questa labda diciamo che l'input deve rimanere uguale al'output di conseguenza il metodo deducera che <I>=<O> e quindi fara un return
        //del tipo che abbiamo dato in ingresso , ma anche se non lo avesse fatto e avess fatto return di List<O> sarebbe andato bene ugualmente
        //perche sarebbe stato comunque un <extend I> perche deriva da I.
    }

    /**
     * A function that applies a test to each element of an {@link Iterable}, returning a list containing only the
     * elements that pass the test.
     * For instance, {@code [1, 2, 3, 4, 5]} could use {@code select} to filter only the odd numbers, thus obtaining
     * {@code [1, 3, 5]}.
     * <b>NOTE:</b> this function is a special flattenTransform whose function returns a list with a single element if
     * the element passes the test, and an empty list otherwise.
     *
     * @param base the elements on which to operate
     * @param test the {@link Function} to use to test whether the elements should be selected.
     * @param <I> elements type
     * @return A list containing only the elements that passed the test
     */
    public static <I> List<I> select(final Iterable<I> base, final Function<I, Boolean> test) {
        final var list = new ArrayList<I>();
        for(final I input:base){
            if(test.call(input)){
                list.add(input);
            }
        }

        return list;
    }
    //Oppure se è obbligatorio l'utilizzo di flattenTransform sarebbe:
    //return flattenTransform(base,x->test.call(x)?list.of(x):listof());
    //Spiegazione return metodo(iterable , function if valoreseVero:valoreseFalso);

    /**
     * A function that applies a test to each element of an {@link Iterable}, returning a list containing only the
     * elements that do not pass the test.
     * For instance, {@code [1, 2, 3, 4, 5]} could use {@code select} to reject all the even numbers, thus obtaining
     * {@code [1, 3, 5]}.
     * <b>NOTE:</b> this function is a special select whose test function return value is negated.
     *
     * @param base the elements on which to operate
     * @param test the {@link Function} to use to test whether the elements should be discarded.
     * @param <I> elements type
     * @return A list containing only the elements that passed the test
     */
    public static <I> List<I> reject(final Iterable<I> base, final Function<I, Boolean> test) {
        //dobbiamo togliere i numeri dispari tenendo quelli pari usando il metodo select
        return select(base,x->!test.call(x));//Bisogna tenere le x che non passano il test(not test)
    }
}
