import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'
import tailwindcss from '@tailwindcss/vite'

const GATEWAY_URL = 'http://localhost:8000';

// https://vite.dev/config/
export default defineConfig({
  plugins: [react(), tailwindcss()],
  server: {
    proxy: {
      // Keep changeOrigin: false (default) so Host: localhost:5173 is preserved
      // for Spring OAuth2 {baseUrl} redirect_uri resolution (same-origin BFF cookies!)
      '/api': { target: GATEWAY_URL, changeOrigin: false },
      '/bff': { target: GATEWAY_URL, changeOrigin: false },
      '/oauth2': { target: GATEWAY_URL, changeOrigin: false },
      '/login': { target: GATEWAY_URL, changeOrigin: false },
      '/logout': { target: GATEWAY_URL, changeOrigin: false },
    },
  },
})
