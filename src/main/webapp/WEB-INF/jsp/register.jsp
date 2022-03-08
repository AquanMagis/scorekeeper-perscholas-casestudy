<jsp:include page="./include/header.jsp"/>
<div class="container overflow-hidden">
<div class="row justify-content-center">
<div class="col-sm-8 formtable">
    <div class="row">
        <div class="col py-3">
            <h2>Register</h2>
        </div>
    </div>
	<form action="./register/submit" method="POST" modelAttribute="newUser">
	    <div class="row">
            <div class="col-md-6 py-3">
                <label for="username" class="form-label">Username</label><br>
                <input id="username" type="text" name="username" class="form-control">
            </div>
            <div class="col-md-6 py-3">
                <label for="email" class="form-label">Email Address</label><br>
                <input id="email" type="email" name="email" class="form-control">
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 py-3">
                <label for="password" class="form-label">Password</label><br>
                <input id="password" type="password" name="password" class="form-control">
            </div>
            <div class="col-md-6 py-3">
                <label for="password_confirm" class="form-label">Confirm Password</label><br>
                <input id="password_confirm" type="password" class="form-control">
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 py-3">
                <label for="firstName" class="form-label">First Name</label><br>
                <input id="firstName" type="text" name="firstName" class="form-control">
            </div>
            <div class="col-md-6 py-3">
                <label for="lastName" class="form-label">Last Name</label><br>
                <input id="lastName" type="text" name="lastName" class="form-control">
            </div>
        </div>
        <div class="row">
            <div class="col py-3">
                <input id="submit" type="submit" value="Register" class="btn btn-light">
            </div>
        </div>
	</form>
</div>
</div>
</div>
<jsp:include page="./include/footer.jsp"/>