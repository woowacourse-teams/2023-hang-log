import { Box, Button } from 'hang-log-design-system';
import type { ComponentPropsWithoutRef } from 'react';
import { memo, useRef } from 'react';

import { useImageUpload } from '@hooks/common/useImageUpload';

import {
  imageStyling,
  inputStyling,
  uploadButtonStyling,
  wrapperStyling,
} from '@components/myPage/EditUserProfileForm/ProfileImageInput/ProfileImageInput.style';

interface ProfileImageInputProps extends ComponentPropsWithoutRef<'div'> {
  imageUrl: string;
}

const ProfileImageInput = ({ imageUrl, ...attributes }: ProfileImageInputProps) => {
  const inputRef = useRef<HTMLInputElement | null>(null);

  const handleImageUploadButtonClick = () => {
    inputRef.current?.click();
  };

  const { uploadedImageUrls, handleImageUpload } = useImageUpload({
    initialImageUrls: [imageUrl],
  });

  return (
    <Box css={wrapperStyling} {...attributes}>
      <Button
        css={uploadButtonStyling}
        type="button"
        size="small"
        variant="text"
        onClick={handleImageUploadButtonClick}
      >
        수정
      </Button>
      <input
        css={inputStyling}
        type="file"
        accept="image/*"
        id="profile-image"
        ref={inputRef}
        onChange={handleImageUpload}
      />
      <img css={imageStyling} src={uploadedImageUrls[0]} alt="사용자 프로필 이미지" />
    </Box>
  );
};

export default memo(ProfileImageInput);
