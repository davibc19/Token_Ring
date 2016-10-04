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

public class cadastraAssociado extends HttpServlet
{

    private PreparedStatement pstmt;
    private String insertSQL;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        int codigo = Integer.parseInt(request.getParameter("codigo"));
        String status = request.getParameter("status");
        String nome = request.getParameter("nome");
        String endereco = request.getParameter("endereco");
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        // Estrutura a consulta no BD e inicializa o Banco com o driver JDBC
        insertSQL = "INSERT INTO usuarios (tipo, codigo, status, nome, endereco, email, login, senha)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        inicializaJdbc();

        try
        {
            // Realiza a inserção dos dados e redireciona para a página administrativa!
            insereUsuario(codigo, status, nome, endereco, email, login, senha);
        } catch (SQLException ex)
        {
            Logger.getLogger("ERRO NA INSERÇÃO DO USUARIO: " + cadastraAssociado.class.getName()).log(Level.SEVERE, null, ex);
        }

        out.println("Associado Cadastrado com Sucesso!");
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

    private void insereUsuario(int codigo, String status, String nome, String endereco,
            String email, String login, String senha) throws SQLException
    {
        pstmt.setString(1, "assoc");
        pstmt.setInt(2, codigo);
        pstmt.setString(3, status);
        pstmt.setString(4, nome);
        pstmt.setString(5, endereco);
        pstmt.setString(6, email);
        pstmt.setString(7, login);
        pstmt.setString(8, senha);

        pstmt.executeUpdate();
    }

}
