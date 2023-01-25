import React from 'react';
import '../style/style.css';
import {Navigate, useLocation} from 'react-router-dom';
import Stomp from 'stompjs';
import SockJS from 'sockjs-client';

let sockJS = new SockJS("http://10.0.0.18:8080/room");
let stompClient = Stomp.over(sockJS);

const FindGame = props => {
    const location = useLocation();
    const login = location.state;
    return <FindGameContent login={login} {...props} />
}

const onConnected = () => {
    console.log("connected");
    stompClient.subscribe(
        "/room/user",
        onMessageReceived
    );
}

const onError = (error) => {
    console.log(error);
}

const onMessageReceived = (message) => {
    console.log(message.body);
}

const sendMessage = (login) => {
    let currentDate = getCurrentDate();
    let ip = '26.118.51.73:8080';
    const message = {
        author: login,
        content:
            {
                roomCode: 1,
                userLogin: login
            },
        messageType: "PLAYER_ROOM_JOIN",
        datetime: currentDate,
        receiver: "ws://" + ip + "/room/websocket"
    };
    stompClient.send("/room/api/socket", {}, JSON.stringify(message));
};

const getCurrentDate = () => {
    let date = new Date();
    return date.getFullYear() + '-' + ("0" + (date.getMonth() + 1)).slice(-2) + '-' + ("0" + date.getDay()).slice(-2) + 'T' + ("0" + date.getHours()).slice(-2) + ':' + ("0" + date.getMinutes()).slice(-2) + ':' + ("0" + date.getSeconds()).slice(-2) + '.' + ("0" + date.getMilliseconds()).slice(-2);
}

class FindGameContent extends React.Component {
    constructor(props) {
        super(props);
        this.findGame = this.findGame.bind(this);
        this.state = {
            message: "",
            success: false,
            login: this.props.login,
            game: null
        }
    }

    componentDidMount() {
        stompClient.connect({}, onConnected, onError);
    }

    findGame() {
        let game = document.getElementById("game").value;
        sendMessage(this.state.login);
    }

    render() {
        if (this.state.success) {
            const login = this.state.login;
            const game = this.state.game;
            return <Navigate to="/game" state={{login, game}} />
        }
        return (
            <div className="find-game">
                <div className="find-game__header">
                    <h2>Найти игру</h2>
                </div>
                <div className="chip"></div>
                <div className="card1"></div>
                <div className="card2"></div>
                <div className="find-game__main">
                    <div>
                        <p>Введите идентификатор комнаты</p>
                        <input type="text" id="game" placeholder="Идентификатор комнаты"/>
                    </div>
                    <p>{this.state.message}</p>
                    <button className="find-game__button" onClick={this.findGame}>Найти</button>
                </div>
            </div>
        )
    }
}

export default FindGame;