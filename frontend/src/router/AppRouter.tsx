import App from '@/App';
import { PATH } from '@constants/path';
import { Suspense } from 'react';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';

import ExpensePage from '@pages/ExpensePage/ExpensePage';
import ExpensePageSkeleton from '@pages/ExpensePage/ExpensePageSkeleton';
import NotFoundPage from '@pages/NotFoundPage/NotFoundPage';
import TripCreatePage from '@pages/TripCreatePage/TripCreatePage';
import TripEditPage from '@pages/TripEditPage/TripEditPage';
import TripEditPageSkeleton from '@pages/TripEditPage/TripEditPageSkeleton';
import TripsPage from '@pages/TripsPage/TripsPage';
import TripsPageSkeleton from '@pages/TripsPage/TripsPageSkeleton';

const router = createBrowserRouter([
  {
    path: PATH.ROOT,
    element: <App />,
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
        path: PATH.EDIT_TRIP(':tripId'),
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
      {
        path: PATH.EXPENSE,
        element: (
          <Suspense fallback={<ExpensePageSkeleton />}>
            <ExpensePage />
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
