import { images } from '@mocks/data/image';
import { Button } from 'hang-log-design-system';
import { Link } from 'react-router-dom';

import {
  buttonStyling,
  formStyling,
  imageInputStyling,
  linkStyling,
} from '@components/myPage/EditUserProfileForm/EditUserProfileForm.style';
import NicknameInput from '@components/myPage/EditUserProfileForm/NicknameInput/NicknameInput';
import ProfileImageInput from '@components/myPage/EditUserProfileForm/ProfileImageInput/ProfileImageInput';

const EditUserProfileForm = () => {
  return (
    <form css={formStyling}>
      <ProfileImageInput css={imageInputStyling} imageUrl={images[0]} />
      <NicknameInput />
      <Button variant="primary" css={buttonStyling}>
        수정하기
      </Button>
      {/* TODO : 탈퇴 구글 폼으로 변경해야 함 */}
      <Link to="hanglog.com" css={linkStyling}>
        탈퇴하기
      </Link>
    </form>
  );
};

export default EditUserProfileForm;
