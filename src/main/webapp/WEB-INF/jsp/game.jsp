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
    	handHandling(tsumoForm);
    }
    function ron(){
    	handHandling(ronForm);
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
    	let activate = this.getAttribute("data-activate");
    	this.hidden = true;
    	document.getElementById(activate).hidden = false;
    }
    function removeRonRow(){
		invertRonRemove();
		let deactivate = document.getElementById(this.getAttribute("data-deactivate"));
		let addButton = document.getElementById(this.getAttribute("data-activate"));

		addButton.hidden = false;
		deactivate.hidden = true;

		let inputs = deactivate.getElementsByTagName("input");
		for(let input of inputs){
			if(input.type == "number")
				input.value = null;
		}

		options = deactivate.getElementsByTagName("option");
		for(let option of options){
			console.log(option.text);
			if(option.value.text == null){
				option.selected = true;
				break;
			}
		}
    }
    function invertRonRemove(){
    	//TODO: This is a cludgy hack that will break if you ever add sanma support.  Fix before then.
    	//...To be fair, this whole page is a cludgy hack that will break if you add sanma support.
    	for(button of ronRemoveButtons){
    		button.hidden = !button.hidden;
    	}
    }
    function windNumToString(num){
    	switch(num){
    		case "0": return "East";
    		case "1": return "South";
    		case "2": return "West";
    		case "3": return "North";
    		default: return "ERR";
    	}
    }
    function windNumToChar(num){
    	switch(num){
    		case "0": return "E";
    		case "1": return "S";
    		case "2": return "W";
    		case "3": return "N";
    		default: return "ERR";
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

        let winds = document.getElementsByName("seat");
        for(let element of winds){
        	element.textContent = windNumToString(element.textContent);
        }
        let roundWind = document.getElementById("roundWind");
        roundWind.textContent = windNumToString(roundWind.textContent);
        winds = document.getElementsByName("tableWind");
		for(let element of winds){
			element.textContent = windNumToChar(element.textContent);
		}
    }

    $().ready(onLoad);
</script>
<div class="container formTable" style="height:80%">
    <div class="row">
        <div class="col-md-6 scoreDisplay">
            <!--Score display-->
            <div class="row">
                <div class="col" id="acrossScore">
                    <div name="seat">${game.getPlayerCurrentSeat(players.get(2))}</div>
                    <div name="score">${game.getScore().get(players.get(2))}</div>
                    <div name="displayName">${game.getDisplayName(players.get(2))}</div>
                </div>
            </div>
            <div class="row">
                <div class="col-4" id="leftScore">
                    <div name="seat">${game.getPlayerCurrentSeat(players.get(3))}</div>
                    <div name="score">${game.getScore().get(players.get(3))}</div>
                    <div name="displayName">${game.getDisplayName(players.get(3))}</div>
                </div>
                <div class="col-4 my-auto" style="font-size:150%" id="round">
                    <span id="roundWind">${game.getWind()}</span>
                    <span style="display: inline-block">
                    	<span id="deal">${game.getCurrentDealer() + 1}<span>-<span id="bonus">${game.getRepeats()}</span>
					</span>
                </div>
                <div class="col-4" id="rightScore">
                    <div name="seat">${game.getPlayerCurrentSeat(players.get(1))}</div>
                    <div name="score">${game.getScore().get(players.get(1))}</div>
                    <div name="displayName">${game.getDisplayName(players.get(1))}</div>
                </div>
            </div>
            <div class="row">
                <div class="col" id="selfScore">
                    <div name="seat">${game.getPlayerCurrentSeat(players.get(0))}</div>
                    <div name="score">${game.getScore().get(players.get(0))}</div>
                    <div name="displayName">${game.getDisplayName(players.get(0))}</div>
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
            <form:form id="tsumoForm" action="game/tsumo-submit" method="POST" hidden="true" modelAttribute="tsumoForm">
                <div class="row">
                    <div class="col">
                        <!--TODO: Make this look nice later.-->
                        Winner<br>
                        <c:forEach items="${players}" var="player">
                            <label for="tsumoPlayer${player.getId()}">${game.getDisplayName(player)}</label>
                            <form:radiobutton id="tsumoPlayer${player.getId()}" name="tsumoPlayer" value="${player.getId()}" path="winnerId"/>
                        </c:forEach>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <form:input type="number" id="tsumoFu" step="10" min="20" name="fu" path="fu"/><label for="tsumoFu">Fu</label>
                        <form:errors path="fu"/>
                        <form:input type="number" id="tsumoHan" min="1" name="han" path="han"/><label for="tsumoHan">Han</label>
                        <form:errors path="han"/>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <input type="submit" class="btn btn-light" id="tsumoSubmit" value="Tsumo!">
                        <input type="button" class="btn btn-danger" name="handCancel" value="Cancel">
                    </div>
                </div>
            </form:form>
            <!--TODO: Make this look nice later.-->
            <form:form id="ronForm" action="game/ron-submit" method="POST" modelAttribute = "ronForm" hidden="true">
            	<div class="row" id="ronLoserRow">
					<div class="col-4">
						Losing Player
					</div>
					<div class="col-8">
						<form:select id="ronLoser" class="form-select" path="loserId">
							<option selected value="false"/>
							<c:forEach items="${players}" var="player">
								<form:option name="ronDropdownPlayer" value="${player.getId()}" label="${game.getDisplayName(player)}"/>
							</c:forEach>
						</form:select>
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
									<form:select id="ronWinner${i}" name="ronWinner" class="form-select" path="winnerIds[${i - 1}]">
										<option selected>
										<c:forEach items="${players}" var="player">
											<form:option name="ronDropdownPlayer" value="${player.getId()}" label="${game.getDisplayName(player)}"/>
										</c:forEach>
									</form:select>
									<c:if test="${i > 1}">
										<input type="button" class="btn btn-danger" name="removeRonWinner" value="-" ${(i == 2)?'hidden':''} data-deactivate="ronDiv${i}" data-activate="addRonWinner${i-1}">
									</c:if>
								</div>
							</div>
							<div class="col-2">
								<form:input type="number" id="ronFu${i}" class="form-control" step="10" min="20" path="fu[${i - 1}]"/>
								<label for="ronFu${i}">Fu</label>
							</div>
							<div class="col-2">
								<form:input type="number" id="ronHan${i}" class="form-control" min="1" path="han[${i - 1}]"/>
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
            </form:form>
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
            <!--TODO: Put winners at top of table cell, line winner up with own hand.-->
            <table class="table table-dark">
                <thead>
                    <tr>
                        <th scope="col">Round</th>
                        <th scope="col">Outcome</th>
                    </tr>
                </thead>
                <tbody>
                	<c:forEach items="${rounds}" var="round" varStatus="status">
                		<tr>
							<th scope="row">
								<span name="tableWind">${round.getWind()}</span><span name="tableRound">${round.getCurrentDealer() + 1}</span>-<span name="tableBonus">${round.getRepeats()}</span>
							</th>
							<td>
								<c:if test='${round.getWinType().toString().equals("DRAW")}'>
									Draw
								</c:if>
								<c:forEach items="${round.getHands()}" var="hand">
									<div>${hand.getFu()}fu/${hand.getHan()}han
									${round.getWinType().toString().substring(0, 1)}${round.getWinType().toString().toLowerCase().substring(1)}<div>
								</c:forEach>
							</td>
							<td>
								<c:set var="change" value="${round.getScoreChange()}"/>
								<c:forEach items="${change.keySet()}" var="player">
									<div>${game.getDisplayName(player)} (<span style='color: ${(change.get(player) >=0) ? "green":"red"}'>${(change.get(player) > 0)?"+":""}${change.get(player)}</span>)${round.getInRiichi().contains(player)?"<span style='color:red; background-color:white'>&#9679</span>":""}</div>
								</c:forEach>
							</td>
							<td>
								Edit
								<c:if test="${status.isFirst()}">
									|Delete
								</c:if>
							</td>
						</tr>
                	</c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<jsp:include page="./include/footer.jsp"/>