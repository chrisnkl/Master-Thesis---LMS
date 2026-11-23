import { createContext, useContext, useState, useEffect, type ReactNode } from "react";
import { ServiceEndpoints } from "../../config/ServiceEndpoints";
import { getCsrfToken } from "../../services/apiClient";

export interface BffUser {
    sub: string;
    username: string;
    email?: string;
    roles: string[];
}

interface BffMeResponse {
    authenticated: boolean;
    sub?: string;
    username?: string;
    email?: string;
    roles?: string[];
}

interface AuthContextType {
    user: BffUser | null;
    isAuthenticated: boolean;
    isLoading: boolean;
    login: () => void;
    logout: () => void;
    hasRole: (role: string) => boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
    const [user, setUser] = useState<BffUser | null>(null);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        let cancelled = false;

        fetch(ServiceEndpoints.AUTH.ME, { credentials: "include" })
            .then((res) => (res.ok ? res.json() : { authenticated: false }))
            .then((data: BffMeResponse) => {
                if (cancelled) return;
                if (data.authenticated && data.sub) {
                    setUser({
                        sub: data.sub,
                        username: data.username || data.email || data.sub,
                        email: data.email,
                        roles: data.roles || [],
                    });
                } else {
                    setUser(null);
                }
            })
            .catch(() => {
                if (!cancelled) setUser(null);
            })
            .finally(() => {
                if (!cancelled) setIsLoading(false);
            });

        return () => {
            cancelled = true;
        };
    }, []);

    const login = () => {
        window.location.href = ServiceEndpoints.AUTH.LOGIN;
    };

    // Real form POST so the browser smoothly follows the 302 OIDC logout redirect to Keycloak (no CORS error)
    const logout = () => {
        const form = document.createElement("form");
        form.method = "POST";
        form.action = ServiceEndpoints.AUTH.LOGOUT;

        const csrfToken = getCsrfToken();
        if (csrfToken) {
            const input = document.createElement("input");
            input.type = "hidden";
            input.name = "_csrf";
            input.value = csrfToken;
            form.appendChild(input);
        }

        document.body.appendChild(form);
        form.submit();
    };

    const hasRole = (role: string) => {
        const normalized = role.toUpperCase();
        return user?.roles.some((r) => r.toUpperCase() === normalized || r.toUpperCase() === `ROLE_${normalized}`) ?? false;
    };

    return (
        <AuthContext.Provider
            value={{
                user,
                isAuthenticated: user !== null,
                isLoading,
                login,
                logout,
                hasRole,
            }}
        >
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    const context = useContext(AuthContext);
    if (!context) throw new Error("useAuth must be used within an AuthProvider");
    return context;
}