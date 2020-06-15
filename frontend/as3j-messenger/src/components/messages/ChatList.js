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

function ChatList() {
    const [data, setData] = React.useState([]);
    const [chat, setChat] = React.useState('');
    const { setAuthToken } = useAuth();
    const history = useHistory();

    useEffect(() => {
        axios.get(`${backendUrl}/chats`, {headers: {Authorization: authHeader()}}).then(result => {
            setData(result.data)
            setAuthToken(result.headers.authorization);
        }).catch(e => {
            history.push("/login")
        });
    }, [history,setAuthToken])


    const socket = new SockJS("http://localhost:8080/ws");
    const stompClient = Stomp.over(socket);
    stompClient.connect({}, frame => {

      // Should be current user's UUID
      const currentUserUuid = "b5607d38-8fc1-43ef-b44e-34967083c80a";

      stompClient.subscribe(`/chats/add/${currentUserUuid}`, message => {
          // Reaction for new chat arrival
          console.log(message);
      });
    });

    return (
        <Table height="100vh">
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
                                <Button hoverIndicator={true} plain focusIndicator={false} color={"brand"} icon={<FormNext/>} secondary key={chat.name} label={
                                    <Box pad={"medium"}>
                                        <Text size={"small"}>{chat.name}</Text>
                                        <Text size={"xsmall"}>{chat.lastMessage}</Text>
                                        <Text size={"xsmall"}>{new Date(chat.timestamp).toDateString()}</Text>
                                    </Box>}>
                                </Button>
                            </TableCell>
                        </TableRow>
                    )
                })}
            </TableBody>
        </Table>
    );
}

export default ChatList;