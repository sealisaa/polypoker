import React from 'react';
import '../style/style.css';
import {Navigate, useLocation} from 'react-router-dom';
import ws from "../websocket/websocketConfig";

const FindGame = props => {
    const location = useLocation();
    const login = location.state;
    console.log(login);
    return <FindGameContent login={login} {...props} />
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
        try {
            ws.onopen = () => {
                console.log('find-game connected');
            }
        } catch (e) {
            console.log(e);
        }
    }

    findGame() {
        let game = document.getElementById("game").value;
        // тут запрос
        // в случае ошибки:
        // this.setState({message: "Идентификатор не найден :("});
        this.setState({game: game, success: true});
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