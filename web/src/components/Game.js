import React from 'react';
import '../style/style.css';
import {Link, useLocation} from "react-router-dom";
import stompClient from "../websocket/websocketConfig";

const Modal = ({visible = false, btn}) => {
    if (!visible) {
        return null
    }
    return (
        <div className='modal'>
            <div className='game__modal'>
                <div className="game__modal-dialog">
                    <h3 className="game__modal-header">Поставьте малый блайнд</h3>
                    <input className="modal-input" type="text" id="smallBlind" placeholder="Размер ставки"/>
                    {btn}
                </div>
            </div>
        </div>
    )
}

const defaultPlayerInfo = {
    present: false,
    login: "",
    name: "",
    newStake: 0,
    currentStake: 0,
    cash: 0,
    card1: null,
    card2: null,
    smallBlind: false,
    bigBlind: false,
    ready: false
};

const Game = props => {
    const location = useLocation();
    const roomInfo = location.state.roomInfo;
    return <GameContent roomInfo={roomInfo} {...props} />
}

const sendMessage = (message) => {
    stompClient.send("/room/api/socket", {}, JSON.stringify(message));
};

const getCurrentDate = () => {
    let date = new Date();
    return date.getFullYear() + '-' + ("0" + (date.getMonth() + 1)).slice(-2) + '-' + ("0" + date.getDay()).slice(-2) + 'T' + ("0" + date.getHours()).slice(-2) + ':' + ("0" + date.getMinutes()).slice(-2) + ':' + ("0" + date.getSeconds()).slice(-2) + '.' + ("0" + date.getMilliseconds()).slice(-2);
}

class GameContent extends React.Component {
    constructor(props) {
        super(props);
        this.flipCards = this.flipCards.bind(this);
        this.onMessageReceived = this.onMessageReceived.bind(this);
        this.onConnected = this.onConnected.bind(this);
        this.checkRoomPlayers = this.checkRoomPlayers.bind(this);
        this.updateRoomPlayers = this.updateRoomPlayers.bind(this);
        this.getReady = this.getReady.bind(this);
        this.beginRound = this.beginRound.bind(this);
        this.getSmallBlind = this.getSmallBlind.bind(this);
        this.setSmallBlind = this.setSmallBlind.bind(this);
        this.openSmallBlindModal = this.openSmallBlindModal.bind(this);
        this.submitSmallBlind = this.submitSmallBlind.bind(this);
        this.showPlayersBet = this.showPlayersBet.bind(this);
        this.getBigBlind = this.getBigBlind.bind(this);
        this.state = {
            change: false,
            isModal: false,
            connected: stompClient.connected,
            roomCode: this.props.roomInfo.content.roomCode,
            activePlayerLogin: this.props.roomInfo.content.userLogin,
            activePlayer: {
                login: "",
                name: "",
                currentStake: 0,
                newStake: 0,
                cash: 0,
                card1: null,
                card2: null,
                smallBlind: false,
                bigBlind: false,
                ready: false
            },
            players: [
                defaultPlayerInfo,
                defaultPlayerInfo,
                defaultPlayerInfo,
                defaultPlayerInfo,
                defaultPlayerInfo,
            ],
        };
        if (stompClient.connected) {
            stompClient.subscribe(
                "/room/user",
                this.onMessageReceived
            );
            this.checkRoomPlayers();
        }
        stompClient.connectCallback = this.onConnected;
    }

    onConnected() {
        this.setState({connected: true});
        stompClient.subscribe(
            "/room/user",
            this.onMessageReceived
        );
        this.checkRoomPlayers();
    }

    onMessageReceived(message) {
        let messageJSON = JSON.parse(message.body);
        let roomPlayers = messageJSON.content.roomPlayersList;
        if (messageJSON.messageType === 'CHECK_ROOM_PLAYERS' && messageJSON.content.roomCode === this.state.roomCode) {
            this.updateRoomPlayers(roomPlayers);
        }
        if (messageJSON.messageType === 'ROUND_BEGIN') {
            this.beginRound();
        }
        if (messageJSON.messageType === 'WHO_IS_SMALL_BLIND' && messageJSON.content.userLogin === this.state.activePlayerLogin) {
            this.setSmallBlind();
        }
        if (messageJSON.messageType === 'PLAYER_MAKE_BET') {
            this.showPlayersBet(messageJSON.content.userLogin, messageJSON.content.moneyValue);
        }
    }

    updateRoomPlayers(players) {
        let currentPlayersList = [];
        for (let i = 0; i < 6; i++) {
            if (players[i]) {
                let player = players[i];
                if (player.login !== this.state.activePlayerLogin) {
                    let newPlayer = {
                        present: true,
                        login: player.login,
                        name: player.name,
                        currentStake: player.currentStake,
                        newStake: 0,
                        cash: player.cash,
                        card1: player.card1,
                        card2: player.card2,
                        smallBlind: player.smallBlind,
                        bigBlind: player.bigBlind,
                        ready: player.ready
                    };
                    currentPlayersList.push(newPlayer);
                } else {
                    this.setState({
                        activePlayer: {
                            login: player.login,
                            name: player.name,
                            currentStake: player.currentStake,
                            newStake: 0,
                            cash: player.cash,
                            card1: player.card1,
                            card2: player.card2,
                            smallBlind: player.smallBlind,
                            bigBlind: player.bigBlind,
                            ready: player.ready
                        }
                    })
                }
            } else {
                currentPlayersList.push(defaultPlayerInfo);
            }
        }
        this.setState({players: currentPlayersList});
    }

