// @ts-ignore
import Keycloak from 'keycloak-js';

const kkConfig = new Keycloak({
    url: process.env.REACT_APP_KEYCLOAK_SERVER_URL ?? '',
    realm: process.env.REACT_APP_KEYCLOAK_REALM ?? '',
    clientId: process.env.REACT_APP_KEYCLOAK_CLIENT_ID ?? ''
});

const kkOptions = {
    onLoad: 'login-required',
    KeycloakResponseType: 'code',
    checkLoginIframe: false,
    pkceMethod: 'S256'
};

export {
    kkConfig,
    kkOptions
};