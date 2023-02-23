import { useState } from "react";
import style from "./App.module.scss";

let idCounter = 0;

function App() {
    const [accounts, setAccounts] = useState([]);
    const [errorMsg, setErrorMsg] = useState("");

    //console.log(accounts);

    const handleSubmit = (event) => {
        event.preventDefault();
        let object = {};

        const formData = new FormData(event.target);

        const email = formData.get("appName");

        if (uniqueEmail(email)) {
            setErrorMsg("");
            formData.forEach((value, key) => {
                object[key] = value;
            });

            setAccounts([...accounts, { id: ++idCounter, ...object }]);
            event.target.reset();
        } else {
            setErrorMsg("Email already exists");
        }
    };

    const handleDelete = (event) => {
        const newArr = accounts.filter((acc) => acc.id !== event.id);
        setAccounts(newArr);
    };

    const uniqueEmail = (email) => {
        // Checks to see if email is unique
        // returns false if found in accounts, and true if not found

        const found = accounts.find(
            (acc) => acc["appName"].toLowerCase() == email.toLowerCase()
        );
        return found ? false : true;
    };

    return (
        <div className={style.App}>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="appName">App name: </label>
                    <input type="text" name="appName" id="appName" required />
                </div>

                <div>
                    <label htmlFor="username">Username: </label>
                    <input type="text" name="username" id="username" required />
                </div>
                <button>Submit</button>
                <span id="errorMsg" className={style.errorMsg}>{errorMsg}</span>
            </form>

            {accounts.length === 0 && <p>There are no accounts to display</p>}

            {accounts.length > 0 &&
                accounts.map((account) => (
                    <p key={account.id} id={account.id}>
                        {`${account.appName} - ${account.username} `}
                        <button
                            className={style.deleteBtn}
                            onClick={() => {
                                handleDelete(account);
                            }}
                        >
                            x
                        </button>
                    </p>
                ))}
        </div>
    );
}

export default App;
