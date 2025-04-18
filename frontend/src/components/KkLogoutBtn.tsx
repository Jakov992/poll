import React from 'react';
import { useKeycloak } from '@react-keycloak/web';

const KkLogoutBtn: React.FC = () => {
    const { keycloak } = useKeycloak();

    return (
        <button
            onClick={() => keycloak.logout()}
            className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600"
        >
            Logout
        </button>
    );
};

export default KkLogoutBtn;