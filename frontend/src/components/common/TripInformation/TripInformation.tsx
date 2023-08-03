import DefaultThumbnail from '@assets/png/trip-information_default-thumbnail.png';
import type { TripData } from '@type/trip';
import { Badge, Box, Flex, Heading, Text, Theme, useOverlay } from 'hang-log-design-system';
import { memo } from 'react';

import { formatDate } from '@utils/formatter';

import {
  badgeStyling,
  badgeWrapperStyling,
  buttonContainerStyling,
  descriptionStyling,
  imageWrapperStyling,
  sectionStyling,
  titleStyling,
} from '@components/common/TripInformation/TripInformation.style';
import TripInfoEditModal from '@components/trip/TripInfoEditModal/TripInfoEditModal';

import TripButtons from './TripButtons/TripButtons';
import TripEditButtons from './TripEditButtons/TripEditButtons';

interface TripInformationProps extends Omit<TripData, 'dayLogs'> {
  isEditable?: boolean;
}

const TripInformation = ({ isEditable = true, ...information }: TripInformationProps) => {
  const { isOpen: isEditModalOpen, close: closeEditModal, open: openEditModal } = useOverlay();

  return (
    <>
      <header css={sectionStyling}>
        <Box css={imageWrapperStyling}>
          <div />
          <img src={information.imageUrl ?? DefaultThumbnail} alt="여행 대표 이미지" />
        </Box>
        <Box tag="section">
          <Flex styles={{ gap: Theme.spacer.spacing1, wrap: 'wrap' }} css={badgeWrapperStyling}>
            {information.cities.map(({ id, name }) => (
              <Badge key={id} css={badgeStyling}>
                {name}
              </Badge>
            ))}
          </Flex>
          <Heading css={titleStyling} size="large">
            {information.title}
          </Heading>
          <Text>
            {formatDate(information.startDate)} - {formatDate(information.endDate)}
          </Text>
          <Text css={descriptionStyling} size="small">
            {information.description}
          </Text>
        </Box>
        <Box css={buttonContainerStyling}>
          {isEditable ? (
            <TripEditButtons tripId={information.id} openEditModal={openEditModal} />
          ) : (
            <TripButtons tripId={information.id} />
          )}
        </Box>
      </header>
      {isEditModalOpen && (
        <TripInfoEditModal isOpen={isEditModalOpen} onClose={closeEditModal} {...information} />
      )}
    </>
  );
};

export default memo(TripInformation);
