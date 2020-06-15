import React, {useEffect, useState} from 'react';
import {Box, Grid} from "grommet";
import axios from "axios";
import {backendUrl} from "../../utils/constants";
import authHeader from "../../utils/authHeader";
import {useAuth} from "../../context/context";
import {useHistory} from "react-router-dom";


export const Chat = ({chat}) => {
    const [data, setData] = useState([]);
    const {setAuthToken} = useAuth();
    const history = useHistory();

    useEffect(() => {
        axios.get(`${backendUrl}/chats/${chat}`, {headers: {Authorization: authHeader()}}).then(result => {
            setData(result.data)
            setAuthToken(result.headers.authorization);
        }).catch(e => {
            history.push("/login")
        });
    }, [chat, history])

    return (
        <Grid
            pad={'10px'}
            rows={['80vh', '20%']}
            columns={['100%']}
            gap="small"
            areas={[
                { name: 'messages', start: [0, 0], end: [0, 0] },
                { name: 'send', start: [0, 1], end: [0, 1] },
            ]}
        >
            <Box round gridArea="messages" background="light-1">

            </Box>
            <Box round gridArea="send" background="light-5" />
        </Grid>
    );
};

export default Chat;