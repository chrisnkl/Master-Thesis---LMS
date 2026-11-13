import i18n from 'i18next';
import HttpBackend from 'i18next-http-backend';
import { initReactI18next } from 'react-i18next';
import {ServiceEndpoints} from "./config/ServiceEndpoints.ts";

i18n
    .use(HttpBackend)
    .use(initReactI18next)
    .init({
        fallbackLng: 'en',
        supportedLngs: ['en', 'el'],
        debug: false,
        backend: {
            loadPath: ServiceEndpoints.LANGUAGE.I18N_LOAD_PATH,
            parse: (response: string) => {
                const json = JSON.parse(response);
                return json.data;
            },
        },
        interpolation: {
            escapeValue: false
        },
    });

export default i18n;
