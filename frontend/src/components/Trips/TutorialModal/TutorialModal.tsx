import Tutorial1SVG from '@assets/svg/tutorial1.svg';
import Tutorial2SVG from '@assets/svg/tutorial2.svg';
import Tutorial3SVG from '@assets/svg/tutorial3.svg';
import Tutorial4SVG from '@assets/svg/tutorial4.svg';
import { Button, Flex, Modal, SVGCarousel, useOverlay } from 'hang-log-design-system';
import { useEffect } from 'react';

import { boxStyling, buttonStyling } from '@components/trips/TutorialModal/TutorialModal.style';

const TutorialModal = () => {
  const { isOpen: isTutorialOpen, open: openTutorial, close: closeTutorial } = useOverlay();

  useEffect(() => {
    openTutorial();
  }, []);

  return (
    <Modal isOpen={isTutorialOpen} closeModal={closeTutorial} hasCloseButton={true}>
      <Flex css={boxStyling}>
        <SVGCarousel
          width={385}
          height={412}
          images={[Tutorial1SVG, Tutorial2SVG, Tutorial3SVG, Tutorial4SVG]}
        />
        <Button variant="primary" css={buttonStyling} onClick={closeTutorial}>
          닫기
        </Button>
      </Flex>
    </Modal>
  );
};

export default TutorialModal;
