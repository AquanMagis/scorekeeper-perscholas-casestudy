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
				Preferred display name:<br>
				${player.getDisplayName()}
			</div>
		</div>
		<div class = "row">
			<div class = "col-md-6">
				First Name<br>
				<c:choose>
					<c:when test="${player.isShowFirstName()}">
						${player.getFirstName()}
					</c:when>
					<c:otherwise>
						<i>Hidden</i>
					</c:otherwise><br>
				</c:choose>
			</div>
			<div class = "col-md-6">
				Last Name<br>
				<c:choose>
					<c:when test="${player.isShowLastName()}">
						${player.getLastName()}
					</c:when>
					<c:otherwise>
						<i>Hidden</i>
					</c:otherwise><br>
				</c:choose>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<label for="bio" class="form-label">Bio:</label></br>
				<textarea id="bio" path="bio" class="form-label" value="${player.getBio()}" rows="10" readonly>
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