import './App.css';
import {useState} from "react";

function App() {

    const [name, setName] = useState("");
    const [password, setPassword] = useState("");
    let [resourceResult, setResourceResult] = useState("");

    const loginWithPassword = async function () {
        const res = await fetch('/resource', {
            method: 'GET',
            headers: {'Authorization': 'Basic ' + btoa(name + ":" + password)}
        });
        if (res.ok) {
            const json = await res.json()
            setResourceResult(json[0])
        } else {
            setResourceResult("Nope")
        }
    };

    const login = async function () {
        if (!window.ethereum) {
            console.error('Please install MetaMask');
            return;
        }

        // Prompt user to connect MetaMask
        const accounts = await window.ethereum.request({method: 'eth_requestAccounts'});
        const address = accounts[0];

        // Receive nonce and sign a message
        const nonce = await getNonce(address);
        const message = `Signing a message to login: ` + nonce;
        const signature = await window.ethereum.request({method: 'personal_sign', params: [message, address]});

        // Login with signature
        await sendLoginData(address, signature);
    };

    async function getNonce(address) {
        return await fetch(`/user/nonce/${address}`)
            .then(response => response.text());
    }

    async function sendLoginData(address, signature) {
        const res = await fetch('/resource', {
            method: 'GET',
            headers: {
                //'address': encodeURIComponent(address),
                'signature': encodeURIComponent(signature),
                'name': 'roie'
            }
        })
        if (res.ok) {
            const json = await res.json()
            setResourceResult(json[0])
        } else {
            setResourceResult("Nope")
        }
    }

    return (
        <div className="App">
            <header className="App-header">
                {/*<img src={logo} className="App-logo" alt="logo" />*/}
                <p>
                    Edit <code>src/App.js</code> and save to reload.
                </p>
                <a
                    className="App-link"
                    href="https://reactjs.org"
                    target="_blank"
                    rel="noopener noreferrer"
                >
                    Learn React
                </a>
                <button className="btn btn-lg btn-primary btn-block" type="submit" onClick={() => login()}>Get resource
                    with Metamask
                </button>
                <br/>
                <br/>
                <br/>
                <label>
                    name: <input value={name} onChange={e => setName(e.target.value)}/>
                </label>
                <label>
                    password: <input value={password} onChange={e => setPassword(e.target.value)}/>
                </label>

                <button className="btn btn-lg btn-primary btn-block" type="submit"
                        onClick={() => loginWithPassword()}>Get resource with user + pass
                </button>
                <h1>{resourceResult}</h1>

            </header>
        </div>
    );
}

export default App;
