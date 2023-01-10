import React from 'react';
import '../style/style.css';
import {Link, Navigate} from 'react-router-dom';
import UserService from '../services/UserService';

class Login extends React.Component {
    constructor(props) {
        super(props);
        this.authorizeUser = this.authorizeUser.bind(this);
        this.state = {
            message: "",
            authorized: false,
            login: ""
        }
    }

    authorizeUser() {
        let login = document.getElementById("login").value;
        let password = document.getElementById("password").value;
        UserService.authorizeUser(login, password).then((response) => {
            if (response.data) {
                this.setState({authorized: true, login: login});
            } else {
                this.setState({message: "Неверное имя пользователя или пароль"});
            }
        }).catch(err => {
            this.setState({message: "Неверное имя пользователя или пароль"});
        });
    }

    render() {
        if (this.state.authorized) {
            const login = this.state.login;
            return <Navigate to="/lobby" state={login} />
        }
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
                        <input type="text" id="login" placeholder="Логин"/>
                        <input type="password" id="password" placeholder="Пароль"/>
                    </div>
                    <p>{this.state.message}</p>
                    <div className="login__actions">
                        <button className="login__button" onClick={this.authorizeUser}>Войти</button>
                        <div className="login__create-account">
                            <span>Нет аккаунта?</span>
                            <Link to="/register">Зарегистрироваться</Link>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default Login;