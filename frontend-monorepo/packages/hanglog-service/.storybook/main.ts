import type { StorybookConfig } from '@storybook/react-webpack5';
import path from 'path';

const config: StorybookConfig = {
  stories: ['../src/stories/**/*.mdx', '../src/**/*.stories.@(js|jsx|ts|tsx)'],
  addons: [
    '@storybook/addon-links',
    '@storybook/addon-essentials',
    '@storybook/addon-interactions',
    '@storybook/addon-a11y',
  ],
  framework: {
    name: '@storybook/react-webpack5',
    options: {},
  },
  docs: {
    autodocs: true,
  },
  staticDirs: ['../public'],
  webpackFinal: async (config) => {
    if (config.resolve) {
      config.resolve.alias = {
        ...config.resolve.alias,
        '@': path.resolve(__dirname, '../src'),
        '@components': path.resolve(__dirname, '../src/components'),
        '@type': path.resolve(__dirname, '../src/types'),
        '@hooks': path.resolve(__dirname, '../src/hooks'),
        '@pages': path.resolve(__dirname, '../src/pages'),
        '@styles': path.resolve(__dirname, '../src/styles'),
        '@constants': path.resolve(__dirname, '../src/constants'),
        '@assets': path.resolve(__dirname, '../src/assets'),
        '@api': path.resolve(__dirname, '../src/api'),
        '@mocks': path.resolve(__dirname, '../src/mocks'),
        '@stories': path.resolve(__dirname, '../src/stories'),
        '@router': path.resolve(__dirname, '../src/router'),
        '@store': path.resolve(__dirname, '../src/store'),
        '@utils': path.resolve(__dirname, '../src/utils'),
      };
    }
    const imageRule = config.module?.rules?.find((rule) => {
      const test = (rule as { test: RegExp }).test;

      if (!test) return false;

      return test.test('.svg') || test.test('.png');
    }) as { [key: string]: any };

    imageRule.exclude = /\.(svg|png)$/;

    config.module?.rules?.push({
      test: /\.svg$/,
      issuer: /\.[jt]sx?$/,
      use: ['@svgr/webpack'],
    });

    config.module?.rules?.push({
      test: /\.svg$/,
      issuer: /\.(style.js|style.ts)$/,
      use: ['url-loader'],
    });

    config.module?.rules?.push({
      test: /\.png$/i,
      issuer: /\.[jt]sx?$/,
      use: ['url-loader'],
    });

    return config;
  },
};
export default config;
