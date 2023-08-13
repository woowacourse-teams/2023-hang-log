import { useRecoilValue } from 'recoil';

import { Badge, Box, Heading, Text } from 'hang-log-design-system';

import {
  badgeWrapperStyling,
  sectionStyling,
  titleStyling,
} from '@components/expense/ExpenseInformation/ExpenseInformation.style';

import { mediaQueryMobileState } from '@store/mediaQuery';

import { formatDate } from '@utils/formatter';

import type { ExpenseData } from '@type/expense';

interface ExpenseInformationProps
  extends Pick<ExpenseData, 'title' | 'startDate' | 'endDate' | 'cities'> {
  tripId: number;
}

const ExpenseInformation = ({ ...information }: ExpenseInformationProps) => {
  const isMobile = useRecoilValue(mediaQueryMobileState);

  return (
    <header css={sectionStyling}>
      <Box css={badgeWrapperStyling}>
        {information.cities.map(({ id, name }) => (
          <Badge key={id}>{name}</Badge>
        ))}
      </Box>
      <Heading css={titleStyling} size={isMobile ? 'medium' : 'large'}>
        {information.title}
      </Heading>
      <Text>
        {formatDate(information.startDate)} - {formatDate(information.endDate)}
      </Text>
    </header>
  );
};

export default ExpenseInformation;
