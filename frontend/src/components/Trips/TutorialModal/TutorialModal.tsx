import Tutorial1SVG from '@assets/svg/tutorial1.svg';
import Tutorial2SVG from '@assets/svg/tutorial2.svg';
import Tutorial3SVG from '@assets/svg/tutorial3.svg';
import Tutorial4SVG from '@assets/svg/tutorial4.svg';
import { Button, Flex, Modal, SVGCarousel, useOverlay } from 'hang-log-design-system';
import { useCallback, useEffect } from 'react';

import {
  ModalCarouselBoxStyling,
  ModalCloseButtonStyling,
} from '@components/trips/TutorialModal/TutorialModal.style';

const TutorialModal = () => {
  const { isOpen, open, close } = useOverlay();

  useEffect(() => {
    open();
  }, []);

  return (
    <Modal isOpen={isOpen} closeModal={close} hasCloseButton={true}>
      <Flex css={ModalCarouselBoxStyling}>
        <SVGCarousel
          width={385}
          height={412}
          images={[Tutorial1SVG, Tutorial2SVG, Tutorial3SVG, Tutorial4SVG]}
        />
        <Button variant="primary" css={ModalCloseButtonStyling} onClick={close}>
          닫기
        </Button>
      </Flex>
    </Modal>
  );
};

export default TutorialModal;
