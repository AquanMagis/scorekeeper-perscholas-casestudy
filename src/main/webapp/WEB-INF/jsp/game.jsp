<jsp:include page="./include/header.jsp"/>
<div class="container formTable" style="height:80%">
    <div class="row">
        <div class="col-md-6 scoreDisplay">
            <!--Score display-->
            <div class="row">
                <div class="col" id="toimen">
                    East<br>25000<br>Toimen
                </div>
            </div>
            <div class="row">
                <div class="col-3" id="kamicha">
                    South<br>25000<br>Kamicha
                </div>
                <div class="col-6 my-auto" style="font-size:150%">
                    East 1-0
                </div>
                <div class="col-3" id="shimocha">
                    North<br>25000<br>Shimocha
                </div>
            </div>
            <div class="row">
                <div class="col" id="self">
                    West<br>25000<br>Self
                </div>
            </div>
        </div>
        <div class="col-md-6">
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