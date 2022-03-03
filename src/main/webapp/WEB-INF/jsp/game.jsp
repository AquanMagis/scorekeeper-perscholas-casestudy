<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="./include/header.jsp"/>

<script>
    let handDisplay;
    let newHand;
    let handCancelButtons;
    let tsumoButton;
    let ronButton;
    let drawButton;
    let tsumoForm;
    let ronForm;
    let drawForm;
    let ronLoserSelect;
    let ronWinnerSelects;
    let ronRemoveButtons;
    let riichiForm;

    const POSITIONS = ["selfScore", "rightScore", "acrossScore", "leftScore"]

    function handCancel(){
        this.form.hidden=true;
        //handCancelButton.hidden = true;
        newHand.hidden = false;
    }
    function handHandling(form){
        newHand.hidden = true;
    	riichiInputs = riichiForm.getElementsByTagName("input");
    	for(input of riichiInputs){
    		input.setAttribute("form", form.id);
    	}
		form.hidden = false;
    }
    function tsumo(){
    	riichiForm.form = tsumoForm.id;
        newHand.hidden = true;
        tsumoForm.hidden = false;
    }
    function ron(){
    	riichiForm.form = ronForm.id;
        newHand.hidden = true;
        ronForm.hidden = false;
    }
    function draw(){
        handHandling(drawForm);
    }
    function addRound(){

    }
    function populateRonDropdowns(){
		for(let dropdown of ronWinnerSelects){
			let id = dropdown.id;
			console.log(id);
			dropdown = ronLoserSelect.cloneNode();
			dropdown.id = id;
		}
    }
    function addRonRow(){
    	invertRonRemove();
    	activate = this.getAttribute("data-activate");
    	this.hidden = true;
    	document.getElementById(activate).hidden = false;
    }
    function removeRonRow(){
		invertRonRemove();
		deactivate = this.getAttribute("data-deactivate");
		addButton = this.getAttribute("data-activate");
		document.getElementById(addButton).hidden = false;
		document.getElementById(deactivate).hidden = true;
    }
    function invertRonRemove(){
    	//TODO: This is a cludgy hack that will break if you ever add sanma support.  Fix before then.
    	//...To be fair, this whole page is a cludgy hack that will break if you add sanma support.
    	for(button of ronRemoveButtons){
    		button.hidden = !button.hidden;
    	}
    }
    function onLoad(){
        handDisplay = document.getElementById("handDisplay");

        newHand = document.getElementById("newHand");
        //newHand.remove();

        handCancelButtons = document.getElementsByName("handCancel");
        for(b of handCancelButtons){
            b.addEventListener("click", handCancel);
        }

        tsumoForm = document.getElementById("tsumoForm");
        tsumoButton = document.getElementById("tsumoButton");
        tsumoButton.addEventListener("click", tsumo);

        ronForm = document.getElementById("ronForm");
        ronButton = document.getElementById("ronButton");
        ronButton.addEventListener("click", ron);

        drawForm = document.getElementById("drawForm");
        drawButton = document.getElementById("drawButton");
        drawButton.addEventListener("click", draw);

        riichiForm = document.getElementById("riichis");

        ronLoserSelect = document.getElementById("ronLoser");
        ronWinnerSelects = document.getElementsByName("ronWinner");

        for(let button of document.getElementsByName("addRonWinner")){
			button.addEventListener("click", addRonRow);
        }

		ronRemoveButtons = document.getElementsByName("removeRonWinner");
		for(let button of ronRemoveButtons){
			button.addEventListener("click", removeRonRow);
		}
        //populateRonDropdowns();
    }

    $().ready(onLoad);
