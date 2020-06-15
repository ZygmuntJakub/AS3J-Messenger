import SockJS from "sockjs-client";
import Stomp from "stompjs";

export function enableWebSocketMessaging() {
    /*
    Content of this function should be cut and pasted to
    component representing chat window.
     */
    const socket = new SockJS("http://localhost:8080/ws");
    const stompClient = Stomp.over(socket);
    stompClient.connect({}, frame => {

        // Should be current chat UUID
        const chatUuid = "86acf316-9811-11ea-bb37-0242ac130002";

        stompClient.subscribe(`/messages/add/${chatUuid}`, message => {
            // Reaction for new message arrival
            console.log(message);
        });
    });
}