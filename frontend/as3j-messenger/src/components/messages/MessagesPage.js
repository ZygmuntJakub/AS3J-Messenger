import React from 'react';
import {useAuth} from "../../context/context";
import {Avatar, Box, Button, Text, Sidebar, Nav} from "grommet";
import {Logout, Chat, UserSettings} from 'grommet-icons';
import ChatList from "./ChatList";
import { useHistory } from "react-router-dom";

function MessagesPage() {
    const { setAuthToken } = useAuth();

    const logout = () => {
        setAuthToken();
    }

    const history = useHistory();

    return (
        <Box direction={"row"} animation={"fadeIn"}>
            <Sidebar background="brand" height="100vh"
                     header={
                         <Box direction={"row"}>
                         <Avatar src="//s.gravatar.com/avatar/b7fb138d53ba0f573212ccce38a7c43b?s=80" />
                         <Text margin={"small"}>Hello</Text>
                         </Box>
                     }
                     footer={
                         <Button icon={<Logout />} label={"logout"} onClick={logout}  hoverIndicator />
                     }
            >
                <Nav gap="small">
                    <Button icon={<Chat />} label={"messages"} onClick={() => history.push("/")} active/>
                    <Button icon={<UserSettings />} label={"account"}  onClick={() => history.push("/account")} />
                </Nav>
            </Sidebar>
            <Sidebar  height="100vh">
                <ChatList />
            </Sidebar>
        </Box>
    );
}

export default MessagesPage;