import axios from 'axios';
import { kkConfig } from '../Keycloak';

const apiClient = axios.create({
    baseURL: process.env.REACT_APP_BASE_URL,
    headers: {
        'Content-Type': 'application/json'
    }
});

export const fetchPolls = async () => apiClient.get('/api/polls');
export const submitVote = async (pollId: number, optionId: number) =>
    apiClient.post(`/api/polls/${pollId}/vote`, { optionId });

export const createPoll = async (pollData: { title: string; description: string; options: { text: string }[] }) =>
    apiClient.post('/api/polls', pollData);


apiClient.interceptors.request.use(async (config) => {
    if (kkConfig?.token) {
        try {
            await kkConfig.updateToken(15);
            config.headers.Authorization = `Bearer ${kkConfig.token}`;
        } catch (error) {
            console.error('Refresh token fails', error);
            kkConfig.login();
        }
    }
    return config;
});