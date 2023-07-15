import type { TripItemData } from '@type/tripItem';
import { Button, Divider, Heading, Text } from 'hang-log-design-system';
import { Fragment } from 'react';

import TripItem from '@components/common/TripItem/TripItem';
import {
  addItemButtonStyling,
  containerStyling,
  emptyTextStyling,
} from '@components/common/TripItemList/TripItemList.style';

interface TripItemListProps {
  items: TripItemData[];
}

const TripItemList = ({ items }: TripItemListProps) => {
  return (
    <ol css={containerStyling}>
      {items.map((item) => (
        <Fragment key={item.id}>
          <TripItem {...item} />
          <Divider />
        </Fragment>
      ))}
    </ol>
  );
};

TripItemList.Empty = () => {
  return (
    <>
      <Heading size="xSmall">아직 추가된 일정 기록이 없습니다!</Heading>
      <Text css={emptyTextStyling}>
        여행하면서 갔던 장소, 사용했던 경비와 같은 여행 일정 기록들을 추가해 보세요.
      </Text>
      {/* 클릭하면 일정 추가 모달 열기 */}
      <Button css={addItemButtonStyling} type="button" variant="primary">
        일정 기록 추가하기
      </Button>
    </>
  );
};

export default TripItemList;
