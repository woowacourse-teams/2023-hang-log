const HtmlWebpackPlugin = require('html-webpack-plugin');
const Dotenv = require('dotenv-webpack');
const path = require('path');
const webpack = require('webpack');

const prod = process.env.NODE_ENV === 'production';

const plugins = [
  new webpack.ProvidePlugin({
    React: 'react',
  }),
  new HtmlWebpackPlugin({
    template: './public/index.html',
  }),
  new webpack.HotModuleReplacementPlugin(),
  new Dotenv(),
];

if (!prod) {
  const CopyPlugin = require('copy-webpack-plugin');
  plugins.push(
    new CopyPlugin({
      patterns: [{ from: 'public/mockServiceWorker.js', to: '' }],
    })
  );
}

module.exports = {
  mode: prod ? 'production' : 'development',
  devtool: prod ? 'hidden-source-map' : 'eval',
  entry: './src/index.tsx',
  resolve: {
    extensions: ['.js', '.jsx', '.ts', '.tsx'],
  },
  module: {
    rules: [
      {
        test: /\.(js|jsx|ts|tsx)$/,
        exclude: /node_modules/,
        use: ['ts-loader'],
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
        test: /\.png$/i,
        issuer: /\.[jt]sx?$/,
        use: ['url-loader'],
      },
    ],
  },
  output: {
    path: path.join(__dirname, '/dist'),
    filename: 'bundle.js',
    publicPath: '/',
  },

  devServer: {
    historyApiFallback: true,
    port: 3000,
    hot: true,
    static: path.resolve(__dirname, 'dist'),
  },

  resolve: {
    extensions: ['.js', '.ts', '.jsx', '.tsx', '.json'],
    alias: {
      '@': path.resolve(__dirname, './src'),
      '@components': path.resolve(__dirname, './src/components'),
      '@type': path.resolve(__dirname, './src/types'),
      '@hooks': path.resolve(__dirname, './src/hooks'),
      '@pages': path.resolve(__dirname, './src/pages'),
      '@styles': path.resolve(__dirname, './src/styles'),
      '@constants': path.resolve(__dirname, './src/constants'),
      '@assets': path.resolve(__dirname, './src/assets'),
      '@api': path.resolve(__dirname, './src/api'),
      '@mocks': path.resolve(__dirname, './src/mocks'),
      '@stories': path.resolve(__dirname, './src/stories'),
      '@router': path.resolve(__dirname, './src/router'),
      '@store': path.resolve(__dirname, './src/store'),
      '@utils': path.resolve(__dirname, './src/utils'),
    },
  },
  plugins,
};
