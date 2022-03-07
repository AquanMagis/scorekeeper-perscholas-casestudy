<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="./include/header.jsp"/>

<div class="container formTable">
	<form:form id="playerUpdateForm" modelAttribute="playerUpdateForm" action="edit-submit" method="POST">
		<div class = "row">
			<div class = "col-md-6">
				<h2>${player.getUsername()}<h2>
			</div>
			<div class = "col-md-6">
				<label for="displayName" class="form-label">Preferred display name:</label><br>
				<form:input id="displayName" path="displayName" class="form-label" value="${player.getDisplayName()}"/>
			</div>
		</div>
		<div class = "row">
			<div class = "col-md-6">
				<label for="firstName" class="form-label">First Name</label><br>
				<form:input id="firstName" path="firstName" class="form-label" value="${player.getFirstName()}"/><br>
				<label for="showFirstName">Show</label>
				<form:checkbox id="showFirstName" path="showFirstName" value="${player.getShowFirstName()}"/>
			</div>
			<div class = "col-md-6">
				<label for="lastName" class="form-label">Last Name</label><br>
				<form:input id="lastName" path="lastName" class="form-label" value="${player.getLastName()}"/><br>
				<label for="showLastName">Show</label>
				<form:checkbox id="showLastName" path="showLastName" value="${player.getShowLastName()}"/>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<label for="bio" class="form-label">Bio:</label></br>
				<form:textarea id="bio" path="bio" class="form-label" value="${player.getBio()}" rows="10"/>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<input type="submit" value="Save Changes">
			</div>
		</div>
	</form:form>
</div>

<jsp:include page="./include/footer.jsp"/>