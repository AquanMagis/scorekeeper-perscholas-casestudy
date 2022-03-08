<jsp:include page="./include/header.jsp"/>
<div class="container overflow-hidden">
<div class="row justify-content-center">
<div class="col-sm-6 formtable">
    <form action="./login/login-submit" method="POST">
        <div class="row gy-5">
            <div class="col py-3">
                <label for="username" class="form-label">Username</label><br>
                <input type="text" id="username" name="username" class="form-control">
            </div>
        </div>
        <div class="row gy-5">
            <div class="col py-3">
                <label for="password" class="form-label">Password</label><br>
                <input type="password" id="password" name="password" class="form-control">
            </div>
        </div>
        <div class="row gy-5">
            <div class="col py-3">
                <input id="submit" type="submit" value="Log In" class="btn btn-light">
            </div>
        </div>
    </form>
</div>
</div>
</div>
<jsp:include page="./include/footer.jsp"/>