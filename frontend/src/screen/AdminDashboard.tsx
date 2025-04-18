import React from 'react';
import { useNavigate } from 'react-router-dom';
import KkLogoutBtn from '../components/KkLogoutBtn';
import AdminPollCreate from '../components/AdminPollCreate';

const AdminDashboard: React.FC = () => {
    const navigate = useNavigate();

    return (
        <div className="min-h-screen bg-gray-50">
            <header className="bg-white shadow-md">
                <div className="container mx-auto flex justify-between items-center p-4">
                    <div className="flex items-center space-x-2">
                        <img src="/cogify.png" alt="Dashboard Icon" className="w-8 h-8" />
                        <h1 className="text-2xl font-bold text-gray-800">Admin Dashboard</h1>
                    </div>

                    <div className="flex items-center gap-4">
                        <button
                            onClick={() => navigate('/')}
                            className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded transition"
                        >
                            Go to Dashboard
                        </button>
                        <KkLogoutBtn />
                    </div>
                </div>
            </header>

            <main className="container mx-auto py-10">
                <AdminPollCreate />
            </main>
        </div>
    );
};

export default AdminDashboard;