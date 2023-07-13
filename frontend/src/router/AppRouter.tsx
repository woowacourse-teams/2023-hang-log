import App from '@/App';
import { PATH } from '@constants/path';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';

import TripsPage from '@pages/TripsPage';

const router = createBrowserRouter([
  {
    path: PATH.ROOT,
    element: <App />,
  },
  {
    path: PATH.TRIPS,
    element: <TripsPage />,
  },
]);

const AppRouter = () => {
  return <RouterProvider router={router} />;
};

export default AppRouter;
