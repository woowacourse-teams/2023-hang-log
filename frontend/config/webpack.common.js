/* eslint-disable @typescript-eslint/no-var-requires */
const HtmlWebpackPlugin = require('html-webpack-plugin');
const Dotenv = require('dotenv-webpack');
const webpack = require('webpack');
const { convertToAbsolutePath } = require('./webpackUtil');
const ForkTsCheckerWebpackPlugin = require('fork-ts-checker-webpack-plugin');
const SpeedMeasurePlugin = require('speed-measure-webpack-plugin');

const smp = new SpeedMeasurePlugin();

module.exports = smp.wrap({
  entry: convertToAbsolutePath('src/index.tsx'),
  module: {
    rules: [
      {
        test: /\.(js|jsx|ts|tsx)$/,
        exclude: /node_modules/,
        loader: 'esbuild-loader',
        options: {
          target: 'es2021',
        },
      },
      {
        test: /\.svg$/i,
        issuer: /\.[jt]sx?$/,
        use: ['@svgr/webpack'],
      },
      {
        test: /\.svg$/i,
        issuer: /\.(style.js|style.ts)$/,
        use: ['url-loader'],
      },
      {
        test: /\.(png|jpg)$/i,
        issuer: /\.[jt]sx?$/,
        use: ['url-loader'],
      },
    ],
  },

  output: {
    path: convertToAbsolutePath('dist'),
    filename: '[name].[chunkhash].bundle.js',
    publicPath: '/',
    clean: true,
  },

  resolve: {
    extensions: ['.js', '.ts', '.jsx', '.tsx', '.json'],
    alias: {
      '@': convertToAbsolutePath('src'),
      '@components': convertToAbsolutePath('src/components'),
      '@type': convertToAbsolutePath('src/types'),
      '@hooks': convertToAbsolutePath('src/hooks'),
      '@pages': convertToAbsolutePath('src/pages'),
      '@styles': convertToAbsolutePath('src/styles'),
      '@constants': convertToAbsolutePath('src/constants'),
      '@assets': convertToAbsolutePath('src/assets'),
      '@api': convertToAbsolutePath('src/api'),
      '@mocks': convertToAbsolutePath('src/mocks'),
      '@stories': convertToAbsolutePath('src/stories'),
      '@router': convertToAbsolutePath('src/router'),
      '@store': convertToAbsolutePath('src/store'),
      '@utils': convertToAbsolutePath('src/utils'),
    },
  },

  plugins: [
    new webpack.ProvidePlugin({
      React: 'react',
    }),
    new HtmlWebpackPlugin({
      template: convertToAbsolutePath('public/index.html'),
      favicon: convertToAbsolutePath('public/favicon.ico'),
    }),
    new Dotenv(),
    new ForkTsCheckerWebpackPlugin(),
  ],
});
