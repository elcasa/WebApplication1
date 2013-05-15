/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parkTorino;

/**
 *
 * @author Giulio
 */
public class LastStor implements java.io.Serializable {
 
    private static int ora;
    
 
    // Costruttore senza argomenti
    public LastStor() { }
 
    // Proprietà "nome" (da notare l'uso della maiuscola) lettura / scrittura
    public static int getOra() {
        return ora;
    }
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