import React from 'react';
import {Box, Heading, TextInput, Form, FormField, Button} from "grommet";


function RegisterForm() {

    const [data, setData] = React.useState({});

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
                onSubmit={({value}) => {
                }}
            >
                <FormField name="username" htmlfor="username" label="username">
                    <TextInput id="username" name="username"/>
                </FormField>
                <FormField name="email" htmlfor="email" label="email">
                    <TextInput id="email" name="email"/>
                </FormField>
                <FormField name="avatar" htmlfor="avatar" label="avatar link">
                    <TextInput id="avatar" name="avatar"/>
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