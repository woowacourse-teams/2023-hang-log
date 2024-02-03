import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import tsconfigPaths from 'vite-tsconfig-paths';
import svgr from 'vite-plugin-svgr';

export default defineConfig({
  plugins: [react(), tsconfigPaths(), svgr()],
  resolve: {
    alias: {
      'hang-log-design-system': '../hang-log-design-system',
    },
  },
  optimizeDeps: {
    include: ['hang-log-design-system'],
  },
});
