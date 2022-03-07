<jsp:include page="./include/header.jsp"/>
<div class="container formTable">
    <form action="./login/login-submit" method="POST">
        <div class="row">
            <div class="row">
                <label for="username">Username or Email</label>
                <input type="text" id="username" name="username">
            </div>
        </div>
        <div class="row">
            <div class="row">
                <label for="password">Password</label>
                <input type="text" id="password" name="password">
            </div>
        </div>
        <div class="row">
            <div class="col">
                <input id="submit" type="submit" value="Register" class="btn btn-light">
            </div>
        </div>
    </form>
</div>
<jsp:include page="./include/footer.jsp"/>