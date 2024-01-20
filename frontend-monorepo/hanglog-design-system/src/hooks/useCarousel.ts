import { useRef, useState } from 'react';
import type { MouseEvent } from 'react';
import { flushSync } from 'react-dom';

const useCarousel = (itemLength: number) => {
  const [viewIndex, setViewIndex] = useState(0);
  const carouselBoxRef = useRef<HTMLDivElement | null>(null);
  const itemRef = useRef<HTMLDivElement | null>(null);

  const handleMoveImage = (imageNumber: number) => {
    if (itemRef.current) {
      flushSync(() => {
        setViewIndex(imageNumber);
      });

      itemRef.current.scrollIntoView({
        behavior: 'smooth',
        block: 'nearest',
        inline: 'center',
      });
    }
  };

  const handleClickLeft = (e: MouseEvent<HTMLButtonElement>) => {
    e.stopPropagation();
    if (itemRef.current) {
      flushSync(() => {
        if (viewIndex === 0) setViewIndex(0);
        else setViewIndex(viewIndex - 1);
      });

      itemRef.current.scrollIntoView({
        behavior: 'smooth',
        block: 'nearest',
        inline: 'center',
      });
    }
  };

  const handleClickRight = (e: MouseEvent<HTMLButtonElement>) => {
    e.stopPropagation();
    if (itemRef.current) {
      flushSync(() => {
        if (viewIndex === itemLength - 1) setViewIndex(viewIndex);
        else setViewIndex(viewIndex + 1);
      });

      itemRef.current.scrollIntoView({
        behavior: 'smooth',
        block: 'nearest',
        inline: 'center',
      });
    }
  };

  return {
    viewIndex,
    itemRef,
    carouselBoxRef,
    handleMoveImage,
    handleClickLeft,
    handleClickRight,
  };
};

export default useCarousel;
