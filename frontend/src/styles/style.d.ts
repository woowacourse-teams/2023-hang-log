import '@emotion/react';

declare module '@emotion/react' {
  export interface Theme {
    [key: string]: {
      [key: string]: string | { [key: string]: string };
    };
  }
}
