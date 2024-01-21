import type { KeyboardEvent, MouseEvent, TouchEvent } from 'react';
import { useRef, useState } from 'react';

import { limitToRange } from '@utils/number';

export const useImageCarousel = (width: number, slideLength: number) => {
  const [currentPosition, setCurrentPosition] = useState(0);
  const [translateX, setTranslateX] = useState(0);
  const [animate, setAnimate] = useState(false);
  const sliderRef = useRef<HTMLDivElement>(null);

  const handleDragChange = (offsetX: number) => {
    setTranslateX(limitToRange(offsetX, -width, width));
  };

  const handleDragEnd = (offsetX: number) => {
    const maxPosition = slideLength - 1;

    if (offsetX < -50) setCurrentPosition(limitToRange(currentPosition + 1, 0, maxPosition));
    if (offsetX > 50) setCurrentPosition(limitToRange(currentPosition - 1, 0, maxPosition));

    setAnimate(true);
    setTranslateX(0);
  };

  const handleSliderNavigationClick =
    (position: number) => (event: MouseEvent<HTMLButtonElement>) => {
      event.stopPropagation();

      if (position < 0 || position >= slideLength) return;

      setCurrentPosition(position);
      setAnimate(true);
      setTranslateX(0);
    };

  const handleSliderNavigationEnterKeyPress =
    (position: number) => (event: KeyboardEvent<HTMLElement>) => {
      if (event.key === 'Enter') {
        handleSliderNavigationClick(position);
      }
    };

  const handlerSliderMoueDown = (clickEvent: MouseEvent<HTMLDivElement>) => {
    const handleMouseMove = (moveEvent: globalThis.MouseEvent) => {
      const offsetX = moveEvent.pageX - clickEvent.pageX;
      handleDragChange(offsetX);
    };

    const handleMouseUp = (moveEvent: globalThis.MouseEvent) => {
      const offsetX = moveEvent.pageX - clickEvent.pageX;
      handleDragEnd(offsetX);

      sliderRef.current?.removeEventListener('mousemove', handleMouseMove);
    };

    sliderRef.current?.addEventListener('mousemove', handleMouseMove);
    sliderRef.current?.addEventListener('mouseup', handleMouseUp, { once: true });
  };

  const handleSliderTouchStart = (touchEvent: TouchEvent<HTMLDivElement>) => {
    const handleTouchMove = (moveEvent: globalThis.TouchEvent) => {
      /** 모바일에서 드래그할 떄 스크롤하지 못하도록 막아준다 */
      if (moveEvent.cancelable) moveEvent.preventDefault();

      const offset = moveEvent.touches[0].pageX - touchEvent.touches[0].pageX;
      handleDragChange(offset);
    };

    const handleTouchEnd = (moveEvent: globalThis.TouchEvent) => {
      const offset = moveEvent.changedTouches[0].pageX - touchEvent.changedTouches[0].pageX;
      handleDragEnd(offset);

      sliderRef.current?.removeEventListener('touchmove', handleTouchMove);
    };

    sliderRef.current?.addEventListener('touchmove', handleTouchMove, { passive: false });
    sliderRef.current?.addEventListener('touchend', handleTouchEnd, { once: true });
  };

  const handleSliderTransitionEnd = () => {
    setAnimate(false);
  };

  return {
    sliderRef,
    animate,
    currentPosition,
    translateX,
    handleSliderNavigationClick,
    handleSliderNavigationEnterKeyPress,
    handlerSliderMoueDown,
    handleSliderTouchStart,
    handleSliderTransitionEnd,
  };
};
