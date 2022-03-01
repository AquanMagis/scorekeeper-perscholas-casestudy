<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="./include/header.jsp"/>

<script>
    //const game = ${game};
    let handDisplay;
    let newHand;
    let handCancelButton;
    let tsumoButton;
    let ronButton;
    let drawButton;
    let tsumoForm;
    let ronForm;
    let drawForm;

    const POSITIONS = ["selfScore", "rightScore", "acrossScore", "leftScore"]

    function handCancel(){
        this.form.hidden=true;
        //handCancelButton.hidden = true;
        newHand.hidden = false;
    }
    function tsumo(){
        newHand.hidden = true;
        tsumoForm.hidden = false;
    }
    function ron(){

    }
    function draw(){

    }
    function addRound(){

    }
    function onLoad(){
        handDisplay = document.getElementById("handDisplay");

        newHand = document.getElementById("newHand");
        //newHand.remove();

        handCancelButton = document.getElementById("handCancel");
        handCancelButton.addEventListener("click", handCancel);

        tsumoForm = document.getElementById("tsumoForm");
        tsumoButton = document.getElementById("tsumoButton");
        tsumoButton.addEventListener("click", tsumo);

        //TODO: Uncomment this when the form is implemented.
        /*ronForm = document.getElementById("ronForm");
        ronButton = document.getElementById("ronButton");
        ronButton.addEventListener("click", ron);*/

        //TODO: Uncomment this when the form is implemented.
        /*drawForm = document.getElementById("drawForm");
        drawButton = document.getElementById("drawButton");
        drawButton.addEventListener("click", draw);*/
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
            <div class="row" id="newHand">
                <div class="col-3 my-auto">New Hand:</div>
                <div class="col-3"><input type="button" class="btn btn-light" value="Tsumo" id="tsumoButton"></div>
                <div class="col-3"><input type="button" class="btn btn-light" value="Ron" id="ronButton"></div>
                <div class="col-3"><input type="button" class="btn btn-light" value="Draw" id="drawButton"></div>
            </div>
            <form id="tsumoForm" action="game/tsumo-submit">
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
                        <input type="number" id="tsumoFu" step="10"><label for="tsumoFu">Fu</label>
                        <input type="number" id="tsumoHan"><label for="tsumoHan">Han</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <input type="submit" class="btn btn-light" id="tsumoSubmit" value="Tsumo!">
                        <input type="button" class="btn btn-danger" id="handCancel" value="Cancel">
                    </div>
                </div>
            </form>
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