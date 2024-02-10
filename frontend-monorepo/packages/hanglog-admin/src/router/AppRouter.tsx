import { Suspense } from 'react';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';

import App from '@/App';

import AdminMainPage from '@pages/AdminMainPage/AdminMainPage';
import AdminMemberPage from '@pages/AdminMemberPage/AdminMemberPage';
import CategoryPage from '@pages/CategoryPage/CategoryPage';
import CityPage from '@pages/CityPage/CityPage';
import CurrencyPage from '@pages/CurrencyPage/CurrencyPage';
import NotFoundPage from '@pages/NotFoundPage/NotFoundPage';

import { PATH } from '@constants/path';

const router = createBrowserRouter([
  {
    path: PATH.ROOT,
    element: <App />,
    errorElement: <NotFoundPage />,
    children: [
      {
        path: '',
        element: <div></div>,
      },
      {
        path: PATH.HOME,
        element: (
          <Suspense>
            <AdminMainPage />
          </Suspense>
        ),
      },
      {
        path: PATH.ADMIN_MEMBER,
        element: (
          <Suspense>
            <AdminMemberPage />
          </Suspense>
        ),
      },
      {
        path: PATH.CITY,
        element: (
          <Suspense>
            <CityPage />
          </Suspense>
        ),
      },
      {
        path: PATH.CATEGORY,
        element: (
          <Suspense>
            <CategoryPage />
          </Suspense>
        ),
      },
      {
        path: PATH.CURRENCY,
        element: (
          <Suspense>
            <CurrencyPage />
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
