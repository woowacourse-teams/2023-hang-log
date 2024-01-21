/* eslint-disable jsx-a11y/no-static-element-interactions */
import LeftIcon from '@assets/svg/left-icon.svg';
import RightIcon from '@assets/svg/right-icon.svg';

import { useImageCarousel } from '@hooks/useImageCarousel';

import Box from '@components/Box/Box';
import {
  dotStyling,
  getButtonContainerStyling,
  getContainerStyling,
  getDotsWrapperStyling,
  getSliderContainerStyling,
  leftButtonStyling,
  rightButtonStyling,
  sliderWrapperStyling,
} from '@components/ImageCarousel/ImageCarousel.style';

export interface ImageCarouselProps {
  width: number;
  height: number;
  images: string[];
  isDraggable?: boolean;
  showArrows?: boolean;
  showDots?: boolean;
  showNavigationOnHover?: boolean;
  children: JSX.Element | JSX.Element[];
}

const ImageCarousel = ({
  width,
  height,
  images,
  isDraggable = false,
  showArrows = false,
  showDots = false,
  showNavigationOnHover = false,
  children,
}: ImageCarouselProps) => {
  const {
    sliderRef,
    animate,
    currentPosition,
    translateX,
    handleSliderNavigationClick,
    handleSliderNavigationEnterKeyPress,
    handlerSliderMoueDown,
    handleSliderTouchStart,
    handleSliderTransitionEnd,
  } = useImageCarousel(width, images.length);

  return (
    <Box
      tabIndex={-1}
      css={getContainerStyling(width, height)}
      className="image-carousel-container"
      aria-label="캐러셀"
    >
      <Box css={sliderWrapperStyling}>
        <div
          ref={sliderRef}
          css={getSliderContainerStyling(currentPosition, width, translateX, animate)}
          onMouseDown={isDraggable ? handlerSliderMoueDown : undefined}
          onTouchStart={isDraggable ? handleSliderTouchStart : undefined}
          onTransitionEnd={isDraggable ? handleSliderTransitionEnd : undefined}
        >
          {children}
        </div>
      </Box>
      {showArrows && images.length !== 1 && (
        <div css={getButtonContainerStyling(showNavigationOnHover)}>
          <button
            type="button"
            css={leftButtonStyling}
            aria-label="뒤로가기"
            onClick={handleSliderNavigationClick(currentPosition - 1)}
          >
            <LeftIcon />
          </button>
          <button
            type="button"
            css={rightButtonStyling}
            aria-label="앞으로가기"
            onClick={handleSliderNavigationClick(currentPosition + 1)}
          >
            <RightIcon />
          </button>
        </div>
      )}
      {showDots && (
        <div css={getDotsWrapperStyling(showNavigationOnHover)}>
          {Array.from({ length: images.length }, (_, index) => (
            <span
              role="button"
              key={index}
              tabIndex={0}
              css={dotStyling(currentPosition === index)}
              aria-label={`${index + 1}번 슬라이드로 이동`}
              onClick={handleSliderNavigationClick(index)}
              onKeyDown={handleSliderNavigationEnterKeyPress(index)}
            />
          ))}
        </div>
      )}
    </Box>
  );
};

export default ImageCarousel;
