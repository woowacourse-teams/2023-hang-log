import { useDayLogOrderMutation } from '@/hooks/api/useDayLogOrderMutation';
import type { TripItemData } from '@type/tripItem';
import { Button, Divider, Heading, Text } from 'hang-log-design-system';
import { Fragment, useEffect } from 'react';

import { useDragAndDrop } from '@hooks/common/useDragAndDrop';

import TripItem from '@components/common/TripItem/TripItem';
import {
  addItemButtonStyling,
  containerStyling,
  emptyTextStyling,
} from '@components/common/TripItemList/TripItemList.style';

interface TripItemListProps {
  tripId: number;
  dayLogId: number;
  tripItems: TripItemData[];
}

const TripItemList = ({ tripId, dayLogId, tripItems }: TripItemListProps) => {
  const dayLogOrderMutation = useDayLogOrderMutation();
  const handlePositionChange = async (newItems: TripItemData[]) => {
    const itemIds = newItems.map((item) => item.id);

    dayLogOrderMutation.mutate({ tripId, dayLogId, itemIds });
  };

  const { items, handleItemsUpdate, handleDragStart, handleDragEnter, handleDragEnd } =
    useDragAndDrop(tripItems, handlePositionChange);

  useEffect(() => {
    handleItemsUpdate(tripItems);
  }, [tripItems]);

  // useEffect를 안 사용하고 롤백할 수 있는 방법??
  useEffect(() => {
    if (dayLogOrderMutation.isError) {
      handleItemsUpdate(tripItems);
    }
  }, [dayLogOrderMutation.isError]);

  return (
    <ol css={containerStyling}>
      {items.map((item, index) => (
        <Fragment key={item.id}>
          <TripItem
            tripId={tripId}
            onDragStart={handleDragStart(index)}
            onDragEnter={handleDragEnter(index)}
            onDragEnd={handleDragEnd}
            {...item}
          />
          <Divider />
        </Fragment>
      ))}
    </ol>
  );
};

TripItemList.Empty = ({ openAddModal }: { openAddModal: () => void }) => {
  return (
    <>
      <Heading size="xSmall">아직 추가된 일정 기록이 없습니다!</Heading>
      <Text css={emptyTextStyling}>
        여행하면서 갔던 장소, 사용했던 경비와 같은 여행 일정 기록들을 추가해 보세요.
      </Text>
      {/* 클릭하면 일정 추가 모달 열기 */}
      <Button css={addItemButtonStyling} type="button" variant="primary" onClick={openAddModal}>
        일정 기록 추가하기
      </Button>
    </>
  );
};

export default TripItemList;
