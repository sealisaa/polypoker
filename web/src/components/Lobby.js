import React from 'react';
import '../style/style.css';
import {Link} from "react-router-dom";

class Lobby extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <div className="lobby">
                <div className="lobby__header">
                    <div className="lobby__avatar" />
                    <span className="lobby__username">User Name</span>
                    <div className="lobby__balance">
                        <div className="lobby__balance-chip" />
                        <span className="lobby__username">9999</span>
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
                            <span className="lobby__all-games-count">10536</span><br></br>
                            <span className="lobby__all-games-text">всего партий сыграно</span>
                        </div>
                    </div>
                    <div className="lobby__win-games">
                        <div className="lobby__win-games-pic"></div>
                        <div className="lobby__win-games-content">
                            <span className="lobby__win-games-count">2567</span><br></br>
                            <span className="lobby__win-games-text">выигрышных партий</span>
                        </div>
                    </div>
                    <div className="lobby__money-earned">
                        <div className="lobby__money-earned-pic"></div>
                        <div className="lobby__money-earned-content">
                            <span className="lobby__money-earned-count">$ 999</span><br></br>
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