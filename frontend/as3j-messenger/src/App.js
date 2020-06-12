import React, {useState} from 'react';
import {BrowserRouter as Router, Redirect, Route, Switch} from "react-router-dom";
import LoginPage from "./components/auth/LoginPage";
import MessagesPage from "./components/messages/MessagesPage";
import {AuthContext} from "./context/context";
import {Grommet} from 'grommet';
import {theme} from "./utils/styles";

function App() {
    const existingToken = JSON.parse(localStorage.getItem("token"));
    const [authToken, setAuthToken] = useState(existingToken);

    const setToken = (data) => {
        data ? localStorage.setItem("token", JSON.stringify(data)) :
            localStorage.clear();
        setAuthToken(data);
    }

    return (
        <AuthContext.Provider value={{authToken, setAuthToken: setToken}}>
            <Grommet plain theme={theme}>
                <Router>
                    <Switch>
                        <Route exact path="/login" component={LoginPage}/>
                        <Route path="/" render={() => authToken ? (
                            <MessagesPage/>
                        ) : (
                            <Redirect to="/login"/>
                        )}/>
                    </Switch>

                </Router>
            </Grommet>
        </AuthContext.Provider>
    );
}

export default App;
