import { keyframes } from '@emotion/react';

export const spinnerRotation = keyframes`
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
`;

export const fadeIn = keyframes`
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
`;

export const fadeOut = keyframes`
  from {
    opacity: 1;
  }
  to {
    opacity: 0;
  }
`;

export const moveUp = keyframes`
  from {
    transform: translateY(50%);
  }
  to {
    transform: translateY(0%);
  }    
`;
