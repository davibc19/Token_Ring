/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Bean.UsuarioBean;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OperationServlet extends HttpServlet
{

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String operacao = request.getParameter("tipoOperacao");
        String caminho = null;

        if (operacao == null)
        {
            caminho = "/WEB-INF/Responses/ErroSelecaoAdm.jsp";
        } else if (operacao.equals("cadastrarAssociado"))
        {
            caminho = "/WEB-INF/OperacoesAdm/cadastraAssociado.jsp";
        } else if (operacao.equals("cadastrarPublicacao"))
        {
            caminho = "/WEB-INF/OperacoesAdm/cadastraPublicacao.jsp";
        } else if (operacao.equals("cadastrarExemplar"))
        {
            caminho = "/WEB-INF/OperacoesAdm/cadastraExemplar.jsp";
        } else if (operacao.equals("consultarPublicacao"))
        {
            caminho = "/WEB-INF/OperacoesAdm/consultaPublicacao.jsp";
        } else if (operacao.equals("realizarEmprestimo"))
        {
            caminho = "/WEB-INF/OperacoesAdm/realizaEmprestimo.jsp";
        } else if (operacao.equals("realizarDevolucao"))
        {
            caminho = "/WEB-INF/OperacoesAdm/realizaDevolucao.jsp";
        } else if (operacao.equals("gerarRelatorios"))
        {
            caminho = "/WEB-INF/OperacoesAdm/geraRelatorio.jsp";
        } else if (operacao.equals("consultarPublicacaoAssoc"))
        {
            caminho = "/WEB-INF/OperacoesAssoc/consultaPublicacao.jsp";
        } else if (operacao.equals("consultarHistorico"))
        {
            caminho = "/WEB-INF/OperacoesAssoc/consultaHistorico.jsp";
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(caminho);
        dispatcher.forward(request, response);
    }

    // Trada o redirecionamento para a PÁGINA INICIAL
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        UsuarioBean usuario = new UsuarioBean();
        HttpSession sessao = request.getSession();
        
        usuario = (UsuarioBean) sessao.getAttribute("usuario");
        if (usuario.getTipo().equals("assoc"))
        {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Pages/indexAssoc.jsp");
            dispatcher.forward(request, response);
        } else if (usuario.getTipo().equals("adm"))
        {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Pages/indexAdm.jsp");
            dispatcher.forward(request, response);
        }
    }
}
