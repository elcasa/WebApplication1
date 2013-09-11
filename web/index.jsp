<%-- 
    Document   : newjsp
    Created on : 3-set-2013, 19.36.48
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
   
public void jspInit() {
   
   System.out.println("INIT");
    // far partire il parsing e schedularlo
   
    Thread t = new Thread( new Parser());
    t.run();
           
    try {
         t.join();
    } catch (InterruptedException ie) {
    
    }
}
%>

<%! private ArrayList<String[]> getData() 
{
    String tmp[];
    //DataManager dm =null;
    ArrayList<String[]> res = new ArrayList<String[]>();   
    
    try {
    dm = new DataManager();
  
    ResultSet rs=null;
    
    rs = dm.query("select name,total,free,lat,lng from PARCHEGGI");
    
        
    while (rs.next()) {
        tmp = new String[4];   
        tmp[0]=rs.getString(1);
        
        tmp[1]= String.valueOf( (int)( ( rs.getInt(2)-rs.getInt(3) ) / rs.getDouble(2) * 100 ) ); // percentuale occupata
        tmp[2]= String.valueOf( rs.getDouble(4) );
        tmp[3]= String.valueOf( rs.getDouble(5) );
        
        res.add(tmp);
        }
    
        rs.close(); 
        dm.close();
    
    } catch (SQLException ex) {
           Logger.getLogger("index.jsp").log(Level.SEVERE, null, ex);
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
        
        for(int j=0;j<4;j++){
            
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
      <title>TAW - Valori Attuali, index</title>
      
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
                
      var lat = coords[index][2];
		var lng = coords[index][3];
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


function print_2d_string_array(array) 
{ 
	var row; 
   	
	var parkList = document.getElementById("parkList");
   	
	for (row=0;row<array.length;++row) 
	{ 
				
		/*
		<span>Park 1<br/>Occupati: 3</span>
		<div class="progress-bar">
			<span style="width: 4%;"></span>
		</div>
		*/
			
         			
			var span=document.createElement("span");
			span.innerHTML=array[row][0]+" Occupati: "+array[row][1]+"%";
         			
			var div=document.createElement("div");
         div.setAttribute("class","progress-bar");
						
			var span2=document.createElement("span");
         span2.setAttribute("style","width: "+array[row][1]+"%;")
						
			div.appendChild(span2);
			
			parkList.appendChild(span);
			parkList.appendChild(div);
       
	}

}
</script>
      
   </head>
   
   <body onload="print_2d_string_array(coords);">
      
      <div id="banner">ParcheggiaTorino - Progetto Taw 2012/2013</div>
      <div class="center-link"> <a href="storici.jsp">Passa agli Storici</a> </div>
      <div class="right-link"><a href="jsonProviderServlet">JSON</a> </div>
                  
      <div class="main">
		<div class="container">
		
			<div id="parkList" class="parcheggilist">
			 
         <!--
				<span >Park 1<br/>Occupati: 3</span>
				<div class="progress-bar">
				<span style="width: 4%;"></span>
				</div>
			-->
						
			</div>
                  
		</div> <!-- END container -->
         
		<div id="map-canvas"></div>
                  
      </div> <!-- END main -->
      
   </body>
</html>

<%!

/*
    INIT
    // mi connetto al db
    try {
      dm = new DataManager();
    } catch (SQLException ex) {
      Logger.getLogger("index.jsp").log(Level.SEVERE, null, ex);
    }
    */
/*
public void jspDestroy() {
   
   System.out.println("DESTROY");
   
   try {
      dm.close();
   } catch (SQLException ex) {
      Logger.getLogger("index.jsp").log(Level.SEVERE, null, ex);
   }
     
}
*/
%>