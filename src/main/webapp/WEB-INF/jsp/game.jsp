<jsp:include page="./include/header.jsp"/>
<div class="container formTable">
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
            <table class="table table-light">
                <thead>
                    <tr>
                        <th scope="col">Round</th>
                        <th scope="col">Outcome</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th scope="row">E1-0</th>
                        <td>Shimocha</td>
                        <td>Ron</td>
                        <td>130/13<br>32000</td>
                        <td>You</td>
                        <td>Edit|Delete</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<jsp:include page="./include/footer.jsp"/>