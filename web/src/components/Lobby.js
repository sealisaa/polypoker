import React from 'react';
import '../style/style.css';
import {Link, Navigate, useLocation} from "react-router-dom";
import UserService from "../services/UserService";
import stompClient from "../websocket/websocketConfig";

const Lobby = props => {
    const location = useLocation();
    const login = location.state;
    return <LobbyContent login={login} {...props} />
}

const sendMessage = (message) => {
    stompClient.send("/room/api/socket", {}, JSON.stringify(message));
};

const CreateRoomModal = ({visible = false, btn, cancel, text}) => {
    if (!visible) {
        return null
    }
    return (
        <div className='modal'>
            <div className='lobby__modal'>
                <div className="game__modal-dialog">
                    <h3 className="game__modal-header">{text}</h3>
                    <input className="modal-input" type="text" id="roomId" placeholder="Номер комнаты"/>
                    <input className="modal-input" type="text" id="smallBlind" placeholder="Размер малого блайнда"/>
                    <div className="modal-buttons">
                        {cancel}
                        {btn}
                    </div>
                </div>
            </div>
        </div>
    )
}

class LobbyContent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isModal: false,
            login: props.login,
            totalGamesPlayed: 0,
            winGames: 0,
            currentCoinsCount: 0,
            totalEarn: 0
        }
        this.createRoom = this.createRoom.bind(this);
        this.submitRoom = this.submitRoom.bind(this);
        this.closeModal = this.closeModal.bind(this);
        this.onConnected = this.onConnected.bind(this);
        this.onMessageReceived = this.onMessageReceived.bind(this);
        this.joinRoom = this.joinRoom.bind(this);
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

    componentDidMount() {
        UserService.getUserStatistic(this.state.login).then((response) => {
            this.setState({
                totalGamesPlayed: response.data.totalGamesPlayed,
                winGames: response.data.winGames,
                currentCoinsCount: response.data.currentCoinsCount,
                totalEarn: response.data.totalEarn
            });
        });
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

    onMessageReceived(message) {
        let messageJSON = JSON.parse(message.body);
        if (messageJSON.messageType === 'OK') {
            this.joinRoom(messageJSON.content.roomCode);
        }
        if (messageJSON.messageType === 'PLAYER_ROOM_JOIN' && messageJSON.content.userLogin === this.state.login) {
            this.setState({
                roomInfo: messageJSON,
                success: true
            });
        }
    }

    joinRoom(id) {
        const message = {
            author: this.state.login,
            content:
                {
                    roomCode: id,
                    userLogin: this.state.login
                },
            messageType: "PLAYER_ROOM_JOIN",
            receiver: "receiver"
        };
        sendMessage(message);
    }

    createRoom() {
        this.setState({isModal: true});
    }

    submitRoom() {
        let id = document.getElementById("roomId").value;
        let blind = document.getElementById("smallBlind").value;
        let message = {
            messageType: "ROOM_CREATE",
            content: {
                roomCode: id,
                moneyValue: blind
            },
            author: this.state.login,
            receiver: "receiver"
        }
        sendMessage(message);
    }

    closeModal() {
        this.setState({isModal: false});
    }


    render() {
        if (this.state.success) {
            const roomInfo = this.state.roomInfo;
            return <Navigate to="/game" state={{ roomInfo }} />
        }
        let login = this.state.login;
        return (
            <div className="lobby-container">
                <CreateRoomModal
                    visible={this.state.isModal}
                    btn={<button className="game__modal-button" onClick={this.submitRoom}>Ок</button>}
                    cancel={<button className="cancel-button" onClick={this.closeModal}>Отмена</button>}
                    text="Создать игру"
                />
                <div className="lobby">
                    <div className="lobby__header">
                        <div className="lobby__avatar">
                            <img src={require('../img/cat1.png')} className="avatar-pic"></img>
                        </div>
                        <span className="lobby__username">{this.state.login}</span>
                        <div className="lobby__balance">
                            <div className="lobby__balance-chip" />
                            <span className="lobby__username">{this.state.currentCoinsCount}</span>
                        </div>
                        <div className="lobby__exit">
                            <Link to="/">
                                <button className="lobby__exit-button"></button>
                            </Link>
                        </div>
                    </div>
                    <div className="lobby__statistics">
                        <div className="lobby__all-games">
                            <div className="lobby__all-games-pic"></div>
                            <div className="lobby__all-games-content">
                                <span className="lobby__all-games-count">{this.state.totalGamesPlayed}</span><br></br>
                                <span className="lobby__all-games-text">всего партий сыграно</span>
                            </div>
                        </div>
                        <div className="lobby__win-games">
                            <div className="lobby__win-games-pic"></div>
                            <div className="lobby__win-games-content">
                                <span className="lobby__win-games-count">{this.state.winGames}</span><br></br>
                                <span className="lobby__win-games-text">выигрышных партий</span>
                            </div>
                        </div>
                        <div className="lobby__money-earned">
                            <div className="lobby__money-earned-pic"></div>
                            <div className="lobby__money-earned-content">
                                <span className="lobby__money-earned-count">$ {this.state.totalEarn}</span><br></br>
                                <span className="lobby__money-earned-text">заработано за все время</span>
                            </div>
                        </div>
                    </div>
                    <div className="lobby__actions">
                        <div className="lobby__donate">
                            <div className="lobby__donate-pic"></div>
                            <span className="lobby__donate-text">Пополнить баланс</span>
                        </div>
                        <Link to="/find-game" state={login} className="lobby__find-game">
                            <div className="lobby__find-game-pic"></div>
                            <span className="lobby__find-game-text">Найти игру</span>
                        </Link>
                        <div className="lobby__create-game" onClick={this.createRoom}>
                            <div className="lobby__create-game-pic"></div>
                            <span className="lobby__create-game-text">Создать игру</span>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default Lobby;