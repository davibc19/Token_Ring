<jsp:include page="/WEB-INF/Pages/header.jsp"/>
<center>
    <h1><b>CONSULTAR PUBLICAÇÃO</b></h1>
    <form action="./consultaPublicacaoAssoc" method="POST">
        <table>
            <tr>
                <th><label for="isbn"><b>ISBN</b></label></th>
                <td><input type="text" name="isbn" id="isbn"/></td>
            </tr>
            <tr>
                <th><label for="titulo"><b>TITULO</b></label></th>
                <td><input type="text" name="titulo" id="titulo"/></td>
            </tr>
            <tr style="text-align: center">
                <td><input type="submit" name="submit" value="Submit"></td>
                <td><input type="reset" name="reset" value="Reset"></td>
            </tr>
        </table>
    </form>
</center>

</body>
</html>
