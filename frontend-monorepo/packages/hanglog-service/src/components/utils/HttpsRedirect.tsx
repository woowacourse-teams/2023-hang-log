import type { ReactNode } from 'react';

import { REGEX } from '@constants/regex';

interface HttpsRedirectProps {
  children: ReactNode;
}

const HttpsRedirect = ({ children }: HttpsRedirectProps) => {
  if (window.location.hostname !== 'localhost' && window.location.protocol === 'http:') {
    window.location.href = window.location.href.replace(REGEX.HTTP_TO_HTTPS, 'https');
  }

  return <>{children}</>;
};

export default HttpsRedirect;
