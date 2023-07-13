import { Outlet } from 'react-router-dom';

import Header from '@components/layout/Header/Header';

const App = () => {
  return (
    <>
      <Header />
      <main>
        <Outlet />
      </main>
    </>
  );
};

export default App;
