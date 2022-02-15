<jsp:include page="./include/header.jsp"/>
<div class="container">
	<form action="./register-submit">
	    <div class="row">
            <div class="col">
                <label for="username">Username</label><br>
                <input id="username" type="text">
            </div>
        </div>
        <div class="row">
            <div class="col col-md-6">
                <label for="password">Password</label><br>
                <input id="password" type="password">
            </div>
            <div class="col col-md-6">
                <label for="password_confirm">Confirm Password</label><br>
                <input id="password_confirm" type="text">
            </div>
        </div>
	</form>
</div>
<jsp:include page="./include/footer.jsp"/>