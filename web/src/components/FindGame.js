import React from 'react';
import '../style/style.css';
import {Navigate, useLocation} from 'react-router-dom';
import stompClient from "../websocket/websocketConfig";

const FindGame = props => {
    const location = useLocation();
    const login = location.state;
    return <FindGameContent login={login} {...props} />
}

const sendMessage = (login, game) => {
    let ip = '26.118.51.73:8080';
    const message = {
        author: login,
        content:
            {
                roomCode: game,
                userLogin: login
            },
        messageType: "PLAYER_ROOM_JOIN",
        receiver: "ws://" + ip + "/room/websocket"
    };
    stompClient.send("/room/api/socket", {}, JSON.stringify(message));
};

class FindGameContent extends React.Component {
    constructor(props) {
        super(props);
        this.findGame = this.findGame.bind(this);
        this.onMessageReceived = this.onMessageReceived.bind(this);
        this.onConnected = this.onConnected.bind(this);
        this.state = {
            connected: stompClient.connected,
            message: "",
            success: false,
            login: this.props.login,
            roomInfo: null
        }
        if (stompClient.connected) {
            stompClient.unsubscribe("poker");
            stompClient.subscribe(
                "/room/user",
                this.onMessageReceived,
                {id: "poker"}
            );
        }
        stompClient.connectCallback = this.onConnected;
    }

    onConnected() {
        this.setState({connected: true});
        stompClient.unsubscribe("poker");
        stompClient.subscribe(
            "/room/user",
            this.onMessageReceived,
            {id: "poker"}
        );
    }

    findGame() {
        if (this.state.connected) {
            let game = document.getElementById("game").value;
            if (game === '1') {
                sendMessage(this.state.login, game);
            }
        }
    }

    onMessageReceived(message) {
        let messageJSON = JSON.parse(message.body);
        if (messageJSON.messageType === 'PLAYER_ROOM_JOIN' && messageJSON.content.userLogin === this.state.login) {
            this.setState({
                roomInfo: messageJSON,
                success: true
            });
        }
    }

    render() {
        if (this.state.success) {
            const roomInfo = this.state.roomInfo;
            return <Navigate to="/game" state={{ roomInfo }} />
        }
        return (
            <div className="find-game">
                <div className="find-game__header">
                    <h2>Найти игру</h2>
                </div>
                <div className="find-game__main">
                    <div className="chip"></div>
                    <div className="card1"></div>
                    <div className="card2"></div>
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