import { useRecoilValue } from 'recoil';

import { Box, Heading } from 'hang-log-design-system';

import { containerStyling, headingStyling } from '@pages/MyPage/MyPage.style';

import EditUserProfileForm from '@components/myPage/EditUserProfileForm/EditUserProfileForm';

import { useUserInfoQuery } from '@hooks/api/useUserInfoQuery';

import { mediaQueryMobileState } from '@store/mediaQuery';

const MyPage = () => {
  const isMobile = useRecoilValue(mediaQueryMobileState);

  const { userInfoData } = useUserInfoQuery();

  return (
    <Box css={containerStyling}>
      <Heading css={headingStyling} size={isMobile ? 'small' : 'medium'}>
        나의 프로필
      </Heading>
      <EditUserProfileForm initialData={userInfoData} />
    </Box>
  );
};

export default MyPage;
