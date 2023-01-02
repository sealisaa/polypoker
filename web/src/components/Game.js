import React from 'react';
import '../style/style.css';
import {Link} from "react-router-dom";

class Game extends React.Component {
    constructor(props) {
        super(props);
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
                        <div className="game__user5">
                            <div className="game__user-info">
                                <div className="game__active-user-avatar"></div>
                                <span className="game__user-login">You</span>
                                <span className="game__user-balance">$ 100.000</span>
                            </div>
                            <div className="game__user5-cards">
                                <div className="game__user-card game__active-user-card1"></div>
                                <div className="game__user-card game__active-user-card2"></div>
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
                            <div className="game__card1 flip-card h">
                                <div className="game__card-back back"></div>
                                <div className="game__card1-front front"></div>
                            </div>
                            <div className="game__card2 flip-card h">
                                <div className="game__card-back back"></div>
                                <div className="game__card2-front front"></div>
                            </div>
                            <div className="game__card3 flip-card h">
                                <div className="game__card-back back"></div>
                                <div className="game__card3-front front"></div>
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
                    <div className="game__bank">
                        <div className="game__bank-chip"></div>
                        <span className="game__bank-value">BANK: $ 1.000.000</span>
                    </div>
                </div>
            </div>
        )
    }
}

export default Game;