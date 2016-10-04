<jsp:include page="/WEB-INF/Pages/header.jsp"/>
<center>
    <h1><b>CADASTRAR ASSOCIADOS</b></h1>
    <form action="./cadastraAssociado" method="POST">
        <table>
            <tr>
                <th><label for="codigo"><b>CÓDIGO</b></label></th>
                <td><input type="text" required name="codigo" id="codigo"/></td>
            </tr>
            <tr>
                <th><label for="status"><b>STATUS</b></label></th>
                <td>
                    <input type="radio" name="status" value="grad">Aluno de Graduação</option>
                    <br/>
                    <input type="radio" name="status" value="posgrad">Aluno de Pós Graduação</option>
                    <br/>
                    <input type="radio" name="status" value="prof">Professor</option>
                    <br/>
                </td>
            </tr>
            <tr>
                <th><label for="nome"><b>NOME</b></label></th>
                <td><input type="text" required name="nome" id="nome"/></td>
            </tr>
            <tr>
                <th><label for="endereco"><b>ENDEREÇO</b></label></th>
                <td><input type="text" required name="endereco" id="endereco"/></td>
            </tr>
            <tr>
                <th><label for="email"><b>EMAIL</b></label></th>
                <td><input type="text" required name="email" id="email"/></td>
            </tr>
            <tr>
                <th><label for="login"><b>LOGIN</b></label></th>
                <td><input type="text" required name="login" id="login"/></td>
            </tr>
            <tr>
                <th><label for="senha"><b>SENHA</b></label></th>
                <td><input type="password" required name="senha" id="senha"/></td>
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
