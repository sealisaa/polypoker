import { Routes, Route } from 'react-router-dom';
import Login from './components/Login';
import Register from './components/Register';
import Lobby from "./components/Lobby";
import Game from "./components/Game";

function App() {
    return (
        <div className="App">
            <Routes>
                <Route exact path="/" element={<Login />} />
                <Route exact path="/register" element={<Register />} />
                <Route exact path="/lobby" element={<Lobby />} />
                <Route exact path="/game" element={<Game />} />
            </Routes>
        </div>
    );
}

export default App;
