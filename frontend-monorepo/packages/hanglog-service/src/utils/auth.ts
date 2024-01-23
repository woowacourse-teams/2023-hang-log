export const isAuthProvider = (provider: string | undefined): provider is string => {
  return ['kakao', 'google'].some((authProvider) => authProvider === provider);
};
