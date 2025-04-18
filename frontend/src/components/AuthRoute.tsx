import {FC, JSXElementConstructor, ReactElement} from 'react';
import { useKeycloak } from '@react-keycloak/web';
import React from 'react';
import {CircularProgress} from "@mui/material";

interface AuthRouteProps {
    children: ReactElement<any, string | JSXElementConstructor<any>>;
}


export const AuthRoute: FC<AuthRouteProps> = ({ children }) => {
    const { keycloak, initialized } = useKeycloak();

    if (!initialized) {
        return <CircularProgress />;
    }
    console.log(keycloak.authenticated)
    if (!keycloak.authenticated) {
        return <CircularProgress />;
    }

    return children;
};