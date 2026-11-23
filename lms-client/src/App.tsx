import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Layout from "./components/Layout/Layout.tsx";
import HomePage from "./pages/HomePage.tsx";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    children: [
      {
        index: true,
        element: <HomePage/>,
      },
    ],
  },
]);

export default function App() {
  return (
      <RouterProvider router={router} />
  );
}
