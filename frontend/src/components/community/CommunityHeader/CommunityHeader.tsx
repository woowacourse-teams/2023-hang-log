import { Flex, Heading, Theme } from 'hang-log-design-system';

import { headingEmphasisStyling } from '@components/community/CommunityHeader/CommunityHeader.style';
import { headingStyling, imageStyling } from '@components/trips/TripsHeader/TripsHeader.style';

import CommunityHeaderImage from '@assets/svg/TripsHeader.svg';

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
        우리의 여행 기록, <span css={headingEmphasisStyling}>행록</span>
      </Heading>
      <CommunityHeaderImage css={imageStyling} />
    </Flex>
  );
};

export default CommunityHeader;
