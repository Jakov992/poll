import {useEffect} from 'react';
import {useKeycloak} from '@react-keycloak/web';

const useAuth = () => {
    const { initialized, keycloak } = useKeycloak();
    // const dispatch = useAppDispatch();
    // const user = useAppSelector(selectUser);
    // const kkUser = useAppSelector(selectKkUser);

    useEffect(() => {
        if (!initialized) return;

        const token = keycloak.token;
        // token && dispatch(storeKkUser(token));
    }, [keycloak, initialized]);

    // useEffect(() => {
    //     if (!user && kkUser) {
    //         getUserInfoByKeycloakUserId(kkUser.id)
    //             .then(res => {
    //                 if (res.error) return;
    //                 dispatch(setAppVersion());
    //
    //                 const user: User | null = UserConverter.convertFromUserApiToUser(res.data);
    //                 dispatch(storeUser(user));
    //             });
    //     }
    // }, [kkUser]);

    return {
        initialized
    };
};

export default useAuth;