<jsp:include page="/WEB-INF/Pages/header.jsp"/>
<center>
    <h1><b>CADASTRAR PUBLICACAO</b></h1>
    <form action="./cadastraPublicacao" method="POST">
        <table>
            <tr>
                <th><label for="isbn"><b>ISBN</b></label></th>
                <td><input type="text" required name="isbn" id="isbn"/></td>
            </tr>
            <tr>
                <th><label for="titulo"><b>TITULO</b></label></th>
                <td><input type="text" required name="titulo" id="titulo"/></td>
            </tr>
            <tr>
                <th><label for="autor"><b>AUTOR</b></label></th>
                <td><input type="text" required name="autor" id="autor"/></td>
            </tr>
            <tr>
                <th><label for="editora"><b>EDITORA</b></label></th>
                <td><input type="text" required name="editora" id="editora"/></td>
            </tr>
            <tr>
                <th><label for="anoPublicacao"><b>ANO DE PUBLICAÇÃO</b></label></th>
                <td><input type="text" required name="anoPublicacao" id="anoPublicacao" maxlength="4" placeholder="YYYY"/></td>
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
