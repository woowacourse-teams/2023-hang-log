import { useEffect } from 'react';

import { useRecoilValue } from 'recoil';

import {
  // Button,
  // Flex,
  // Modal,
  // SVGCarousel,
  SVGCarouselModal,
  useOverlay,
} from 'hang-log-design-system';

// import {
//   boxStyling,
//   buttonStyling,
//   modalStyling,
// } from '@components/trips/TutorialModal/TutorialModal.style';
import { mediaQueryMobileState } from '@store/mediaQuery';

import Tutorial1SVG from '@assets/svg/tutorial1.svg';
import Tutorial2SVG from '@assets/svg/tutorial2.svg';
import Tutorial3SVG from '@assets/svg/tutorial3.svg';
import Tutorial4SVG from '@assets/svg/tutorial4.svg';

const TutorialModal = () => {
  const { isOpen: isTutorialOpen, open: openTutorial, close: closeTutorial } = useOverlay();
  const isMobile = useRecoilValue(mediaQueryMobileState);

  useEffect(() => {
    openTutorial();
  }, [openTutorial]);

  return (
    <SVGCarouselModal
      modalWidth={isMobile ? window.innerWidth - 32 - 48 : 385}
      modalHeight={isMobile ? window.innerWidth : 412}
      isOpen={isTutorialOpen}
      closeModal={closeTutorial}
      carouselWidth={isMobile ? window.innerWidth - 32 - 48 : 385}
      carouselHeight={isMobile ? window.innerWidth : 412}
      carouselImages={[Tutorial1SVG, Tutorial2SVG, Tutorial3SVG, Tutorial4SVG]}
      showArrows
      showDots
      buttonGap={isMobile ? 0 : 48}
    />
  );
};

export default TutorialModal;
