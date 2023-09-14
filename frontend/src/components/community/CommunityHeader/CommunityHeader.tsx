import { Flex, Heading, Theme } from 'hang-log-design-system';

import { headingStyling, imageStyling } from '@components/trips/TripsHeader/TripsHeader.style';

import CommunityHeaderImage from '@assets/svg/TripsHeader.svg';

import { headingEmphasisStyling } from './CommunityHeader.style';

const CommunityHeader = () => {
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
        나만의 여행 기록, <span css={headingEmphasisStyling}>행록</span>
      </Heading>
      <CommunityHeaderImage css={imageStyling} />
    </Flex>
  );
};

export default CommunityHeader;
