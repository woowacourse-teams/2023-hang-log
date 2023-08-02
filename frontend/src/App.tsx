import { Outlet } from 'react-router-dom';

import { useResetError } from '@hooks/common/useResetError';

import Error from '@components/common/Error/Error';
import ErrorBoundary from '@components/common/ErrorBoundary/ErrorBoundary';
import Footer from '@components/layout/Footer/Footer';
import Header from '@components/layout/Header/Header';

const App = () => {
  const { handleErrorReset } = useResetError();

  return (
    <ErrorBoundary Fallback={Error} onReset={handleErrorReset}>
      <Header />
      <main>
        <Outlet />
      </main>
      <Footer />
    </ErrorBoundary>
  );
};

export default App;
