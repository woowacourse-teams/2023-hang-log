import { lazy } from 'react';

export const ExpensePage = lazy(
  () => import(/* webpackChunkName: "ExpensePage" */ '@pages/ExpensePage/ExpensePage')
);

export const IntroPage = lazy(
  () => import(/* webpackChunkName: "IntroPage" */ '@pages/IntroPage/IntroPage')
);

export const LogInPage = lazy(
  () => import(/* webpackChunkName: "LogInPage" */ '@pages/LogInPage/LogInPage')
);

export const MyPage = lazy(() => import(/* webpackChunkName: "MyPage" */ '@pages/MyPage/MyPage'));

export const SharedPage = lazy(
  () => import(/* webpackChunkName: "SharedPage" */ '@pages/SharedPage/SharedPage')
);

export const TripCreatePage = lazy(
  () => import(/* webpackChunkName: "TripCreatePage" */ '@pages/TripCreatePage/TripCreatePage')
);

export const TripEditPage = lazy(
  () => import(/* webpackChunkName: "TripEditPage" */ '@pages/TripEditPage/TripEditPage')
);

export const TripPage = lazy(
  () => import(/* webpackChunkName: "TripPage" */ '@pages/TripPage/TripPage')
);

export const TripsPage = lazy(
  () => import(/* webpackChunkName: "TripsPage" */ '@pages/TripsPage/TripsPage')
);
