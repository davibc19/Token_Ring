<jsp:include page="/WEB-INF/Pages/header.jsp"/>
<center>
    <h1><b>CONSULTAR HISTÓRICO DE ${usuario.getNome()}</b></h1>
    <form action="./consultaHistorico" method="POST">
        <table>
            <tr style="text-align: center">
                <td><input type="submit" name="submit" value="Submit"></td>
            </tr>
        </table>

    </form>
</center>
</body>
</html>
