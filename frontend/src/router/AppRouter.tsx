import App from '@/App';
import { PATH } from '@constants/path';
import { Suspense } from 'react';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';

import TripCreatePage from '@pages/TripCreatePage/TripCreatePage';
import TripEditPage from '@pages/TripEditPage/TripEditPage';
import TripEditPageSkeleton from '@pages/TripEditPage/TripEditPageSkeleton';
import TripsPage from '@pages/TripsPage/TripsPage';

const router = createBrowserRouter([
  {
    path: PATH.ROOT,
    element: <App />,
    children: [
      {
        path: PATH.TRIPS,
        element: <TripsPage />,
      },
      {
        path: PATH.EDIT_TRIP,
        element: (
          <Suspense fallback={<TripEditPageSkeleton />}>
            <TripEditPage />
          </Suspense>
        ),
      },
      {
        path: PATH.CREATE_TRIP,
        element: (
          <Suspense fallback={<TripEditPageSkeleton />}>
            <TripCreatePage />
          </Suspense>
        ),
      },
    ],
  },
]);

const AppRouter = () => {
  return <RouterProvider router={router} />;
};

export default AppRouter;
