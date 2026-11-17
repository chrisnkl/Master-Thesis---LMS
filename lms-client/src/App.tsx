import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Layout from "./components/Layout/Layout.tsx";
import HomePage from "./pages/HomePage.tsx";
import OAuthCallbackPage from "./pages/OAuthCallbackPage.tsx";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    children: [
      {
        index: true,
        element: <HomePage/>,
      },
      {
        path: "/oauth2/callback",
        element: <OAuthCallbackPage />,
      },
    ],
  },
]);

export default function App() {
  return (
      <RouterProvider router={router} />
  );
}
