import { Toast, useOverlay } from 'hang-log-design-system';
import type { ChangeEvent } from 'react';
import { useEffect, useState } from 'react';

import { useDayLogTitleMutation } from '@hooks/api/useDayLogTitleMutation';

import { inputStyling } from '@components/common/DayLogItem/TitleInput/TitleInput.style';

interface TitleInputProps {
  tripId: number;
  dayLogId: number;
  initialTitle: string;
}

const TitleInput = ({ tripId, dayLogId, initialTitle }: TitleInputProps) => {
  const dayLogTitleMutation = useDayLogTitleMutation();
  const { isOpen: isErrorTostOpen, open: openErrorToast, close: closeErrorToast } = useOverlay();
  const [title, setTitle] = useState(initialTitle);

  useEffect(() => {
    setTitle(initialTitle);
  }, [initialTitle]);

  const handleTitleChange = (event: ChangeEvent<HTMLInputElement>) => {
    setTitle(event.target.value);
  };

  const handleInputBlur = () => {
    dayLogTitleMutation.mutate(
      { tripId, dayLogId, title },
      {
        onError: () => {
          setTitle(initialTitle);
          openErrorToast();
        },
      }
    );
  };

  return (
    <>
      <input
        css={inputStyling}
        value={title}
        placeholder="소제목"
        onChange={handleTitleChange}
        onBlur={handleInputBlur}
      />
      {isErrorTostOpen && (
        <Toast variant="error" closeToast={closeErrorToast}>
          소제목 변경을 실패했습니다. 잠시 후 다시 시도해 주세요.
        </Toast>
      )}
    </>
  );
};

export default TitleInput;
