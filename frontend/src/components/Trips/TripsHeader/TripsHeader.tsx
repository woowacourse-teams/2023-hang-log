import TripsHeaderSVG from '@assets/svg/TripsHeader.svg';
import { Box, Flex, Heading } from 'hang-log-design-system';

const TripsHeader = () => {
  return (
    <>
      <Flex styles={{ justify: 'space-between', width: '100%', gap: '200px' }}>
        <Box>
          <Box
            style={{ height: '200px', paddingLeft: '50px', display: 'flex', alignItems: 'center' }}
          >
            <Heading size="large">라곤의 여행</Heading>
          </Box>
        </Box>
        <Flex styles={{ width: '70%', height: '18%', paddingRight: '50px', justify: 'right' }}>
          <TripsHeaderSVG style={{ width: '854px' }} />
        </Flex>
      </Flex>
    </>
  );
};

export default TripsHeader;
