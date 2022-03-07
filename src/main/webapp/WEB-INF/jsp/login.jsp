<jsp:include page="./include/header.jsp"/>
<div class="container-md formTable">
    <form action="./login/login-submit" method="POST">
        <div class="row">
            <div class="col">
                <label for="username">Username</label><br>
                <input type="text" id="username" name="username">
            </div>
        </div>
        <div class="row">
            <div class="col">
                <label for="password">Password</label><br>
                <input type="text" id="password" name="password">
            </div>
        </div>
        <div class="row">
            <div class="col">
                <input id="submit" type="submit" value="Log In" class="btn btn-light">
            </div>
        </div>
    </form>
</div>
<jsp:include page="./include/footer.jsp"/>