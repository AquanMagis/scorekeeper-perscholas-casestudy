<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="./include/header.jsp"/>

<div class="container">
<div class="row justify-content-center">
<div class="col-lg-8 formTable" style="background:black">
	<c:forEach items="${games}" var="game">
		<div class="row gameTableRow">
			<div class="col-10 py-1">
				<!--Game info-->
				<div class="row">
					<c:forEach items="${game.getPlayers()}" var="player">
						<div class="col-6 py-1">
							${game.getDisplayName(player)} (${game.getScore().get(player)})
						</div>
					</c:forEach>
				</div>
			</div>
			<div class="col-2 my-auto">
				<!--Continue/view button-->
				<a href="../game/play?game=${game.getId()}"><input type="button" class="btn btn-light" value="View"></a>
			</div>
		</div class="row">
	</c:forEach>
</div>
</div>
</div>
<jsp:include page="./include/footer.jsp"/>