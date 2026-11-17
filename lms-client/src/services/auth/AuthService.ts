import {ServiceEndpoints} from "../../config/ServiceEndpoints.ts";
import {generateCodeChallenge, generateCodeVerifier} from "./pkce.ts";

const CLIENT_ID = import.meta.env.VITE_CLIENT_ID;
const REDIRECT_URI = `${window.location.origin}/oauth2/callback`;
const AUTH_SERVER_URL = ServiceEndpoints.AUTH.BASE;

export interface TokenResponse {
    access_token: string;
    token_type: string;
    expires_in: number;
    scope: string;
    id_token?: string;
}

export async function loginWithRedirect() {
    const codeVerifier = generateCodeVerifier();
    const codeChallenge = await generateCodeChallenge(codeVerifier);
    const state = generateCodeVerifier(32);

    sessionStorage.setItem("pkce_code_verifier", codeVerifier);
    sessionStorage.setItem("pkce_state", state);

    const params = new URLSearchParams({
        response_type: "code",
        client_id: CLIENT_ID,
        scope: "openid profile email",
        redirect_uri: REDIRECT_URI,
        state: state,
        code_challenge: codeChallenge,
        code_challenge_method: "S256",
    });

    window.location.href = `${AUTH_SERVER_URL}/oauth2/authorize?${params.toString()}`;
}

export async function handleAuthCallback(code: string, state: string): Promise<TokenResponse> {
    const savedVerifier = sessionStorage.getItem("pkce_code_verifier");
    const savedState = sessionStorage.getItem("pkce_state");
    if (!savedVerifier || savedState !== state) {
        throw new Error("Invalid state or missing code verifier. Possible CSRF attack.");
    }
    const body = new URLSearchParams({
        grant_type: "authorization_code",
        client_id: CLIENT_ID,
        redirect_uri: REDIRECT_URI,
        code: code,
        code_verifier: savedVerifier,
    });
    const response = await fetch(`${AUTH_SERVER_URL}/oauth2/token`, {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
        },
        body: body.toString(),
    });
    if (!response.ok) {
        const errorData = await response.json().catch(() => ({}));
        throw new Error(errorData.error_description || "Failed to exchange authorization code for token");
    }
    // Cleanup PKCE state
    sessionStorage.removeItem("pkce_code_verifier");
    sessionStorage.removeItem("pkce_state");
    return response.json();
}