import React, {useState} from 'react';
import {BrowserRouter as Router, Redirect, Route, Switch} from "react-router-dom";
import LoginPage from "./components/auth/LoginPage";
import MainPage from "./components/main/MainPage";
import {AuthContext} from "./context/context";
import {Grommet} from 'grommet';
import {theme} from "./utils/styles";

function App() {
    const existingToken = JSON.parse(localStorage.getItem("token"));
    const [authToken, setAuthToken] = useState(existingToken);
    const [user, setUser] = useState(existingToken);

    const setToken = (data) => {
        data ? localStorage.setItem("token", JSON.stringify(data)) :
            localStorage.clear();
        setAuthToken(data);
    }

    const setUserState = (data) => {
        data ? localStorage.setItem("user", JSON.stringify(data)) :
            localStorage.clear();
        setUser(data);
    }

    return (
        <AuthContext.Provider value={{authToken, setAuthToken: setToken, user, setUser: setUserState}}>
            <Grommet plain theme={theme}>
                <Router>
                    <Switch>
                        <Route exact path="/login" component={LoginPage}/>
                        <Route path="/" render={() => authToken ? (
                            <MainPage/>
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
