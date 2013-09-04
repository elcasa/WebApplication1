/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parkTorino;

/**
 * Javabean contente la fascia oraia dell'ultimo aggiornamento 
 * della tabella storici, utilizzato per limitare gli update degli storici
 * 
 * @author Giulio
 */
public class LastStor implements java.io.Serializable {
 
    private static int ora;
    
 
    // Costruttore senza argomenti
    /**
    *
    */
   public LastStor() { }
 
    // Proprietà "nome" (da notare l'uso della maiuscola) lettura / scrittura
   
    /**
    *
    * @return
    */
   public static int getOra() {
        return ora;
    }
   
    /**
    *
    * @param o
    */
   public void setOra(int o) {
        ora = o;
    }
    /*
    private boolean deceduto;
 
    // Diversa sintassi per gli attributo boolean ( 'is' al posto di 'get' )
    public boolean isDeceduto() {
        return this.deceduto;
    }
    public void setDeceduto(boolean deceduto) {
        this.deceduto = deceduto;
    }
    */
}