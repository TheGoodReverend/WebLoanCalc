<%-- 
    Document   : CalcLoan
    Created on : Jan 24, 2021, 12:27:40 PM
    Author     : KBowe
--%>

<%@page import="java.text.NumberFormat"%>
<%@page import="business.Loan"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Loan Schedule</title>
    </head>
    <body>
        <h1>Monthly Payment Schedule by KBowen</h1>
        
        <% //scriptlet
            
            try{
                NumberFormat curr = NumberFormat.getCurrencyInstance();
                NumberFormat pct = NumberFormat.getPercentInstance();
                double p = Double.parseDouble( request.getParameter("prinbal"));
                double r = Double.parseDouble( request.getParameter("intrate"));
                int t = Integer.parseInt( request.getParameter("term"));
            
                Loan ln = new Loan(p,r,t);
                
                if (!ln.getErrorMsg().isEmpty())
                {
        %>
        
        <p> <%= ln.getErrorMsg() %> <p/>
        <% } else { %>  
        
        
        <p> A payment of <%= curr.format(ln.getMonthlyPayment())%> is required to pay off a loan of <%= curr.format(ln.getPrincipal())%> in <%= ln.getTerm()%> months at a rate of <%= pct.format(ln.getRate())%> </p>
        
        <br>
        <table border ="1">
            <tr> 
                <th>Month</th>
                <th>Beg.Bal.</th>
                <th>Int.Charge</th>
                <th>Prin.Amt.</th>
                <th>End.Bal.</th>
            </tr>
            <% for (int i=1; i<= ln.getTerm(); i++) { %>
            <tr>
                <td align="center"><%= i %></td>
                <td align="center"><%= curr.format(ln.getBegBal(i)) %></td>
                <td align="center"><%= curr.format(ln.getIntCharged(i)) %></td>
                <td align="center"><%= curr.format(ln.getMonthlyPayment() - ln.getIntCharged(i)) %></td> </td>
                <td align="center"><%= curr.format(ln.getEndBal(i)) %></td>
            </tr>
            <% }} %>
            
        <%} catch (Exception e)    {%>
        <br><p> Process error on data validation: <%= e.getMessage() %> </p>    
        <% } %>    
    </body>
</html>
