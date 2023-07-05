import '@emotion/react';

declare module '@emotion/react' {
  export interface Theme {
    color: { [key: string]: string };
    text: TextStyle;
    heading: TextStyle;
    spacer: { [key: string]: string };
    borderRadius: { [key: string]: string };
    boxShadow: { [key: string]: string };
  }
}
