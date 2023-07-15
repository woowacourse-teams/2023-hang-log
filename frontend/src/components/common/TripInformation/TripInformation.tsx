import DefaultThumbnail from '@assets/png/trip-information_default-thumbnail.png';
import type { TripData } from '@type/trip';
import { Badge, Button, Flex, Heading, Text, Theme } from 'hang-log-design-system';

import { formatDate } from '@utils/formatter';

import {
  buttonContainerStyling,
  descriptionStyling,
  editButtonStyling,
  imageWrapperStyling,
  sectionStyling,
  titleStyling,
} from '@components/common/TripInformation/TripInformation.style';

type TripInformationProps = Omit<TripData, 'dayLogs'>;

const TripInformation = ({ ...information }: TripInformationProps) => {
  return (
    <section css={sectionStyling}>
      <div css={imageWrapperStyling}>
        <div />
        <img src={information.imageUrl ?? DefaultThumbnail} alt="여행 대표 이미지" />
      </div>
      <div>
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
      </div>
      <div css={buttonContainerStyling}>
        {/* 수정 모드일 때만 보인다 */}
        <Button css={editButtonStyling} variant="outline" size="small">
          여행 정보 수정
        </Button>
        <Button variant="primary" size="small">
          저장
        </Button>
      </div>
    </section>
  );
};

export default TripInformation;
