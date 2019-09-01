function path () {
    return window.location.pathname;
}
const connect= () => {
    webSocketEndpoit = path() + "/web-socket-endpoint";
    stompClient = Stomp.over(new SockJS(webSocketEndpoit));

    stompClient.connect({}, (frame) => {
        console.log('Connected: ' + frame);
        stompClient.subscribe( '/topic/usersResponse', (usersCallback) => {
            console.log('usersResponse...');
            document.getElementById("userListDiv").innerHTML =
                showUsersResultTable(JSON.parse(usersCallback.body));} );
});
};

function initWebSocket() {
    connect();
}

$(window).on("beforeunload", function () {
    disconnect();
});

const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
};

function showUsersResultTable(users) {
    console.log('showUsersResultTable...');
    /*let rows="";
    let result = "<table><tr> <th>Name</th><th>Age</th><th>Address</th> </tr>" + rows + "</table>";
    for (let key in users) {
        rows += "<tr><td>" +key.name +"</td>"+ "<td>" +key.age +"</td>" + + "<td>" +key.address +"</td></tr>";
    }
    return result;*/
    return users;
}


const createUser =  () =>{
    console.log('createUser...');
    stompClient.send(
        "/app/user",
        {},
        JSON.stringify({'name': $('#nameInput').val(), 'age': $('#ageInput').val(), 'address': $('#addressInput').val()})
    );
}

$(function () {
    $("form").on('submit', (event) => {
        event.preventDefault();
});
   $("#createSubmit").click(createUser);
});




