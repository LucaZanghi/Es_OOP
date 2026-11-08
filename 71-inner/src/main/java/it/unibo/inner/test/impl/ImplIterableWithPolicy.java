package it.unibo.inner.test.impl;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import it.unibo.inner.api.IterableWithPolicy;
                                            //Siccome implmentiamo un interfaccia dobbiamo fare l'import del package in cui si trova 
import it.unibo.inner.api.Predicate;        //Per perdicate <T> in seIteracionPolicy
public class ImplIterableWithPolicy<T> implements IterableWithPolicy<T>{ 
                                            //Le classii top level  non possono essere static (rimosso)
                                            //T sta per type cioe tipo generico siccome e statica 

    private final T[] elements;
    private Predicate<T> filters;//Sono delle interfaccie con un solo metodo di base che fa un solo controllo 
                                 //Controllo che avviene sul oggetto T,dove viene valutato(di base si chiama "test")
                                 //resistuisce true se lo accetta altriementi false(scartato)
    
    
    //costruttore 1
    public ImplIterableWithPolicy(T[] elements){   
        //this.elements = Arrays.copyOf(elements, elements.length);
        this(elements,elem->true);//Elem pk serve una variabile singolare per t la quale va forzata a true
    }//pk viene passata al secondo costruttore , e si passa a un costruttore cosi
    
    //costruttore 2
    public ImplIterableWithPolicy(T elements[],Predicate<T> filters){
        this.elements = Arrays.copyOf(elements, elements.length);
        this.filters=filters;
    }

    @Override 
    public void setIterationPolicy(Predicate<T> filter){
                                    //deve impostare predecrive<T> per filtrare gli elementi durante l'iterazione
                                    //lo fa lo stesso il costruttore ma torna utile se vogliamo cambiare il criterio in corso d'oopera
        this.filters=filter;        
    }
                                    //Siccome implentiamo itarable lo dobbiamo inserire
    @Override 
    public Iterator<T> iterator(){
        return new policyIterator();//Instanza e restituzione del meotdo
    }

    public class policyIterator implements Iterator<T>{
                                            //Abbiamon tolto static perche avrebbe reso la classe con i propi metodi non acedibili (e non richiamabili) al suo interno
        private int index=0;                //Punto di inizio
                                            //Implmentiamo solo due metodi perche' gli altri due sono di default 
        

        @Override 
        public boolean hasNext(){
                                            //ce anora un elemento se l'indice non ha raggiunto la fine del array
            while(index<elements.length 
                && 
                filters.test(elements[index])==false){//filter(il buttafuori) fa il test su l'elemento(test che non abbiamo programmato) e cotinua finche e falso
                index++;
            }
            return index < elements.length;     //Confronta l'indice con la lunghezza del array;
                                                //dara true se e vero che l'indice e piu piccolo
                                                //false se array lenghe e piu piccolo e quindi non ha nulla da vedere dopo
        }

        @Override                           //I tipi come E o T sono considerati tipi generici in java 
        public T next(){
            if(hasNext()==false){           //Se non ha nulla dopo --> non ci sara nessuno 
                throw new NoSuchElementException();
                                            //Quindi lanciamo l'errrore
            }
            return elements[index++];       //In ogni altro caso ritorniamo il next
        }



    }
}