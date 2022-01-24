function addUsernameValidation(){
    let usernameField = document.getElementById("username");
    usernameField.setAttribute("pattern", "^[\\w\\-]+$");
    usernameField.setAttribute("title", "Usernames must contain only alphanumeric characters, dashes, and underscores.");
    //window.alert("validation added")
}