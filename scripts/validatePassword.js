/*
    Foteinos Konstantinos (2023086)
*/

var form = document.getElementById("change-password-form");

var submitButton = document.getElementById("submit-button");

var usernameField = document.getElementById("username");
var oldPasswordField = document.getElementById("old-password");
var newPasswordField = document.getElementById("new-password");
var newPasswordCheckedField = document.getElementById("new-password-checked");

var messagePanel = document.getElementById("message");

form.onchange = () => {
    if(newPasswordCheckedField.value!=newPasswordField.value){
        messagePanel.style.color = "red";
        messagePanel.innerText = "The given passwords do not match.";
        submitButton.disabled=true;
    }else if(newPasswordField.value==oldPasswordField.value){
        messagePanel.style.color = "red";
        messagePanel.innerText = "You should give a password different from the old one.";
        submitButton.disabled=true;
    }else if(newPasswordField.value.length<=5){
        messagePanel.style.color = "red";
        messagePanel.innerText = "The new password should have more than five characters.";
        submitButton.disabled=true;
    }else if(!/^[a-z]+[0-9]+[a-z0-9]*$|^[0-9]+[a-z]+[a-z0-9]*$/i.test(newPasswordField.value)){
        messagePanel.style.color = "red";
        messagePanel.innerText = "The new password should consists of letters AND digits.";
        submitButton.disabled=true;
    }else{
        messagePanel.style.color = "green";
        messagePanel.innerText = "Correct.";
        submitButton.disabled=false;
    }
};

form.onsubmit = event => {
    if(submitButton.disabled){
        event.preventDefault();
    }
}