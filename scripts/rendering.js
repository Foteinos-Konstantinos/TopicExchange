/*
    Foteinos Konstantinos (2023086)
*/

function renderElements(folder) {

    var userName = null;

    decodeURIComponent(document.cookie).split("; ").forEach(val => {
        let cookieName = val.split("=")[0];
        let cookieValue = val.split("=")[1];
        console.log("Found cookie: " + cookieName + " " + cookieValue);
        if (cookieName === "user-name") {
            userName = cookieValue + "";  // Create copy
        }
    });

    //  Add style
    document.querySelector("head").innerHTML += `
    <title>TopicExchange</title>
    <link rel="stylesheet" href="${folder}/styles/main.css" />
    <link rel="stylesheet" href="${folder}/styles/forms.css" />
    <link rel="stylesheet" href="${folder}/styles/auth.css" />
    <link rel="stylesheet" href="${folder}/styles/index.css" />
    <meta charset="utf-8">
    `;

    var bodyElement = document.querySelector("body");
    //  Add bottom bar
    bodyElement.innerHTML = bodyElement.innerHTML + `
    <div id="bottom-bar">
        <span>Foteinos Konstantinos (it2023086) @ HUA DIT</span>
        <span>Web Dev</span>
    </div>
    `;
    //  Add top bar
    bodyElement.innerHTML = `
    <div id="top-bar">
        <a href="${folder}/index.html" class="back-to-index">TopicExchange</a>
        <a href="${folder}/pages/login.html" class="animated-button">Log In</a>
        <a href="${folder}/pages/changePassword.html" class="animated-button">Change Password</a>
        <a href="${folder}/pages/sendMessage.jsp" class="animated-button">Send Message</a>
        <a href="${folder}/pages/createTopic.html" class="animated-button">Create Topic</a>
        <a href="https://github.com/Foteinos-Konstantinos/TopicExchange" class="animated-button">GitHub Repository</a>
        <span id="connected-user">${(userName === null) ? '-' : userName}</span>
    </div>
    ` + bodyElement.innerHTML;

};