</script>
<div class="container formTable" style="height:80%">
    <div class="row">
        <div class="col-md-6 scoreDisplay">
            <!--Score display-->
            <div class="row">
                <div class="col" id="acrossScore">
                    <div name="seat">East</div><div name="score">25000</div><div name="displayName">Toimen</div>
                </div>
            </div>
            <div class="row">
                <div class="col-4" id="leftScore">
                    <div name="seat">South</div><div name="score">25000</div><div name="displayName">Kamicha</div>
                </div>
                <div class="col-4 my-auto" style="font-size:150%" id="round">
                    <span id="roundWind">East</span> <span style="display: inline-block"><span id="deal">1<span>-<span id="bonus">0</span></span>
                </div>
                <div class="col-4" id="rightScore">
                    <div name="seat">North</div><div name="score">25000</div><div name="displayName">Shimocha</div>
                </div>
            </div>
            <div class="row">
                <div class="col" id="selfScore">
                    <div name="seat">West</div><div name="score">25000</div><div name="displayName">Self</div>
                </div>
            </div>
        </div>
        <div class="col-md-6" id="handDisplay">
        	<div class="row" id="riichis">
        		<div hidden>
        			<input type="hidden" id="gameId" name="gameId" value='${game.getId()}' path='id'>
        		</div>
        		<div class="col">
        			Riichis<br>
					<c:forEach items="${players}" var="player">
						<label for="riichiPlayer${player.getId()}">${game.getDisplayName(player)}</label>
						<input type="checkbox" id="riichiPlayer${player.getId()}" name="inRiichi" value="${player.getId()}" path="inRiichi">
						<input type="hidden" name="_inRiichi" value="on">
					</c:forEach>
        		</div>
        	</div>
            <div class="row" id="newHand">
                <div class="col-3 my-auto">New Hand:</div>
                <div class="col-3"><input type="button" class="btn btn-light" value="Tsumo" id="tsumoButton"></div>
                <div class="col-3"><input type="button" class="btn btn-light" value="Ron" id="ronButton"></div>
                <div class="col-3"><input type="button" class="btn btn-light" value="Draw" id="drawButton"></div>
            </div>
            <form id="tsumoForm" action="game/tsumo-submit" method="POST" hidden>
                <div class="row">
                    <div class="col">
                        <!--TODO: Make this look nice later.-->
                        Winner<br>
                        <c:forEach items="${players}" var="player">
                            <label for="tsumoPlayer${player.getId()}">${game.getDisplayName(player)}</label>
                            <input type="radio" id="tsumoPlayer${player.getId()}" name="tsumoPlayer" value="player.getId()">
                        </c:forEach>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <input type="number" id="tsumoFu" step="10" min="20"><label for="tsumoFu">Fu</label>
                        <input type="number" id="tsumoHan" min="1"><label for="tsumoHan">Han</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <input type="submit" class="btn btn-light" id="tsumoSubmit" value="Tsumo!">
                        <input type="button" class="btn btn-danger" name="handCancel" value="Cancel">
                    </div>
                </div>
            </form>
            <!--TODO: Make this look nice later.-->
            <form id="ronForm" action="game/ron-submit" method="POST" hidden>
            	<div class="row" id="ronLoserRow">
					<div class="col-4">
						Losing Player
					</div>
					<div class="col-8">
						<select id="ronLoser" class="form-select">
							<option selected value="false"/>
							<c:forEach items="${players}" var="player">
								<option name="ronDropdownPlayer" value="${player.getId()}">
									${game.getDisplayName(player)}
								</option>
							</c:forEach>
						</select>
					</div>
				</div>
            	<c:forEach begin="1" end="3" var="i">
            		<div id="ronDiv${i}" ${(i > 1)?"hidden":""}>
						<div class="row" id="ronRow${i}">
							<div class="col-4">
								Winner ${i}
							</div>
							<div class="col-4">
								<div class="input-group">
									<select id="ronWinner${i}" name="ronWinner" class="form-select">
										<option selected value="false"/>
										<c:forEach items="${players}" var="player">
											<option name="ronDropdownPlayer" value="${player.getId()}">
												${game.getDisplayName(player)}
											</option>
										</c:forEach>
									</select>
									<c:if test="${i > 1}">
										<input type="button" class="btn btn-danger" name="removeRonWinner" value="-" ${(i == 2)?'hidden':''} data-deactivate="ronDiv${i}" data-activate="addRonWinner${i-1}">
									</c:if>
								</div>
							</div>
							<div class="col-2">
								<input type="number" id="ronFu${i}" class="form-control" step="10" min="20">
								<label for="ronFu${i}">Fu</label>
							</div>
							<div class="col-2">
								<input type="number" id="ronHan${i}" class="form-control" min="1">
								<label for="ronHan${i}">Han</label>
							</div>
						</div>
						<c:if test="${i < 3}">
							<div class="row">
								<div class="col">
									<input type="button" class="btn btn-light" id="addRonWinner${i}" name="addRonWinner" value="+" data-activate="ronDiv${i + 1}">
								</div>
							</div>
						</c:if>
					</div>
				</c:forEach>
				<div class="row">
					<div class="col">
						<input type="submit" class="btn btn-light" id="drawSubmit" value="Ron!">
						<input type="button" class="btn btn-danger" name="handCancel" value="Cancel">
					</div>
				</div>
            </form>
            <form:form id="drawForm" action="game/draw-submit" method="POST" modelAttribute="drawForm" hidden="true">
                <div class="row">
                    <div class="col">
                        <!--TODO: Make this look nice later.-->
                        Tenpai<br>
                        <c:forEach items="${players}" var="player">
                            <label for="drawPlayer${player.getId()}">${game.getDisplayName(player)}</label>
                            <form:checkbox id="drawPlayer${player.getId()}" value="${player.getId()}" path="inTenpai"/>
                        </c:forEach>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <input type="submit" class="btn btn-light" id="drawSubmit" value="Draw">
                        <input type="button" class="btn btn-danger" name="handCancel" value="Cancel">
                    </div>
                </div>
            </form:form>
            <!--Hand display/entry-->
            <table class="table table-dark">
                <thead>
                    <tr>
                        <th scope="col">Round</th>
                        <th scope="col">Outcome</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th scope="row">E2-0</th>
                        <td>13fu/20han Tsumo</td>
                        <td>
                            Shimocha (<span style="color:green">+33000</span>)<br>
                            Toimen (<span style="color:red">-8000</span>)<br>
                            Kamicha (<span style="color:red">-17000</span>)
                            <span style="color:red; background-color:white">&#9679</span><br>
                            You (<span style="color:red">-8000</span>)<br>
                        </td>
                        <td>Edit|Delete</td>
                    </tr>
                    <tr>
                        <th scope="row">E1-0</th>
                        <td>Yakuman Ron</td>
                        <td>
                            Shimocha (<span style="color:green">+32000</span>)<br>
                            You (<span style="color:red">-32000</span>)
                        </td>
                        <td>Edit|Delete</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<jsp:include page="./include/footer.jsp"/>