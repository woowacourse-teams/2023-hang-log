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
  const [title, setTitle] = useState(initialTitle);

  useEffect(() => {
    setTitle(initialTitle);
  }, [initialTitle, dayLogId]);

  const handleTitleChange = (event: ChangeEvent<HTMLInputElement>) => {
    setTitle(event.target.value);
  };

  const handleInputBlur = () => {
    dayLogTitleMutation.mutate(
      { tripId, dayLogId, title },
      {
        onError: () => setTitle(initialTitle),
      }
    );
  };

  return (
    <input
      css={inputStyling}
      value={title}
      maxLength={25}
      placeholder="소제목"
      onChange={handleTitleChange}
      onBlur={handleInputBlur}
    />
  );
};

export default TitleInput;
