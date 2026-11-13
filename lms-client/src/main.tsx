import './i18n.ts';
import {StrictMode, Suspense} from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import FullPagedSpinner from "./components/Spinner/FullPagedSpinner.tsx";

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <Suspense fallback={<FullPagedSpinner/>}><App />
    </Suspense>
  </StrictMode>,
)
