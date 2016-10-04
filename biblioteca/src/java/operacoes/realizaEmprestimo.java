package operacoes;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import login.Login;

public class realizaEmprestimo extends HttpServlet
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
        String data = request.getParameter("data");
        int codigo = Integer.parseInt(request.getParameter("codigo"));

        // Busca o ISBN de acordo com o título
        SQL = "SELECT * FROM exemplar WHERE isbn = ? AND numero = ?";
        inicializaJdbc();

        try
        {
            ResultSet rs = consultaExemplar(isbn, numero);

            if (!rs.next())
            {
                out.println("ERRO NA BUSCA DO EXEMPLAR");
            } else
            {
                // Testa para ver se está disponível
                if (rs.getString("status").equals("Disponivel"))
                {
                    // Verifica o tipo de usuario
                    SQL = "SELECT * FROM usuarios WHERE codigo = ?";
                    inicializaJdbc();
                    int prazoEstipulado = 0;

                    ResultSet rsUsr = consultaUsuario(codigo);
                    if(rsUsr.next())
                    {
                        if(rsUsr.getString("status").equals("grad"))
                            prazoEstipulado = 7;
                        else if(rsUsr.getString("status").equals("posgrad"))
                            prazoEstipulado = 10;
                        else if(rsUsr.getString("status").equals("prof"))
                            prazoEstipulado = 14;
                    }
                    
                    
                    // Define variáveis auxiliares
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date dataLimite = sdf.parse(data);
                    dataLimite.setDate(dataLimite.getDate() + prazoEstipulado);

                    // Realiza o Empréstimo
                    String limite = sdf.format(dataLimite);
                    SQL = "INSERT INTO emprestimo (isbn, numero, data, idAssociado, prazo, atraso, devolucao) VALUES (?, ?, ?, ?, ?, 0, '0000-00-00')";
                    inicializaJdbc();
                    realizaEmprestimo(isbn, numero, data, codigo, limite);

                    // Atualiza Status do Exemplar
                    SQL = "UPDATE exemplar SET status = 'Emprestado' WHERE isbn = ? AND numero = ?";
                    inicializaJdbc();
                    atualizaStatus(isbn, numero);

                    out.println("LIVRO ALOCADO COM SUCESSO!");

                } else
                {
                    out.println("ESTE LIVRO NÃO ESTÁ DISPONÍVEL");
                }
            }

            out.println("<br/><br/><a href='./OperationServlet'><button>Voltar</button></a>");

        } catch (SQLException ex)
        {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex)
        {
            Logger.getLogger(realizaEmprestimo.class.getName()).log(Level.SEVERE, null, ex);
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

    private ResultSet consultaExemplar(Long isbn, int numero) throws SQLException
    {
        pstmt.setLong(1, isbn);
        pstmt.setInt(2, numero);

        return pstmt.executeQuery();
    }

    private ResultSet consultaUsuario(int codigo) throws SQLException
    {
        pstmt.setInt(1, codigo);

        return pstmt.executeQuery();
    }

    private void realizaEmprestimo(long isbn, int numero, String data, int codigo, String prazo) throws SQLException
    {
        pstmt.setLong(1, isbn);
        pstmt.setInt(2, numero);
        pstmt.setString(3, data);
        pstmt.setInt(4, codigo);
        pstmt.setString(5, prazo);

        pstmt.executeUpdate();
    }

    private void atualizaStatus(Long isbn, int numero) throws SQLException
    {
        pstmt.setLong(1, isbn);
        pstmt.setInt(2, numero);

        pstmt.executeUpdate();
    }

}
