import React from 'react';
import {Box, Button, Form, FormField, Heading, Text, TextInput} from "grommet";
import axios from "axios";
import {backendUrl} from "../../utils/constants";
import authHeader from "../../utils/authHeader";
import {useHistory} from "react-router";


const EditUser = () => {
    const [error, setError] = React.useState(false);
    const [username, setUsername] = React.useState({});
    const history = useHistory();

    const onChangeHandler = event => {
        const data = new FormData();
        data.append('file', event.target.files[0])
        axios.post(`${backendUrl}/files/avatars`,
            data, {headers: {Authorization: authHeader()}})
            .then(() => {
                history.push("/messages");
            })
            .catch(e => {
                alert(e);
            });
    };

    const onUsernameSubmitHandler = (data) => {
        axios.patch(`${backendUrl}/users`,
            data, {headers: {Authorization: authHeader()}})
            .then(() => {
                history.push("/messages");
            })
            .catch(e => {
                alert(e);
            });
    }


    return (
        <Box direction={"column"} width={"100%"} align={"center"}>
            <Heading alignSelf={"center"} size={"xsmall"}>upload new avatar</Heading>
            <Box direction={"row"}>
                <Box margin={"medium"}>
                    <input type="file" name="file" onChange={onChangeHandler}/>
                </Box>
            </Box>
            <Heading alignSelf={"center"} size={"xsmall"}>change username</Heading>
            {error && (<Text color={"red"}>fill data</Text>)}
            <Box direction={"row"}>
                <Box margin={"medium"}>
                    <Form
                        value={username}
                        onChange={nextValue => setUsername(nextValue)}
                        onSubmit={({ value }) => {onUsernameSubmitHandler(value)}}
                    >
                        <FormField name="username" htmlfor="username-input" label="username">
                            <TextInput id="username-input" name="username" />
                        </FormField>
                        <Box direction="row" gap="medium">
                            <Button type="submit" primary label="Submit" />
                        </Box>
                    </Form>
                </Box>
            </Box>
        </Box>
    );
};

export default EditUser;