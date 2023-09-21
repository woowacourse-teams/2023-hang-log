import type { ChangeEvent } from 'react';
import { useEffect, useState } from 'react';

import { inputStyling } from '@components/common/DayLogItem/TitleInput/TitleInput.style';

import { useDayLogTitleMutation } from '@hooks/api/useDayLogTitleMutation';

import { DAYLOG_TITLE_MAX_LENGTH } from '@constants/ui';

interface TitleInputProps {
  tripId: number | string;
  dayLogId: number;
  initialTitle: string;
}

const TitleInput = ({ tripId, dayLogId, initialTitle }: TitleInputProps) => {
  const dayLogTitleMutation = useDayLogTitleMutation();
  const [title, setTitle] = useState(initialTitle);

  useEffect(() => {
    setTitle(initialTitle);
  }, [initialTitle, dayLogId]);

  const handleTitleChange = (event: ChangeEvent<HTMLInputElement>) => {
    setTitle(event.target.value);
  };

  const handleInputBlur = () => {
    dayLogTitleMutation.mutate(
      { tripId: Number(tripId), dayLogId, title },
      {
        onError: () => setTitle(initialTitle),
      }
    );
  };

  return (
    <input
      css={inputStyling}
      value={title}
      maxLength={DAYLOG_TITLE_MAX_LENGTH}
      placeholder="소제목"
      onChange={handleTitleChange}
      onBlur={handleInputBlur}
    />
  );
};

export default TitleInput;
