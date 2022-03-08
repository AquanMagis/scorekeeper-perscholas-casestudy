<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="./include/header.jsp"/>

<div class="container">
<div class="row justify-content-center">
<div class="col-md-8 formTable">
	<form:form id="playerUpdateForm" modelAttribute="playerUpdateForm" action="edit-submit" method="POST">
		<div class = "row">
			<div class = "col-md-6 py-3 my-auto">
				<h2><i>${player.getUsername()}</i><h2>
			</div>
			<div class = "col-md-6 py-3">
				<label for="displayName" class="form-label">Preferred display name:</label><br>
				<form:input id="displayName" path="displayName" class="form-control" value="${player.getDisplayName()}"/>
			</div>
		</div>
		<div class = "row">
			<div class = "col-md-6 py-3">
				<label for="firstName" class="form-label">First Name</label><br>
				<form:input id="firstName" path="firstName" class="form-control" value="${player.getFirstName()}"/><br>
				<label for="showFirstName" class="form-label">Show</label>
				<form:checkbox id="showFirstName" path="showFirstName" checked="${player.isShowFirstName()}" class="form-check-input"/>
			</div>
			<div class = "col-md-6 py-3">
				<label for="lastName" class="form-label" class="form-label">Last Name</label><br>
				<form:input id="lastName" path="lastName" class="form-control" value="${player.getLastName()}"/><br>
				<label for="showLastName" class="form-label">Show</label>
				<form:checkbox id="showLastName" path="showLastName" checked="${player.isShowLastName()}" class="form-check-input"/>
			</div>
		</div>
		<div class="row justify-content-center">
			<div class="col-xl-6 py-3">
				<label for="bio" class="form-label">Bio:</label></br>
				<textarea id="bio" name="bio" class="form-control" rows="10">${player.getBio()}</textarea>
			</div>
		</div>
		<div class="row">
			<div class="col py-3">
				<input type="submit" value="Save Changes">
			</div>
		</div>
	</form:form>
</div>
</div>
</div>

<jsp:include page="./include/footer.jsp"/>