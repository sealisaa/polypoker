import React from 'react';
import '../style/style.css';

class Register extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <div className="register__main">
                <div className="register__left">
                    <h2>Регистрация</h2>
                    <div className="register__name">
                        <input type="text" placeholder="Имя"/>
                        <input type="text" placeholder="Фамилия"/>
                    </div>
                    <input type="text" placeholder="Логин"/>
                    <input type="password" placeholder="Пароль"/>
                    <input type="password" placeholder="Пароль еще раз"/>
                    <button className="register__button">Зарегистрироваться</button>
                </div>
                <div className="register__right">
                </div>
            </div>
        )
    }
}

export default Register;