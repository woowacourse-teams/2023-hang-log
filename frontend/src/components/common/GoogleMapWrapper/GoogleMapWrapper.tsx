import { Status, Wrapper } from '@googlemaps/react-wrapper';
import { Spinner } from 'hang-log-design-system';
import type { PropsWithChildren } from 'react';

type GoogleMapWrapperProps = PropsWithChildren;

const render = (status: Status) => {
  if (status === Status.FAILURE) throw new Error('유류가 발생했습니다.');

  return <Spinner />;
};

const GoogleMapWrapper = ({ children }: GoogleMapWrapperProps) => {
  if (!process.env.GOOGLE_API_KEY) throw new Error('유효한 구글 API 키가 필요합니다.');

  return (
    <Wrapper
      apiKey={process.env.GOOGLE_API_KEY}
      render={render}
      libraries={['places']}
      language="KO"
    >
      {children}
    </Wrapper>
  );
};

export default GoogleMapWrapper;
