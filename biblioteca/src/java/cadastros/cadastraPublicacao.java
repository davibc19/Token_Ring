package cadastros;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class cadastraPublicacao extends HttpServlet
{
    private PreparedStatement pstmt;
    private String insertSQL;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        long isbn = Long.parseLong(request.getParameter("isbn"));
        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");
        String editora = request.getParameter("editora");
        int ano = Integer.parseInt(request.getParameter("anoPublicacao"));

        // Estrutura a consulta no BD e inicializa o Banco com o driver JDBC
        insertSQL = "INSERT INTO publicacao (isbn, titulo, autor, editora, ano)"
                + " VALUES (?, ?, ?, ?, ?)";
        inicializaJdbc();

        try
        {
            // Realiza a inserção dos dados e redireciona para a página administrativa!
            inserePublicacao(isbn, titulo, autor, editora, ano);
        } catch (SQLException ex)
        {
            Logger.getLogger("ERRO NA INSERÇÃO DO USUARIO: " + cadastraAssociado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        out.println("Publicação Cadastrada com Sucesso");
        out.println("<br/><br/><a href='./OperationServlet'><button>Voltar</button></a>");

    }

    private void inicializaJdbc()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");
            pstmt = c.prepareStatement(insertSQL);
        } catch (Exception ex)
        {
            System.out.println("Erro na conexão com o Banco de Dados: " + ex);
        }
    }

    private void inserePublicacao(long isbn, String titulo, String autor, String editora, int ano) throws SQLException
    {
        pstmt.setLong(1, isbn);
        pstmt.setString(2, titulo);
        pstmt.setString(3, autor);
        pstmt.setString(4, editora);
        pstmt.setInt(5, ano);

        pstmt.executeUpdate();
    }

}
