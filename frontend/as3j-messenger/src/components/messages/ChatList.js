import React, {useState, useEffect} from 'react';
import {List} from "grommet";
import axios from "axios";
import {backendUrl} from "../../utils/constants";
import authHeader from "../../utils/authHeader";
import {useHistory} from "react-router-dom";


function ChatList() {
    const [data, setData] = React.useState({});
    const [isError, setIsError] = useState(false);
    const history = useHistory();

    useEffect(() => {
        axios.get(`${backendUrl}/chats`, {headers: {Authorization: authHeader()}}).then(result => {
            setData(data)
        }).catch(e => {
            history.push("/login")
        });
    })

    return (
        <List>
            {console.log(data)}
        </List>
    );
}

export default ChatList;