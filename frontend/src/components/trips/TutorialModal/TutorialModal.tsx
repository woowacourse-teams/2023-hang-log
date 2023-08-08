import Tutorial1SVG from '@assets/svg/tutorial1.svg';
import Tutorial2SVG from '@assets/svg/tutorial2.svg';
import Tutorial3SVG from '@assets/svg/tutorial3.svg';
import Tutorial4SVG from '@assets/svg/tutorial4.svg';
import { mediaQueryMobileState } from '@store/mediaQuery';
import { Button, Flex, Modal, SVGCarousel, useOverlay } from 'hang-log-design-system';
import { useEffect } from 'react';
import { useRecoilValue } from 'recoil';

import {
  boxStyling,
  buttonStyling,
  modalStyling,
} from '@components/trips/TutorialModal/TutorialModal.style';

const TutorialModal = () => {
  const { isOpen: isTutorialOpen, open: openTutorial, close: closeTutorial } = useOverlay();
  const isMobile = useRecoilValue(mediaQueryMobileState);

  useEffect(() => {
    openTutorial();
  }, [openTutorial]);

  return (
    <Modal isOpen={isTutorialOpen} closeModal={closeTutorial} hasCloseButton css={modalStyling}>
      <Flex css={boxStyling}>
        <SVGCarousel
          width={isMobile ? 340 : 385}
          height={isMobile ? 353 : 412}
          images={[Tutorial1SVG, Tutorial2SVG, Tutorial3SVG, Tutorial4SVG]}
        />
      </Flex>
      <Button variant="primary" css={buttonStyling} onClick={closeTutorial}>
        닫기
      </Button>
    </Modal>
  );
};

export default TutorialModal;
