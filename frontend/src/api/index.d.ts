import 'axios';

declare module 'axios' {
  export interface AxiosRequestConfig {
    useAuth?: boolean;
  }
}
