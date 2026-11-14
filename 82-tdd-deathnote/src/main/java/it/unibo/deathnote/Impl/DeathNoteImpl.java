package it.unibo.deathnote.Impl;
import it.unibo.deathnote.api.DeathNote;
import java.util.Map;
import java.util.LinkedHashMap;
public class DeathNoteImpl implements DeathNote {

    private final Map<String,Death>deathList;
    
    public DeathNoteImpl(){
        deathList = new LinkedHashMap<>();        
    }   

    @Override 
    public String getRule(int ruleNumber){
        if(ruleNumber<1||ruleNumber>RULES.size()){
            throw new IllegalArgumentException("the given rule number is smaller than 1 or larger than the number of rules");
        }
        else{
            return RULES.get(ruleNumber-1);
        }
    }

    /**
     * The human whose name is written in this DeathNote will die.
     *
     * @param name the name of the human to kill
     * @throws NullPointerException if the given name is null.
     */
    @Override 
    public void writeName(String name){
        if(name==null){
            throw new NullPointerException("the given name is null.");
        }
        else{
            deathList.put(name, new Death());
        }
    }


    /**
     * If the cause of death is written within the next 40 milliseconds of writing the person's
     * name, it will happen.
     *
     * @param cause the cause of the human's death
     * @return true if the cause was written within 40 milliseconds, false otherwise
     * @throws IllegalStateException if there is no name written in this DeathNote,
     *     or the cause is null
     */
    @Override 
    public boolean writeDeathCause(String cause){
        //Noi dobbiamo scrivere la causa , pero se questa causa non ha un nome dobbiamo lanciare exception
        String last_name = null;
        for(String chiave : deathList.keySet())//key set riconosce il tipo a cui lo associamo nella temporanea e ci da l'insieme di solo quel tipo(in questo caso l'insieme dei nomi)
        {
            last_name = chiave;//Troviamo l'ultimo nome
        }

        if(last_name==null){//Se l'ultimo nome e vuoto--> exception
            throw new IllegalStateException(" there is no name written in this DeathNote,the cause is null");
        }

        Death d = deathList.get(last_name);//Aggiugiamo il nome
        d.writeCause(cause);//Chiamiamo il metodo in cause 
        return true;
    }

    /**
     * After writing the cause of death, details of the death should be written in the next
     * 6 seconds and 40 milliseconds.
     *
     * @param details the details of the human's death
     * @return true if the details were written within 6 seconds and 40 milliseconds, false otherwise
     * @throws IllegalStateException if there is no name written in this DeathNote,
     *     or the details are null
     */
    @Override 
    public boolean writeDetails(String details){
        String last_name = null;
        for(String chiave:deathList.keySet()){
            last_name = chiave;
        }

        if(last_name==null||details==null)
        {
            throw new IllegalStateException(" there is no name written in this DeathNote,or the details are null");
        }
        Death d = deathList.get(last_name);
        d.writeDetails(details);
        return true;
    }

    /**
     * Provides the cause of death of the person with the given name.
     *
     * @param name the name of the person whose death cause to return
     * @return the death cause of the person with the given name.
     *     If the cause of death is not specified, the method will return "heart attack".
     * @throws IllegalArgumentException if the provider name is not written in this DeathNote
     */
    @Override 
    public String getDeathCause(String name){
        for(String input : deathList.keySet())//Dobbiamo verificare che il nome dato in input sià prensente nel deathnote
        {
            if(input.equals(name))//le stringhe non si confrontano con ==
            {
                return deathList.get(name).cause;
            }
        }
        return "heart attack";
    }

    /**
     * Provides the details of the death of the person with the given name.
     *
     * @param name the name of the person whose death cause to return
     * @return the death details of the person with the given name,
     *     or an empty string if no details have been provided.
     * @throws IllegalArgumentException if the provider name is not written in this DeathNote.
     */
    @Override 
    public String getDeathDetails(String name){
        if(deathList.containsKey(name)){
            return deathList.get(name).details;//dammi i dettagli sapendo il nome (LinkedHashedList)
        }
        throw new IllegalArgumentException("the provider name is not written in this DeathNote.");
    }

    /**
     * Checks if the given name is written in this DeathNote.
     *
     * @param name the name of the person
     * @return true if the given name is written in this DeathNote, false otherwise
     */
    @Override 
    public boolean isNameWritten(String name){
        return deathList.containsKey(name);
    }

    private static final class Death{
        private static final String STANDARD_DEAD_CASE = "heart attack";
        private static final byte VALID_CAUSE_TIMEOUT = 40;//Tempo massimo per scrivere la causa
        private static final short  VALID_DETAIL_TIMEOUT = 6000+VALID_CAUSE_TIMEOUT;//Tempo massimo per scrivere dettagli  
        private final String cause;
        private final String details;
        private final long timeOfDeath;

        //costruttore personalizzato 
        private Death(String cause, String details){
            this.cause = cause;
            this.details=details;
            this.timeOfDeath=System.currentTimeMillis();
        }
        //costruttore secondario , gli da in ingreso dei valori standard e parte ogni volta che crei un oggetto nuovo con new()
        Death(){
            this(STANDARD_DEAD_CASE,"");
        }

        Death writeCause(final String deathCause){
            //Dobbiamo considerare il fatto che ce un tempo minimo entro il quale si puo scrivere la causa
            if(System.currentTimeMillis()<timeOfDeath+VALID_CAUSE_TIMEOUT){
                return new Death(deathCause,"");//new perché un costruttore si puo chiamare solo con new primaà            
            }
            //Se abbiamo superato il tempo massimo rimandiamo indietro l'oggetto su cui stavamo lavorando,senza ulteriori modifiche
            return this;
        }

        Death writeDetails(String details){
            //Lo stesso vale per i dettagli 
            if(System.currentTimeMillis()<timeOfDeath+VALID_DETAIL_TIMEOUT){
                return new Death(this.cause,details);
            }
            return this;
        }
    }
}
