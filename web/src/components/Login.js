import React from 'react';
import '../style/style.css';
import { Link } from 'react-router-dom';

class Login extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <div className="login">
                <div className="login__header">
                    <h2>Вход</h2>
                </div>
                <div className="chip"></div>
                <div className="card1"></div>
                <div className="card2"></div>
                <div className="login__main">
                    <div className="login__data">
                        <input type="text" placeholder="Логин"/>
                        <input type="password" placeholder="Пароль"/>
                    </div>
                    <div className="login__settings">
                        <input type="checkbox"/>
                        <span>Запомнить меня</span>
                        <a>Забыли пароль?</a>
                    </div>
                    <button className="login__button">
                        <Link to="/lobby" className="button-text">
                            Войти
                        </Link>
                    </button>
                    <div className="login__create-account">
                        <span>Нет аккаунта?</span>
                            <Link to="/register">Зарегистрироваться</Link>
                    </div>
                </div>
            </div>
        )
    }
}

export default Login;