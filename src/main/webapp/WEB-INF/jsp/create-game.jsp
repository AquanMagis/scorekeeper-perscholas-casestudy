<!--TODO: Re-add all the logic for game rule changing that was lost in the html->jsp conversion.-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="./include/header.jsp"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<style>
    .carousel-caption{
        background-color: #000000DF;
        border-radius:15px;
    }
    .container{
        background-color: #0000007F;
        border-radius: 15px;
    }
    .carousel-control-next, .carousel-control-prev{
        background-color: #000000Bf;
    }
    .carousel-control-next{
        border-radius: 0 15px 15px 0;
    }
    .carousel-control-prev{
        border-radius: 15px 0 0 15px;
    }
    .rule-entry{
        margin:auto;
        text-align:left;
    }
    tr{
        height: 80px;
    }
    td{
        padding: 10px;
    }
</style>
<script>
    var rulesetList = new Array();
    <c:forEach items="${rulesetJsons}" var="ruleset">
        ruleset = JSON.parse('${ruleset}');
        rulesetList.push(ruleset);
    </c:forEach>

    // TODO: This is going to need a "confirm lose changes" prompt in the final version.
    function adjustRules(to){
        let ruleset = rulesetList[to];
        const form = document.getElementById("ruleForm");
        for(let field in ruleset){
            if(field == "id") continue;
            let formField = document.getElementById(field);
            if(!formField){
                let newNode = document.createElement("input");
                newNode.type = "hidden";
                newNode.id = field;
                newNode.name = field;
                form.appendChild(newNode);
                formField = document.getElementById(field);
            }
            console.log(formField.tagName);
            if(formField.tagName == "SELECT")
                document.getElementById(ruleset[field].toLowerCase()).selected = true;
            else if(formField.tagName = "INPUT" && formField.getAttribute("type") == "checkbox")
                formField.checked = ruleset[field];
            else formField.value = ruleset[field];
        }
    }
    function onLoad(){
        let myCarousel = document.getElementById('ruleTemplates')
        myCarousel.addEventListener('slide.bs.carousel', function () {
            adjustRules(event.relatedTarget.getAttribute("data-ruleset"));
        })
        adjustRules(0);
    }
    $().ready(onLoad);
</script>
<div class="container">
    <div class="row">
        <div class="col-md-6">
            <div id="ruleTemplates" class="carousel slide" data-bs-ride="carousel" data-bs-interval="false">
                <div class="carousel-indicators">
                    <button type="button" data-bs-target="#ruleTemplates" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                    <c:forEach var="i" begin="1" end="${rulesetList.size() - 1}">
                        <button type="button" data-bs-target="#ruleTemplates" data-bs-slide-to="${i}" aria-label="Slide ${i + 1}"></button>
                    </c:forEach>
                </div>
                <div class="carousel-inner">
                    <c:forEach items="${rulesetList}" var="ruleset">
                        <c:set var="index" scope="request" value="${rulesetList.indexOf(ruleset)}"/>
                        <div class='carousel-item ${index == 0 ? "active" : ""}' data-ruleset="${index}">
                            <img src=${ruleset.imageUrl} class="d-block w-100" alt="...">
                            <div class="carousel-caption d-none d-md-block">
                                <h5>${ruleset.setName}</h5>
                                <p>${ruleset.description}</p>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#ruleTemplates" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#ruleTemplates" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        </div>
        <div class="col-md-6 rule-entry">
            <form action="create-game/submit" method = "POST" id="ruleForm" modelAttribute="game">
                <h2>Basic Rules</h2>
                <table>
                    <tr>
                        <td><label for="startingScore">Starting Score:</label></td>
                        <td><input type="number" id="startingScore" step="1000" name="startingScore"></td>
                    </tr>
                    <tr>
                        <td><label for="endingScore">Ending Score:</label></td>
                        <td><input type="number" id="endingScore" step="1000" name="endingScore"></td>
                    </tr>
                    <tr>
                        <td><label for="busting">Busting:</label></td>
                        <td><input type="checkbox" id="busting" name="busting"></td>
                    </tr>
                    <tr>
                        <td>Leftover Riichi Sticks:</td>
                        <td>
                            <select id = "leftoverRiichis" name="leftoverRiichis">
                                <option selected id="winner" value="WINNER">To Winner</option>
                                <option id="lost" value="LOST">Lost</option>
                                <option id="returned" value="RETURNED">Returned</option>
                            </select>
                        </td>
                    </tr>
                    <tr><td><input class="btn btn-light" type="submit" value="Finalize" action="./create-game/submit" method="POST"></td></tr>
                </table>
            </form>
        </div>
    </div>
</div>
<jsp:include page="./include/footer.jsp"/>