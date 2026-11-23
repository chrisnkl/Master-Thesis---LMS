
// All requests go through the BFF API Gateway (same-origin via Vite proxy in dev, reverse proxy in prod)
export const ServiceEndpoints = {
    AUTH: {
        LOGIN: '/oauth2/authorization/keycloak',
        LOGOUT: '/logout',
        ME: '/bff/me',
    },

    USERS: {
        ME: '/api/v1/users/me',
        PREFERENCES: '/api/v1/users/me/preferences',
    },

    LANGUAGE: {
        I18N_LOAD_PATH: '/api/lang?language={{lng}}',
        GET_TRANSLATIONS: (lang: string) => `/api/lang?language=${lang}`,
    },
} as const;