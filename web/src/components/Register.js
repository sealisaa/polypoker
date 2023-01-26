import React from 'react';
import '../style/style.css';
import UserService from '../services/UserService';
import { Navigate } from "react-router-dom";

const Modal = ({visible = false, title = '', content = '', footer = '', onClose}) => {
    const onKeydown = ({ key }: KeyboardEvent) => {
        switch (key) {
            case 'Escape':
                onClose()
                break
        }
    }
    React.useEffect(() => {
        document.addEventListener('keydown', onKeydown)
        return () => document.removeEventListener('keydown', onKeydown)
    })
    if (!visible) {
        return null
    }
    return (
        <div className='modal' onClick={onClose}>
            <div className='modal-dialog' onClick={e => e.stopPropagation()}>
                <div className='modal-header'>
                    <h3 className='modal-title'>{title}</h3>
                </div>
                {footer && <div className='modal-footer'>{footer}</div>}
            </div>
        </div>
    )
}

class Register extends React.Component {
    constructor(props) {
        super(props);
        this.saveUser = this.saveUser.bind(this);
        this.onClose = this.onClose.bind(this);
        this.state = {
            message: "",
            saved: false,
            isModal: false
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
        } else {
            UserService.saveUser(name, surname, login, password).then((response) => {
                this.setState({message: " ", isModal: true});
            }).catch(err => {
                this.setState({message: "Что-то пошло не так"});
                console.log(err)
            });

        }
    }

    onClose() {
        this.setState({isModal: false, saved: true});
    }

    render() {
        if (this.state.saved) {
            return <Navigate to="/" />
        }
        return (
            <div className="register">
                <div className="register__main">
                    <Modal
                        visible={this.state.isModal}
                        title='Пользователь успешно зарегистрирован'
                        footer={<button className="button" onClick={this.onClose}>Ок</button>}
                        onClose={this.onClose}
                    />
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
            </div>
        )
    }
}

export default Register;