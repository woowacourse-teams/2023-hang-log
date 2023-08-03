import { PATH } from '@constants/path';
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

  const goToEditPage = () => {
    navigate(PATH.EDIT_TRIP(tripId));
  };

  return (
    <Box styles={{ marginTop: Theme.spacer.spacing3 }}>
      <Heading size="xSmall">아직 추가된 비용 기록이 없습니다!</Heading>
      <Text css={emptyTextStyling}>여행하면서 사용했던 경비 기록을 추가해 보세요.</Text>
      <Button css={addItemButtonStyling} type="button" variant="primary" onClick={goToEditPage}>
        여행 수정하기
      </Button>
    </Box>
  );
};
