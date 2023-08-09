import App from '@/App';
import { PATH } from '@constants/path';
import { Suspense } from 'react';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';

import ExpensePage from '@pages/ExpensePage/ExpensePage';
import ExpensePageSkeleton from '@pages/ExpensePage/ExpensePageSkeleton';
import LogInPage from '@pages/LogInPage/LogInPage';
import MyPage from '@pages/MyPage/MyPage';
import NotFoundPage from '@pages/NotFoundPage/NotFoundPage';
import RedirectPage from '@pages/RedirectPage/RedirectPage';
import SignInPage from '@pages/SignInPage/SignInPage';
import TripCreatePage from '@pages/TripCreatePage/TripCreatePage';
import TripEditPage from '@pages/TripEditPage/TripEditPage';
import TripPage from '@pages/TripPage/TripPage';
import TripPageSkeleton from '@pages/TripPage/TripPageSkeleton';
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
        path: PATH.TRIP(':tripId'),
        element: (
          <Suspense fallback={<TripPageSkeleton />}>
            <TripPage />
          </Suspense>
        ),
      },
      {
        path: PATH.EDIT_TRIP(':tripId'),
        element: (
          <Suspense fallback={<TripPageSkeleton />}>
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
        path: PATH.EXPENSE(':tripId'),
        element: (
          <Suspense fallback={<ExpensePageSkeleton />}>
            <ExpensePage />
          </Suspense>
        ),
      },
      {
        path: process.env.REDIRECT_URI,
        element: <RedirectPage />,
      },
      {
        path: PATH.SIGN_UP,
        element: <SignInPage />,
      },
      {
        path: PATH.LOGIN,
        element: <LogInPage />,
      },
      {
        path: PATH.MY_PAGE,
        element: (
          <Suspense fallback={<div>LOADING</div>}>
            <MyPage />
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
