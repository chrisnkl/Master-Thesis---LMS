import './i18n.ts';
import { StrictMode, Suspense } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import FullPagedSpinner from "./components/Spinner/FullPagedSpinner.tsx";
import { ThemeProvider } from "./context/Theme/ThemeContext.tsx";
import { AuthProvider } from './context/Auth/AuthContext.tsx';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <AuthProvider>
      <ThemeProvider>
        <Suspense fallback={<FullPagedSpinner />}><App /></Suspense>
      </ThemeProvider>
    </AuthProvider>

  </StrictMode>,
)
