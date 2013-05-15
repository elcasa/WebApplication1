/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parkTorino.utils;

import parkTorino.Utils;

/**
 *
 * @author gcasarin
 */
public class ParcheggioItem {
    /*
}

package com.magidev.parcheggitorino;

import android.graphics.Color;
import com.google.android.gms.maps.model.LatLng;

public class ParcheggioItem {
    */
    private String titolo;
    private int id;
    private int status;
    private int disp;
    private int totale;
    private double lat;
    private double lng;
    private int tendenza;
    private double distanza;
    
    public ParcheggioItem(String titolo,int disponibilita,int totale,int tendenza)
    {
     this.titolo = titolo;
     this.disp = disponibilita;
     this.totale = totale;
     this.tendenza = tendenza;
    }
    
    
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
    
    public String getTitolo() {
/*
     //nel caso il testo sia troppo lungo lo mando a capo
String titoloS = titolo;
titoloS = Utils.splitString(titoloS);
titoloS=titoloS.replace("#","\n");
        return titoloS;
        */
     return this.titolo;
    }
  /*  
    public LatLng getCoords()
    {
     return new LatLng(lat,lng);
    }
  */
    
    public String getDisponibilita(){
     if(disp>totale)
     return "occupati "+totale+"/"+totale;
    
     return "occupati "+disp+"/"+totale;
    }
    
    public int getDisp(){
     return this.disp;   
        
    }
    
    public int getTotale(){
     return this.totale;           
    }
    
    public double getLat(){
     return this.lat;           
    }
    
    public double getLng(){
     return this.lng;           
    }
    
    public int getTend(){
        return this.tendenza;        
    }
    
    public int getId(){
        return this.id;
    }
    
    public int getStatus(){
        return this.status;
    }
  
    
    public String getTendenza()
    {
     if(tendenza>0) return "+";
     if(tendenza<0) return "-";
    
     return "";
    }
    
    /*
    public int getTendenzaColor()
    {
     if(tendenza>0) return Color.parseColor("#339900");
     if(tendenza<0) return Color.RED;
    
     return Color.WHITE;
    }
    */
    
    
    public double getDistanza()
    {
     return distanza;
    }
    
    /*
    public void updateDistanza()
    {
     this.distanza = LocationUtils.distanzaMax(
     LocationUtils.getLocation(),new LatLng(this.lat,this.lng));
     //Log.d("Distanza",this.titolo+":"+distanza);
    }
    */
    
    public int getOccupazione()
    {
     if(totale>0)
     //return 100-((totale-disp)*100)/totale;
     return ((disp)*100)/totale;
    
     return -1;
    }
}