    checkRoomPlayers() {
        let currentDate = getCurrentDate();
        let message = {
            messageType: "CHECK_ROOM_PLAYERS",
            content:
                {
                    roomCode: this.state.roomCode
                },
            author: this.state.activePlayerLogin,
            datetime: currentDate,
            receiver: "ws://192.168.1.116:8080/room/websocket"
        }
        sendMessage(message);
    }

    getReady() {
        let currentDate = getCurrentDate();
        let message = {
            messageType: "PLAYER_READY_SET",
            content:
                {
                    roomCode: this.state.roomCode
                },
            author: this.state.activePlayerLogin,
            datetime: currentDate,
            receiver: "ws://192.168.1.116:8080/room/websocket"
        }
        sendMessage(message);
        let readyBtn = document.getElementById("readyBtn");
        readyBtn.innerText = "НЕ ГОТОВ";
        readyBtn.className = "game__not-ready";
    }

    beginRound() {
        this.getSmallBlind();
    }

    getSmallBlind() {
        let currentDate = getCurrentDate();
        let message = {
            messageType: "WHO_IS_SMALL_BLIND",
            content:
                {   roomCode: this.state.roomCode,
                    userLogin: null,
                    userName: null,
                    moneyValue: 0,
                    cardSuit: null,
                    cardNumber: null,
                    roomPlayersList: null
                },
            author: this.state.activePlayerLogin,
            dateTime: currentDate,
            receiver: "ws://192.168.1.116:8080/room/websocket"
        }
        sendMessage(message);
    }

    setSmallBlind() {
        this.state.activePlayer.smallBlind = true;
        this.openSmallBlindModal();
    }

    flipCards() {
        let card1 = document.getElementById("game__card1");
        let card2 = document.getElementById("game__card2");
        let card3 = document.getElementById("game__card3");
        card1.classList.toggle('is-flipped');
        card2.classList.toggle('is-flipped');
        card3.classList.toggle('is-flipped');
    }

    openSmallBlindModal() {
        this.setState({isModal: true});
    }

    submitSmallBlind() {
        let smallBlind = document.getElementById("smallBlind").value;
        let currentDate = getCurrentDate();
        let message = {
            messageType: "PLAYER_MAKE_BET",
            content:
                {
                    roomCode: this.state.roomCode,
                    userLogin: this.state.activePlayerLogin,
                    moneyValue: smallBlind
                },
            author: this.state.activePlayerLogin,
            datetime: currentDate,
            receiver: "ws://192.168.1.116:8080/room/websocket"
        }
        sendMessage(message);
        this.setState({isModal: false});

    }

    getBigBlind() {

    }

    showPlayersBet(userLogin, moneyValue) {
        if (userLogin === this.state.activePlayerLogin) {
            this.state.activePlayer.newStake = moneyValue;
            this.state.activePlayer.currentStake += moneyValue;
            this.setState({change: true});
            return;
        }
        for (let i = 0; i < 5; i++) {
            if (this.state.players[i].login === userLogin) {
                this.state.players[i].newStake = moneyValue;
                this.state.players[i].currentStake += moneyValue;
                this.setState({change: true});
            }
        }
    }

