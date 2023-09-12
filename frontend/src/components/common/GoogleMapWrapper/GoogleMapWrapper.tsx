import { Status, Wrapper } from '@googlemaps/react-wrapper';

import type { PropsWithChildren } from 'react';

import { Flex, Spinner } from 'hang-log-design-system';

type GoogleMapWrapperProps = PropsWithChildren;

const render = (status: Status) => {
  if (status === Status.FAILURE) throw new Error('오류가 발생했습니다.');

  return (
    <Flex
      styles={{
        width: '100%',
        height: '100%',
        justify: 'center',
        align: 'center',
      }}
    >
      <Spinner />
    </Flex>
  );
};

const GoogleMapWrapper = ({ children }: GoogleMapWrapperProps) => {
  if (!process.env.GOOGLE_API_KEY) throw new Error('유효한 구글 API 키가 필요합니다.');

  return (
    <Wrapper
      apiKey={process.env.GOOGLE_API_KEY}
      render={render}
      libraries={['places', 'marker']}
      language="KO"
    >
      {children}
    </Wrapper>
  );
};

export default GoogleMapWrapper;
