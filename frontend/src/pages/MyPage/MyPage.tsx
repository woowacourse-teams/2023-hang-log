import { mediaQueryMobileState } from '@store/mediaQuery';
import { Box, Heading } from 'hang-log-design-system';
import { useRecoilValue } from 'recoil';

import { containerStyling, headingStyling } from '@pages/MyPage/MyPage.style';

import EditUserProfileForm from '@components/myPage/EditUserProfileForm/EditUserProfileForm';

const MyPage = () => {
  const isMobile = useRecoilValue(mediaQueryMobileState);

  return (
    <Box css={containerStyling}>
      <Heading css={headingStyling} size={isMobile ? 'small' : 'medium'}>
        나의 프로필
      </Heading>
      <EditUserProfileForm />
    </Box>
  );
};

export default MyPage;
