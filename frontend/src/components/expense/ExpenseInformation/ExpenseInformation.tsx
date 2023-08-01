import type { ExpenseData } from '@type/expense';
import { Badge, Box, Flex, Heading, Text, Theme } from 'hang-log-design-system';

import { formatDate } from '@utils/formatter';

import {
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
      <Box tag="section">
        <Flex styles={{ gap: Theme.spacer.spacing1 }}>
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
      </Box>
    </header>
  );
};

export default ExpenseInformation;
