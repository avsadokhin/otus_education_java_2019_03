function path () {
    return window.location.pathname;
}

const setConnected = (connected) => {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#chatLine").show();
    }
    else {
        $("#chatLine").hide();
    }
    $("#message").html("");
}

const connect=  () => {
    webSocketEndpoit = /*path() + */"/web-socket-endpoint";
    stompClient = Stomp.over(new SockJS(webSocketEndpoit));

    stompClient.connect({}, (frame) => {
        console.log('Connected: ' + frame);
    setConnected(true);
        stompClient.subscribe( '/topic/usersResponse', (usersCallback) => {

            console.log('usersResponse...');
            document.getElementById("userListDiv").innerHTML =
                showUsersResultTable(JSON.parse(usersCallback.body));} );
});
};



function init() {
     connect();

}

$(window).on("beforeunload", function () {
    disconnect();
});

const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");

};

function showUsersResultTable(users) {
    let rows="";

    for (let key in users) {
        rows += "<tr><td>" +users[key].id +"</td><td>" +users[key].name +"</td><td>" +users[key].age +"</td><td>" +users[key].address.street
            +"</td><td>" +users[key].phoneList[0].number +"</td><td>" +users[key].phoneList[1].number +"</td></tr>";
    }

    let result = "<table><tr> <th>Id</th><th>Name</th><th>Age</th><th>Address</th><th>Phone1</th><th>Phone1</th></tr>" + rows + "</table>";

    return result;
}


const createUser =  () =>{
    console.log('createUser...');
    stompClient.send(
        "/app/user",
        {},
        JSON.stringify({'name': $('#nameInput').val(), 'age': $('#ageInput').val(), 'address': $('#addressInput').val(),
            'phoneList':[{'number':$('#phone1').val()},{'number':$('#phone2').val()}]}) );
}

const getUsers =  () =>{
    console.log('getUsers...');
    stompClient.send(
        "/app/getUsers",
        {},
        {}
    );
}

$(function () {
    $("form").on('submit', (event) => {
        event.preventDefault();
});
    $("#connect").click(connect);
    $("#disconnect").click(disconnect);
   $("#createSubmit").click(createUser);
    $("#getSubmit").click(getUsers);
});




