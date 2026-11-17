import { useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { useAuth } from "../context/Auth/AuthContext";
import FullPagedSpinner from "../components/Spinner/FullPagedSpinner";
import {handleAuthCallback} from "../services/auth/AuthService.ts";

export default function OAuthCallbackPage() {
    const [searchParams] = useSearchParams();
    const navigate = useNavigate();
    const { setSession } = useAuth();
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const code = searchParams.get("code");
        const state = searchParams.get("state");
        const errorParam = searchParams.get("error_description") || searchParams.get("error");

        if (errorParam) {
            setError(errorParam);
            return;
        }

        if (code && state) {
            handleAuthCallback(code, state)
                .then((tokens) => {
                    setSession(tokens.access_token);
                    navigate("/", { replace: true });
                })
                .catch((err) => {
                    setError(err.message);
                });
        } else {
            setError("Missing code or state from Authorization Server");
        }
    }, [searchParams, navigate, setSession]);

    if (error) {
        return (
            <div className="min-h-screen flex items-center justify-center bg-background text-foreground">
                <div className="bg-surface border border-red-500/20 p-6 rounded-xl max-w-md text-center shadow-xs">
                    <h2 className="text-red-500 font-bold text-lg">Authentication Failed</h2>
                    <p className="text-muted text-sm mt-2">{error}</p>
                    <button
                        onClick={() => navigate("/")}
                        className="mt-4 px-4 py-2 bg-primary text-primary-fg rounded-lg text-sm font-medium"
                    >
                        Return Home
                    </button>
                </div>
            </div>
        );
    }

    return <FullPagedSpinner />;
}