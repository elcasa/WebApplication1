<%-- 
    Document   : test
    Created on : 1-set-2013, 22.54.16
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

<%! private ArrayList<String[]> getData() 
{
    String tmp[];
    DataManager dm =null;
    ArrayList<String[]> res = new ArrayList<String[]>();   
    
    try {
        dm = new DataManager();
  
    ResultSet rs=null;
    
    rs = dm.query("select name,total,free,lat,lng from PARCHEGGI");
    
        
    while (rs.next()) {
        tmp = new String[4];   
        tmp[0]=rs.getString(1);
        // sarebbe int ma faccio getDouble per avere una divisione real
        tmp[1]= String.valueOf( (int)( ( rs.getInt(2)-rs.getInt(3) ) / rs.getDouble(2) * 100 ) ); // percentuale occupata
        tmp[2]= String.valueOf( rs.getDouble(4) );
        tmp[3]= String.valueOf( rs.getDouble(5) );
        
        res.add(tmp);
        }
    } catch (SQLException ex) {
           // Logger.getLogger(ParsingServlet.class.getName()).log(Level.SEVERE, null, ex);
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
<!--	<link rel="stylesheet" type="text/css" href="style.css"> -->
    <title>Simple Map</title>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <style>
      html, body, #map-canvas {
        margin: 200px;
        padding: 50px;
        height: 300px;
		width: 300px;
      }
    </style>
    
<style type="text/css">
	@font-face { font-family: RobotoLight; src: url('Roboto-Light.ttf'); }
	@font-face { font-family: RobotoThin; src: url('Roboto-Thin.ttf'); }
	@font-face { font-family: RobotoRegular; src: url('Roboto-Regular.ttf'); }
	body {
    padding-left: 11em;
    font-family: Verdana;
    /*//color: purple; */
	}
	
	
ul.navbar {
    position: absolute;
    top: 1em;
    left: 1em;
    width: 9em;
    background: #555555;
    /* //margin: 0 auto;  */
	
    font-family: RobotoRegular;
    font-size: 20px;
    width: 200px;
    color: #fff;
}

.parcheggilist {
    position: absolute;
    top: 2em;
    left: 15em;
    width: 9em;
    /* //margin: 0 auto;   */
    font-family: RobotoRegular;
    font-size: 20px;
    width: 400px;
    
    background: #e1ffff; /* Old browsers */
    
    background: -moz-linear-gradient(top, #e1ffff 0%, #c8eefb 5%, #bee4f8 75%, #b1d8f5 100%); /* FF3.6+ */
    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#e1ffff), color-stop(5%,#c8eefb), color-stop(75%,#bee4f8), color-stop(100%,#b1d8f5)); /* Chrome,Safari4+ */
    background: -webkit-linear-gradient(top, #e1ffff 0%,#c8eefb 5%,#bee4f8 75%,#b1d8f5 100%); /* Chrome10+,Safari5.1+ */
    background: -o-linear-gradient(top, #e1ffff 0%,#c8eefb 5%,#bee4f8 75%,#b1d8f5 100%); /* Opera 11.10+ */
    background: -ms-linear-gradient(top, #e1ffff 0%,#c8eefb 5%,#bee4f8 75%,#b1d8f5 100%); /* IE10+ */
    background: linear-gradient(to bottom, #e1ffff 0%,#c8eefb 5%,#bee4f8 75%,#b1d8f5 100%); /* W3C */
    filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#e1ffff', endColorstr='#b1d8f5',GradientType=0 ); /* IE6-9 */
}

.parcheggilist span{
    font-size: 15px;
}

.progress-bar {
    background-color: #33b5e5;
    height: 25px;
    padding: 2px;
    width: 360px;
    margin: 60px 0;
            
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
    border-radius: 5px;
            
    -moz-box-shadow: 0 1px 5px #000 inset, 0 1px 0 #444;
    -webkit-box-shadow: 0 1px 5px #000 inset, 0 1px 0 #444;
    box-shadow: 0 1px 5px #000 inset, 0 1px 0 #444;
}

.progress-bar span {
    display: inline-block;
    height: 25px;
    width: 50%;
            
    -moz-border-radius: 3px;
    -webkit-border-radius: 3px;
    border-radius: 3px;
            
    -moz-box-shadow: 0 1px 0 rgba(255, 255, 255, .5) inset;
    -webkit-box-shadow: 0 1px 0 rgba(255, 255, 255, .5) inset;
    box-shadow: 0 1px 0 rgba(255, 255, 255, .5) inset;

    background: rgb(1,182,178);
    background: linear-gradient(bottom, rgb(1,182,178) 39%, rgb(27,219,214) 70%, rgb(54,255,255) 85%);
    background: -o-linear-gradient(bottom, rgb(1,182,178) 39%, rgb(27,219,214) 70%, rgb(54,255,255) 85%);
    /* //background: -moz-linear-gradient(bottom, rgb(1,182,178) 39%, rgb(27,219,214) 70%, rgb(54,255,255) 85%); */
    background: -moz-linear-gradient(left, #3ef207 0%, #eaa800 49%, #ff3a3a 100%);
    background: -webkit-linear-gradient(bottom, rgb(1,182,178) 39%, rgb(27,219,214) 70%, rgb(54,255,255) 85%);
    background: -ms-linear-gradient(bottom, rgb(1,182,178) 39%, rgb(27,219,214) 70%, rgb(54,255,255) 85%);
    
    background: -webkit-gradient(
    	linear,
    	left bottom,
    	left top,
    	color-stop(0.39, rgb(1,182,178)),
    	color-stop(0.7, rgb(27,219,214)),
    	color-stop(0.85, rgb(54,255,255))
    );

    -webkit-transition: width .4s ease-in-out;
    -moz-transition: width .4s ease-in-out;
    -ms-transition: width .4s ease-in-out;
    -o-transition: width .4s ease-in-out;
    transition: width .4s ease-in-out;
    
}
</style>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>

<script>
var map;
var coords = new Array();
// name, occupied, lat, lng
    /*
    coords[0] = new Array(); 
    coords[0][0] = "venchi"; coords[0][1] = 10;coords[0][2] = 45.07243; coords[0][3] = 7.67480;
    coords[1] = new Array(); 
    coords[1][0] = "menchi"; coords[1][1] = 42;coords[1][2] = 45.06355; coords[1][3] = 7.68357;
    coords[2] = new Array(); 
    coords[2][0] = "manchi"; coords[2][1] = 80;coords[2][2] = 45.07248; coords[2][3] = 7.67754;
    */
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
	  
	  //aggiungo i marker
	  //var marker = new google.maps.Marker({
      //position: new google.maps.LatLng(coords[0][0],coords[0][1]),
      //map: map,
      //title: 'aggiunto il marker'
	//});

}
google.maps.event.addDomListener(window, 'load', initialize);
print_2d_string_array(coords);

function print_2d_string_array(array) 
{ 
	var row; 
	document.writeln("<div class=\"parcheggilist\">");
	for (row=0;row<array.length;++row) 
	{ 
		//var col;
		//for(col = 0;col<array[row].length;++col)
		//	document.writeln("<td>"+array[row][col]+"</td>");
		//	document.writeln (" </tr>");
		//for(col = 0;col<array[row].length;++col)
		//{
		document.writeln("<p>Parcheggio "+array[row][0]);
                /*
		document.writeln("<span>");
		document.writeln("<a href='viewPark.html'><img src=\"images/ic_menu_myplaces.png\" width=\"50\" height=\"50\" /></a>")
		document.writeln("</span>");
                */
               // document.writeln("</p>");
                document.writeln("Posti Occupati: "+array[row][1]+"%");
                document.writeln("</p>");
		document.writeln("<div class=\"progress-bar\">");
		//document.writeln("<span></span>");
                document.writeln("<span style=\"width: "+array[row][1]+"%;\"></span>");
		document.writeln("</div>");
                
		//}	
	}
	document.writeln("</div>");
}
    </script>
    <!-- 
	<script type="text/javascript">
		$(function(){ 

		$('#bar_change').click(function(){
			var bar = $('.progress-bar span');
			var value = parseInt($('#bar_value').val()) > 100 ? 100 : $('#bar_value').val();
			bar.css('width', value + '%');
       
			return false;
		});
		});
	</script>
    -->
  </head>
  <body>
	<ul style="list-style: none" class="navbar">
		<li>Aggiorna lista</li>
		<li><img src="images/ic_menu_mapmode.png" width="50" height="50" />Vai alla mappa</li>
		<li><a href='map.html'>Lista parcheggi</a></li>
	</ul>
    <div id="map-canvas"></div>
    
    <div> 
        <%=  printArrayData()  %> 
    </div>
    <!--
	<form>
		Partenza <input type="text" name="start_address">
	</form>
	<div class="progress-bar">
		<span></span>
	</div>
	<div class="controls">
        <label for="bar_value">Valore</label>
        <input type="text" name="bar_value" id="bar_value" value="100" />
        <input type="submit" id="bar_change" value="Cambia" />
        -->
    </div>
  </body>
</html>

