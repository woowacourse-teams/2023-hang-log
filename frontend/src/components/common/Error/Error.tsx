import ErrorImage from '@assets/svg/error-image.svg';
import { ERROR_CODE, HTTP_ERROR_MESSAGE, HTTP_STATUS_CODE } from '@constants/api';
import { mediaQueryMobileState } from '@store/mediaQuery';
import { Box, Button, Flex, Heading, Text } from 'hang-log-design-system';
import { useRecoilValue } from 'recoil';

import { hasKeyInObject } from '@utils/typeGuard';

import { useTokenError } from '@hooks/member/useTokenError';

import {
  buttonStyling,
  containerStyling,
  headingStyling,
  textStyling,
} from '@components/common/Error/Error.style';

export interface ErrorProps {
  statusCode?: number;
  errorCode?: number;
  resetError?: () => void;
}

const Error = ({ statusCode = HTTP_STATUS_CODE.NOT_FOUND, errorCode, resetError }: ErrorProps) => {
  const isHTTPError = hasKeyInObject(HTTP_ERROR_MESSAGE, statusCode);
  const isMobile = useRecoilValue(mediaQueryMobileState);

  const { handleTokenError } = useTokenError();

  if (!isHTTPError) return null;

  if (errorCode && errorCode > ERROR_CODE.TOKEN_ERROR_RANGE) {
    handleTokenError();

    return null;
  }

  return (
    <Box>
      <Flex styles={{ direction: 'column', align: 'center' }} css={containerStyling}>
        <ErrorImage width={isMobile ? '80%' : '476px'} aria-label="에러 이미지" />
        <Heading css={headingStyling} size="small">
          {HTTP_ERROR_MESSAGE[statusCode].HEADING}
        </Heading>
        <Text css={textStyling}>{HTTP_ERROR_MESSAGE[statusCode].BODY}</Text>
        <Button css={buttonStyling} variant="primary" onClick={resetError}>
          {HTTP_ERROR_MESSAGE[statusCode].BUTTON}
        </Button>
      </Flex>
    </Box>
  );
};

export default Error;
