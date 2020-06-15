import React, {useState} from 'react';
import {Box, Heading, TextInput, Form, FormField, Button, Text} from "grommet";
import axios from "axios";
import {backendUrl} from "../../utils/constants";


function RegisterForm() {

    const [data, setData] = React.useState({});
    const [isSuccessful, setIsSuccessful] = useState(false);
    const [isError, setIsError] = useState(false);

    const register = () => {
        setIsSuccessful(false);
        setIsError(false);
        axios.post(`${backendUrl}/users`, {
            ...data
        }).then(result => {
            if (result.status === 200) {
                setIsSuccessful(true);
            } else {
                setIsError(true);
            }
        }).catch(e => {
            setIsError(true);
        });
    }

    return (
        <Box
            margin={"medium"}
            animation={"fadeIn"}
            pad={"large"}
            align={"center"}
        >
            <Heading size={"small"}>Sign Up</Heading>
            <Form
                value={data}
                onChange={nextValue => setData(nextValue)}
                onSubmit={register}
            >
                {isSuccessful && (<Text color={"green"}>Registration was successful</Text>)}
                {isError && (<Text color={"red"}>Error with request</Text>)}
                <FormField name="username" htmlfor="username" label="username">
                    <TextInput id="username" name="username"/>
                </FormField>
                <FormField name="email" htmlfor="email" label="email">
                    <TextInput id="email" name="email"/>
                </FormField>
                <FormField name="password" htmlfor="password" label="password">
                    <TextInput id="password" name="password"/>
                </FormField>
                <Box direction="row" gap="medium">
                    <Button type="submit" primary label="Submit"/>
                </Box>
            </Form>
        </Box>

    );
}

export default RegisterForm;