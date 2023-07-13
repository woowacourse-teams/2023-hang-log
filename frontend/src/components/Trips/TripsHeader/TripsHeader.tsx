import TripsHeaderSVG from '@assets/svg/TripsHeader.svg';
import { Box, Center, Flex, Heading } from 'hang-log-design-system';

const TripsHeader = () => {
  return (
    <>
      <Flex styles={{ justify: 'space-around', width: '100%' }}>
        <Box styles={{ width: '30%', height: '250px' }}>
          <Center>
            <Heading size="large">라곤의 여행</Heading>
          </Center>
        </Box>
        <TripsHeaderSVG />
      </Flex>
    </>
  );
};

export default TripsHeader;
