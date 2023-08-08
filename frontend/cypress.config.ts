import { defineConfig } from 'cypress';

export default defineConfig({
  projectId: 'y3zdkv',
  e2e: {
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
  },
  env: {
    NODE_ENV: 'development',
  },
});
