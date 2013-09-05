/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parkTorino.utils;


/**
 * Classe che difinisce l'oggetto ParcheggioItem usato per rappresentare ogni parcheggio
 * @author gcasarin
 */
public class ParcheggioItem {
   
    private String titolo;
    private int id;
    private int status;
    private int disp;
    private int totale;
    private double lat;
    private double lng;
    private int tendenza;
    private double distanza;
    
    /**
    * Costruttore limitato
    * @param titolo
    * @param disponibilita
    * @param totale
    * @param tendenza
    */
   public ParcheggioItem(String titolo,int disponibilita,int totale,int tendenza)
    {
     this.titolo = titolo;
     this.disp = disponibilita;
     this.totale = totale;
     this.tendenza = tendenza;
    }
    
    
    /**
    * Costruttore
    * 
    * @param titolo
    * @param id
    * @param status
    * @param disp
    * @param totale
    * @param tendenza
    * @param lat
    * @param lng
    */
   public ParcheggioItem(String titolo, int id, int status, int disp,int totale,int tendenza,double lat,double lng)
    {
     this.titolo = titolo;
     this.id=id;
     this.status=status;
     this.disp = disp;
     this.totale = totale;
     this.lat = lat;
     this.lng = lng;
     this.tendenza = tendenza;
     
    }
    
    /**
    *
    * @return
    */
   public String getTitolo() {
     return this.titolo;
    }

    
    /**
    *
    * @return
    */
   public String getDisponibilita(){
     if(disp>totale)
     return "occupati "+totale+"/"+totale;
    
     return "occupati "+disp+"/"+totale;
    }
    
    /**
    *
    * @return
    */
   public int getDisp(){
     return this.disp;   
        
    }
    
    /**
    *
    * @return
    */
   public int getTotale(){
     return this.totale;           
    }
    
    /**
    *
    * @return
    */
   public double getLat(){
     return this.lat;           
    }
    
    /**
    *
    * @return
    */
   public double getLng(){
     return this.lng;           
    }
    
    /**
    *
    * @return
    */
   public int getTend(){
        return this.tendenza;        
    }
    
    /**
    *
    * @return
    */
   public int getId(){
        return this.id;
    }
    
    /**
    *
    * @return
    */
   public int getStatus(){
        return this.status;
    }
  
    
    /**
    *
    * @return
    */
   public String getTendenza()
    {
     if(tendenza>0) return "+";
     if(tendenza<0) return "-";
    
     return "";
    }
    
    
    
    /**
    *
    * @return
    */
   public double getDistanza()
    {
     return distanza;
    }
    
        
    /**
    *
    * @return
    */
   public int getOccupazione()
    {
     if(totale>0)
     //return 100-((totale-disp)*100)/totale;
     return ((disp)*100)/totale;
    
     return -1;
    }
}