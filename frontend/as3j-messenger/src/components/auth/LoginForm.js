import React, {useState} from 'react';
import {Box, Heading, TextInput, Form, FormField, Button, Text} from "grommet";
import {backendUrl} from "../../utils/constants";
import {useAuth} from "../../context/context";
import axios from 'axios';
import {Redirect} from "react-router";


function LoginForm() {
    const [data, setData] = React.useState({});
    const [isLoggedIn, setLoggedIn] = useState(false);
    const [isError, setIsError] = useState(false);
    const { setAuthToken } = useAuth();

    const login = () => {
        axios.post(`${backendUrl}/auth/login`, {
            ...data
        }).then(result => {
            if (result.status === 200) {
                setAuthToken(result.data);
                setLoggedIn(true);
            } else {
                setIsError(true);
            }
        }).catch(e => {
            setIsError(true);
        });
    }

    if (isLoggedIn) {
        return <Redirect to="/" />;
    }

    return (

        <Box
            margin={"medium"}
            animation={"fadeIn"}
            pad={"large"}
            align={"center"}
            gap={"small"}
        >
            <Heading size={"small"}>Sign In</Heading>
            <Form
                value={data}
                onChange={nextValue => setData(nextValue)}
                onSubmit={login}
            >
                {isError && (<Text color={"red"}>Error with request</Text>)}
                <FormField name="email" htmlfor="email" label="email">
                    <TextInput id="email" name="email" />
                </FormField>
                <FormField name="password" htmlfor="password" label="password">
                    <TextInput id="password" name="password" type={"password"}/>
                </FormField>
                <Box direction="row" gap="medium">
                    <Button type="submit" primary label="Submit" />
                </Box>
            </Form>
        </Box>

    );
}

export default LoginForm;