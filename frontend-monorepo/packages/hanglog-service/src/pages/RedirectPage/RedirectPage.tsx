import { useEffect } from 'react';
import { useParams, useSearchParams } from 'react-router-dom';

import { Flex, Heading, Spinner, Theme } from 'hang-log-design-system';

import { containerStyling } from '@pages/RedirectPage/RedirectPage.style';

import { useLogInMutation } from '@hooks/api/useLogInMutation';

import { isAuthProvider } from '@utils/auth';

const RedirectPage = () => {
  const { provider } = useParams();
  const [searchParams] = useSearchParams();

  const code = searchParams.get('code');
  const error = searchParams.get('error');
  const authError = searchParams.get('authError');

  if (error || authError || !code || !isAuthProvider(provider)) {
    throw new Error('로그인/회원가입에 실패했습니다.');
  }

  const { mutateLogIn } = useLogInMutation();

  useEffect(() => {
    mutateLogIn({ provider, code });
  }, [code, mutateLogIn, provider]);

  return (
    <Flex
      styles={{
        direction: 'column',
        justify: 'center',
        align: 'center',
        gap: Theme.spacer.spacing5,
      }}
      css={containerStyling}
    >
      <Spinner size={80} width={8} />
      <Heading size="xSmall">로그인 중입니다</Heading>
    </Flex>
  );
};

export default RedirectPage;
