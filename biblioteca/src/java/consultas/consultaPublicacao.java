package consultas;

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

public class consultaPublicacao extends HttpServlet
{

    private PreparedStatement pstmt;
    private String SQL;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String titulo = request.getParameter("titulo");
        String isbnStr = request.getParameter("isbn");
        long isbn = 0;

        // Estrutura a consulta no BD e inicializa o Banco com o driver JDBC
        if (isbnStr.equals("") && !titulo.equals(""))
        {
            // Busca o ISBN de acordo com o título
            SQL = "SELECT * FROM publicacao WHERE titulo = ?";
            inicializaJdbc();
            try
            {
                ResultSet rs = consultaIsbnPorTitulo(titulo);
                if (!rs.next())
                {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Responses/Erro.jsp");
                    dispatcher.forward(request, response);
                } else
                {
                    isbn = Long.parseLong(rs.getString("isbn"));
                }
            } catch (Exception ex)
            {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (isbnStr.equals("") && titulo.equals(""))
        {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Responses/Erro.jsp");
            dispatcher.forward(request, response);
        } else if (!isbnStr.equals(""))
        {
            isbn = Long.parseLong(isbnStr);
        }

        SQL = "SELECT * FROM exemplar AS e JOIN publicacao AS p ON e.isbn = p.isbn WHERE e.isbn = ?";
        inicializaJdbc();

        try
        {
            ResultSet rs = consultaPublicacao(isbn);

            if (!rs.next())
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Responses/Erro.jsp");
                dispatcher.forward(request, response);
            } else
            {
                do
                {
                    out.println("<html>"
                            + "<body>"
                            + "<b>ISBN: </b>" + rs.getString("e.isbn") + ""
                            + "<br/><b>TITULO: </b>" + rs.getString("p.titulo") + ""
                            + "<br/><b>NÚMERO DO EXEMPLAR:</b> " + rs.getString("e.numero") + ""
                            + "<br/><b>STATUS:</b> "+rs.getString("e.status")
                            + "<hr/>"
                            + "</body>"
                            + "</html>");
                } while (rs.next());
            }
            
            out.println("<br/><br/><a href='./OperationServlet'><button>Voltar</button></a>");

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
            pstmt = c.prepareStatement(SQL);
        } catch (Exception ex)
        {
            System.out.println("Erro na conexão com o Banco de Dados: " + ex);
        }
    }

    private ResultSet consultaPublicacao(Long isbn) throws SQLException
    {
        pstmt.setLong(1, isbn);

        return pstmt.executeQuery();
    }

    private ResultSet consultaIsbnPorTitulo(String titulo) throws SQLException
    {
        pstmt.setString(1, titulo);

        return pstmt.executeQuery();
    }

}
