const color = {
  /** heading text */
  black: 'black',
  /** default text */
  gray800: '#282828',
  gray700: '#5e5e5e',
  /** light text */
  gray600: '#727272',
  gray500: '#a6a6a6',
  gray400: '#bbbbbb',
  gray300: '#dddddd',
  /** border */
  gray200: '#e8e8e8',
  /** background */
  gray100: '#f3f3f3',
  /** background */
  white: 'white',

  blue800: '#004765',
  blue700: '#006f9f',
  blue600: '#009ee2',
  /** primary color */
  blue500: '#00b2ff',
  blue400: '#80d9ff',
  blue300: '#bbebff',
  blue200: '#d8f3ff',
  blue100: '#eaf9ff',

  /** dark red */
  red300: '#c50000',
  /** red */
  red200: '#ea0000',
  /** light red */
  red100: '#fff2f2',

  green: '#2FC56E',
} as const;

const text = {
  large: {
    fontSize: '18px',
    lineHeight: '28px',
  },
  /** default text font setting */
  medium: {
    fontSize: '16px',
    lineHeight: '24px',
  },
  small: {
    fontSize: '14px',
    lineHeight: '20px',
  },
  xSmall: {
    fontSize: '12px',
    lineHeight: '20px',
  },
} as const;

const heading = {
  xxLarge: {
    fontSize: '40px',
    lineHeight: '52px',
  },
  xLarge: {
    fontSize: '36px',
    lineHeight: '44px',
  },
  large: {
    fontSize: '32px',
    lineHeight: '40px',
  },
  /** default heading font setting */
  medium: {
    fontSize: '28px',
    lineHeight: '36px',
  },
  small: {
    fontSize: '24px',
    lineHeight: '32px',
  },
  xSmall: {
    fontSize: '20px',
    lineHeight: '28px',
  },
} as const;

const spacer = {
  spacing0: '0',
  spacing1: '4px',
  spacing2: '8px',
  spacing3: '16px',
  spacing4: '24px',
  spacing5: '32px',
  spacing6: '48px',
  spacing7: '64px',
  spacing8: '96px',
  spacing9: '128px',
} as const;

const borderRadius = {
  small: '4px',
  /** default border radius */
  medium: '8px',
  large: '16px',
} as const;

const boxShadow = {
  shadow1: '0px 0px 0px 1px rgba(0, 0, 0, 0.05)',
  shadow2: '0px 1px 2px 0px rgba(0, 0, 0, 0.05)',
  shadow3: '0px 1px 2px 0px rgba(0, 0, 0, 0.06), 0px 1px 3px 0px rgba(0, 0, 0, 0.10)',
  shadow4: '0px 2px 4px -1px rgba(0, 0, 0, 0.06), 0px 4px 6px -1px rgba(0, 0, 0, 0.10)',
  shadow5: '1px 2px 4px 0px rgba(0, 0, 0, 0.15)',
  shadow6: '0px 4px 6px -2px rgba(0, 0, 0, 0.05), 0px 10px 15px -3px rgba(0, 0, 0, 0.10)',
  shadow7: '0px 10px 10px -5px rgba(0, 0, 0, 0.04), 0px 20px 25px -5px rgba(0, 0, 0, 0.10)',
  shadow8: '0px 0px 5px 0px rgba(0, 0, 0, 0.15)',
  shadow9: '0px 0px 10px 0px rgba(0, 0, 0, 0.20)',
  shadow10: '0px 2px 4px 0px rgba(0, 0, 0, 0.06) inset',
} as const;

const zIndex = {
  overlayPeak: 4,
  overlayTop: 3,
  overlayMiddle: 2,
  overlayBottom: 1,
} as const;

export const Theme = {
  color,
  text,
  heading,
  spacer,
  borderRadius,
  boxShadow,
  zIndex,
};
