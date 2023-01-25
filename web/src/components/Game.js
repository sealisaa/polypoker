import React from 'react';
import '../style/style.css';
import {Link, useLocation} from "react-router-dom";
import stompClient from "../websocket/websocketConfig";

const Game = props => {
    const location = useLocation();
    const roomInfo = location.state.roomInfo;
    return <GameContent roomInfo={roomInfo} {...props} />
}

const onError = (error) => {
    console.log(error);
}

const sendMessage = (login, game) => {
    const message = {
        author: login,
        content:
            {
                roomCode: game,
                userLogin: login
            },
        messageType: "PLAYER_ROOM_JOIN"
    };
    stompClient.send("/room/api/socket", {}, JSON.stringify(message));
};

class GameContent extends React.Component {
    constructor(props) {
        super(props);
        this.flipCards = this.flipCards.bind(this);
        this.onMessageReceived = this.onMessageReceived.bind(this);
        this.state = {
            roomCode: this.props.roomInfo.content.roomCode,
            activeUser: this.props.roomInfo.content.userLogin,
            roomPlayersList: []
        };
    }

    componentDidMount() {
        stompClient.subscribe(
            "/room/user",
            this.onMessageReceived
        );
    }

    onMessageReceived(message) {
        console.log(message);
    }

    flipCards() {
        let card1 = document.getElementById("game__card1");
        let card2 = document.getElementById("game__card2");
        let card3 = document.getElementById("game__card3");
        card1.classList.toggle('is-flipped');
        card2.classList.toggle('is-flipped');
        card3.classList.toggle('is-flipped');
    }

    render() {
        return (
            <div className="game">
                <div className="game__header">
                    <Link to="/lobby"><button className="game__home-button"></button></Link>
                    <button className="game__menu-button"></button>
                </div>
                <div className="game__main">
                    <div className="game__table">
                        <div className="game__active-user">
                            <div className="game__user-info">
                                <div className="game__active-user-avatar"></div>
                                <span className="game__user-login">You</span>
                                <span className="game__user-balance">$ 100.000</span>
                            </div>
                            <div className="game__active-user-cards">
                                <div className="game__user-card game__active-user-card1"></div>
                                <div className="game__user-card game__active-user-card2"></div>
                            </div>
                        </div>
                        <div className="game__user1">
                            <div className="game__user-info">
                                <div className="game__user-avatar game__user-avatar1"></div>
                                <span className="game__user-login">Mattew</span>
                                <span className="game__user-balance">$ 1.000</span>
                            </div>
                            <div className="game__user1-cards">
                                <div className="game__user-card"></div>
                                <div className="game__user-card"></div>
                            </div>
                        </div>
                        <div className="game__user2">
                            <div className="game__user-info">
                                <div className="game__user-avatar game__user-avatar2"></div>
                                <span className="game__user-login">Angela</span>
                                <span className="game__user-balance">$ 3.000</span>
                            </div>
                            <div className="game__user2-cards">
                                <div className="game__user-card"></div>
                                <div className="game__user-card"></div>
                            </div>
                        </div>
                        <div className="game__user3">
                            <div className="game__user3-cards">
                                <div className="game__user-card"></div>
                                <div className="game__user-card"></div>
                            </div>
                            <div className="game__user-info">
                                <div className="game__user-avatar game__user-avatar3"></div>
                                <span className="game__user-login">Justin</span>
                                <span className="game__user-balance">$ 58.000</span>
                            </div>
                        </div>
                        <div className="game__user4">
                            <div className="game__user-info">
                                <div className="game__user-avatar game__user-avatar4"></div>
                                <span className="game__user-login">Kim</span>
                                <span className="game__user-balance">$ 15.000</span>
                            </div>
                            <div className="game__user4-cards">
                                <div className="game__user-card"></div>
                                <div className="game__user-card"></div>
                            </div>
                        </div>
                        <div className="game__user6">
                            <div className="game__user6-cards">
                                <div className="game__user-card"></div>
                                <div className="game__user-card"></div>
                            </div>
                            <div className="game__user-info">
                                <div className="game__user-avatar game__user-avatar6"></div>
                                <span className="game__user-login">Ed</span>
                                <span className="game__user-balance">$ 13.000</span>
                            </div>
                        </div>
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
                        <button className="game__check" onClick={this.flipCards}>ГОТОВ</button>
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