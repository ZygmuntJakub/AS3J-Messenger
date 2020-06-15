import React, {useEffect} from 'react';
import {Box, Button, Table, TableBody, TableCell, TableHeader, TableRow, Text} from "grommet";
import {FormNext} from 'grommet-icons';
import axios from "axios";
import {backendUrl} from "../../utils/constants";
import authHeader from "../../utils/authHeader";
import {useHistory} from "react-router-dom";
import {useAuth} from "../../context/context";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import Chat from "./Chat";
import userInfo from "../../utils/userInfo";

function ChatList() {
    const [data, setData] = React.useState([]);
    const [chat, setChat] = React.useState('');
    const {setAuthToken} = useAuth();
    const history = useHistory();
    const socket = new SockJS(`${backendUrl}/ws`);
    const stompClient = Stomp.over(socket);

    useEffect(() => {
        axios.get(`${backendUrl}/chats`, {headers: {Authorization: authHeader()}}).then(result => {
            setData(result.data)
            setAuthToken(result.headers.authorization);
            stompClient.connect({}, frame => {
                stompClient.subscribe(`/chats/add/${userInfo().uuid}`, message => {
                    const chats = result.data;
                    chats.unshift(JSON.parse(message.body))
                    setData(() => [
                        ...chats
                    ]);
                });
            });
        }).catch(e => {
            history.push("/login")
        });
    }, [history,setAuthToken]);

    return (
        <>
            <Box width={"400px"} overflow={"auto"} height="100vh">
                <Table >
                    <TableHeader>
                        <TableRow>
                            <TableCell><Text color={"brand"}>Chats</Text></TableCell>
                        </TableRow>
                    </TableHeader>
                    <TableBody>
                        {data && data.map((chat) => {
                            return (
                                <TableRow key={chat.name}>
                                    <TableCell>
                                        <Button
                                            hoverIndicator={true}
                                            plain
                                            focusIndicator={false}
                                            color={"brand"}
                                            icon={<FormNext/>}
                                            secondary
                                            key={chat.name}
                                            label={
                                                <Box pad={"medium"}>
                                                    <Text size={"small"}>{chat.name}</Text>
                                                    <Text size={"xsmall"}>{chat.lastMessage}</Text>
                                                    <Text
                                                        size={"xsmall"}>
                                                        {chat.timestamp.nano ?
                                                            chat.timestamp.hour + ":" + chat.timestamp.minute + ":" + chat.timestamp.second :
                                                            new Date(chat.timestamp).toDateString()}
                                                    </Text>
                                                </Box>}
                                            onClick={() => setChat(chat.chatUuid)}
                                        >
                                        </Button>
                                    </TableCell>
                                </TableRow>
                            )
                        })}
                    </TableBody>
                </Table>
            </Box>
            <Box overflow={"hidden"} animation={"fadeIn"} height="100vh" width={"100%"} background={'brand'}>
                {chat ? (
                    <Chat chat={chat} />
                ) : (
                    <Box fill align={"center"} justify={"center"} direction={"row"}>
                        <Text size={"xxlarge"}>Select chat</Text>
                    </Box>
                )}
            </Box>
        </>
    );
}

export default ChatList;