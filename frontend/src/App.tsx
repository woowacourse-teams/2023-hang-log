import { Outlet } from 'react-router-dom';

import Header from '@components/layout/Header/Header';

const App = () => {
  return (
    <div css={{ height: '100vh' }}>
      <Header />
      <main>
        <Outlet />
      </main>
    </div>
  );
};

export default App;
