<%-- 
    Document   : storici
    Created on : 4-set-2013, 1.09.20
    Author     : Giulio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="parkTorino.DataManager" %> 
<%@page import="java.sql.ResultSet" %>
<%@page import="java.sql.SQLException" %>
<%@page import="java.util.logging.Level" %>
<%@page import="java.util.logging.Logger" %>
<%@page import="java.util.List" %> 
<%@page import="java.util.Iterator" %> 
<%@page import="java.util.ArrayList" %>
<%@page import="parkTorino.utils.Parser;" %>


<%! 
   
DataManager dm; 

private int getPercentage( double total, int free){
   
   if ( free== -1){
      return -1;
   }
   
   return (int)( ( total-free ) / total * 100 );
}   

private ArrayList<String[]> getData() 
{
    String tmp[];
    //DataManager dm =null;
    ArrayList<String[]> res = new ArrayList<String[]>();   
    
    try {
         dm = new DataManager();
  
         ResultSet rs=null;

         //rs = dm.query("select name,total,free,lat,lng from PARCHEGGI");

         rs = dm.query("select PARCHEGGI.id,name,total,lat,lng,mattinamed,pranzomed,pomemed,seramed from PARCHEGGI left join STORICI on PARCHEGGI.id = STORICI.id");    

         while (rs.next()) {
             tmp = new String[7];   
             tmp[0]=rs.getString(2);

             // total, free
             tmp[1]= String.valueOf( getPercentage( rs.getDouble(3), rs.getInt(6)) );
             tmp[2]= String.valueOf( getPercentage( rs.getDouble(3), rs.getInt(7)) );
             tmp[3]= String.valueOf( getPercentage( rs.getDouble(3), rs.getInt(8)) );
             tmp[4]= String.valueOf( getPercentage( rs.getDouble(3), rs.getInt(9)) );

             tmp[5]= String.valueOf( rs.getDouble(4) );
             tmp[6]= String.valueOf( rs.getDouble(5) );

             res.add(tmp);
             }

             rs.close(); 
             dm.close();
        
    } catch (SQLException ex) {
           Logger.getLogger("storici.jsp").log(Level.SEVERE, null, ex);
    }
    
    return res;  
        
}

private String printArrayData(){
    ArrayList<String[]> data = getData();
    Iterator<String[]> dataIte= data.iterator();
    
    String res=new String();
    
    int i=0;
    while (dataIte.hasNext()){
        String[] tmp=dataIte.next();
        res+= "coords["+i+"] = new Array(); ";
        
        for(int j=0;j<tmp.length;j++){
            
            // coords[i][j] = 
            res += "coords["+i+"]["+j+"] =";
            
            if(j==0){
                //"venchi";
                res += "\""+tmp[j]+"\"; ";
            }
            else{
                // 7.67480;
                res += tmp[j]+"; ";
            }   
        }        
        i++;
    }
    
    return res;
}
/*
    coords[0] = new Array(); 
    coords[0][0] = "venchi"; coords[0][1] = 10;coords[0][2] = 45.07243; coords[0][3] = 7.67480;
    coords[1] = new Array(); 
    coords[1][0] = "menchi"; coords[1][1] = 42;coords[1][2] = 45.06355; coords[1][3] = 7.68357;
*/

%>


<!DOCTYPE html>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>TAW - Storici</title>
      
      <link rel="stylesheet" type="text/css" href="css\style.css"> 
                
      <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
      <meta charset="utf-8">
	
      <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
      <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>

<script>
var map;
var coords = new Array();

<%=  printArrayData()  %>
   
function initialize() {
  var center = new google.maps.LatLng(45.070854, 7.676640);
  var mapOptions = {
    zoom: 11,
    center: center,
    mapTypeId: google.maps.MapTypeId.ROADMAP
  };
  map = new google.maps.Map(document.getElementById('map-canvas'),
      mapOptions);
	  
	var index;
	for(index=0;index<coords.length;index++)
	{
		var name = coords[index][0];
                
      var lat = coords[index][5];
		var lng = coords[index][6];
		var marker = new google.maps.Marker({
		position: new google.maps.LatLng(lat,lng),
		map: map,
		//title: 'aggiunto il marker '+index
      title: name
		});
		marker.setMap(map);
	}
	  
}
google.maps.event.addDomListener(window, 'load', initialize);
//print_2d_string_array(coords);

function print_2d_string_array(array) 
{ 
	var row; 
   	   
   var tableStorici = document.getElementById("table-storici");
      
   	
	for (row=0;row<array.length;++row) 
	{ 
				
		/*
		<span>Park 1<br/>Occupati: 3</span>
		<div class="progress-bar">
			<span style="width: 4%;"></span>
		</div>
		*/
			                  
         var nome=document.createElement("td");
         nome.innerHTML=array[row][0];
         
         var stor1=document.createElement("td");
         stor1.innerHTML=array[row][1];
         
         var stor2=document.createElement("td");
         stor2.innerHTML=array[row][2];
         
         var stor3=document.createElement("td");
         stor3.innerHTML=array[row][3];
         
         var stor4=document.createElement("td");
         stor4.innerHTML=array[row][4];         
                 
         
         var tr =document.createElement("tr");
         
         if (row%2 === 0){
            tr.setAttribute("class","alt")
         }
         
         tr.appendChild(nome);
         tr.appendChild(stor1);
         tr.appendChild(stor2);
         tr.appendChild(stor3);
         tr.appendChild(stor4);
         
         tableStorici.appendChild(tr);        
	}

}
</script>
      
   </head>
   
   <body onload="print_2d_string_array(coords);">
      
      <div id="banner">ParcheggiaTorino - Progetto Taw 2012/2013</div>
      <div class="center-link"> <a href="index.jsp">Passa ai Valori Attuali</a> </div>
      <div class="right-link"><a href="jsonProviderServlet">JSON</a> </div>
                  
      <div class="main">
		<div class="container">
		
         <table id="table-storici" >
            <tr>
            <th>Parcheggio</th>
            <th colspan="4">Occupazione Media</th>
            </tr>
            <tr>
            <td></td>
            <th>Mattina</th>
            <th>Pranzo</th>
            <th>Pomeriggio</th>
            <th>Sera</th>
            </tr>

</table>
                  
		</div> <!-- END container -->
         
		<div id="map-canvas"></div>
                  
      </div> <!-- END main -->
      
   </body>
</html>

<%!
/*
public void jspInit() {

   try {
      dm = new DataManager();
    } catch (SQLException ex) {
      Logger.getLogger("storici.jsp").log(Level.SEVERE, null, ex);
    }

}   

public void jspDestroy() {
   
   try {
      dm.close();
   } catch (SQLException ex) {
      Logger.getLogger("storici.jsp").log(Level.SEVERE, null, ex);
   }
     
}
*/
%>