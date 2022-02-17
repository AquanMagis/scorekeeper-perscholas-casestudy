<!--TODO: Re-add all the logic for game rule changing that was lost in the html->jsp conversion.-->
<jsp:include page="./include/header.jsp"/>
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
<div class="container">
    <div class="row">
        <div class="col-md-6">
            <div id="ruleTemplates" class="carousel slide" data-bs-ride="carousel" data-bs-interval="false">
                <div class="carousel-indicators">
                    <button type="button" data-bs-target="#ruleTemplates" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                    <button type="button" data-bs-target="#ruleTemplates" data-bs-slide-to="1" aria-label="Slide 2"></button>
                </div>
                <div class="carousel-inner">
                    <div class="carousel-item active" id="onlineRules">
                        <img src="./images/chun_large.png" class="d-block w-100" alt="...">
                        <div class="carousel-caption d-none d-md-block">
                            <h5>Online Rules</h5>
                            <p>A rule set resembling popular online clients such as Tenhou and MahjongSoul.</p>
                        </div>
                    </div>
                    <div class="carousel-item" id="wrcRules">
                        <img src="https://images.squarespace-cdn.com/content/v1/5834cfa4f5e231d203fec0cb/1526941446143-MZKA55DK80I2NJLMBKGW/wrc_navy_v1.1_400dpi.png?format=1500w" class="d-block w-100" alt="...">
                        <div class="carousel-caption d-none d-md-block">
                            <h5>WRC Rules</h5>
                            <p>A rule set resembling the official rules of the World Riichi Championship.</p>
                        </div>
                    </div>
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
            <h2>Basic Rules</h2>
            <table>
                <tr>
                    <td><label for="startingScore">Starting Score:</label></td>
                    <td><input type="number" id="startingScore" step="1000"></td>
                </tr>
                <tr>
                    <td><label for="endingScore">Ending Score:</label></td>
                    <td><input type="number" id="endingScore" step="1000"></td>
                </tr>
                <tr>
                    <td><label for="busting">Busting:</label></td>
                    <td><input type="checkbox" id="busting"></td>
                </tr>
                <tr>
                    <td>Leftover Riichi Sticks:</td>
                    <td>
                        <select>
                            <option selected id="winner">To Winner</option>
                            <option id="lost">Lost</option>
                            <option id="returned">Returned</option>
                        </select>
                    </td>
                </tr>
                <tr><td><input class="btn btn-light" type="button" value="Finalize" onclick="window.alert('Not implemented.');"></td></tr>
            </table>

        </div>
    </div>
</div>
<jsp:include page="./include/footer.jsp"/>