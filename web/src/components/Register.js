import React from 'react';
import '../style/style.css';
import UserService from '../services/UserService';
import { Navigate } from "react-router-dom";

class Register extends React.Component {
    constructor(props) {
        super(props);
        this.saveUser = this.saveUser.bind(this);
        this.state = {
            message: "",
            saved: false
        }
    }

    saveUser() {
        let name = document.getElementById("name").value;
        let surname = document.getElementById("surname").value;
        let login = document.getElementById("login").value;
        let password = document.getElementById("password").value;
        let password2 = document.getElementById("password2").value;
        if (password !== password2) {
            this.setState({message: "Пароли не совпадают"});
            return;
        } else {
            this.setState({message: " "});
            UserService.saveUser(name, surname, login, password).then((response) => {
                this.setState({saved: true});
            }).catch(err => {
                this.setState({message: "Что-то пошло не так"});
                console.log(err)
            });
        }
    }

    render() {
        if (this.state.saved) {
            return <Navigate to="/" />
        }
        return (
            <div className="register__main">
                <div className="register__left">
                    <h2>Регистрация</h2>
                    <div className="register__name">
                        <input type="text" id="name" placeholder="Имя"/>
                        <input type="text" id="surname" placeholder="Фамилия"/>
                    </div>
                    <input type="text" id="login" placeholder="Логин"/>
                    <input type="password" id="password" placeholder="Пароль"/>
                    <input type="password" id="password2" placeholder="Пароль еще раз"/>
                    <button className="register__button" onClick={this.saveUser}>Зарегистрироваться</button>
                    <p>{this.state.message}</p>
                </div>
                <div className="register__right">
                </div>
            </div>
        )
    }
}

export default Register;