//console.log("hello");

if (typeof window !== 'undefined') {
    console.log('You are on the browser')
} else {
    console.log('You are on the server')
}

let userName = document.getElementById("name");


let roomName = document.getElementById("room");


let messages = document.getElementById("message");

let sendMessage = document.getElementById("send");
let name;
let room;
function handleKeyPressedUserNameAndRoom(event){
    if(event.keyCode === 13){
        event.preventDefault();
        name = userName.value;

        room = roomName.value;


        for (let ch of room) {
            if (ch < 'a' || ch > 'z') {
                alert("The room name should be lowercase and no space!");
                return;
            }
        }
        if (wsOpen){
            //console.log(name+room);
            ws.send("join " + name + " " + room);
            userName.value="";
            roomName.value="";

        }
        else {

            //display websocket not open
            alert("Server not open");
        }
     }
}



let newMessage;
function handleKeyPressedMessage(event){
    if(event.keyCode == 13||event.keyCode == "click"){
        event.preventDefault();

        newMessage = sendMessage.value;

        if (wsOpen){

            ws.send( "message "+ name + " " +room + " " + newMessage);


        }
        else {
            //display websocket not open
            alert("Server not open");
        }

    }


}




userName.addEventListener("keypress", handleKeyPressedUserNameAndRoom);

roomName.addEventListener("keypress", handleKeyPressedUserNameAndRoom);
sendMessage.addEventListener("keypress",handleKeyPressedMessage);


let wsOpen = false;

function  handleOpen(){
    wsOpen = true;
    console.log("HandShake Successful!!");
}


let ws = new WebSocket("ws://localhost:8080");
ws.onopen = handleOpen;
ws.onmessage = handleJoinMsg;
ws.onclose = handleClose;
window.onbeforeunload = handleBeforeClose;

function handleClose(){
    wsOpen = false;
}
function handleBeforeClose(){
    ws.send( "leave"+ " " +room + " " + name );
}



function handleJoinMsg( event ) {


     console.log("Message in function handleMessage: ");
     const msg = event.data;
      console.log(msg);
    const msgObj = JSON.parse(msg);
    console.log(msgObj);

            if (msgObj.type == "join") {
                let ms = document.createElement("p");
                ms.textContent = "" + msgObj.user + " has joined the room " +msgObj.time;
                messages.appendChild(ms);
            }


            else if(msgObj.type == "message"){
                let ms = document.createElement("p");
                ms.textContent = msgObj.user + ": " + msgObj.message + " " +msgObj.time;
                messages.appendChild(ms);
            }
            else {
                let ms = document.createElement("p");
                ms.textContent = "" + msgObj.user + " has left the room " +msgObj.time;
                messages.appendChild(ms);
            }

    console.log(msgObj);


}

function main(){
    console.log("End");
}
window.onload = main();