import DefaultThumbnail from '@assets/png/trip-information_default-thumbnail.png';
import type { TripData } from '@type/trip';
import { Badge, Box, Button, Flex, Heading, Text, Theme, useOverlay } from 'hang-log-design-system';
import { memo } from 'react';

import { formatDate } from '@utils/formatter';

import {
  buttonContainerStyling,
  descriptionStyling,
  editButtonStyling,
  imageWrapperStyling,
  sectionStyling,
  titleStyling,
} from '@components/common/TripInformation/TripInformation.style';
import TripInfoEditModal from '@components/trip/TripInfoEditModal/TripInfoEditModal';

type TripInformationProps = Omit<TripData, 'dayLogs'>;

const TripInformation = ({ ...information }: TripInformationProps) => {
  const { isOpen: isEditModalOpen, close: closeEditModal, open: openEditModal } = useOverlay();

  return (
    <>
      <header css={sectionStyling}>
        <Box css={imageWrapperStyling}>
          <div />
          <img src={information.imageUrl ?? DefaultThumbnail} alt="여행 대표 이미지" />
        </Box>
        <Box tag="section">
          <Flex styles={{ gap: Theme.spacer.spacing1 }}>
            {information.cities.map(({ id, name }) => (
              <Badge key={id}>{name}</Badge>
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
          {/* 수정 모드일 때만 보인다 */}
          <Button onClick={openEditModal} css={editButtonStyling} variant="outline" size="small">
            여행 정보 수정
          </Button>
          {/* TODO : 클릭하면 읽기 전용 페이지로 이동 i.e. /trip/1 */}
          <Button variant="primary" size="small">
            저장
          </Button>
        </Box>
      </header>
      {isEditModalOpen && (
        <TripInfoEditModal isOpen={isEditModalOpen} onClose={closeEditModal} {...information} />
      )}
    </>
  );
};

export default memo(TripInformation);
