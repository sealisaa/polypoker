import axios from 'axios'
import ip from '../ipConfig';

const URL = "http://" + ip + ":8080/";
const SAVE_USER_URL = URL + 'user/save';
const USER_URL = URL + 'user/';
const AUTH_USER_URL = URL + 'user/auth';

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