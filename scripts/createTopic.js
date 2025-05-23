/*
    Foteinos Konstantinos (2023086)
*/

//  Check if there exists a cookie for the user id and get its value.

var userId = null;
decodeURIComponent(document.cookie).split(";").forEach(val => {
    let cookieName = val.split("=")[0];
    let cookieValue = val.split("=")[1];
    if(cookieName==="user-id"){
        userId = parseInt(cookieValue);
    }
})

if(userId!==null){
    document.getElementById("main-content").innerHTML = `
    <form id="new-topic-form">
        <label for="title">Please enter the title of the new topic:</label>
        <input type="text" name="title" id="title" required/>
        <label for="description">Please enter the description of the new topic:</label>
        <textarea name="description" id="description"></textarea>
        <button type="submit" id="submit-button">Create topic</button>
    </form>`
    document.getElementById("new-topic-form").onsubmit = event => {
        event.preventDefault();
        event.stopImmediatePropagation();
        //  Send the information (saved on a JSON) correspond to the new topic to the servlet.
        fetch("../createTopicServlet", {
            method: "POST",
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            },
            body: JSON.stringify({
                name: document.getElementById("title").value,
                description: document.getElementById("description").value,
                userId: userId
            })
        }).then(response => response.json()).then(jsonData=>{
            if(jsonData!==null){
                document.getElementById("main-content").innerHTML = `
                <div class="success">
                    <h1>Operation succeeded</h1>
                    <span>${jsonData.rowsAffected} records were inserted.</span>
                </div>
                `;
            }else{
                document.getElementById("main-content").innerHTML = `
                <div class="failure">
                    <h1>Operation failed</h1>
                    <span>Please try again.</span>
                </div>
                `;
            }
        });
    };
}else{
    document.getElementById("main-content").innerHTML = `
    <div class="failure">
        <h1>Operation failed</h1>
        <span>Please log in first.</span>
    </div>`
}