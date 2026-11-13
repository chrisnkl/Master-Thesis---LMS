
const AUTH_BASE_URL = import.meta.env.VITE_AUTH_SERVER_URL || 'http://localhost:8081';
const LANG_FUNC_BASE_URL = import.meta.env.VITE_LANGUAGE_ENDPOINT_URL || 'http://localhost:7071';

export const ServiceEndpoints = {
    AUTH: {
        BASE: AUTH_BASE_URL,
        LOGIN: `${AUTH_BASE_URL}/auth/login`,
        USER_INFO: `${AUTH_BASE_URL}/api/v1/user/me`,
    },

    LANGUAGE: {
        BASE: LANG_FUNC_BASE_URL,
        I18N_LOAD_PATH: `${LANG_FUNC_BASE_URL}/api/lang?language={{lng}}`,
        GET_TRANSLATIONS: (lang: string) => `${LANG_FUNC_BASE_URL}/api/lang?language=${lang}`,
    }
} as const;