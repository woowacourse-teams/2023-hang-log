import TripsHeaderSVG from '@assets/svg/TripsHeader.svg';
import { Box, Center, Flex, Heading } from 'hang-log-design-system';

const TripsHeader = () => {
  return (
    <>
      <Flex styles={{ justify: 'space-between', width: '100%', gap: '200px' }}>
        <Box styles={{ width: '30%', height: '18%' }}>
          <Center>
            <Heading size="large">라곤의 여행</Heading>
          </Center>
        </Box>
        <Box styles={{ width: '70%', height: '18%' }}>
          <TripsHeaderSVG style={{ width: '100%' }} />
        </Box>
      </Flex>
    </>
  );
};

export default TripsHeader;
