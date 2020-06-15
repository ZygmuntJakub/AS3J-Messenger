import React from 'react';
import {Box, Button, Form, FormField, Heading, Text, TextInput} from "grommet";
import axios from "axios";
import {backendUrl} from "../../utils/constants";
import authHeader from "../../utils/authHeader";
import {useUser} from "../../context/context";


const EditUser = () => {
    const [error, setError] = React.useState(false);
    const [success, setSuccess] = React.useState(false);
    const [username, setUsername] = React.useState({});
    const [password, setPassword] = React.useState({});
    const [passwordError, setPasswordError] = React.useState(false);
    const [avatarUuid, setAvatarUuid] = React.useState('');
    const {setUser} = useUser();

    const onChangeHandler = event => {
        const data = new FormData();
        data.append('file', event.target.files[0])
        axios.post(`${backendUrl}/files/avatars`,
            data, {headers: {Authorization: authHeader()}})
            .then((res) => {
                setAvatarUuid(res.data.value);
            })
            .catch(e => {
                alert(e);
            });
    };

    const onUsernameSubmitHandler = (data) => {
        if (!data.username) {
            setError(true);
            return;
        }
        axios.patch(`${backendUrl}/users`,
            {...data, photoID: avatarUuid}, {headers: {Authorization: authHeader()}})
            .then((res) => {
                setUser(res.data);
                setSuccess(true);
                setUsername({})
            })
            .catch(e => {
                alert(e);
            });
    }

    const onPasswordSubmitHandler = (data) => {
        if (data.currentPassword && data.newPassword && data.retype){
            if(data.newPassword === data.retype){
                axios.patch(`${backendUrl}/users/password`,
                    {...data}, {headers: {Authorization: authHeader()}})
                    .then((res) => {
                        setUser(res.data);
                        setSuccess(true);
                        setPassword({})
                    })
                    .catch(e => {
                        alert(e);
                    });
            } else {
                setPasswordError(true);
            }
        } else {
            setError(true)
        }

    }


    return (
        <Box animation={"fadeIn"} pad={"medium"} fill direction={"column"} align={"center"}>
            {success && (<Text color={"green"}>success</Text>)}
            {error && (<Text color={"red"}>fill data</Text>)}
            <Heading alignSelf={"center"} size={"xsmall"}>edit account</Heading>
            <Box fill justify={"around"} direction={"row"} width={"100%"} align={"center"}>
                <Box align={"center"} direction={"column"}>
                    <Text>upload avatar</Text>
                    <Box margin={"medium"}>
                        <input type="file" name="file" onChange={onChangeHandler}/>
                    </Box>
                </Box>
                <Box direction={"column"} align={"center"}>
                    <Box margin={"medium"}>
                        <Form
                            value={username}
                            onChange={nextValue => setUsername(nextValue)}
                            onSubmit={({value}) => {
                                onUsernameSubmitHandler(value)
                            }}
                        >
                            <FormField name="username" htmlfor="username-input" label="username">
                                <TextInput id="username-input" name="username"/>
                            </FormField>
                            <Box direction="row" gap="medium">
                                <Button type="submit" primary label="Submit"/>
                            </Box>
                        </Form>
                    </Box>
                </Box>
            </Box>
            <Heading alignSelf={"center"} size={"xsmall"}>change password</Heading>
            {passwordError && (<Text color={"red"}>new passwords are not the same</Text>)}
            <Box>
                <Form
                    value={password}
                    onChange={nextValue => setPassword(nextValue)}
                    onSubmit={({value}) => {
                        onPasswordSubmitHandler(value)
                    }}
                >
                    <FormField name="currentPassword" htmlfor="currentPassword-input" label="current password">
                        <TextInput type={"password"} id="currentPassword-input" name="currentPassword"/>
                    </FormField>
                    <FormField name="newPassword" htmlfor="newPassword-input" label="new password">
                        <TextInput type={"password"} id="newPassword-input" name="newPassword"/>
                    </FormField>
                    <FormField name="retype" htmlfor="retype-input" label="retype new password">
                        <TextInput type={"password"} id="retype-input" name="retype"/>
                    </FormField>
                    <Box direction="row" gap="medium">
                        <Button type="submit" primary label="Submit"/>
                    </Box>
                </Form>
            </Box>
        </Box>
    );
};

export default EditUser;