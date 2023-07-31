import { useScrollFocus } from '@/hooks/common/useScrollFocus';
import type { TripItemData } from '@type/tripItem';
import { Button, Divider, Heading, Text, Toast, useOverlay } from 'hang-log-design-system';
import { Fragment, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import { useDayLogOrderMutation } from '@hooks/api/useDayLogOrderMutation';
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
  isEditable?: boolean;
}

const TripItemList = ({ tripId, dayLogId, tripItems, isEditable = true }: TripItemListProps) => {
  const dayLogOrderMutation = useDayLogOrderMutation();
  const { observer } = useScrollFocus();
  const { isOpen: isErrorTostOpen, open: openErrorToast, close: closeErrorToast } = useOverlay();

  const handlePositionChange = (newItems: TripItemData[]) => {
    const itemIds = newItems.map((item) => item.id);

    dayLogOrderMutation.mutate(
      { tripId, dayLogId, itemIds },
      {
        onError: () => {
          handleItemsUpdate(tripItems);
          openErrorToast();
        },
      }
    );
  };

  const { items, handleItemsUpdate, handleDragStart, handleDragEnter, handleDragEnd } =
    useDragAndDrop(tripItems, handlePositionChange);

  useEffect(() => {
    handleItemsUpdate(tripItems);
  }, [handleItemsUpdate, tripItems]);

  return (
    <>
      <ol css={containerStyling}>
        {items.map((item, index) => (
          <Fragment key={item.id}>
            <TripItem
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
      {isErrorTostOpen && (
        <Toast variant="error" closeToast={closeErrorToast}>
          아이템 순서 변경을 실패했습니다. 잠시 후 다시 시도해 주세요.
        </Toast>
      )}
    </>
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

  return isEditable ? (
    <>
      <Heading size="xSmall">아직 추가된 일정 기록이 없습니다!</Heading>
      <Text css={emptyTextStyling}>
        여행하면서 갔던 장소, 사용했던 경비와 같은 여행 일정 기록들을 추가해 보세요.
      </Text>
      <Button css={addItemButtonStyling} type="button" variant="primary" onClick={openAddModal}>
        일정 기록 추가하기
      </Button>
    </>
  ) : (
    <>
      <Heading size="xSmall">추가된 일정 기록이 없습니다!</Heading>
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
    </>
  );
};

export default TripItemList;
