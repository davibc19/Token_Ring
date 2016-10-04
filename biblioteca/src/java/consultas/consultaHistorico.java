package consultas;

import Bean.UsuarioBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import operacoes.realizaDevolucao;

public class consultaHistorico extends HttpServlet
{

    private PreparedStatement pstmt;
    private String SQL;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        // Recupera dados da Sessão
        UsuarioBean usuario = new UsuarioBean();
        HttpSession sessao = request.getSession();
        usuario = (UsuarioBean) sessao.getAttribute("usuario");

        // Busca os dados de todos os exemplares emprestados
        SQL = "SELECT * FROM exemplar AS ex JOIN emprestimo AS emp ON ex.isbn = emp.isbn AND ex.numero = emp.numero "
                + "JOIN usuarios AS usr ON usr.codigo = emp.idAssociado "
                + "WHERE usr.codigo = ?";
        inicializaJdbc();

        try
        {
            ResultSet rs = consultaHistorico(usuario.getCod());

            while (rs.next())
            {
                // Se o livro ainda não foi devolvido
                if (rs.getString("ex.status").equals("Emprestado"))
                {
                    // Recupera a data limite do Banco de Dados
                    String prazoStr = rs.getString("emp.prazo");

                    // Define variáveis auxiliares
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date dataLimite = sdf.parse(prazoStr);
                    Date dataDevolucao = new Date();

                    long diasAtrasado;

                    diasAtrasado = (dataDevolucao.getTime() - dataLimite.getTime()) / (1000 * 60 * 60 * 24);

                    // Compara SE há atraso. <0 NÃO HÁ; >0 HÁ
                    if (diasAtrasado > 0)
                    {
                        out.println("<p><b>-- PUBLICAÇÃO ATRASADA --</b>");
                        out.println("<br/>PUBLICACAO ATRASADA: " + rs.getString("ex.isbn"));
                        out.println("<br/>DATA DE EMPRÉSTIMO: " + rs.getString("emp.data"));
                        out.println("<br/>PRAZO PARA DEVOLUÇÃO: " + rs.getString("emp.prazo"));
                        out.println("<br/>DIAS DE ATRASO: " + diasAtrasado + " dia(s)");
                        out.println("<br/>VALOR DA MULTA: R$" + diasAtrasado + ",00");
                        out.println("<hr/>");
                        out.println("</p>");
                    } else
                    {
                        out.println("<p><b>-- PUBLICAÇÃO EMPRESTADA --</b>");
                        out.println("<br/>PUBLICACAO ATRASADA: " + rs.getString("ex.isbn"));
                        out.println("<br/>DATA DE EMPRÉSTIMO: " + rs.getString("emp.data"));
                        out.println("<br/>PRAZO PARA DEVOLUÇÃO: " + rs.getString("emp.prazo"));
                        out.println("<hr/>");
                        out.println("</p>");
                    }
                } else
                {
                    out.println("<p><b>-- PUBLICAÇÃO DEVOLVIDA --</b>");
                    out.println("<br/>PUBLICACAO: " + rs.getString("ex.isbn"));
                    out.println("<br/>DATA DE EMPRÉSTIMO: " + rs.getString("emp.data"));
                    out.println("<br/>PRAZO PARA DEVOLUÇÃO: " + rs.getString("emp.prazo"));
                    out.println("<br/>DATA DE DEVOLUÇÃO: " + rs.getString("emp.devolucao"));
                    out.println("<br/>DIAS DE ATRASO: " + rs.getString("emp.atraso") + " dia(s)");
                    out.println("<br/>VALOR DA MULTA: R$" + rs.getString("emp.atraso") + ",00");
                    out.println("<hr/>");
                    out.println("</p>");
                }
            }
        } catch (Exception ex)
        {
            Logger.getLogger(realizaDevolucao.class.getName()).log(Level.SEVERE, null, ex);
        }

        out.println("<br/><br/><a href='./OperationServlet'><button>Voltar</button></a>");
    }

    private void inicializaJdbc()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");
            pstmt = c.prepareStatement(SQL);
        } catch (Exception ex)
        {
            System.out.println("Erro na conexão com o Banco de Dados: " + ex);
        }
    }

    private ResultSet consultaHistorico(int cod) throws SQLException
    {
        pstmt.setInt(1, cod);
        return pstmt.executeQuery();
    }

    private void atualizaAtraso(long atraso, Long isbn, int numero) throws SQLException
    {
        pstmt.setLong(1, atraso);
        pstmt.setLong(2, isbn);
        pstmt.setInt(3, numero);

        pstmt.executeUpdate();
    }

}
