import App from '@/App';

import { Suspense } from 'react';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';

import { useRecoilValue } from 'recoil';

import ExpensePageSkeleton from '@pages/ExpensePage/ExpensePageSkeleton';
import NotFoundPage from '@pages/NotFoundPage/NotFoundPage';
import RedirectPage from '@pages/RedirectPage/RedirectPage';
import TripPageSkeleton from '@pages/TripPage/TripPageSkeleton';
import TripsPageSkeleton from '@pages/TripsPage/TripsPageSkeleton';

import { isLoggedInState } from '@store/auth';
import { mediaQueryMobileState } from '@store/mediaQuery';

import * as Lazy from '@router/lazy';

import { PATH } from '@constants/path';

const AppRouter = () => {
  const isLoggedIn = useRecoilValue(isLoggedInState);
  const isMobile = useRecoilValue(mediaQueryMobileState);

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
              <Lazy.TripsPage />
            </Suspense>
          ) : (
            <Lazy.IntroPage />
          ),
        },
        {
          path: PATH.TRIP(':tripId'),
          element: (
            <Suspense fallback={<TripPageSkeleton />}>
              {isMobile ? <Lazy.TripMobilePage /> : <Lazy.TripPage />}
            </Suspense>
          ),
        },
        {
          path: PATH.EDIT_TRIP(':tripId'),
          element: (
            <Suspense fallback={<TripPageSkeleton />}>
              <Lazy.TripEditPage />
            </Suspense>
          ),
        },
        {
          path: PATH.CREATE_TRIP,
          element: (
            <Suspense>
              <Lazy.TripCreatePage />
            </Suspense>
          ),
        },
        {
          path: PATH.EXPENSE(':tripId'),
          element: (
            <Suspense fallback={<ExpensePageSkeleton />}>
              <Lazy.ExpensePage />
            </Suspense>
          ),
        },
        {
          path: PATH.REDIRECT,
          element: <RedirectPage />,
        },
        {
          path: PATH.LOGIN,
          element: (
            <Suspense>
              <Lazy.LogInPage />
            </Suspense>
          ),
        },
        {
          path: PATH.MY_PAGE,
          element: (
            <Suspense fallback={<div>LOADING</div>}>
              <Lazy.MyPage />
            </Suspense>
          ),
        },
        {
          path: PATH.SHARE_TRIP(':tripId'),
          element: (
            <Suspense fallback={<TripPageSkeleton />}>
              {isMobile ? <Lazy.TripMobilePage isShared /> : <Lazy.SharedTripPage />}
            </Suspense>
          ),
        },
        {
          path: PATH.SHARE_EXPENSE(':tripId'),
          element: (
            <Suspense fallback={<ExpensePageSkeleton />}>
              <Lazy.ExpensePage isShared />
            </Suspense>
          ),
        },
        {
          path: PATH.COMMUNITY_TRIP(':tripId'),
          element: (
            <Suspense fallback={<TripPageSkeleton />}>
              {isMobile ? <Lazy.TripMobilePage isShared isPublished /> : <Lazy.CommunityTripPage />}
            </Suspense>
          ),
        },
        {
          path: PATH.COMMUNITY_EXPENSE(':tripId'),
          element: (
            <Suspense fallback={<ExpensePageSkeleton />}>
              <Lazy.ExpensePage isShared isPublished />
            </Suspense>
          ),
        },
      ],
    },
  ]);

  return <RouterProvider router={router} />;
};

export default AppRouter;
