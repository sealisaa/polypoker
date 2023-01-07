import React from 'react';
import '../style/style.css';
import {Link, useLocation} from "react-router-dom";
import UserService from "../services/UserService";

const Lobby = props => {
    const location = useLocation();
    const login = location.state;
    return <LobbyContent login={login} {...props} />
}

class LobbyContent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            login: props.login,
            totalGamesPlayed: 0,
            winGames: 0,
            currentCoinsCount: 0,
            totalEarn: 0
        }
    }

    componentDidMount(){
        UserService.getUserStatistic(this.state.login).then((response) => {
            console.log(response.data);
            this.setState({
                totalGamesPlayed: response.data.totalGamesPlayed,
                winGames: response.data.winGames,
                currentCoinsCount: response.data.currentCoinsCount,
                totalEarn: response.data.totalEarn
            });
        });
    }

    render() {
        return (
            <div className="lobby">
                <div className="lobby__header">
                    <div className="lobby__avatar" />
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
                    <Link to="/game" className="lobby__find-game">
                        <div className="lobby__find-game-pic"></div>
                        <span className="lobby__find-game-text">Найти игру</span>
                    </Link>
                </div>
            </div>
        )
    }
}

export default Lobby;