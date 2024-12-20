package com.bapt;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddServlet extends HttpServlet
{
  public void service(HttpServletRequest req, HttpServletResponse res) throws IOException 
  {
    String cssTag = "<link rel='stylesheet' type='text/css' href='style.css'>";
    String errorTag = "error";
    String successTag = "success";
    String amount = req.getParameter("num1");
    String curr1 = req.getParameter("curr1");
    String curr2 = req.getParameter("curr2");
    
    double result = 0;
    try
    {
      result = MoneyApi.main(new String [] {amount, curr1, curr2});
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    PrintWriter out = res.getWriter();
    if (result == 0) {
      out.println("<html>");
      out.println("<head><title>Title Name</title>"+cssTag+"</head>");
      out.println("<body>");
      out.println("<div class="+errorTag+">Error with request, please retry.</div>");
      out.println("</body></html>");
      return;
    }
    out.println("<html>");
    out.println("<head><title>Title Name</title>"+cssTag+"</head>");
    out.println("<body>");
    out.println("<div class="+successTag+">");
    out.println(result + " ");
    out.println(curr2);
    out.println("</div>");
    out.println("</body></html>");
  }
}
