import webpackPreprocessor from '@cypress/webpack-preprocessor'
import webpackConfig from './config/webpack.dev.js'
import { defineConfig } from 'cypress';

const options = {
  
  webpackOptions: webpackConfig,
  watchOptions: {},
}

export default defineConfig({
  projectId: 'y3zdkv',
  e2e: {
    setupNodeEvents(on, config) {
      on('file:preprocessor', webpackPreprocessor(options))
    },
  },
  env: {
    NODE_ENV: 'development',
  },
  video: false,
  screenshotOnRunFailure: false,  
});
