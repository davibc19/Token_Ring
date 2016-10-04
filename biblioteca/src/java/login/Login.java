package login;

import Bean.UsuarioBean;
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
import javax.servlet.http.HttpSession;

public class Login extends HttpServlet
{

    private PreparedStatement pstmt;
    private String selectSQL;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        // Estrutura a consulta no BD e inicializa o Banco com o driver JDBC
        selectSQL = "SELECT * FROM usuarios WHERE login = ? AND senha = ?";
        inicializaJdbc();

        // Busca os dados. Se encontrar, então ele pode realizar o login. Se não, avisa que ele não pode
        try
        {
            ResultSet rs = consultaLogin(login, senha);

            if (!rs.next())
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Responses/ErroLogin.jsp");
                dispatcher.forward(request, response);
            } else
            {
                UsuarioBean usuario = new UsuarioBean();

                usuario.setCod(rs.getInt("codigo"));
                usuario.setNome(rs.getString("nome"));
                usuario.setTipo(rs.getString("tipo"));

                HttpSession sessao = request.getSession();
                sessao.setAttribute("usuario", usuario);
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

        } catch (SQLException ex)
        {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void inicializaJdbc()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");
            pstmt = c.prepareStatement(selectSQL);
        } catch (Exception ex)
        {
            System.out.println("Erro na conexão com o Banco de Dados: " + ex);
        }
    }

    private ResultSet consultaLogin(String login, String senha) throws SQLException
    {
        pstmt.setString(1, login);
        pstmt.setString(2, senha);

        return pstmt.executeQuery();
    }

}
