<%@page import="Bean.UsuarioBean"%>
<jsp:include page="/WEB-INF/Pages/header.jsp"/>
<jsp:useBean id="usuario" class="UsuarioBean" scope="session"/> 
<jsp:setProperty name="usuario" property="*"/> 

<center>
    <h1>SEJA BEM VINDO ASSOCIADO ${usuario.nome}!</h1>
    <form method="POST" action="./OperationServlet">
        <table>
            <tr>
                <th>
                    Selecione uma das opções abaixo:
                </th>
            </tr>
            <tr>
                <td><input type="radio" name="tipoOperacao" value="consultarPublicacaoAssoc"/> Consultar Publicação</td>
            </tr>
            <tr>
                <td><input type="radio" name="tipoOperacao" value="consultarHistorico"/> Consultar Histórico</td>
            </tr>
        </table>
        <input class="btn-success" type="submit" name="submit" value="Submit"/>
    </form>
</center>

</body>
</html>


<!--

2. Cadastrar publicação: disponibiliza formulário com os seguintes campos: ISBN,
título, autor, editora e ano da publicação. Cada publicação é identificada pelo seu
ISBN, o qual, para efeito de simplificação, pode ser representado por um valor
inteiro.

3. Cadastrar exemplar: disponibiliza formulário com os seguintes campos: número,
ISBN, preço. Cada exemplar é identificado por um número de sequência (iniciando
por 1) e pelo ISBN da publicação.

4. Consultar publicação: dado o ISBN de uma publicação ou seu título, verifica o
status de todos os exemplares da referida publicação, mostrando os emprestados e os
não emprestados.

5. Realizar empréstimo: os empréstimos são feitos através da criação de um registro
contendo o número do exemplar, seu ISBN, a data do empréstimo e o código do
associado que está realizando o empréstimo. Deve ser possível digitar a data de
empréstimo ? a mesma não deve ser gerada automaticamente com a data atual. O
sistema não pode permitir o empréstimo de um exemplar que já esteja emprestado.

6. Realizar devolução: as devoluções ocorrem mediante a entrega do exemplar retirado
pelo associado. No ato da entrega, o sistema deve verificar se a devolução está
ocorrendo dentro do prazo. Se ocorrer fora do prazo, o sistema deve informar o
valor da multa a ser pago (para cada dia de atraso, deve-se pagar R$ 1,00 de multa).

7. Gerar relatórios de associados em atraso, listando o código do associado, seu nome e
a(s) publicação(ões) que está(ão) em atraso.



-->
