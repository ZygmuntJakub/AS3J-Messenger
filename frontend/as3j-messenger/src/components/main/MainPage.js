import React from 'react';
import {useAuth} from "../../context/context";
import {Avatar, Box, Button, Nav, Sidebar, Text} from "grommet";
import {Add, Chat, Group, Logout, UserSettings} from 'grommet-icons';
import ChatList from "../messages/ChatList";
import {Route, Switch, useHistory} from "react-router-dom";
import EditUser from "../user/EditUser";
import Users from "../user/Users";
import NewChat from "../messages/NewChat";
import userInfo from "../../utils/userInfo";

function MainPage() {
    const {setAuthToken} = useAuth();

    const logout = () => {
        setAuthToken();
    }

    const history = useHistory();

    return (
        <Box direction={"row"} animation={"fadeIn"}>
            <Sidebar width={"300px"} background="brand" height="100vh"
                     header={
                         <Box direction={"row"}>
                             {userInfo().avatarPresent ? (
                                 <Avatar src={`https://storage.cloud.google.com/as3j-messenger/${userInfo().uuid}.png`}/>
                             ) : (
                                 <Avatar src="https://innostudio.de/fileuploader/images/default-avatar.png"/>
                             )}
                             <Text margin={"small"}>{userInfo().username}</Text>
                         </Box>
                     }
                     footer={
                         <Button icon={<Logout/>} label={"logout"} onClick={logout} hoverIndicator/>
                     }
            >
                <Nav gap="small">
                    <Button icon={<Chat/>} label={"messages"} onClick={() => history.push("/messages")} />
                    <Button icon={<Group/>} label={"users"} onClick={() => history.push("/users")}/>
                    <Button icon={<Add/>} label={"new chat"} onClick={() => history.push("/new-chat")}/>
                    <Button icon={<UserSettings/>} label={"account"} onClick={() => history.push("/account")}/>
                </Nav>
            </Sidebar>


            <Switch>
                <Route exact path="/messages" component={ChatList}/>
                <Route exact path="/users" component={Users}/>
                <Route exact path="/account" component={EditUser}/>
                <Route exact path="/new-chat" component={NewChat}/>
            </Switch>
        </Box>
    );
}

export default MainPage;