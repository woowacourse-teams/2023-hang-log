import ErrorImage from '@assets/svg/error-image.svg';
import { HTTP_ERROR_MESSAGE } from '@constants/api';
import { Box, Button, Flex, Heading, Text } from 'hang-log-design-system';

import { hasKeyInObject } from '@utils/typeGuard';

import {
  buttonStyling,
  containerStyling,
  headingStyling,
  textStyling,
} from '@components/common/Error/Error.style';

export interface ErrorProps {
  statusCode?: number;
  resetError?: () => void;
}

const Error = ({ statusCode = 404, resetError }: ErrorProps) => {
  const isHTTPError = hasKeyInObject(HTTP_ERROR_MESSAGE, statusCode);

  if (!isHTTPError) return null;

  return (
    <Box>
      <Flex styles={{ direction: 'column', align: 'center' }} css={containerStyling}>
        <ErrorImage aria-label="에러 이미지" />
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
