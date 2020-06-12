import React from 'react';
import {Box, Layer, Main} from "grommet";
import RegisterForm from "./RegisterForm";
import LoginForm from "./LoginForm";

function LoginPage() {

    return (
        <Main>
                <Layer>
                    <Box
                        basis={"large"}
                        direction={"row"}
                        border={{ color: 'brand', size: 'large' }}
                        animation={"fadeIn"}
                        pad={"small"}
                        width={"xlarge"}
                        justify={"center"}
                    >
                        <RegisterForm />
                        <LoginForm />
                    </Box>
                </Layer>

        </Main>
    );
}

export default LoginPage;