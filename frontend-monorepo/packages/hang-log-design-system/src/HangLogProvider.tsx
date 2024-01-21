import { Global, ThemeProvider } from '@emotion/react';
import type { PropsWithChildren } from 'react';

import ToastContainer from '@components/ToastContainer/ToastContainer';

import { GlobalStyle } from '@styles/GlobalStyle';
import { Theme } from '@styles/Theme';

type HangLogProviderProps = PropsWithChildren;

const HangLogProvider = ({ children }: HangLogProviderProps) => {
  return (
    <ThemeProvider theme={Theme}>
      <Global styles={GlobalStyle} />
      {children}
      <ToastContainer />
    </ThemeProvider>
  );
};

export default HangLogProvider;
