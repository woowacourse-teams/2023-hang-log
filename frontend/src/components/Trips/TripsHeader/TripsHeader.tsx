import TripsHeaderImage from '@assets/svg/TripsHeader.svg';
import { Box, Flex, Heading, Theme } from 'hang-log-design-system';

const TripsHeader = () => {
  return (
    <>
      <Flex
        styles={{
          justify: 'space-between',
          align: 'center',
          width: '100%',
          marginTop: Theme.spacer.spacing3,
        }}
      >
        <Box>
          <Flex styles={{ paddingLeft: '50px', align: 'center' }}>
            <Heading size="large">라곤의 여행</Heading>
          </Flex>
        </Box>
        <Flex styles={{ paddingRight: '50px' }}>
          <TripsHeaderImage />
        </Flex>
      </Flex>
    </>
  );
};

export default TripsHeader;
