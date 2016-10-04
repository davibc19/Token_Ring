package cadastros;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import login.Login;

public class cadastraExemplar extends HttpServlet
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
        Float preco = Float.parseFloat(request.getParameter("preco"));
        int numero = 0;

        // Estrutura a consulta no BD e inicializa o Banco com o driver JDBC
        SQL = "SELECT * FROM publicacao WHERE isbn = ?";
        inicializaJdbc();

        try
        {
            ResultSet rs = consultaQuantidade(isbn);

            if (!rs.next())
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Responses/Erro.jsp");
                dispatcher.forward(request, response);
            } else
            {
                numero = rs.getInt("quantidade");
                numero = numero + 1;
                SQL = "UPDATE publicacao SET quantidade = ? WHERE isbn = ?";
                inicializaJdbc();

                atualizaPublicacao(numero, isbn);
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Estrutura a consulta no BD e inicializa o Banco com o driver JDBC
        SQL = "INSERT INTO exemplar (isbn, numero, preco)"
                + " VALUES (?, ?, ?)";
        inicializaJdbc();

        try
        {
            // Realiza a inserção dos dados e redireciona para a página administrativa!
            inserePublicacao(isbn, numero, preco);
        } catch (SQLException ex)
        {
            Logger.getLogger("ERRO NA INSERÇÃO DO USUARIO: " + cadastraAssociado.class.getName()).log(Level.SEVERE, null, ex);
        }

        out.println("Cadastro de Exemplar Bem Sucedido!");
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

    private ResultSet consultaQuantidade(Long isbn) throws SQLException
    {
        pstmt.setLong(1, isbn);

        return pstmt.executeQuery();
    }

    private void inserePublicacao(long isbn, int numero, float preco) throws SQLException
    {
        pstmt.setLong(1, isbn);
        pstmt.setInt(2, numero);
        pstmt.setFloat(3, preco);

        pstmt.executeUpdate();
    }

    private void atualizaPublicacao(int quantidade, long isbn) throws SQLException
    {
        pstmt.setInt(1, quantidade);
        pstmt.setLong(2, isbn);

        pstmt.executeUpdate();
    }
}
