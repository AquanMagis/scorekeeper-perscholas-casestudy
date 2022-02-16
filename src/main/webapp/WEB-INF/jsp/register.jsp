<jsp:include page="./include/header.jsp"/>
<div class="container-sm formTable">
    <div class="row">
        <div class="col">
            <h2>Register</h2>
        </div>
    </div>
	<form action="./register-submit" method="POST" modelAttribute="newUser">
	    <div class="row">
            <div class="col">
                <label for="username">Username</label><br>
                <input id="username" type="text" name="username">
            </div>
            <div class="col">
                <label for="email">Email Address</label><br>
                <input id="email" type="email" name="email">
            </div>
        </div>
        <div class="row">
            <div class="col col-md-6">
                <label for="password">Password</label><br>
                <input id="password" type="password" name="password">
            </div>
            <div class="col col-md-6">
                <label for="password_confirm">Confirm Password</label><br>
                <input id="password_confirm" type="password">
            </div>
        </div>
        <div class="row">
            <div class="col col-md-6">
                <label for="firstName">First Name</label><br>
                <input id="firstName" type="password" name="firstName">
            </div>
            <div class="col col-md-6">
                <label for="lastName">Last Name</label><br>
                <input id="lastName" type="password" name="lastName">
            </div>
        </div>
        <div class="row">
            <div class="col">
                <input id="submit" type="submit" value="Register">
            </div>
        </div>
	</form>
</div>
<jsp:include page="./include/footer.jsp"/>