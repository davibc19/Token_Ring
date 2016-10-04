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
                    Selecione uma das op��es abaixo:
                </th>
            </tr>
            <tr>
                <td><input type="radio" name="tipoOperacao" value="consultarPublicacaoAssoc"/> Consultar Publica��o</td>
            </tr>
            <tr>
                <td><input type="radio" name="tipoOperacao" value="consultarHistorico"/> Consultar Hist�rico</td>
            </tr>
        </table>
        <input class="btn-success" type="submit" name="submit" value="Submit"/>
    </form>
</center>

</body>
</html>


<!--

2. Cadastrar publica��o: disponibiliza formul�rio com os seguintes campos: ISBN,
t�tulo, autor, editora e ano da publica��o. Cada publica��o � identificada pelo seu
ISBN, o qual, para efeito de simplifica��o, pode ser representado por um valor
inteiro.

3. Cadastrar exemplar: disponibiliza formul�rio com os seguintes campos: n�mero,
ISBN, pre�o. Cada exemplar � identificado por um n�mero de sequ�ncia (iniciando
por 1) e pelo ISBN da publica��o.

4. Consultar publica��o: dado o ISBN de uma publica��o ou seu t�tulo, verifica o
status de todos os exemplares da referida publica��o, mostrando os emprestados e os
n�o emprestados.

5. Realizar empr�stimo: os empr�stimos s�o feitos atrav�s da cria��o de um registro
contendo o n�mero do exemplar, seu ISBN, a data do empr�stimo e o c�digo do
associado que est� realizando o empr�stimo. Deve ser poss�vel digitar a data de
empr�stimo ? a mesma n�o deve ser gerada automaticamente com a data atual. O
sistema n�o pode permitir o empr�stimo de um exemplar que j� esteja emprestado.

6. Realizar devolu��o: as devolu��es ocorrem mediante a entrega do exemplar retirado
pelo associado. No ato da entrega, o sistema deve verificar se a devolu��o est�
ocorrendo dentro do prazo. Se ocorrer fora do prazo, o sistema deve informar o
valor da multa a ser pago (para cada dia de atraso, deve-se pagar R$ 1,00 de multa).

7. Gerar relat�rios de associados em atraso, listando o c�digo do associado, seu nome e
a(s) publica��o(�es) que est�(�o) em atraso.



-->
