import type { ExpenseData } from '@type/expense';
import { Badge, Flex, Heading, Text, Theme } from 'hang-log-design-system';

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
  return (
    <header css={sectionStyling}>
      <Flex styles={{ gap: Theme.spacer.spacing1, wrap: 'wrap' }} css={badgeWrapperStyling}>
        {information.cities.map(({ id, name }) => (
          <Badge key={id}>{name}</Badge>
        ))}
      </Flex>
      <Heading css={titleStyling} size="large">
        {information.title}
      </Heading>
      <Text>
        {formatDate(information.startDate)} - {formatDate(information.endDate)}
      </Text>
    </header>
  );
};

export default ExpenseInformation;
