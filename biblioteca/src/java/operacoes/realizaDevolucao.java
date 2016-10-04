package operacoes;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import login.Login;

public class realizaDevolucao extends HttpServlet
{

    private PreparedStatement pstmt;
    private String SQL;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        long isbn = Long.parseLong(request.getParameter("isbn"));
        int numero = Integer.parseInt(request.getParameter("numero"));

        // Busca os dados de empréstimo de acordo com o ISBN e Numero
        SQL = "SELECT * FROM emprestimo WHERE isbn = ? AND numero = ?";
        inicializaJdbc();

        try
        {
            ResultSet rs = consultaEmprestimo(isbn, numero);

            if (!rs.next())
            {
                out.println("NÃO FOI ENCONTRADO UM EXEMPLAR EMPRESTADO!");
            } else
            {

                SQL = "SELECT * FROM exemplar WHERE isbn = ? AND numero = ?";
                inicializaJdbc();

                ResultSet rs_exemplar = consultaExemplar(isbn, numero);

                if (rs_exemplar.next() && rs_exemplar.getString("status").equals("Emprestado"))
                {
                    // Recupera a data limite do Banco de Dados
                    String prazoStr = rs.getString("prazo");

                    // Define variáveis auxiliares
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date dataLimite = sdf.parse(prazoStr);
                    Date dataDevolucao = new Date();

                    long diasAtrasado;

                    diasAtrasado = (dataDevolucao.getTime() - dataLimite.getTime()) / (1000 * 60 * 60 * 24);

                    // Compara SE há atraso. <0 NÃO HÁ; >0 HÁ
                    if (diasAtrasado < 0)
                    {
                        SQL = "UPDATE emprestimo SET devolucao = '2016-10-01' WHERE isbn = ? AND numero = ?";
                        inicializaJdbc();
                        devolucaoSemAtraso(isbn, numero);
                    } else
                    {
                        SQL = "UPDATE emprestimo SET devolucao = '2016-10-01', atraso = ? WHERE isbn = ? AND numero = ?";
                        // Se não, atualiza apenas a data de devolucao
                        inicializaJdbc();
                        devolucaoComAtraso(diasAtrasado, isbn, numero);
                    }
                    // Atualiza Status do Exemplar
                    SQL = "UPDATE exemplar SET status = 'Disponivel' WHERE isbn = ? AND numero = ?";
                    inicializaJdbc();
                    atualizaStatus(isbn, numero);

                    out.println("LIVRO DEVOLVIDO COM SUCESSO!");
                } else
                {
                    out.println("ESTE LIVRO NÃO ESTÁ EMPRESTADO");
                }
            }

            out.println("<br/><br/><a href='./OperationServlet'><button>Voltar</button></a>");

        } catch (SQLException ex)
        {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex)
        {
            Logger.getLogger(realizaDevolucao.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private ResultSet consultaEmprestimo(Long isbn, int numero) throws SQLException
    {
        pstmt.setLong(1, isbn);
        pstmt.setInt(2, numero);

        return pstmt.executeQuery();
    }

    private ResultSet consultaExemplar(Long isbn, int numero) throws SQLException
    {
        pstmt.setLong(1, isbn);
        pstmt.setInt(2, numero);

        return pstmt.executeQuery();
    }

    private void atualizaStatus(Long isbn, int numero) throws SQLException
    {
        pstmt.setLong(1, isbn);
        pstmt.setInt(2, numero);

        pstmt.executeUpdate();
    }

    private void devolucaoSemAtraso(Long isbn, int numero) throws SQLException
    {
        pstmt.setLong(1, isbn);
        pstmt.setInt(2, numero);

        pstmt.executeUpdate();
    }

    private void devolucaoComAtraso(long atraso, Long isbn, int numero) throws SQLException
    {
        pstmt.setLong(1, atraso);
        pstmt.setLong(2, isbn);
        pstmt.setInt(3, numero);

        pstmt.executeUpdate();
    }

}
