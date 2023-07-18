import App from '@/App';
import { PATH } from '@constants/path';
import { Suspense } from 'react';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';

import TripEditPage from '@pages/TripEditPage/TripEditPage';
import TripEditPageSkeleton from '@pages/TripEditPage/TripEditPageSkeleton';

const router = createBrowserRouter([
  {
    path: PATH.ROOT,
    element: <App />,
    children: [
      {
        path: PATH.EDIT_TRIP,
        element: (
          <Suspense fallback={<TripEditPageSkeleton />}>
            <TripEditPage />
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
