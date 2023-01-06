import axios from 'axios'

const GET_ALL_USERS_URL = 'http://localhost:8080/user/get-all';
const SAVE_USER_URL = 'http://localhost:8080/user/save';

class User {
    constructor(name, surname, login, password) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
    }
}

class UserService {

    getUsers() {
        return axios.get(GET_ALL_USERS_URL);
    }

    saveUser(name, surname, login, password) {
        try {
            return axios.post(
                SAVE_USER_URL,
                {name: name, surname: surname, login: login, password: password})
        } catch (error) {
            console.error(error)
        }
    }
}

export default new UserService();