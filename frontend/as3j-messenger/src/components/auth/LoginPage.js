import React from 'react';
import {Box, Main} from "grommet";
import RegisterForm from "./RegisterForm";
import LoginForm from "./LoginForm";

function LoginPage() {

    return (
        <Main background={
            {
                "opacity": true,
                "position": "bottom",
                "repeat": "no-repeat",
                "size": "cover",
                "image": "url(https://i.ibb.co/chw0gDP/447.jpg)"
            }
        }>
            <Box
                background={{
                    color: "light-1",
                    opacity: "strong"
                }}
                margin={{
                    "vertical": "5vw",
                }}
                basis={"large"}
                direction={"row"}
                border={{color: 'brand', size: 'large'}}
                animation={"fadeIn"}
                pad={"small"}
                width={"xlarge"}
                justify={"center"}
                alignSelf={"center"}
            >
                <RegisterForm/>
                <LoginForm/>
            </Box>

        </Main>
    );
}

export default LoginPage;