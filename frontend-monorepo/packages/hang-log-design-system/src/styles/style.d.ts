import '@emotion/react';

interface TextStyle {
  [key: string]: {
    fontSize: string;
    lineHeight: string;
  };
}

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
