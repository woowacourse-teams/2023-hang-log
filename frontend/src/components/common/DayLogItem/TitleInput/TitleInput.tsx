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
  }, [initialTitle]);

  const handleTitleChange = (event: ChangeEvent<HTMLInputElement>) => {
    setTitle(event.target.value);
  };

  const handleInputBlur = () => {
    dayLogTitleMutation.mutate(
      { tripId, dayLogId, title },
      {
        onError: () => {
          // ! 에러 발생 시 기존 값으로 롤백
          setTitle(initialTitle);
        },
      }
    );
  };

  return (
    <input
      css={inputStyling}
      value={title}
      placeholder="소제목"
      onChange={handleTitleChange}
      onBlur={handleInputBlur}
    />
  );
};

export default TitleInput;
