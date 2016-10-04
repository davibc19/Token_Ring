package org.apache.jsp.WEB_002dINF.OperacoesAdm;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class cadastraAssociado_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<!--\n");
      out.write("To change this license header, choose License Headers in Project Properties.\n");
      out.write("To change this template file, choose Tools | Templates\n");
      out.write("and open the template in the editor.\n");
      out.write("-->\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <title>Sistema da Biblioteca</title>\n");
      out.write("        <meta charset=\"UTF-8\">\n");
      out.write("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("    <center>\n");
      out.write("        <h1><b>CADASTRAR ASSOCIADOS</b></h1>\n");
      out.write("        <form action=\"./OperationServlet\" method=\"POST\">\n");
      out.write("            <table>\n");
      out.write("                <tr>\n");
      out.write("                    <th><label for=\"login\"><b>CÓDIGO</b></label></th>\n");
      out.write("                    <td><input type=\"text\" required name=\"codigo\" id=\"codigo\"/></td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <th><label for=\"status\"><b>STATUS</b></label></th>\n");
      out.write("                    <td>\n");
      out.write("                        <select>\n");
      out.write("                            <option value=\"grad\">Aluno de Graduação</option>\n");
      out.write("                            <option value=\"posgrad\">Aluno de Pós Graduação</option>\n");
      out.write("                            <option value=\"prof\">Professor</option>\n");
      out.write("                        </select>\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <th><label for=\"nome\"><b>NOME</b></label></th>\n");
      out.write("                    <td><input type=\"text\" required name=\"nome\" id=\"nome\"/></td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <th><label for=\"endereco\"><b>ENDEREÇO</b></label></th>\n");
      out.write("                    <td><input type=\"text\" required name=\"endereco\" id=\"endereco\"/></td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <th><label for=\"email\"><b>EMAIL</b></label></th>\n");
      out.write("                    <td><input type=\"text\" required name=\"email\" id=\"email\"/></td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <th><label for=\"login\"><b>LOGIN</b></label></th>\n");
      out.write("                    <td><input type=\"text\" required name=\"login\" id=\"login\"/></td>\n");
      out.write("                </tr>\n");
      out.write("                <tr>\n");
      out.write("                    <th><label for=\"senha\"><b>SENHA</b></label></th>\n");
      out.write("                    <td><input type=\"password\" required name=\"senha\" id=\"senha\"/></td>\n");
      out.write("                </tr>\n");
      out.write("                <tr style=\"text-align: center\">\n");
      out.write("                    <td><input type=\"submit\" name=\"submit\" value=\"Submit\"></td>\n");
      out.write("                    <td><input type=\"reset\" name=\"reset\" value=\"Reset\"></td>\n");
      out.write("                </tr>\n");
      out.write("            </table>\n");
      out.write("\n");
      out.write("        </form>\n");
      out.write("    </center>\n");
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
