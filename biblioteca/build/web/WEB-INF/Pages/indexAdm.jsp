<%@page import="Bean.UsuarioBean"%>
<jsp:include page="/WEB-INF/Pages/headerAdm.jsp"/>
<jsp:useBean id="usuario" class="UsuarioBean" scope="session"/> 
<center>
    <h1>SEJA BEM VINDO ADMINISTRADOR ${usuario.nome}!</h1>
    <form method="POST" action="./OperationServlet">
        <table>
            <tr>
                <th>
                    Selecione uma das op��es abaixo:
                </th>
            </tr>
            <tr>
                <td><input type="radio" name="tipoOperacao" value="cadastrarAssociado"/> Cadastrar Associado</td>
            </tr>
            <tr>
                <td><input type="radio" name="tipoOperacao" value="cadastrarPublicacao"/> Cadastrar Publica��o</td>
            </tr>
            <tr>
                <td><input type="radio" name="tipoOperacao" value="cadastrarExemplar"/> Cadastrar Exemplar</td>
            </tr>
            <tr>
                <td><input type="radio" name="tipoOperacao" value="consultarPublicacao"/> Consultar Publica��o</td>
            </tr>
            <tr>
                <td><input type="radio" name="tipoOperacao" value="realizarEmprestimo"/> Realizar Empr�stimo</td>
            </tr>
            <tr>
                <td><input type="radio" name="tipoOperacao" value="realizarDevolucao"/> Realizar Devolu��o</td>
            </tr>
            <tr>
                <td><input type="radio" name="tipoOperacao" value="gerarRelatorios"/> Gerar Relat�rios</td>
            </tr>
        </table>
        <input class="btn-success" type="submit" name="submit" value="Submit"/>
    </form>
</center>

</body>
</html>
