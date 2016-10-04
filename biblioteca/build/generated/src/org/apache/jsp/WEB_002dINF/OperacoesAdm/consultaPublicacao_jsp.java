package org.apache.jsp.WEB_002dINF.OperacoesAdm;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class consultaPublicacao_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/WEB-INF/Pages/header.jsp", out, false);
      out.write("\n");
      out.write("<center>\n");
      out.write("    <h1><b>CADASTRAR EXEMPLAR</b></h1>\n");
      out.write("    <form action=\"./consultaPublicacao\" method=\"POST\">\n");
      out.write("        <table>\n");
      out.write("            <tr>\n");
      out.write("                <th><label for=\"isbn\"><b>ISBN</b></label></th>\n");
      out.write("                <td><input type=\"text\" name=\"isbn\" id=\"isbn\"/></td>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("                <th><label for=\"titulo\"><b>TITULO</b></label></th>\n");
      out.write("                <td><input type=\"text\" name=\"titulo\" id=\"titulo\"/></td>\n");
      out.write("            </tr>\n");
      out.write("            <tr style=\"text-align: center\">\n");
      out.write("                <td><input type=\"submit\" name=\"submit\" value=\"Submit\"></td>\n");
      out.write("                <td><input type=\"reset\" name=\"reset\" value=\"Reset\"></td>\n");
      out.write("            </tr>\n");
      out.write("        </table>\n");
      out.write("    </form>\n");
      out.write("</center>\n");
      out.write("\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
