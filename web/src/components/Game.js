import React from 'react';
import '../style/style.css';
import {Navigate, useLocation} from "react-router-dom";
import stompClient from "../websocket/websocketConfig";

const Modal = ({visible = false, btn, cancel, text}) => {
    if (!visible) {
        return null
    }
    return (
        <div className='modal'>
            <div className='game__modal'>
                <div className="game__modal-dialog">
                    <h3 className="game__modal-header">{text}</h3>
                    <input className="modal-input" type="text" id="bet" placeholder="Размер ставки"/>
                    <div className="modal-buttons">
                        {cancel}
                        {btn}
                    </div>
                </div>
            </div>
        </div>
    )
}

const WinnerModal = ({visible = false, btn, login, money}) => {
    if (!visible) {
        return null
    }
    return (
        <div className='modal'>
            <div className='winner-modal'>
                <div className="game__modal-dialog">
                    <div className="winner-pic"></div>
                    <h3 className="winner-header">
                        Победил игрок {login}<br />
                        Сумма выигрыша: {money}
                    </h3>
                    {btn}
                </div>
            </div>
        </div>
    )
}

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
        this.submitSmallBlind = this.submitSmallBlind.bind(this);
        this.showPlayersBet = this.showPlayersBet.bind(this);
        this.getBigBlind = this.getBigBlind.bind(this);
        this.setBigBlind = this.setBigBlind.bind(this);
        this.submitBigBlind = this.submitBigBlind.bind(this);
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
        this.openWinnerModal = this.openWinnerModal.bind(this);
        this.closeWinnerModal = this.closeWinnerModal.bind(this);
        this.confirmBet = this.confirmBet.bind(this);
        this.exitRoom = this.exitRoom.bind(this);
        this.isNextStepOfRound = this.isNextStepOfRound.bind(this);
        this.nextStepOfRound = this.nextStepOfRound.bind(this);
        this.preflop = this.preflop.bind(this);
        this.flop = this.flop.bind(this);
        this.tern = this.tern.bind(this);
        this.river = this.river.bind(this);
        this.showdown = this.showdown.bind(this);
        this.drawCard = this.drawCard.bind(this);
        this.handleTabClose = this.handleTabClose.bind(this);
        this.addNewPlayer = this.addNewPlayer.bind(this);
        this.playerMustMakeBet = this.playerMustMakeBet.bind(this);
        this.makeBet = this.makeBet.bind(this);
        this.submitBet = this.submitBet.bind(this);
        this.makeCheck = this.makeCheck.bind(this);
        this.showCheck = this.showCheck.bind(this);
        this.makeFold = this.makeFold.bind(this);
        this.showFold = this.showFold.bind(this);
        this.state = {
            winner: "",
            winMoney: 0,
            winnerModal: false,
            mustMakeBet: false,
            card1: null,
            card2: null,
            card3: null,
            card4: null,
            card5: null,
            bank: 0,
            maxBet: 0,
            lastMessage: "",
            gameState: "",
            exit: false,
            modalData: 0,
            modalText: "",
            change: false,
            isModal: false,
            connected: stompClient.connected,
            roomCode: this.props.roomInfo.content.roomCode,
            activePlayerLogin: this.props.roomInfo.content.userLogin,
            activePlayer: {
                fold: false,
                check: false,
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
            players: [],
        };
        if (stompClient.connected) {
            stompClient.unsubscribe("poker");
            stompClient.subscribe(
                "/room/user",
                this.onMessageReceived,
                {id: "poker"}
            );
        }
        stompClient.connectCallback = this.onConnected;
        window.addEventListener('beforeunload', this.handleTabClose);
        this.checkRoomPlayers();
    }

    handleTabClose(event) {
        this.exitRoom();
        event.preventDefault();
    };

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
        if (messageJSON.content.roomCode !== this.state.roomCode) {
            return;
        }
        let roomPlayers = messageJSON.content.roomPlayersList;
        if (messageJSON.messageType === 'CHECK_ROOM_PLAYERS') {
            this.updateRoomPlayers(roomPlayers);
        }
        if (messageJSON.messageType === 'PLAYER_ROOM_JOIN' && messageJSON.content.userLogin !== this.state.activePlayerLogin) {
            this.addNewPlayer(messageJSON.content);
        }
        if (messageJSON.messageType === 'ROUND_BEGIN') {
            this.beginRound();
        }
        if (messageJSON.messageType === 'WHO_IS_SMALL_BLIND') {
            this.setSmallBlind(messageJSON.content.userLogin);
        }
        if (messageJSON.messageType === 'PLAYER_MAKE_BET') {
            this.showPlayersBet(messageJSON.content.userLogin, messageJSON.content.moneyValue, messageJSON.content.betType);
        }
        if (messageJSON.messageType === 'WHO_IS_BIG_BLIND') {
            this.setBigBlind(messageJSON.content.userLogin);
        }
        if (messageJSON.messageType === 'PLAYER_ROOM_EXIT') {
            this.checkRoomPlayers();
        }
        if (messageJSON.messageType === 'NEXT_STEP_OF_ROUND' && this.state.gameState !== messageJSON.content.gameState) {
            this.nextStepOfRound(messageJSON.content.gameState, messageJSON.content.moneyValue);
        }
        if (messageJSON.messageType === 'DRAW_CARD') {
            this.drawCard(messageJSON.content.userLogin, messageJSON.content.cardsList, messageJSON.receiver);
        }
        if (messageJSON.messageType === 'PLAYER_MUST_MAKE_BET') {
            if (messageJSON.content.userLogin === this.state.activePlayerLogin) {
                this.makeBet(messageJSON.content.moneyValue);
            } else {
                this.setState({mustMakeBet: false});
            }
        }
        if (messageJSON.messageType === 'PLAYER_MAKE_CHECK') {
            this.showCheck(messageJSON.content.userLogin);
        }
        if (messageJSON.messageType === 'PLAYER_MAKE_FOLD') {
            this.showFold(messageJSON.content.userLogin);
        }
        if (messageJSON.messageType === 'WINNER_PLAYER' && messageJSON.receiver === this.state.activePlayerLogin) {
            this.openWinnerModal(messageJSON.content.userLogin, messageJSON.content.moneyValue);
        }
    }

    updateRoomPlayers(players) {
        let currentPlayersList = [];
        for (let i = 0; i < 6; i++) {
            if (players[i]) {
                let player = players[i];
                if (player.login !== this.state.activePlayerLogin) {
                    let newPlayer = {
                        fold: false,
                        check: false,
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
            }
        }
        this.setState({players: currentPlayersList});
    }

    addNewPlayer(player) {
        let newPlayer = {
            fold: false,
            check: false,
            present: true,
            login: player.login,
            name: player.name,
            currentStake: 0,
            newStake: 0,
            cash: player.moneyValue,
            card1: null,
            card2: null,
            smallBlind: false,
            bigBlind: false,
            ready: false
        };
        this.state.players.push(newPlayer);
        this.setState({change: true});
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
            receiver: "receiver"
        }
        sendMessage(message);
    }

    getReady() {
        if (this.state.lastMessage === "PLAYER_READY_SET") {
            return;
        }
        let currentDate = getCurrentDate();
        let message = {
            messageType: "PLAYER_READY_SET",
            content:
                {
                    roomCode: this.state.roomCode
                },
            author: this.state.activePlayerLogin,
            datetime: currentDate,
            receiver: "receiver"
        }
        sendMessage(message);
        this.setState({lastMessage: "PLAYER_READY_SET"});
        let readyBtn = document.getElementById("readyBtn");
        readyBtn.innerText = "НЕ ГОТОВ";
        readyBtn.className = "game__not-ready";
    }

    beginRound() {
        this.getSmallBlind();
    }

    getSmallBlind() {
        if (this.state.lastMessage === "WHO_IS_SMALL_BLIND") {
            return;
        }
        let currentDate = getCurrentDate();
        let message = {
            messageType: "WHO_IS_SMALL_BLIND",
            content:
                {   roomCode: this.state.roomCode,
                    userLogin: null,
                    userName: null,
                    moneyValue: 0,
                    suit: null,
                    rank: null,
                    roomPlayersList: null
                },
            author: this.state.activePlayerLogin,
            dateTime: currentDate,
            receiver: "receiver"
        }
        sendMessage(message);
        this.setState({lastMessage: "WHO_IS_SMALL_BLIND"});
    }

    setSmallBlind(login) {
        if (login === this.state.activePlayerLogin) {
            this.state.activePlayer.smallBlind = true;
            this.setState({mustMakeBet: true, modalData: 1, modalText: "Поставьте малый блайнд"});
        } else {
            for (let i = 0; i < this.state.players.length; i++) {
                if (this.state.players[i].login === login) {
                    this.state.players[i].smallBlind = true;
                    this.setState({change: true});
                }
            }
        }
    }

    flipCards() {
        let card1 = document.getElementById("game__card1");
        let card2 = document.getElementById("game__card2");
        let card3 = document.getElementById("game__card3");
        card1.classList.toggle('is-flipped');
        card2.classList.toggle('is-flipped');
        card3.classList.toggle('is-flipped');
    }

    makeBet(moneyValue) {
        this.setState({mustMakeBet: true, modalData: 3, modalText: "Сделайте ставку (минимум: " + moneyValue + ")"});
    }

    makeCheck() {
        let currentDate = getCurrentDate();
        let message = {
            messageType: "PLAYER_MAKE_CHECK",
            content:
                {
                    roomCode: this.state.roomCode,
                    userLogin: this.state.activePlayerLogin
                },
            author: this.state.activePlayerLogin,
            datetime: currentDate,
            receiver: "receiver"
        }
        sendMessage(message);
    }

    showCheck(login) {
        if (login === this.state.activePlayerLogin) {
            this.state.activePlayer.check = true;
            this.setState({change: true});
            this.playerMustMakeBet();
            return;
        }
        for (let i = 0; i < 5; i++) {
            if (this.state.players[i].login === login) {
                this.state.players[i].check = true;
                this.setState({change: true});
                return;
            }
        }
    }

    makeFold() {
        let currentDate = getCurrentDate();
        let message = {
            messageType: "PLAYER_MAKE_FOLD",
            content:
                {
                    roomCode: this.state.roomCode,
                    userLogin: this.state.activePlayerLogin
                },
            author: this.state.activePlayerLogin,
            datetime: currentDate,
            receiver: "receiver"
        }
        sendMessage(message);
    }

    showFold(login) {
        if (login === this.state.activePlayerLogin) {
            this.state.activePlayer.fold = true;
            this.setState({change: true});
            this.playerMustMakeBet();
            return;
        }
        for (let i = 0; i < 5; i++) {
            if (this.state.players[i].login === login) {
                this.state.players[i].fold = true;
                this.setState({change: true});
                return;
            }
        }
    }

    submitBet(bet) {
        let currentDate = getCurrentDate();
        let message = {
            messageType: "PLAYER_MAKE_BET",
            content:
                {
                    roomCode: this.state.roomCode,
                    userLogin: this.state.activePlayerLogin,
                    betType: "BET",
                    moneyValue: bet
                },
            author: this.state.activePlayerLogin,
            datetime: currentDate,
            receiver: "receiver"
        }
        this.setState({mustMakeBet: false, isModal: false});
        sendMessage(message);
    }

    confirmBet() {
        let bet = document.getElementById("bet").value;
        switch (this.state.modalData) {
            case 1:
                this.submitSmallBlind(bet);
                break;
            case 2:
                this.submitBigBlind(bet)
                break;
            case 3:
                this.submitBet(bet)
                break;
        }
    }

    submitSmallBlind(bet) {
        if (this.state.lastMessage === "PLAYER_MAKE_BET") {
            return;
        }
        let currentDate = getCurrentDate();
        let message = {
            messageType: "PLAYER_MAKE_BET",
            content:
                {
                    roomCode: this.state.roomCode,
                    userLogin: this.state.activePlayerLogin,
                    betType: "SMALL_BLIND",
                    moneyValue: bet
                },
            author: this.state.activePlayerLogin,
            datetime: currentDate,
            receiver: "receiver"
        }
        this.setState({mustMakeBet: false, isModal: false});
        sendMessage(message);
        this.setState({lastMessage: "PLAYER_MAKE_BET"});
        this.getBigBlind();
    }

    getBigBlind() {
        if (this.state.lastMessage === "WHO_IS_BIG_BLIND") {
            return;
        }
        let currentDate = getCurrentDate();
        let message = {
            messageType: "WHO_IS_BIG_BLIND",
            content:
                {   roomCode: this.state.roomCode,
                    userLogin: null,
                    userName: null,
                    moneyValue: 0,
                    suit: null,
                    rank: null,
                    roomPlayersList: null
                },
            author: this.state.activePlayerLogin,
            dateTime: currentDate,
            receiver: "receiver"
        }
        sendMessage(message);
        this.setState({lastMessage: "WHO_IS_BIG_BLIND"});
    }

    setBigBlind(login) {
        if (login === this.state.activePlayerLogin) {
            this.state.activePlayer.bigBlind = true;
            this.setState({mustMakeBet: true, modalData: 2, modalText: "Поставьте большой блайнд"});
        } else {
            for (let i = 0; i < this.state.players.length; i++) {
                if (this.state.players[i].login === login) {
                    this.state.players[i].bigBlind = true;
                    this.setState({change: true});
                }
            }
        }
    }

    submitBigBlind(bet) {
        if (this.state.lastMessage === "PLAYER_MAKE_BET") {
            return;
        }
        let currentDate = getCurrentDate();
        let message = {
            messageType: "PLAYER_MAKE_BET",
            content:
                {
                    roomCode: this.state.roomCode,
                    userLogin: this.state.activePlayerLogin,
                    betType: "BIG_BLIND",
                    moneyValue: bet
                },
            author: this.state.activePlayerLogin,
            datetime: currentDate,
            receiver: "receiver"
        }
        this.setState({mustMakeBet: false, isModal: false});
        sendMessage(message);
        this.setState({lastMessage: "PLAYER_MAKE_BET"});
        this.isNextStepOfRound();
    }

    isNextStepOfRound() {
        if (this.state.lastMessage === "IS_NEXT_STEP_OF_ROUND") {
            return;
        }
        let currentDate = getCurrentDate();
        let message = {
            messageType: "IS_NEXT_STEP_OF_ROUND",
            content:
                {
                    roomCode: this.state.roomCode
                },
            author: this.state.activePlayerLogin,
            datetime: currentDate,
            receiver: "receiver"
        }
        sendMessage(message);
        this.setState({lastMessage: "IS_NEXT_STEP_OF_ROUND"});
    }

    nextStepOfRound(gameState, moneyValue) {
        switch(gameState) {
            case "PREFLOP":
                this.setState({gameState: "PREFLOP", bank: moneyValue});
                this.preflop();
                break;
            case "FLOP":
                this.setState({gameState: "FLOP", bank: moneyValue});
                this.flop();
                break;
            case "TERN":
                this.setState({gameState: "TERN", bank: moneyValue});
                this.tern();
                break;
            case "RIVER":
                this.setState({gameState: "RIVER", bank: moneyValue});
                this.river();
                break;
            case "SHOWDOWN":
                this.setState({gameState: "SHOWDOWN", bank: moneyValue});
                this.showdown();
                break;
        }
    }

    preflop() {
        let currentDate = getCurrentDate();
        let message = {
            messageType: "DRAW_CARD",
            content:
                {
                    roomCode: this.state.roomCode,
                    userLogin: this.state.activePlayerLogin
                },
            author: this.state.activePlayerLogin,
            datetime: currentDate,
            receiver: "receiver"
        }
        sendMessage(message);
    }

    flop() {
        this.state.maxBet = 0;
        this.state.activePlayer.currentStake = 0;
        this.state.activePlayer.newStake = 0;
        for (let i = 0; i < this.state.players.length; i++) {
            this.state.players[i].currentStake = 0;
            this.state.players[i].newStake = 0;
        }
        let currentDate = getCurrentDate();
        let message = {
            messageType: "DRAW_CARD",
            content:
                {
                    roomCode: this.state.roomCode,
                    userLogin: null
                },
            author: this.state.activePlayerLogin,
            datetime: currentDate,
            receiver: "receiver"
        }
        sendMessage(message);
    }

    tern() {
        this.state.maxBet = 0;
        this.state.activePlayer.currentStake = 0;
        this.state.activePlayer.newStake = 0;
        for (let i = 0; i < this.state.players.length; i++) {
            this.state.players[i].currentStake = 0;
            this.state.players[i].newStake = 0;
        }
        let currentDate = getCurrentDate();
        let message = {
            messageType: "DRAW_CARD",
            content:
                {
                    roomCode: this.state.roomCode,
                    userLogin: null
                },
            author: this.state.activePlayerLogin,
            datetime: currentDate,
            receiver: "receiver"
        }
        sendMessage(message);
    }

    river() {
        this.state.maxBet = 0;
        this.state.activePlayer.currentStake = 0;
        this.state.activePlayer.newStake = 0;
        for (let i = 0; i < this.state.players.length; i++) {
            this.state.players[i].currentStake = 0;
            this.state.players[i].newStake = 0;
        }
        let currentDate = getCurrentDate();
        let message = {
            messageType: "DRAW_CARD",
            content:
                {
                    roomCode: this.state.roomCode,
                    userLogin: null
                },
            author: this.state.activePlayerLogin,
            datetime: currentDate,
            receiver: "receiver"
        }
        sendMessage(message);
    }

    showdown() {
        this.state.maxBet = 0;
        this.state.activePlayer.currentStake = 0;
        this.state.activePlayer.newStake = 0;
        for (let i = 0; i < this.state.players.length; i++) {
            this.state.players[i].currentStake = 0;
            this.state.players[i].newStake = 0;
        }
        let currentDate = getCurrentDate();
        let message = {
            messageType: "WINNER_PLAYER",
            content:
                {
                    roomCode: this.state.roomCode,
                },
            author: this.state.activePlayerLogin,
            datetime: currentDate,
            receiver: "receiver"
        }
        sendMessage(message);
    }

    drawCard(userLogin, cardsList, receiver) {
        if (userLogin) {
            let card1 = cardsList[0];
            let card2 = cardsList[1];
            if (userLogin === this.state.activePlayerLogin) {
                this.state.activePlayer.card1 = { suit: card1.suit, rank: card1.rank};
                this.state.activePlayer.card2 = { suit: card2.suit, rank: card2.rank};
                this.setState({change: true});
                return;
            }
            for (let i = 0; i < this.state.players.length; i++) {
                if (this.state.players[i]) {
                    if (this.state.players[i].login === userLogin) {
                        this.state.players[i].card1 = { suit: card1.suit, rank: card1.rank};
                        this.state.players[i].card2 = { suit: card2.suit, rank: card2.rank};
                        this.setState({change: true});
                        if (i === this.state.players.length - 1) {
                            this.playerMustMakeBet();
                        }
                        return;
                    }
                }
            }
        } else {
            if (receiver !== this.state.activePlayerLogin) {
                return;
            }
            let card1 = cardsList[0];
            let card2 = cardsList[1];
            let card3 = cardsList[2];
            this.state.card1 = { suit: card1.suit, rank: card1.rank};
            this.state.card2 = { suit: card2.suit, rank: card2.rank};
            this.state.card3 = { suit: card3.suit, rank: card3.rank};
            if (cardsList[3]) {
                this.state.card4 = { suit: cardsList[3].suit, rank: cardsList[3].rank};
            }
            if (cardsList[4]) {
                this.state.card5 = { suit: cardsList[4].suit, rank: cardsList[4].rank};
            }
            this.setState({change: true});
            this.playerMustMakeBet();
        }
    }

    openModal() {
        this.setState({isModal: true});
    }

    closeModal() {
        this.setState({isModal: false});
    }

    openWinnerModal(login, money) {
        this.setState({winner: login, winMoney: money});
        this.setState({winnerModal: true});
    }

    closeWinnerModal() {
        this.setState({winnerModal: false});
    }

    showPlayersBet(userLogin, moneyValue, betType) {
        this.state.activePlayer.check = false;
        for (let i = 0; i < 5; i++) {
            if (this.state.players[i]) {
                this.state.players[i].check = false;
            }
        }
        this.setState({change: true});
        if (userLogin === this.state.activePlayerLogin) {
            this.state.activePlayer.newStake = moneyValue;
            this.state.activePlayer.currentStake += moneyValue;
            this.state.activePlayer.cash -= moneyValue;
            if (this.state.maxBet < this.state.activePlayer.currentStake) {
                this.setState({maxBet: this.state.activePlayer.currentStake});
            }
            this.setState({change: true});
            if (betType === "BET") {
                this.setState({change: true});
                this.playerMustMakeBet();
            }
            return;
        }
        for (let i = 0; i < 5; i++) {
            if (this.state.players[i].login === userLogin) {
                this.state.players[i].newStake = moneyValue;
                this.state.players[i].currentStake += moneyValue;
                this.state.players[i].cash -= moneyValue;
                if (this.state.maxBet < this.state.players[i].currentStake) {
                    this.setState({maxBet: this.state.players[i].currentStake});
                }
                this.setState({change: true});
                return;
            }
        }
    }

    playerMustMakeBet() {
        let currentDate = getCurrentDate();
        let message = {
            messageType: "PLAYER_MUST_MAKE_BET",
            content:
                {
                    roomCode: this.state.roomCode
                },
            author: this.state.activePlayerLogin,
            datetime: currentDate,
            receiver: "receiver"
        }
        sendMessage(message);
    }

    exitRoom() {
        let currentDate = getCurrentDate();
        let message = {
            messageType:"PLAYER_ROOM_EXIT",
            content:
                {
                    roomCode: this.state.roomCode,
                    userLogin: this.state.activePlayerLogin
                },
            author: this.state.activePlayerLogin,
            datetime: currentDate,
            receiver: "receiver"
        };
        sendMessage(message);
        this.setState({exit: true});
    }

    render() {
        let activePlayer = this.state.activePlayer;
        let player1 = this.state.players[0];
        let player2 = this.state.players[1];
        let player3 = this.state.players[2];
        let player4 = this.state.players[3];
        let player5 = this.state.players[4];
        let player1card1;
        let player1card2;
        let player2card1;
        let player2card2;
        let player3card1;
        let player3card2;
        let player4card1;
        let player4card2;
        let player5card1;
        let player5card2;
        if (this.state.exit) {
            const login = this.state.activePlayerLogin;
            return <Navigate to="/lobby" state={login} />
        }
        if (this.state.gameState === "SHOWDOWN") {
            if (player1) {
                player1card1 = <img className="game__user-card" src={require("../img/cards/" + player1.card1.suit + "_" + player1.card1.rank + ".png")} />;
                player1card2 = <img className="game__user-card" src={require("../img/cards/" + player1.card2.suit + "_" + player1.card2.rank + ".png")} />;
            }
            if (player2) {
                player2card1 = <img className="game__user-card" src={require("../img/cards/" + player2.card1.suit + "_" + player2.card1.rank + ".png")} />;
                player2card2 = <img className="game__user-card" src={require("../img/cards/" + player2.card2.suit + "_" + player2.card2.rank + ".png")} />;
            }
            if (player3) {
                player3card1 = <img className="game__user-card" src={require("../img/cards/" + player3.card1.suit + "_" + player3.card1.rank + ".png")} />;
                player3card2 = <img className="game__user-card" src={require("../img/cards/" + player3.card2.suit + "_" + player3.card2.rank + ".png")} />;
            }
            if (player4) {
                player4card1 = <img className="game__user-card" src={require("../img/cards/" + player4.card1.suit + "_" + player4.card4.rank + ".png")} />;
                player4card2 = <img className="game__user-card" src={require("../img/cards/" + player4.card2.suit + "_" + player4.card4.rank + ".png")} />;
            }
            if (player5) {
                player5card1 = <img className="game__user-card" src={require("../img/cards/" + player5.card1.suit + "_" + player5.card1.rank + ".png")} />;
                player5card2 = <img className="game__user-card" src={require("../img/cards/" + player5.card2.suit + "_" + player5.card2.rank + ".png")} />;
            }
        } else if (this.state.gameState === "PREFLOP" || this.state.gameState === "FLOP" || this.state.gameState === "TERN" || this.state.gameState === "RIVER") {
            if (player1 && player1.fold) {
                player1card1 = <img className="game__user-card" src={require("../img/cards/" + player1.card1.suit + "_" + player1.card1.rank + ".png")} />;
                player1card2 = <img className="game__user-card" src={require("../img/cards/" + player1.card2.suit + "_" + player1.card2.rank + ".png")} />;
            } else {
                player1card1 = <img className="game__user-card" src={require("../img/back.png")}/>;
                player1card2 = <img className="game__user-card" src={require("../img/back.png")}/>;
            }
            if (player2 && player2.fold) {
                player2card1 = <img className="game__user-card" src={require("../img/cards/" + player2.card1.suit + "_" + player2.card1.rank + ".png")} />;
                player2card2 = <img className="game__user-card" src={require("../img/cards/" + player2.card2.suit + "_" + player2.card2.rank + ".png")} />;
            } else {
                player2card1 = <img className="game__user-card" src={require("../img/back.png")}/>;
                player2card2 = <img className="game__user-card" src={require("../img/back.png")}/>;
            }
            if (player3 && player3.fold) {
                player3card1 = <img className="game__user-card" src={require("../img/cards/" + player3.card1.suit + "_" + player3.card1.rank + ".png")} />;
                player3card2 = <img className="game__user-card" src={require("../img/cards/" + player3.card2.suit + "_" + player3.card2.rank + ".png")} />;
            } else {
                player3card1 = <img className="game__user-card" src={require("../img/back.png")}/>;
                player3card2 = <img className="game__user-card" src={require("../img/back.png")}/>;
            }
            if (player4 && player4.fold) {
                player4card1 = <img className="game__user-card" src={require("../img/cards/" + player4.card1.suit + "_" + player4.card4.rank + ".png")} />;
                player4card2 = <img className="game__user-card" src={require("../img/cards/" + player4.card2.suit + "_" + player4.card4.rank + ".png")} />;
            } else {
                player4card1 = <img className="game__user-card" src={require("../img/back.png")}/>;
                player4card2 = <img className="game__user-card" src={require("../img/back.png")}/>;
            }
            if (player5 && player5.fold) {
                player5card1 = <img className="game__user-card" src={require("../img/cards/" + player5.card1.suit + "_" + player5.card1.rank + ".png")} />;
                player5card2 = <img className="game__user-card" src={require("../img/cards/" + player5.card2.suit + "_" + player5.card2.rank + ".png")} />;
            } else {
                player5card1 = <img className="game__user-card" src={require("../img/back.png")}/>;
                player5card2 = <img className="game__user-card" src={require("../img/back.png")}/>;
            }
        } else {
            player1card1 = <div className="game__user-card"></div>;
            player1card2 = <div className="game__user-card"></div>;
            player2card1 = <div className="game__user-card"></div>;
            player2card2 = <div className="game__user-card"></div>;
            player3card1 = <div className="game__user-card"></div>;
            player3card2 = <div className="game__user-card"></div>;
            player4card1 = <div className="game__user-card"></div>;
            player4card2 = <div className="game__user-card"></div>;
            player5card1 = <div className="game__user-card"></div>;
            player5card2 = <div className="game__user-card"></div>;
        }
        return (
            <div className="game-container">
                <div className="game">
                    <Modal
                        visible={this.state.isModal}
                        btn={<button className="game__modal-button" onClick={this.confirmBet}>Ок</button>}
                        cancel={<button className="cancel-button" onClick={this.closeModal}>Отмена</button>}
                        text={this.state.modalText}
                    />
                    <WinnerModal
                        visible={this.state.winnerModal}
                        btn={<button className="game__modal-button" onClick={this.closeWinnerModal}>Ок</button>}
                        login={this.state.winner}
                        money={this.state.winMoney}
                    />
                    <div className="game__header">
                        <button className="game__home-button" onClick={this.exitRoom}></button>
                        <button className="game__menu-button"></button>
                    </div>
                    <div className="game__main">
                        <div className="game__table">
                            <div className="game__active-user">
                                <div className="game__user-stake">
                                    <div className="game__user-current-stake">
                                        $ {activePlayer.currentStake}
                                    </div>
                                    { !activePlayer.fold && activePlayer.check ?
                                        <div className="game__user-check">
                                            CHECK
                                        </div>
                                        : null
                                    }
                                    {activePlayer.fold ?
                                        <div className="game__user-check">
                                            FOLD
                                        </div>
                                        : null
                                    }
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
                                    {activePlayer.card1 ?
                                        <img className="game__user-card" src={require("../img/cards/" + activePlayer.card1.suit + "_" + activePlayer.card1.rank + ".png")} />
                                        : <div className="game__user-card"></div>
                                    }
                                    {activePlayer.card2 ?
                                        <img className="game__user-card" src={require("../img/cards/" + activePlayer.card2.suit + "_" + activePlayer.card2.rank + ".png")} />
                                        : <div className="game__user-card"></div>
                                    }
                                </div>
                            </div>

                            {player1 ? <div className="game__user1">
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
                                    {!player1.fold && player1.check ?
                                        <div className="game__user-check">
                                            CHECK
                                        </div>
                                        : null
                                    }
                                    {player1.fold ?
                                        <div className="game__user-check">
                                            FOLD
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
                                    {player1card1}
                                    {player1card2}
                                </div>
                            </div> : null}

                            {player3 ? <div className="game__user3">
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
                                    {!player3.fold && player3.check ?
                                        <div className="game__user-check">
                                            CHECK
                                        </div>
                                        : null
                                    }
                                    {player3.fold ?
                                        <div className="game__user-check">
                                            FOLD
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
                                    {player3card1}
                                    {player3card2}
                                </div>
                            </div> : null}

                            {player2 ? <div className="game__user2">
                                <div className="game__user2-cards">
                                    {player2card1}
                                    {player2card2}
                                </div>
                                <div className="game__user-stake-right">
                                    <div className="game__user-current-stake">
                                        $ {player2.currentStake}
                                    </div>
                                    {player2.newStake !== 0 ?
                                        <div className="game__user-new-stake">
                                            + {player2.newStake}
                                        </div>
                                        : null
                                    }
                                    {!player2.fold && player2.check ?
                                        <div className="game__user-check">
                                            CHECK
                                        </div>
                                        : null
                                    }
                                    {player2.fold ?
                                        <div className="game__user-check">
                                            FOLD
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

                            { player4 ? <div className="game__user4">
                                <div className="game__user-stake-right">
                                    <div className="game__user-current-stake">
                                        $ {player4.currentStake}
                                    </div>
                                    {player4.newStake !== 0 ?
                                        <div className="game__user-new-stake">
                                            + {player4.newStake}
                                        </div>
                                        : null
                                    }
                                    {!player4.fold && player4.check ?
                                        <div className="game__user-check">
                                            CHECK
                                        </div>
                                        : null
                                    }
                                    {player4.fold ?
                                        <div className="game__user-check">
                                            FOLD
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
                                    {player4card1}
                                    {player4card2}
                                </div>
                            </div> : null}

                            { player5 ? <div className="game__user6">
                                <div className="game__user6-cards">
                                    {player5card1}
                                    {player5card2}
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
                                    {!player5.fold && player5.check ?
                                        <div className="game__user-check">
                                            CHECK
                                        </div>
                                        : null
                                    }
                                    {player5.fold ?
                                        <div className="game__user-check">
                                            FOLD
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
                                {this.state.card1 ?
                                    <img className="game__card1" src={require("../img/cards/" + this.state.card1.suit + "_" + this.state.card1.rank + ".png")} />
                                    : <div className="game__card1"></div>
                                }
                                {this.state.card2 ?
                                    <img className="game__card2" src={require("../img/cards/" + this.state.card2.suit + "_" + this.state.card2.rank + ".png")} />
                                    : <div className="game__card2"></div>
                                }
                                {this.state.card3 ?
                                    <img className="game__card3" src={require("../img/cards/" + this.state.card3.suit + "_" + this.state.card3.rank + ".png")} />
                                    : <div className="game__card3"></div>
                                }
                                {this.state.card4 ?
                                    <img className="game__card4" src={require("../img/cards/" + this.state.card4.suit + "_" + this.state.card4.rank + ".png")} />
                                    : <div className="game__card4"></div>
                                }
                                {this.state.card5 ?
                                    <img className="game__card5" src={require("../img/cards/" + this.state.card5.suit + "_" + this.state.card5.rank + ".png")} />
                                    : <div className="game__card5"></div>
                                }
                            </div>
                        </div>
                    </div>
                    <div className="game__actions">
                        {!this.state.activePlayer.fold && this.state.mustMakeBet &&
                        (this.state.gameState === "PREFLOP" || this.state.gameState === "FLOP" ||
                            this.state.gameState === "TERN" || this.state.gameState === "RIVER") ?
                            <button className="game__fold-active" onClick={this.makeFold}>FOLD</button>
                            : <button className="game__fold">FOLD</button>
                        }
                        {!this.state.activePlayer.fold && this.state.mustMakeBet &&
                        this.state.gameState !== "SHOWDOWN" ?
                            <button className="game__bet-active" onClick={this.openModal}>BET</button>
                            : <button className="game__bet">BET</button>
                        }
                        {!this.state.activePlayer.fold && this.state.mustMakeBet
                        && this.state.maxBet === 0 && (this.state.gameState === "FLOP" ||
                            this.state.gameState === "TERN" || this.state.gameState === "RIVER") ?
                            <button className="game__check-active" onClick={this.makeCheck}>CHECK</button>
                        : <button className="game__check">CHECK</button>}
                        <div className="game__right-nav">
                            { this.state.gameState === "" ?
                                <button id="readyBtn" className="game__ready" onClick={this.getReady}>ГОТОВ</button>
                                : null
                            }
                            <div className="game__bank">
                                <div className="game__bank-chip"></div>
                                <span className="game__bank-value">BANK: $ {this.state.bank}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default Game;