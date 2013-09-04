<%-- 
    Document   : index
    Created on : 10-apr-2013, 14.18.30
    Author     : gcasarin
--%>

<%--  <%@page import="" %>  --%>

<%@page import="parkTorino.DataManager" %> 
<%@page import="java.sql.ResultSet" %>
<%@page import="java.sql.SQLException" %>
<%@page import="java.util.logging.Level" %>
<%@page import="java.util.logging.Logger" %>
<%@page import="java.util.List" %> 
<%@page import="java.util.Iterator" %> 
<%@page import="java.util.ArrayList" %>
<%@page import="parkTorino.utils.Parser;" %>



 
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%!
public void jspInit() {
    ; // far partire il parsing e schedularlo
    Thread t = new Thread( new Parser());
    t.run();
}
%>

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

private String test(){
    ArrayList<String[]> data = getData();
    Iterator<String[]> dataIte= data.iterator();
    
    String res=new String();
    
    while (dataIte.hasNext()){
        String[] tmp=dataIte.next();
        
        for(int i=0;i<4;i++){
            res += " "+tmp[i];
        }
        res += "</br>";
    }
    
    return res;
}

%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
                        
        <%=
        test()
        %>
        <a href="test.jsp">vai a test</a>
    </body>
</html>
