import { Outlet } from 'react-router-dom';

import { useMediaQuery } from '@hooks/common/useMediaQuery';
import { useResetError } from '@hooks/common/useResetError';

import Error from '@components/common/Error/Error';
import ErrorBoundary from '@components/common/ErrorBoundary/ErrorBoundary';
import ToastContainer from '@components/common/ToastContainer/ToastContainer';
import Footer from '@components/layout/Footer/Footer';
import Header from '@components/layout/Header/Header';

const App = () => {
  const { handleErrorReset } = useResetError();
  useMediaQuery();

  return (
    <ErrorBoundary Fallback={Error} onReset={handleErrorReset}>
      <Header />
      <main>
        <Outlet />
      </main>
      <Footer />
      <ToastContainer />
    </ErrorBoundary>
  );
};

export default App;
