<jsp:include page="/WEB-INF/Pages/header.jsp"/>
<center>
    <h1><b>REALIZAR DEVOLUCAO</b></h1>
    <form action="./realizaDevolucao" method="POST">
        <table>
            <tr>
                <th><label for="isbn"><b>ISBN</b></label></th>
                <td><input type="text" required name="isbn" id="isbn"/></td>
            </tr>
            <tr>
                <th><label for="numero"><b>NUMERO DO EXEMPLAR</b></label></th>
                <td><input type="text" required name="numero" id="numero"/></td>
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
