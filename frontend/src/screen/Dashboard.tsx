import { useKeycloak } from '@react-keycloak/web';
import { useNavigate } from 'react-router-dom';
import PollList from "../components/PollList";
import KkLogoutBtn from "../components/KkLogoutBtn";

const Dashboard: React.FC = () => {
    const { keycloak } = useKeycloak();
    const navigate = useNavigate();

    const isAdmin = keycloak.tokenParsed?.realm_access?.roles.includes('Admin');

    return (
        <div className="min-h-screen bg-gray-50">
            <header className="bg-white shadow-md">
                <div className="container mx-auto flex justify-between items-center p-4">
                    <h1 className="text-2xl font-bold text-gray-800">🗳️ Poll Dashboard</h1>

                    <div className="flex items-center gap-4">
                        {isAdmin && (
                            <button
                                onClick={() => navigate('/admin')}
                                className="bg-purple-600 hover:bg-purple-700 text-white px-4 py-2 rounded transition"
                            >
                                Admin Dashboard
                            </button>
                        )}
                        <KkLogoutBtn />
                    </div>
                </div>
            </header>

            <main className="container mx-auto p-6">
                <h2 className="text-xl font-semibold mb-4 text-gray-700">Available Polls</h2>
                <PollList />
            </main>
        </div>
    );
};

export default Dashboard;