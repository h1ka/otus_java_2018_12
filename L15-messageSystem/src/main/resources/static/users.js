let stompClient = null;

const setConnected = (connected) => {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#users").show();
    } else {
        $("#users").hide();
    }
    $("#id").html("");
    $("#name").html("");
    $("#age").html("");

};

const connect = () => {
    stompClient = Stomp.over(new SockJS('/admin-panel'));
    stompClient.connect({}, (frame) => {
        setConnected(true);
        sendConnect();
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/users', (msg) => showUsers((JSON.parse(msg.body)).users));
    });
};

function sendConnect() {
    stompClient.send("/app/connect", {}, JSON.stringify({'message': 'connect'}));
}

function sendUser() {
    console.log("send");
    stompClient.send("/app/save", {}, JSON.stringify(
        {'user': {'id': $("#id").val(), 'name': $("#name").val(), 'age': $("#age").val()}}))
}

const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
};

const showUsers = (users) => {
    $("#users").html("");
    for (let i = 0; i < users.length; i++) {
        let user = users[i];
        console.log(user);
        showInTable(user);
    }

};
const showInTable = (user) => {
    $("#users")
        .append("<tr>")
        .append("<td>" + user.id + "</td>")
        .append("<td>" + user.name + "</td>")
        .append("<td>" + user.age + "</td>")
        .append("</tr>");
};


$(function () {
    $("form").on('submit', (event) => {
        event.preventDefault();
    });
    $("#connect").click(connect);
    $("#disconnect").click(disconnect);
    $("#send").click(sendUser);
});