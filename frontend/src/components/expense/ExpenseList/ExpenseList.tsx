import type { ExpenseItemData } from '@type/expense';
import { Box, Button, Divider, Flex, Heading, Text, Theme } from 'hang-log-design-system';
import { Fragment } from 'react';
import { useNavigate } from 'react-router-dom';

import ExpenseItem from '@components/expense/ExpenseItem/ExpenseItem';
import {
  addItemButtonStyling,
  containerStyling,
  emptyTextStyling,
} from '@components/expense/ExpenseList/ExpenseList.style';

interface ExpenseListProps {
  items: ExpenseItemData[];
}

const ExpenseList = ({ items }: ExpenseListProps) => {
  return (
    <Flex
      tag="ol"
      styles={{ direction: 'column', gap: Theme.spacer.spacing4 }}
      css={containerStyling}
    >
      {items.map((item) => (
        <Fragment key={item.id}>
          <ExpenseItem {...item} />
          <Divider />
        </Fragment>
      ))}
    </Flex>
  );
};

export default ExpenseList;

ExpenseList.Empty = ({ tripId }: { tripId: number }) => {
  // eslint-disable-next-line react-hooks/rules-of-hooks
  const navigate = useNavigate();

  return (
    <Box styles={{ marginTop: Theme.spacer.spacing4 }}>
      <Heading size="xSmall">아직 추가된 일정 기록이 없습니다!</Heading>
      <Text css={emptyTextStyling}>
        여행하면서 갔던 장소, 사용했던 경비와 같은 여행 일정 기록들을 추가해 보세요.
      </Text>
      <Button
        css={addItemButtonStyling}
        type="button"
        variant="primary"
        onClick={() => navigate(`/trip-edit/${tripId}`)}
      >
        여행 수정하기
      </Button>
    </Box>
  );
};
