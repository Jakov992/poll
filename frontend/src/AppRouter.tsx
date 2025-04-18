import React from 'react';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import {AuthRoute} from "./components/AuthRoute";
import Dashboard from "./screen/Dashboard";
import AdminDashboard from "./screen/AdminDashboard";

const AppRouter = () => {
    const router = createBrowserRouter([
        {
            path: '/',
            element: <AuthRoute><Dashboard /></AuthRoute>
        },
        {
            path: '/admin',
            element: <AuthRoute><AdminDashboard /></AuthRoute>
        }
    ]);

    return <RouterProvider router={router} />;
};

export default AppRouter;
