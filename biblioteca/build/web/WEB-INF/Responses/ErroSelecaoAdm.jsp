<jsp:include page="/WEB-INF/Pages/header.jsp"/>
<h1>ERRO</h1>

<p>
    <b>ERRO: </b> Opção não selecionada. 
    <br/>
    Tente novamente.
    <br/>
    Se o erro persistir, entre em contato com um administrador do sistema.
    <br/>
    <br/>
    <%
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Pages/indexAdm.jsp");
        dispatcher.forward(request, response);
    %>
</p>
</body>
</html>
