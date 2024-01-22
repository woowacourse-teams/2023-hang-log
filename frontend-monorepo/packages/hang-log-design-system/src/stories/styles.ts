import { css } from '@emotion/react';

import { Theme } from '../styles/Theme';

const containerStyle = css({
  display: 'flex',
  flexDirection: 'column',
  gap: '24px',
  alignItems: 'flex-start',

  width: '300px',
});

const informationStyle = css({
  display: 'flex',
  flexDirection: 'column',
  gap: '12px',

  '& > h6': {
    color: Theme.color.gray500,
    fontSize: '12px',
    fontWeight: 400,
    textTransform: 'uppercase',
  },
});

const containerWrapperStyle = css({
  position: 'absolute',
  top: '0',
  left: '0',
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'center',

  width: '100%',
  height: '100%',
  padding: '20px',
});

export { containerStyle, informationStyle, containerWrapperStyle };
