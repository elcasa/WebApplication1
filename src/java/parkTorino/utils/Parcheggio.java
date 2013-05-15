/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parkTorino.utils;

/**
 *
 * @author Giulio
 */
public class Parcheggio {
    protected String titolo;
    protected int liberi;
    protected int totale;
    protected double lat;
    protected double lng;
    protected int tendenza;
    protected double distanza;
    
        public static int FREE_NOT_VALID = -1;
        public static int FREE_OVERFLOW = -2;
        
        public static int PARK_FULL = 100;
        public static int PARK_EMPTY = 0;
    
    public Parcheggio(String titolo,int liberi,int totale,int tendenza) 
    {
        this.titolo = titolo;
        this.liberi = liberi;
        this.totale = totale;
        this.tendenza = tendenza;
    }
    
    public Parcheggio(String titolo,int liberi,int totale,int tendenza,double lat,double lng)
    {
        this.titolo = titolo;
        this.liberi = liberi;
        this.totale = totale;
        this.lat = lat;
        this.lng = lng;
        this.tendenza = tendenza;
    }
    
    public String getTitolo() {

        return titolo;
    }
    
    public Coords getCoords()
    {
        return new Coords(lat,lng);
    }
    
    public String getTendenza()
    {
        if(tendenza>0) return "+";
        if(tendenza<0) return "-";
        
        return "";      
    }
    
    public int getTendenzaValue()
    {
        return tendenza;
    }
    
    public double getDistanza()
    {
        return distanza;
    }
    
    public int getTotale()
    {
        return totale;
    }
    
    public int getLiberi()
    {
        return liberi;
    }
    
    public int getOccupati()
    {
        if(liberi<0) return FREE_NOT_VALID;
        if(liberi>totale) return FREE_OVERFLOW;
                
        return totale-liberi;
    }
    
    public int getOccupazione()
    {
        if(liberi<0) return PARK_FULL;
        if(liberi>totale) return PARK_EMPTY;
                
        return ((totale-liberi)*100)/totale;
        
        /*if(totale>0)
                //return 100-((totale-disp)*100)/totale;
                return ((totale-liberi)*100)/totale;
        
        return -1;*/
    }
    
    public class Coords
    {
        double lat;
        double lng;
        
        public Coords(double lat,double lng)
        {
                this.lat = lat;
                this.lng = lng;
        }
    }
    
    
}
