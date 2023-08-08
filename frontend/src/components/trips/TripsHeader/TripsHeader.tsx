import TripsHeaderImage from '@assets/svg/TripsHeader.svg';
import { Flex, Heading, Theme } from 'hang-log-design-system';

import { headingStyling, imageStyling } from '@components/trips/TripsHeader/TripsHeader.style';

const TripsHeader = () => {
  return (
    <Flex
      tag="section"
      styles={{
        justify: 'space-between',
        align: 'center',
        width: '100%',
        marginTop: Theme.spacer.spacing3,
        marginBottom: Theme.spacer.spacing4,
      }}
    >
      <Heading css={headingStyling} size="large">
        라곤의 여행
      </Heading>
      <TripsHeaderImage css={imageStyling} />
    </Flex>
  );
};

export default TripsHeader;
