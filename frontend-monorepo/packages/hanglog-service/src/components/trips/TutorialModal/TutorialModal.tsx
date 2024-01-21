import { useEffect } from 'react';

import { useRecoilValue } from 'recoil';

import { Button, GeneralCarousel, Modal, useOverlay } from 'hang-log-design-system';

import {
  ItemStyling,
  buttonStyling,
  modalStyling,
} from '@components/trips/TutorialModal/TutorialModal.style';

import { mediaQueryMobileState } from '@store/mediaQuery';

import Tutorial1SVG from '@assets/svg/tutorial1.svg';
import Tutorial2SVG from '@assets/svg/tutorial2.svg';
import Tutorial3SVG from '@assets/svg/tutorial3.svg';
import Tutorial4SVG from '@assets/svg/tutorial4.svg';

const TutorialModal = () => {
  const { isOpen: isTutorialOpen, open: openTutorial, close: closeTutorial } = useOverlay();
  const isMobile = useRecoilValue(mediaQueryMobileState);
  const carouselImages = [Tutorial1SVG, Tutorial2SVG, Tutorial3SVG, Tutorial4SVG];

  useEffect(() => {
    openTutorial();
  }, [openTutorial]);

  return (
    <Modal css={modalStyling} isOpen={isTutorialOpen} closeModal={closeTutorial}>
      <GeneralCarousel
        showNavigationOnHover={false}
        width={isMobile ? window.innerWidth - 32 - 48 : 385}
        height={isMobile ? window.innerWidth : 412}
        length={4}
      >
        {carouselImages.map((Svg, index) => (
          <GeneralCarousel.Item index={index} key={crypto.randomUUID()}>
            <div
              css={ItemStyling(
                isMobile ? window.innerWidth - 32 - 48 : 385,
                isMobile ? window.innerWidth : 412
              )}
            >
              <Svg />
            </div>
          </GeneralCarousel.Item>
        ))}
      </GeneralCarousel>
      <Button type="button" variant="primary" css={buttonStyling(isMobile)} onClick={closeTutorial}>
        닫기
      </Button>
    </Modal>
  );
};

export default TutorialModal;
