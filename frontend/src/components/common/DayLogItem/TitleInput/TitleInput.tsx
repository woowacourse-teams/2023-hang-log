import type { ChangeEvent } from 'react';
import { useEffect, useState } from 'react';

import { inputStyling } from '@components/common/DayLogItem/TitleInput/TitleInput.style';

interface TitleInputProps {
  initialTitle: string;
}

const TitleInput = ({ initialTitle }: TitleInputProps) => {
  const [title, setTitle] = useState(initialTitle);

  useEffect(() => {
    setTitle(initialTitle);
  }, [initialTitle]);

  const handleTitleChange = (event: ChangeEvent<HTMLInputElement>) => {
    setTitle(event.target.value);
  };

  // input onBlur시에 api 요청 보내기

  return (
    <input css={inputStyling} value={title} placeholder="소제목" onChange={handleTitleChange} />
  );
};

export default TitleInput;