    render() {
        let activePlayer = this.state.activePlayer;
        let player1 = this.state.players[0];
        let player2 = this.state.players[1];
        let player3 = this.state.players[2];
        let player4 = this.state.players[3];
        let player5 = this.state.players[4];
        return (
            <div className="game">
                <Modal
                    visible={this.state.isModal}
                    btn={<button className="game__modal-button" onClick={this.submitSmallBlind}>Ок</button>}
                />
                <div className="game__header">
                    <Link to="/lobby"><button className="game__home-button"></button></Link>
                    <button className="game__menu-button"></button>
                </div>
                <div className="game__main">
                    <div className="game__table">

                        <div className="game__active-user">
                            <div className="game__user-stake">
                                <div className="game__user-current-stake">
                                    $ {activePlayer.currentStake}
                                </div>
                                {activePlayer.newStake !== 0 ?
                                    <div className="game__user-new-stake">
                                        + {activePlayer.newStake}
                                    </div>
                                    : null
                                }
                            </div>
                            <div className="game__user-info">
                                <div className="game__active-user-avatar"></div>
                                <span className="game__user-login">You</span>
                                <span className="game__user-balance">$ {activePlayer.cash}</span>
                            </div>
                            <div className="game__active-user-cards">
                                <div className="game__user-card game__active-user-card1"></div>
                                <div className="game__user-card game__active-user-card2"></div>
                            </div>
                        </div>

                        {player1.present ? <div className="game__user1">
                            <div className="game__user-stake">
                                <div className="game__user-current-stake">
                                    $ {player1.currentStake}
                                </div>
                                {player1.newStake !== 0 ?
                                    <div className="game__user-new-stake">
                                        + {player1.newStake}
                                    </div>
                                    : null
                                }
                            </div>
                            <div className="game__user-info">
                                <div className="game__user-avatar game__user-avatar1"></div>
                                <span className="game__user-login">{player1.login}</span>
                                <span className="game__user-balance">$ {player1.cash}</span>
                            </div>
                            <div className="game__user1-cards">
                                <div className="game__user-card"></div>
                                <div className="game__user-card"></div>
                            </div>
                        </div> : null}

                        {player3.present ? <div className="game__user3">
                            <div className="game__user-stake">
                                <div className="game__user-current-stake">
                                    $ {player3.currentStake}
                                </div>
                                {player3.newStake !== 0 ?
                                    <div className="game__user-new-stake">
                                        + {player3.newStake}
                                    </div>
                                    : null
                                }
                            </div>
                            <div className="game__user-info">
                                <div className="game__user-avatar game__user-avatar3"></div>
                                <span className="game__user-login">{player3.login}</span>
                                <span className="game__user-balance">$ {player3.cash}</span>
                            </div>
                            <div className="game__user3-cards">
                                <div className="game__user-card"></div>
                                <div className="game__user-card"></div>
                            </div>
                        </div> : null}

                        {player2.present ? <div className="game__user2">
                            <div className="game__user2-cards">
                                <div className="game__user-card"></div>
                                <div className="game__user-card"></div>
                            </div>
                            <div className="game__user-stake">
                                <div className="game__user-current-stake">
                                    $ {player2.currentStake}
                                </div>
                                {player2.newStake !== 0 ?
                                    <div className="game__user-new-stake">
                                        + {player2.newStake}
                                    </div>
                                    : null
                                }
                            </div>
                            <div className="game__user-info">
                                <div className="game__user-avatar game__user-avatar2"></div>
                                <span className="game__user-login">{player2.login}</span>
                                <span className="game__user-balance">$ {player2.cash}</span>
                            </div>
                        </div> : null}

                        { player4.present ? <div className="game__user4">
                            <div className="game__user-stake">
                                <div className="game__user-current-stake">
                                    $ {player4.currentStake}
                                </div>
                                {player4.newStake !== 0 ?
                                    <div className="game__user-new-stake">
                                        + {player4.newStake}
                                    </div>
                                    : null
                                }
                            </div>
                            <div className="game__user-info">
                                <div className="game__user-avatar game__user-avatar4"></div>
                                <span className="game__user-login">{player4.login}</span>
                                <span className="game__user-balance">$ {player4.cash}</span>
                            </div>
                            <div className="game__user4-cards">
                                <div className="game__user-card"></div>
                                <div className="game__user-card"></div>
                            </div>
                        </div> : null}

                        { player5.present ? <div className="game__user6">
                            <div className="game__user6-cards">
                                <div className="game__user-card"></div>
                                <div className="game__user-card"></div>
                            </div>
                            <div className="game__user-stake">
                                <div className="game__user-current-stake">
                                    $ {player5.currentStake}
                                </div>
                                {player5.newStake !== 0 ?
                                    <div className="game__user-new-stake">
                                        + {player5.newStake}
                                    </div>
                                    : null
                                }
                            </div>
                            <div className="game__user-info">
                                <div className="game__user-avatar game__user-avatar6"></div>
                                <span className="game__user-login">{player5.login}</span>
                                <span className="game__user-balance">$ {player5.cash}</span>
                            </div>
                        </div> : null}

                        <div className="game__cards">
                            <div id="game__card1" className="game__card1 flip-card card">
                                <div className="game__card-back back card__face"></div>
                                <div className="game__card1-front front card__face"></div>
                            </div>
                            <div id="game__card2" className="game__card2 flip-card card">
                                <div className="game__card-back back card__face"></div>
                                <div className="game__card2-front front card__face"></div>
                            </div>
                            <div id="game__card3" className="game__card3 flip-card card">
                                <div className="game__card-back back card__face"></div>
                                <div className="game__card3-front front card__face"></div>
                            </div>
                            <div className="game__card4"></div>
                            <div className="game__card5"></div>
                        </div>
                    </div>
                </div>
                <div className="game__actions">
                    <button className="game__fold">FOLD</button>
                    <button className="game__call">CALL</button>
                    <button className="game__raise">RAISE</button>
                    <div className="game__right-nav">
                        <button id="readyBtn" className="game__ready" onClick={this.getReady}>ГОТОВ</button>
                        <div className="game__bank">
                            <div className="game__bank-chip"></div>
                            <span className="game__bank-value">BANK: $ 1.000.000</span>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default Game;