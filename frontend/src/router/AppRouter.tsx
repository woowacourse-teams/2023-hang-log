import App from '@/App';
import { PATH } from '@constants/path';
import { Suspense } from 'react';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';

import NotFoundPage from '@pages/NotFoundPage/NotFoundPage';
import TripCreatePage from '@pages/TripCreatePage/TripCreatePage';
import TripEditPage from '@pages/TripEditPage/TripEditPage';
import TripEditPageSkeleton from '@pages/TripEditPage/TripEditPageSkeleton';
import TripsPage from '@pages/TripsPage/TripsPage';
import TripsPageSkeleton from '@pages/TripsPage/TripsPageSkeleton';

const router = createBrowserRouter([
  {
    path: PATH.ROOT,
    element: (
      <Suspense>
        <App />
      </Suspense>
    ),
    errorElement: <NotFoundPage />,
    children: [
      {
        path: '',
        element: (
          <Suspense fallback={<TripsPageSkeleton />}>
            <TripsPage />
          </Suspense>
        ),
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
          <Suspense>
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
