import React, {Suspense} from 'react';
import {ReactKeycloakProvider} from '@react-keycloak/web';
import {kkConfig, kkOptions} from "./Keycloak";
import AppRouter from "./AppRouter";

function App() {
  return (
      <ReactKeycloakProvider
          authClient={kkConfig}
          initOptions={kkOptions}
      >
          <Suspense fallback={<div></div>}>
            <AppRouter />
          </Suspense>
      </ReactKeycloakProvider>
  );
}

export default App;
