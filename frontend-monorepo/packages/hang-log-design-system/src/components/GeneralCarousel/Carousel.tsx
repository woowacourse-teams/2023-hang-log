import LeftIcon from '@assets/svg/left-icon.svg';
import RightIcon from '@assets/svg/right-icon.svg';
import { createContext, useMemo } from 'react';
import type { PropsWithChildren } from 'react';

import useCarousel from '@hooks/useCarousel';

import Box from '@components/Box/Box';

import {
  buttonContainerStyling,
  containerStyling,
  leftButtonStyling,
  rightButtonStyling,
  sliderWrapperStyling,
} from './Carousel.style';
import CarouselItem from './CarouselItem';
import Dots from './Dots';

export interface CarouselProps extends PropsWithChildren {
  width: number;
  height: number;
  length: number;
  showNavigationOnHover?: boolean;
  showArrows?: boolean;
  showDots?: boolean;
  children?: JSX.Element | JSX.Element[];
}

export const CarouselContext = createContext<{
  viewIndex: number;
  width: number;
  height: number;
  itemRef: React.MutableRefObject<HTMLDivElement | null>;
} | null>(null);

const Carousel = ({
  width,
  height,
  length,
  showNavigationOnHover = true,
  showArrows = true,
  showDots = true,
  children,
}: CarouselProps) => {
  const { viewIndex, itemRef, carouselBoxRef, handleMoveImage, handleClickLeft, handleClickRight } =
    useCarousel(length);

  const context = useMemo(
    () => ({
      width,
      height,
      viewIndex,
      itemRef,
      carouselBoxRef,
      handleMoveImage,
      handleClickLeft,
      handleClickRight,
    }),
    [
      width,
      height,
      viewIndex,
      itemRef,
      carouselBoxRef,
      handleMoveImage,
      handleClickLeft,
      handleClickRight,
    ]
  );

  return (
    <CarouselContext.Provider value={context}>
      <div css={containerStyling(width, height)} ref={carouselBoxRef}>
        {showArrows && length !== 1 && (
          <div css={buttonContainerStyling(showNavigationOnHover)}>
            <button type="button" css={leftButtonStyling} onClick={handleClickLeft}>
              <LeftIcon />
            </button>
            <button type="button" css={rightButtonStyling} onClick={handleClickRight}>
              <RightIcon />
            </button>
          </div>
        )}

        {showDots && (
          <Dots imageLength={length} activeNumber={viewIndex} moveImage={handleMoveImage} />
        )}

        <Box css={sliderWrapperStyling(width, height)}>{children}</Box>
      </div>
    </CarouselContext.Provider>
  );
};

Carousel.Item = CarouselItem;

export default Carousel;
