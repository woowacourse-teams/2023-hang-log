import App from '@/App';

import { Suspense } from 'react';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';

import { useRecoilValue } from 'recoil';

import ExpensePage from '@pages/ExpensePage/ExpensePage';
import ExpensePageSkeleton from '@pages/ExpensePage/ExpensePageSkeleton';
import IntroPage from '@pages/IntroPage/IntroPage';
import LogInPage from '@pages/LogInPage/LogInPage';
import MyPage from '@pages/MyPage/MyPage';
import NotFoundPage from '@pages/NotFoundPage/NotFoundPage';
import RedirectPage from '@pages/RedirectPage/RedirectPage';
import SharedPage from '@pages/SharedPage/SharedPage';
import TripCreatePage from '@pages/TripCreatePage/TripCreatePage';
import TripEditPage from '@pages/TripEditPage/TripEditPage';
import TripPage from '@pages/TripPage/TripPage';
import TripPageSkeleton from '@pages/TripPage/TripPageSkeleton';
import TripsPage from '@pages/TripsPage/TripsPage';
import TripsPageSkeleton from '@pages/TripsPage/TripsPageSkeleton';

import { isLoggedInState } from '@store/auth';

import { PATH } from '@constants/path';

const AppRouter = () => {
  const isLoggedIn = useRecoilValue(isLoggedInState);

  const router = createBrowserRouter([
    {
      path: PATH.ROOT,
      element: <App />,
      errorElement: <NotFoundPage />,
      children: [
        {
          path: '',
          element: isLoggedIn ? (
            <Suspense fallback={<TripsPageSkeleton />}>
              <TripsPage />
            </Suspense>
          ) : (
            <IntroPage />
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
          element: <TripCreatePage />,
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
          path: PATH.REDIRECT,
          element: <RedirectPage />,
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
        {
          path: PATH.SHARE(':code'),
          element: (
            <Suspense fallback={<TripPageSkeleton />}>
              <SharedPage />
            </Suspense>
          ),
        },
      ],
    },
  ]);

  return <RouterProvider router={router} />;
};

export default AppRouter;
