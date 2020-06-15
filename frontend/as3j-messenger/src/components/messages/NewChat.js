import React, {useEffect} from 'react';
import {Box, Button, CheckBox, Heading, Text, TextInput} from "grommet";
import axios from "axios";
import {backendUrl} from "../../utils/constants";
import authHeader from "../../utils/authHeader";
import {useAuth} from "../../context/context";
import userInfo from "../../utils/userInfo";
import {useHistory} from "react-router-dom";


const NewChat = () => {

    const [users, setUsers] = React.useState([]);
    const [checkedUsers, setCheckedUsers] = React.useState([userInfo().uuid]);
    const [name, setName] = React.useState('');
    const [error, setError] = React.useState(false);
    const {setAuthToken} = useAuth();
    const history = useHistory();

    useEffect(() => {
        axios.get(`${backendUrl}/users`, {headers: {Authorization: authHeader()}}).then(result => {
            setUsers(result.data)
            setAuthToken(result.headers.authorization);
        }).catch(e => {
            alert(e);
        });
    }, []);


    const handleSubmit = () => {
        if (name === '' || checkedUsers.length === 0) {
            setError(true);
            return;
        }
        axios.post(`${backendUrl}/chats`,
            {
                name: name,
                usersUuid: checkedUsers,
            }
        ,{headers: {Authorization: authHeader()}}).then(result => {
            setUsers(result.data);
            setAuthToken(result.headers.authorization);
            history.push("/messages");
        }).catch(e => {
            alert(e);
        });
    }

    return (
        <Box direction={"column"} width={"100%"} align={"center"}>
            <Heading alignSelf={"center"} size={"xsmall"}>add new chat</Heading>
            {error && (<Text color={"red"}>fill data</Text>)}
            <Box direction={"row"}>
                <Box margin={"medium"}>
                    {users && users.map((user) => (
                        <CheckBox
                            key={user.uuid}
                            checked={checkedUsers.includes(user.uuid)}
                            label={user.username}
                            onChange={(event) => {
                                event.target.checked ?
                                    setCheckedUsers([...checkedUsers, user.uuid]) :
                                    setCheckedUsers([checkedUsers.splice(checkedUsers.indexOf(user.uuid), 1)])
                            }}
                        />
                    ))}
                </Box>
                <Box margin={"medium"}>
                    <TextInput
                        placeholder="type name"
                        value={name}
                        onChange={event => setName(event.target.value)}
                    />
                </Box>
            </Box>
            <Button
                label="create"
                onClick={handleSubmit}
            />
        </Box>
    )
};

export default NewChat;

