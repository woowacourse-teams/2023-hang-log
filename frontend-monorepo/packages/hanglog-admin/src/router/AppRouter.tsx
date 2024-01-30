import App from '@/App';
import NotFoundPage from '@/pages/NotFoundPage/NotFoundPage';
import { PATH } from '@constants/path';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';

const router = createBrowserRouter([
  {
    path: PATH.ROOT,
    element: <App />,
    errorElement: <NotFoundPage />,
    children: [],
  },
]);

const AppRouter = () => {
  return <RouterProvider router={router} />;
};

export default AppRouter;
