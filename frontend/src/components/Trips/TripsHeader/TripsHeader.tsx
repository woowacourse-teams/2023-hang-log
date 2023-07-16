import TripsHeaderSVG from '@assets/svg/TripsHeader.svg';
import { Box, Center, Flex, Heading } from 'hang-log-design-system';

import { SVGBoxStyling } from './TripsHeader.style';

const TripsHeader = () => {
  return (
    <>
      <Flex styles={{ justify: 'space-between', width: '100%', gap: '200px' }}>
        <Box styles={{ width: '30%', height: '18%' }}>
          <Box
            style={{ height: '200px', paddingLeft: '50px', display: 'flex', alignItems: 'center' }}
          >
            <Heading size="large">라곤의 여행</Heading>
          </Box>
        </Box>
        <Box css={SVGBoxStyling} styles={{ width: '70%', height: '18%', paddingRight: '50px' }}>
          <TripsHeaderSVG style={{ width: '854px' }} />
        </Box>
      </Flex>
    </>
  );
};

export default TripsHeader;
