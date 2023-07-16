import Tutorial1SVG from '@assets/svg/tutorial1.svg';
import Tutorial2SVG from '@assets/svg/tutorial2.svg';
import Tutorial3SVG from '@assets/svg/tutorial3.svg';
import Tutorial4SVG from '@assets/svg/tutorial4.svg';
import { Button, Flex, Modal, SVGCarousel } from 'hang-log-design-system';
import { useCallback, useState } from 'react';

import {
  ModalCarouselBoxStyling,
  ModalCloseButtonStyling,
} from '@components/trips/TutorialModal/TutorialModal.style';

const TutorialModal = () => {
  const [modalStatus, setModalStatus] = useState(true);

  const handleModalClose = useCallback(() => {
    setModalStatus(false);
  }, []);

  return (
    <Modal isOpen={modalStatus} closeModal={handleModalClose} hasCloseButton={true}>
      <Flex css={ModalCarouselBoxStyling}>
        <SVGCarousel
          width={390}
          height={420}
          showArrows={true}
          images={[Tutorial1SVG, Tutorial2SVG, Tutorial3SVG, Tutorial4SVG]}
        />
        <Button variant="primary" css={ModalCloseButtonStyling} onClick={handleModalClose}>
          닫기
        </Button>
      </Flex>
    </Modal>
  );
};

export default TutorialModal;
