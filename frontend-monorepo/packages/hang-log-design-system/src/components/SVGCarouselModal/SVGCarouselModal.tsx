/* eslint-disable jsx-a11y/no-static-element-interactions */
import LeftIcon from '@assets/svg/left-icon.svg';
import RightIcon from '@assets/svg/right-icon.svg';

import { useImageCarousel } from '@hooks/useImageCarousel';

import Box from '@components/Box/Box';
import Button from '@components/Button/Button';
import Flex from '@components/Flex/Flex';
import Modal from '@components/Modal/Modal';
import {
  boxStyling,
  buttonStyling,
  dotStyling,
  getButtonContainerStyling,
  getDotsWrapperStyling,
  getSVGWrapperStyling,
  getSliderContainerStyling,
  leftButtonStyling,
  rightButtonStyling,
  sliderWrapperStyling,
} from '@components/SVGCarouselModal/SVGCarouselModal.style';

export interface SVGCarouselModalProps {
  modalWidth: number;
  modalHeight: number;
  isOpen: boolean;
  closeModal: () => void;
  carouselWidth: number;
  carouselHeight: number;
  carouselImages: React.FC<React.SVGProps<SVGSVGElement>>[];
  buttonGap?: number;
  showArrows?: boolean;
  showDots?: boolean;
  showNavigationOnHover?: boolean;
}

const SVGCarouselModal = ({
  modalWidth,
  modalHeight,
  isOpen,
  closeModal,
  carouselWidth,
  carouselHeight,
  carouselImages,
  buttonGap = 0,
  showArrows = false,
  showDots = false,
  showNavigationOnHover = false,
}: SVGCarouselModalProps) => {
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
  } = useImageCarousel(carouselWidth, carouselImages.length);

  return (
    <Modal isOpen={isOpen} closeModal={closeModal} hasCloseButton>
      <Flex css={boxStyling(modalWidth, modalHeight)}>
        <Box css={sliderWrapperStyling}>
          <div
            ref={sliderRef}
            css={getSliderContainerStyling(currentPosition, carouselWidth, translateX, animate)}
            onMouseDown={handlerSliderMoueDown}
            onTouchStart={handleSliderTouchStart}
            onTransitionEnd={handleSliderTransitionEnd}
          >
            {carouselImages.map((SVG, index) => (
              // eslint-disable-next-line react/no-array-index-key
              <div key={index} css={getSVGWrapperStyling(carouselWidth, carouselHeight)}>
                <SVG />
              </div>
            ))}
          </div>
        </Box>
      </Flex>
      <Button variant="primary" css={buttonStyling(buttonGap)} onClick={closeModal}>
        닫기
      </Button>
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
          {Array.from({ length: carouselImages.length }, (_, index) => (
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
    </Modal>
  );
};

export default SVGCarouselModal;
