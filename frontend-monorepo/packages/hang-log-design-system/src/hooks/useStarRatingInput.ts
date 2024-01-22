import { useCallback, useRef, useState } from 'react';

import { useDebounce } from '@hooks/useDebounce';

type InitialRateType = 0 | 0.5 | 1 | 1.5 | 2 | 2.5 | 3 | 3.5 | 4 | 4.5 | 5;

export const useStarRatingInput = (initialRate: InitialRateType, onClick?: CallableFunction) => {
  const [tempStarRate, setStarRate] = useState(initialRate);
  const starRate = useDebounce(tempStarRate, 50);

  const [tempHookStarRate, setHookStarRate] = useState(initialRate);
  const hookStarRate = useDebounce(tempHookStarRate, 50);

  const [tempPrevStarRate, setPrevStarRate] = useState(initialRate);
  const prevStarRate = useDebounce(tempPrevStarRate, 50);

  const hoverState = useRef(false);

  const handleStarClick = useCallback(
    (index: number) => {
      const newRate = ((index + 1) / 2) as InitialRateType;

      if (hookStarRate === newRate) {
        setStarRate(0);
        setPrevStarRate(0);
        setHookStarRate(0);
        onClick?.(0);
      } else {
        setStarRate(newRate);
        setHookStarRate(newRate);
        setPrevStarRate(newRate);
        onClick?.(newRate);
      }
    },
    [hookStarRate, onClick]
  );

  const handleStarHover = useCallback((index: number) => {
    const newRate = ((index + 1) / 2) as InitialRateType;

    setStarRate(newRate);
    hoverState.current = true;
  }, []);

  const handleStarHoverOut = useCallback(() => {
    if (hoverState.current) {
      setStarRate(prevStarRate as InitialRateType);
    }

    hoverState.current = false;
  }, [prevStarRate]);

  return {
    starRate,
    handleStarClick,
    handleStarHover,
    handleStarHoverOut,
  };
};
