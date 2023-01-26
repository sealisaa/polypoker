import Stomp from 'stompjs';
import SockJS from 'sockjs-client';

let sockJS = new SockJS("http://localhost:8080/room");
let stompClient = Stomp.over(sockJS);

const onConnected = () => {
}

const onError = (error) => {
    console.log(error);
}

stompClient.connect({}, onConnected, onError);
export default stompClient;