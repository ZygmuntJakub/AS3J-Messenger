import React from 'react';
import {useAuth} from "../../context/context";
import {Avatar, Box, Button, Nav, Sidebar, Text} from "grommet";
import {Chat, Group, Logout, UserSettings} from 'grommet-icons';
import ChatList from "../messages/ChatList";
import {Route, Switch, useHistory} from "react-router-dom";
import EditUser from "../user/EditUser";
import Users from "../user/Users";

function MainPage() {
    const {setAuthToken} = useAuth();

    const logout = () => {
        setAuthToken();
    }

    const history = useHistory();

    return (
        <Box direction={"row"} animation={"fadeIn"}>
            <Sidebar width={"small"} background="brand" height="100vh"
                     header={
                         <Box direction={"row"}>
                             <Avatar src="//s.gravatar.com/avatar/b7fb138d53ba0f573212ccce38a7c43b?s=80"/>
                             <Text margin={"small"}>Hello</Text>
                         </Box>
                     }
                     footer={
                         <Button icon={<Logout/>} label={"logout"} onClick={logout} hoverIndicator/>
                     }
            >
                <Nav gap="small">
                    <Button icon={<Chat/>} label={"messages"} onClick={() => history.push("/messages")} />
                    <Button icon={<Group/>} label={"users"} onClick={() => history.push("/users")}/>
                    <Button icon={<UserSettings/>} label={"account"} onClick={() => history.push("/account")}/>
                </Nav>
            </Sidebar>


            <Switch>
                <Route exact path="/messages" component={ChatList}/>
                <Route exact path="/users" component={Users}/>
                <Route exact path="/account" component={EditUser}/>
            </Switch>
        </Box>
    );
}

export default MainPage;