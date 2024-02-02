import { useEffect } from 'react';

import { useRecoilValue } from 'recoil';

import { Box, Button, Flex, Heading, Text } from 'hang-log-design-system';

import {
  buttonStyling,
  containerStyling,
  headingStyling,
  textStyling,
} from '@components/common/Error/Error.style';

import { mediaQueryMobileState } from '@store/mediaQuery';

import { hasKeyInObject } from '@utils/typeGuard';

import { ERROR_CODE, HTTP_ERROR_MESSAGE, HTTP_STATUS_CODE } from '@constants/api';

import ErrorImage from '@assets/svg/error-image.svg?react';

export interface ErrorProps {
  statusCode?: number;
  errorCode?: number;
  resetError?: () => void;
}

const Error = ({ statusCode = HTTP_STATUS_CODE.NOT_FOUND, errorCode, resetError }: ErrorProps) => {
  const currentStatusCode =
    statusCode === HTTP_STATUS_CODE.CONTENT_TOO_LARGE ? HTTP_STATUS_CODE.BAD_REQUEST : statusCode;
  const isHTTPError = hasKeyInObject(HTTP_ERROR_MESSAGE, currentStatusCode);
  const isMobile = useRecoilValue(mediaQueryMobileState);

  if (!isHTTPError) return null;

  return (
    <Box>
      <Flex styles={{ direction: 'column', align: 'center' }} css={containerStyling}>
        <ErrorImage width={isMobile ? '80%' : '476px'} aria-label="에러 이미지" />
        <Heading css={headingStyling} size="small">
          {HTTP_ERROR_MESSAGE[currentStatusCode].HEADING}
        </Heading>
        <Text css={textStyling}>{HTTP_ERROR_MESSAGE[currentStatusCode].BODY}</Text>
        <Button css={buttonStyling} variant="primary" onClick={resetError}>
          {HTTP_ERROR_MESSAGE[currentStatusCode].BUTTON}
        </Button>
      </Flex>
    </Box>
  );
};

export default Error;
