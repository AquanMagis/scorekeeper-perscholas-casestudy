<jsp:include page="./include/header.jsp"/>
<div class="container overflow-hidden">
<div class="row justify-content-center">
<div class="col-sm-6 formtable">
    <div class="row gy-5">
        <div class="col p-3">
            <a href="game/create"><input type="button" id="create" name="create" class="btn btn-light" value="Create"></a>
        </div>
    </div>
    <!--div class="row gy-5">
        <div class="col p-3">
            <a href="join-game"><input type="button" id="join" name="join" class="btn btn-light" value="Join"></a>
        </div>
    </div-->
    <div class="row gy-5">
        <div class="col p-3">
            <a href="user/edit"><input type="button" id="userControls" name="userControls" class="btn btn-light" value="User Controls"></a>
        </div>
    </div>
    <div class="row gy-5 p-3">
        <div class="col">
            <a href="user/games"><input type="button" id="userControls" name="userControls" class="btn btn-light" value="Game Records"></a>
        </div>
    </div>
    <div class="row gy-5">
        <div class="col p-3">
            <a href="logout"><input type="button" id="logout" name="logout" class="btn btn-light" value="Log Out"></a>
        </div>
    </div>
</div>
</div>
</div>
<jsp:include page="./include/footer.jsp"/>