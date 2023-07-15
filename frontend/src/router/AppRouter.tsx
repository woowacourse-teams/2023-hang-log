import App from '@/App';
import { PATH } from '@constants/path';
import { Outlet, RouterProvider, createBrowserRouter } from 'react-router-dom';

import TripEditPage from '@pages/TripEditPage/TripEditPage';

const router = createBrowserRouter([
  {
    path: PATH.ROOT,
    element: <App />,
    children: [
      {
        path: PATH.EDIT_TRIP,
        element: <TripEditPage />,
      },
    ],
  },
]);

const AppRouter = () => {
  return <RouterProvider router={router} />;
};

export default AppRouter;
