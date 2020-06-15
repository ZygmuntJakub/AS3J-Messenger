import React, {useEffect, useRef, useState} from 'react';
import {Box, Button, Grid, Text, TextArea, Main} from "grommet";
import axios from "axios";
import {backendUrl} from "../../utils/constants";
import authHeader from "../../utils/authHeader";
import {useAuth} from "../../context/context";
import {useHistory} from "react-router-dom";
import userInfo from "../../utils/userInfo";
import {Send} from "grommet-icons";
import SockJS from "sockjs-client";
import Stomp from "stompjs";


export const Chat = ({chat}) => {
    const [data, setData] = useState([]);
    const [message, setMessage] = React.useState("");
    const {setAuthToken} = useAuth();
    const history = useHistory();
    const messagesEndRef = useRef(null);

    const scrollToBottom = () => {
        messagesEndRef.current.scrollIntoView({ behavior: "smooth" });
    };

    useEffect(() => {
        axios.get(`${backendUrl}/chats/${chat}`, {headers: {Authorization: authHeader()}}).then(result => {
            setData(result.data)
            setAuthToken(result.headers.authorization);
        }).catch(e => {
            alert(e);
        });
        scrollToBottom();
    }, [chat, history])

    useEffect(scrollToBottom, [data])

    const handleSubmit = () => {
        axios.post(`${backendUrl}/chats/${chat}/messages`,
            {
                value: message
            },
            {headers: {Authorization: authHeader()}})
            .catch(e => {
                alert(e);
            });
        setMessage("");
    }

    // const socket = new SockJS(`${backendUrl}/ws`);
    // const stompClient = Stomp.over(socket);
    // stompClient.connect({}, frame => {
    //     const chatUuid = chat;
    //
    //     stompClient.subscribe(`/messages/add/${chatUuid}`, message => {
    //         setData([
    //             ...data,
    //
    //         ])
    //         console.log(message.body)
    //     });
    //
    // });

    return (
        <Grid
            pad={'10px'}
            rows={['80vh', '20%']}
            columns={['100%']}
            gap="small"
            areas={[
                {name: 'messages', start: [0, 0], end: [0, 0]},
                {name: 'send', start: [0, 1], end: [0, 1]},
            ]}
        >
            {data && (
                <Box round overflow={"auto"} gridArea="messages"
                     background="light-1">
                    {data.map((c) => (
                        <>
                            {c.author !== userInfo().username ? (
                                <Box flex={false} basis={"70px"} animation={"fadeIn"} margin={"xsmall"} round pad={"small"} background="light-3">
                                    <Text>{c.content}</Text>
                                    <Text size={"xsmall"}>{c.author}</Text>
                                    <Text size={"xsmall"}>{new Date(c.timestamp).toDateString()}</Text>
                                </Box>
                            ) : (
                                <Box flex={false} basis={"70px"} animation={"fadeIn"} margin={"xsmall"} round pad={"small"} background="brand"
                                     align={"end"}>
                                    <Text>{c.content}</Text>
                                    <Text size={"xsmall"}>You</Text>
                                    <Text size={"xsmall"}>{new Date(c.timestamp).toDateString()}</Text>
                                </Box>
                            )}
                        </>
                    ))}
                    <div ref={messagesEndRef} />
                </Box>
            )}
            <Box animation={"fadeIn"} margin={"medium"} gridArea="send" background="light-1">
                <TextArea
                    resize={false}
                    fill
                    placeholder="type here"
                    value={message}
                    onChange={event => setMessage(event.target.value)}
                />
                <Button
                    gap={"small"}
                    margin={"small"}
                    size={"medium"}
                    onClick={handleSubmit}
                >
                    <Send/><Text margin={"small"}>Send</Text>
                </Button>
            </Box>
        </Grid>
    );
};

export default Chat;