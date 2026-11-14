package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static java.lang.Thread.sleep;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.Impl.DeathNoteImpl;

class TestDeathNote {
    private DeathNote note;//Mettendola fuori rimane visibile a tutti
    @BeforeEach 
    public void set_up(){ 
        note = new DeathNoteImpl();
    }

    @Test 
    public void testRule0(){
        /*
        Dobbiamo controllare che il test non vada per zero e numeri negativi 
        */
        var e1 = assertThrows(IllegalArgumentException.class,()->note.getRule(0));//si usa ci aspettiamo una certa exception 
        assertNotNull(e1.getMessage());//ci aspettiamo che il messsaggio non sia null
        assertFalse(e1.getMessage().isBlank());//Controlliamo se il messaggio e vuoto --> consideando il fatto che la risposta giusto alla nostra domanda e che sia false        
        
        var e2 = assertThrows(IllegalArgumentException.class, ()->note.getRule(-1));
        assertNotNull(e2.getMessage());
        assertFalse(e2.getMessage().isBlank());
    } 


    @Test
    public void testRule1(){
    
        for(int i=1;i<=note.RULES.size();i++){
            assertNotNull(note.getRule(i));
            assertFalse(note.getRule(i).isBlank());
        }
    }

    @Test
    public void testRule2(){
        String infame="Thomas";
        String infame2="Netanyau";
        //Controllo che umano scritto non sia gia presente sul quaderno 
        assertFalse(note.isNameWritten(infame));
        
        //Scrivere il nome del essere
        note.writeName(infame);
        
        //Controllo se essere umano e presente post scrittura
        assertTrue(note.isNameWritten(infame));
        
        //verificare che nessun altro sia stato scritto sul quaderno
        assertFalse(note.isNameWritten(infame2));
        //Controllo che non e stata scritta nessuna stringa vuota
        assertFalse(note.isNameWritten(""));
    }


    @Test
    public void testrule3(){  
        //Se la causa del decesso viene scritta entro i 40 millisecondi successivi all'inserimento del nome di una persona, tale evento si verificherà.
        //Se la causa del decesso non viene specificata, la persona morirà semplicemente per un attacco cardiaco.


        //verificare che, scrivendo la causa del decesso prima del nome, venga sollevata l'eccezione corretta
        assertThrows(IllegalStateException.class,()->note.writeDeathCause("shit")); //Prima si inseisce quello che ci aspettiamo e poi il motivo del quale succede
        //e come equals ma specificio per le exception
        
        //scrivere il nome di un essere umano nel quaderno
        String infame2 = "Donald-Trump";
        note.writeName(infame2);
        //verificare che la causa del decesso sia un attacco cardiaco
        assertEquals("heart attack",note.getDeathCause(infame2));
        //scrivere il nome di un altro essere umano nel quaderno
        note.writeName("Maria_Rosa");
        //impostare la causa del decesso come "incidente con i kart"
        note.writeDeathCause("Go-Kart Incident");
        //verificare che la causa del decesso sia stata impostata correttamente (il valore restituito è "vero" e la causa corrisponde effettivamente a "incidente con i kart")
        assertEquals("Go-Kart Incident",note.getDeathCause("Maria_Rosa"));
        //attendere 100 ms
        try{
            Thread.sleep(100);
        }catch(InterruptedException e){}
        
        //tentare di modificare la causa del decesso
        note.writeDeathCause("sex");
        //verificare che la causa del decesso non sia stata modificata
        assertEquals(note.getDeathCause("Maria_Rosa"),"Go-Kart Incident");
    }


    @Test
    public void testRule4(){//I test sono void perché non devono fare return di nessun tipo di dato
        /*
        Dopo aver scritto la causa della morte, i dettagli della morte dovrebbero essere scritti nei successivi 6 secondi e 40 millisecondi dalla scrittura della causa della morte.
        controlla che scrivere i dettagli della morte prima di scrivere un nome generi l'eccezione corretta
        scrivi il nome di un essere umano sul quaderno
        verificare che i dettagli della morte siano attualmente vuoti
        imposta i dettagli della morte su "è durato troppo a lungo"
        verificare che i dettagli della morte siano stati impostati correttamente (restituito vero e che i dettagli siano effettivamente "gestiti per troppo tempo")
        scrivi il nome di un altro essere umano sul quaderno
        dormire per 6100 ms
        prova a cambiare i dettagli
        verificare che i dettagli non siano stati modificati
        */

        String nome = "Sabita";
        note.writeDeathCause("zap");
        assertThrows(IllegalStateException.class, ()->note.writeDetails("in the park"));
        note.writeName(nome);
        assertEquals("",note.getDeathDetails(nome));
        note.writeDetails("ran for too long");
        assertEquals(note.getDeathDetails(nome),"ran for too long");
        note.writeName("FrancescoS");
        try{
            note.wait(6100);
        }catch(InterruptedException e){}
        note.writeDetails("in a Flight");
        assertEquals("in a Flight", note.writeDetails("in a Flight"));

    }
}   
