import React, {useEffect, useState} from 'react';
import {Box, Grid, InfiniteScroll, Text} from "grommet";
import axios from "axios";
import {backendUrl} from "../../utils/constants";
import authHeader from "../../utils/authHeader";
import {useAuth} from "../../context/context";
import {useHistory} from "react-router-dom";
import SockJS from "sockjs-client";
import Stomp from "stompjs";


export const Chat = ({chat}) => {
    const [data, setData] = useState([]);
    const {setAuthToken} = useAuth();
    const history = useHistory();

    useEffect(() => {
        axios.get(`${backendUrl}/chats/${chat}`, {headers: {Authorization: authHeader()}}).then(result => {
            setData(result.data)
            setAuthToken(result.headers.authorization);
        }).catch(e => {
            alert(e);
        });
    }, [chat, history])

    const socket = new SockJS(`${backendUrl}/ws`);
    const stompClient = Stomp.over(socket);
    stompClient.connect({}, frame => {

        // TODO should be current chat's UUID
        const chatUuid = "86acf316-9811-11ea-bb37-0242ac130002";

        stompClient.subscribe(`/messages/add/${chatUuid}`, message => {
            // Reaction for new message arrival
            console.log(message);
        });
    });

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
            <Box pad={"small"} overflow={"scroll"} round gridArea="messages" background="light-1">
                {data && data.map((c) => (
                    <Box>{c.content}</Box>
                ))}
            </Box>
            <Box round gridArea="send" background="light-5" />
        </Grid>
    );
};

export default Chat;