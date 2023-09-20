/* eslint-disable @typescript-eslint/no-var-requires */
const { merge } = require('webpack-merge');
const common = require('./webpack.common');
const { BundleAnalyzerPlugin } = require('webpack-bundle-analyzer');

module.exports = merge(common, {
  mode: 'production',
  devtool: 'nosources-source-map',
  optimization: {
    splitChunks: {
      chunks: 'all',
    },
  },
  plugins: [
    new BundleAnalyzerPlugin({
      analyzerMode: 'static',
      openAnalyzer: false,
      generateStatsFile: true,
      statsFilename: 'bundle-report.json',
    }),
  ],
});
