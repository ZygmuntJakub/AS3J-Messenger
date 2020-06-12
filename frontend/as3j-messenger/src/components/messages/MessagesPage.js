import React from 'react';
import {useAuth} from "../../context/context";
import {Avatar, Box, Button, Nav, Sidebar} from "grommet";
import {Logout, Chat, UserSettings} from 'grommet-icons';

function MessagesPage() {
    const { setAuthToken } = useAuth();

    const logout = () => {
        setAuthToken();
    }

    return (
        <Box direction={"row"}>
            <Sidebar background="brand" height="100vh"
                     header={
                         <Avatar src="//s.gravatar.com/avatar/b7fb138d53ba0f573212ccce38a7c43b?s=80" />
                     }
                     footer={
                         <Button icon={<Logout />} label={"logout"} onClick={logout}  hoverIndicator />
                     }
            >
                <Nav gap="small">
                    <Button icon={<Chat />} label={"messages"} active/>
                    <Button icon={<UserSettings />} label={"account"}  />
                </Nav>
            </Sidebar>
        </Box>
    );
}

export default MessagesPage;