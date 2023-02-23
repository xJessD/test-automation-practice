import { useState } from "react";
import style from './App.module.scss';

let idCounter = 0;

function App() {
    const [accounts, setAccounts] = useState([]);

    //console.log(accounts);

    const handleSubmit = (event) => {
        event.preventDefault();
        let object = {};
        const formData = new FormData(event.target);

        formData.forEach((value, key) => {
            object[key] = value;
        });

        setAccounts([...accounts, { id: ++idCounter, ...object }]);
        event.target.reset();
    };

    const handleDelete = (event) => {
      const newArr = accounts.filter((acc) => acc.id !== event.id)
      setAccounts(newArr);
    };

    return (
        <div className={style.App}>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="appName">App name: </label>
                    <input type="text" name="appName" id="appName" required/>
                </div>

                <div>
                    <label htmlFor="username">Username: </label>
                    <input type="text" name="username" id="username" required/>
                </div>
                <button>Submit</button>
            </form>

            {accounts.length === 0 && <p>There are no accounts to display</p>}

            {accounts.length > 0 &&
                accounts.map((account) => (
                    <p key={account.id} id={account.id}>
                        {`${account.appName} - ${account.username} `} 
                        <button className={style.deleteBtn} onClick={() => {handleDelete(account)}}>x</button>
                    </p>
                ))}
        </div>
    );
}

export default App;
