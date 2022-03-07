<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="./include/header.jsp"/>

<div class="container formTable">
	<div class = "row">
		<div class = "col">
			${player.getUsername()}
		</div>
	</div>
	<form:form id="playerUpdateForm" modelAttribute="playerUpdateForm" action="user/edit-submit" method="POST">
		<div class = "row">
			<div class = "col-md-6">
				<label for="firstName">First Name</label><form:input id="firstName" path="firstName" value="${player.getFirstName()}"/>
			</div>
			<div class = "col-md-6">
				<label for="lastName">First Name</label><form:input id="lastName" path="firstName" value="${player.getFirstName()}"/>
			</div>
		</div>
	</form:form>
</div>

<jsp:include page="./include/footer.jsp"/>