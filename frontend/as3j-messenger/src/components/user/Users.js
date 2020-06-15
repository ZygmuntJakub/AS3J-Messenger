import React, {useEffect} from 'react';
import axios from "axios";
import {backendUrl} from "../../utils/constants";
import authHeader from "../../utils/authHeader";
import {useAuth} from "../../context/context";
import {useHistory} from "react-router-dom";
import {Box, Button, Table, TableBody, TableCell, TableHeader, TableRow, Text} from "grommet";


const Users = () => {

        const [users, setUsers] = React.useState([]);
        const [blacklist, setBlacklist] = React.useState([]);
        const {setAuthToken} = useAuth();
        const history = useHistory();

        useEffect(() => {
            axios.get(`${backendUrl}/users`, {headers: {Authorization: authHeader()}}).then(result => {
                setUsers(result.data)
                setAuthToken(result.headers.authorization);
            }).catch(e => {
                history.push("/login")
            });

            axios.get(`${backendUrl}/blacklists`, {headers: {Authorization: authHeader()}}).then(result => {
                setBlacklist(result.data)
                setAuthToken(result.headers.authorization);
            }).catch(e => {
                history.push("/login")
            });
        }, [history, setAuthToken])
        return (
            <Box direction={"row"} pad={"small"}  justify={"around"} fill>
                <Box width={"medium"}>
                    <Table height="100vh">
                        <TableHeader>
                            <TableRow>
                                <TableCell><Text color={"brand"}>Users</Text></TableCell>
                            </TableRow>
                        </TableHeader>
                        <TableBody>
                            {users && users.map((user) => {
                                return (
                                    <TableRow key={user.uuid}>
                                        <TableCell>
                                            <Box justify={"between"} direction={"row"} plain>
                                                <Text size={"small"}>{user.username}</Text>
                                                <Button size={"small"} label="start chat"/>
                                                <Button size={"small"} label="block"/>
                                            </Box>
                                        </TableCell>
                                    </TableRow>
                                )
                            })}
                        </TableBody>
                    </Table>
                </Box>
                <Box width={"medium"}>
                    <Table height="100vh">
                        <TableHeader>
                            <TableRow>
                                <TableCell><Text color={"brand"}>Blacklist</Text></TableCell>
                            </TableRow>
                        </TableHeader>
                        <TableBody>
                            {blacklist && blacklist.map((user) => {
                                return (
                                    <TableRow key={user.uuid}>
                                        <TableCell>
                                            <Box justify={"between"} direction={"row"} plain>
                                                <Text size={"small"}>{user.username}</Text>
                                                <Button size={"small"} label="start chat"/>
                                                <Button size={"small"} label="unblock"/>
                                            </Box>
                                        </TableCell>
                                    </TableRow>
                                )
                            })}
                        </TableBody>
                    </Table>
                </Box>
            </Box>
        );
    }
;

export default Users;