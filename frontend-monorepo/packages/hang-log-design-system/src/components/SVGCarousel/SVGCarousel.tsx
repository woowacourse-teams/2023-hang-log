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
  getSVGWrapperStyling,
  getSliderContainerStyling,
  leftButtonStyling,
  rightButtonStyling,
  sliderWrapperStyling,
} from '@components/SVGCarousel/SVGCarousel.style';

export interface SVGCarouselProps {
  width: number;
  height: number;
  images: React.FC<React.SVGProps<SVGSVGElement>>[];
  showArrows?: boolean;
  showDots?: boolean;
  showNavigationOnHover?: boolean;
}

const SVGCarousel = ({
  width,
  height,
  images,
  showArrows = false,
  showDots = false,
  showNavigationOnHover = false,
}: SVGCarouselProps) => {
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
    <Box css={getContainerStyling(width, height)}>
      <Box css={sliderWrapperStyling}>
        <div
          ref={sliderRef}
          css={getSliderContainerStyling(currentPosition, width, translateX, animate)}
          onMouseDown={handlerSliderMoueDown}
          onTouchStart={handleSliderTouchStart}
          onTransitionEnd={handleSliderTransitionEnd}
        >
          {images.map((SVG, index) => (
            // eslint-disable-next-line react/no-array-index-key
            <div key={index} css={getSVGWrapperStyling(width, height)}>
              <SVG />
            </div>
          ))}
        </div>
      </Box>
      {showArrows && (
        <div css={getButtonContainerStyling(showNavigationOnHover)}>
          <button
            type="button"
            css={leftButtonStyling}
            onClick={handleSliderNavigationClick(currentPosition - 1)}
          >
            <LeftIcon />
          </button>
          <button
            type="button"
            css={rightButtonStyling}
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
              aria-label={`${index + 1}번 이미지로 이동`}
              onClick={handleSliderNavigationClick(index)}
              onKeyDown={handleSliderNavigationEnterKeyPress(index)}
            />
          ))}
        </div>
      )}
    </Box>
  );
};

export default SVGCarousel;
