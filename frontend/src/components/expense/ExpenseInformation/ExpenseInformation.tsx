import { mediaQueryMobileState } from '@store/mediaQuery';
import type { ExpenseData } from '@type/expense';
import { Badge, Box, Heading, Text } from 'hang-log-design-system';
import { useRecoilValue } from 'recoil';

import { formatDate } from '@utils/formatter';

import {
  badgeWrapperStyling,
  sectionStyling,
  titleStyling,
} from '@components/expense/ExpenseInformation/ExpenseInformation.style';

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
