<jsp:include page="/WEB-INF/Pages/header.jsp"/>
<center>
    <h1><b>CADASTRAR EXEMPLAR</b></h1>
    <form action="./cadastraExemplar" method="POST">
        <table>
            <tr>
                <th><label for="isbn"><b>ISBN</b></label></th>
                <td><input type="text" required name="isbn" id="isbn"/></td>
            </tr>
            <tr>
                <th><label for="preco"><b>PREÇO</b></label></th>
                <td><input type="text" required name="preco" id="preco"/></td>
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
