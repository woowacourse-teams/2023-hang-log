import { useNavigate } from 'react-router-dom';

import { Box, Button, Heading, Text } from 'hang-log-design-system';

import {
  containerStyling,
  emptyBoxStyling,
  gridBoxStyling,
  headingStyling,
  textStyling,
} from '@components/trips/CommunityTripItemList/CommunityTripItemList.style';
import CommunityTripsItem from '@components/trips/CommunityTripsItem/CommunityTripsItem';
import TutorialModal from '@components/trips/TutorialModal/TutorialModal';

import { formatDate } from '@utils/formatter';

import type { RecommendedTripsData } from '@type/trips';

import { PATH } from '@constants/path';

const CommunityTripItemList = (data: Omit<RecommendedTripsData, 'title'>) => {
  const { trips } = data;

  return (
    <section css={containerStyling}>
      <Box tag="ol" css={gridBoxStyling}>
        {trips.map((trip, index) => {
          return (
            <CommunityTripsItem
              key={trip.id}
              id={trip.id}
              coverImage={trip.imageUrl}
              cityTags={trip.cities}
              itemName={trip.title}
              duration={`${formatDate(trip.startDate)} - ${formatDate(trip.endDate)}`}
              description={trip.description}
              nickName={trip.authorNickname}
              index={index}
              isLikeChecked={trip.isLike}
              likeCount={trip.likeCount}
            />
          );
        })}
      </Box>
    </section>
  );
};

CommunityTripItemList.Empty = () => {
  // eslint-disable-next-line react-hooks/rules-of-hooks
  const navigate = useNavigate();

  return (
    <Box tag="section" css={[emptyBoxStyling, containerStyling]}>
      <Heading css={headingStyling}>아직 기록된 여행이 없습니다!</Heading>
      <Text css={textStyling}>여행 가방에 쌓인 먼지를 털어내고 여행을 기록해 보세요.</Text>
      <Button variant="primary" onClick={() => navigate(PATH.CREATE_TRIP)}>
        여행 기록하기
      </Button>
      <TutorialModal />
    </Box>
  );
};

export default CommunityTripItemList;
