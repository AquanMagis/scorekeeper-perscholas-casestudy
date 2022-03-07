<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="./include/header.jsp"/>

<div class="container formTable">
	<c:forEach items="${games}" var="game">
		<div class="row formTable">
			<div class="col-8">
				<!--Game info-->
				<div class="row">
					<c:forEach items="${game.getPlayers()}" var="player">
						<div class="col-md-6">
							${game.getDisplayName(player)} (${game.getScore().get(player)})
						</div>
					</c:forEach>
				</div>
			</div>
			<div class="col-4">
				<!--Continue/view button-->
				<a href="../game/play?game=${game.getId()}"><input type="button" class="btn btn-light" value="View"></a>
			</div>
		</div class="row">
	</c:forEach>
</div>

<jsp:include page="./include/footer.jsp"/>