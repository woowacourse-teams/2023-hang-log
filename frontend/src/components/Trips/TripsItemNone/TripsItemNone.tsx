import { Box, Button, Heading, Text } from 'hang-log-design-system';

import { TripsItemNoneBoxStyling } from '@components/trips/TripsItemNone/TripsItemNone.style';
import TutorialModal from '@components/trips/TutorialModal/TutorialModal';

const TripsItemNone = () => {
  return (
    <Box css={TripsItemNoneBoxStyling}>
      <Heading size="xSmall">아직 기록된 여행이 없습니다!</Heading>
      <Text style={{ padding: '8px 0 16px' }}>
        여행 가방에 쌓인 먼지를 털어내고 여행을 기록해 보세요.
      </Text>
      <Button variant="primary" size="medium">
        여행 기록하기
      </Button>
      <TutorialModal />
    </Box>
  );
};

export default TripsItemNone;
