import { Routes, Route } from 'react-router-dom';
import Login from './components/Login';
import Register from './components/Register';
import Lobby from "./components/Lobby";
import Game from "./components/Game";
import FindGame from "./components/FindGame";

function App() {
    return (
        <div className="App">
            <Routes>
                <Route exact path="/" element={<Login />} />
                <Route exact path="/register" element={<Register />} />
                <Route exact path="/lobby" element={<Lobby />} />
                <Route exact path="/game" element={<Game />} />
                <Route exact path="/find-game" element={<FindGame />} />
            </Routes>
        </div>
    );
}

export default App;
