<jsp:include page="./include/header.jsp"/>

<script>
    const game = ${game};
    let handDisplay;
    let newHand;
    let handCancelButton;

    function handCancel(){
        handCancelButton.hidden = true;
        newHand.hidden = false;
    }
    function addRound(){

    }
    function onLoad(){
        handDisplay = document.getElementById("handDisplay");

        newHand = document.getElementById("newHand");
        //newHand.remove();

        handCancelButton = document.getElementById("handCancel");
        handCancelButton.addEventListener("click", handCancel);
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
            <input type="button" id="handCancel" class="btn btn-light" value="Cancel">
            <div class="row" id="newHand">
                <div class="col-3 my-auto">New Hand:</div>
                <div class="col-3"><input type="button" class="btn btn-light" value="Tsumo" id="tsumoButton"></div>
                <div class="col-3"><input type="button" class="btn btn-light" value="Ron" id="ronButton"></div>
                <div class="col-3"><input type="button" class="btn btn-light" value="Draw" id="drawButton"></div>
            </div>
            <form action="game/tsumo-submit">
                <div class="row">
                    <div class="col">

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