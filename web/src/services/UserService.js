import axios from 'axios'

const SAVE_USER_URL = 'http://localhost:8080/user/save';
const USER_URL = 'http://localhost:8080/user/';
const AUTH_USER_URL = 'http://localhost:8080/user/auth';

class UserService {

    saveUser(name, surname, login, password) {
        try {
            return axios.post(
                SAVE_USER_URL,
                {name: name, surname: surname, login: login, password: password})
        } catch (error) {
            console.error(error)
        }
    }

    authorizeUser(login, password) {
        try {
            return axios.post(
                AUTH_USER_URL,
                {login: login, password: password})
        } catch (error) {
            console.error(error)
        }
    }

    getUserByLogin(login) {
        return axios.get(USER_URL + login);
    }

    getUserStatistic(login) {
        return axios.get(USER_URL + login + '/get-statistic');
    }
}

export default new UserService();