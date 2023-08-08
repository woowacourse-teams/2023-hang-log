import { PATH } from '@constants/path';
import { clickedMarkerIdState } from '@store/scrollFocus';
import type { TripItemData } from '@type/tripItem';
import { Button, Divider, Heading, Text } from 'hang-log-design-system';
import { Fragment, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import { sortByOrdinal } from '@utils/sort';

import { useDayLogOrderMutation } from '@hooks/api/useDayLogOrderMutation';
import { useAutoScroll } from '@hooks/common/useAutoScroll';
import { useDragAndDrop } from '@hooks/common/useDragAndDrop';
import { useScrollFocus } from '@hooks/common/useScrollFocus';

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
  isEditable?: boolean;
}

const TripItemList = ({ tripId, dayLogId, tripItems, isEditable = true }: TripItemListProps) => {
  const dayLogOrderMutation = useDayLogOrderMutation();
  const { observer } = useScrollFocus();
  const listRef = useRef<HTMLOListElement>(null);
  const itemRef = useRef<HTMLDivElement>(null);
  const { scrollToCenter } = useAutoScroll(listRef, itemRef);
  const clickedMarkerId = useRecoilValue(clickedMarkerIdState);

  useEffect(() => {
    scrollToCenter();
  }, [clickedMarkerId, scrollToCenter]);

  const handlePositionChange = (newItems: TripItemData[]) => {
    const itemIds = newItems.map((item) => item.id);

    dayLogOrderMutation.mutate(
      { tripId, dayLogId, itemIds },
      {
        onError: () => handleItemsUpdate(tripItems),
      }
    );
  };

  const { items, handleItemsUpdate, handleDragStart, handleDragEnter, handleDragEnd } =
    useDragAndDrop(tripItems, handlePositionChange);

  const sortedItems = items.sort(sortByOrdinal);

  useEffect(() => {
    handleItemsUpdate(tripItems);
  }, [handleItemsUpdate, tripItems]);

  return (
    <ol ref={listRef} css={containerStyling}>
      {sortedItems.map((item, index) => (
        <Fragment key={item.id}>
          <TripItem
            scrollRef={item.id === clickedMarkerId ? itemRef : null}
            tripId={tripId}
            dayLogId={dayLogId}
            isEditable={isEditable}
            observer={observer}
            onDragStart={isEditable ? handleDragStart(index) : undefined}
            onDragEnter={isEditable ? handleDragEnter(index) : undefined}
            onDragEnd={isEditable ? handleDragEnd : undefined}
            {...item}
          />
          <Divider />
        </Fragment>
      ))}
    </ol>
  );
};

interface EmptyTripItemListProps {
  tripId: number;
  isEditable?: boolean;
  openAddModal?: () => void;
}

TripItemList.Empty = ({ tripId, openAddModal, isEditable = true }: EmptyTripItemListProps) => {
  // eslint-disable-next-line react-hooks/rules-of-hooks
  const navigate = useNavigate();

  return (
    <>
      <Heading size="xSmall">추가된 일정 기록이 없습니다!</Heading>
      <Text css={emptyTextStyling}>
        여행하면서 갔던 장소, 사용했던 경비와 같은 여행 일정 기록들을 추가해 보세요.
      </Text>
      {isEditable ? (
        <Button css={addItemButtonStyling} type="button" variant="primary" onClick={openAddModal}>
          일정 기록 추가하기
        </Button>
      ) : (
        <Button
          css={addItemButtonStyling}
          type="button"
          variant="primary"
          onClick={() => navigate(PATH.EDIT_TRIP(tripId))}
        >
          여행 수정하기
        </Button>
      )}
    </>
  );
};

export default TripItemList;
