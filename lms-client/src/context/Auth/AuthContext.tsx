import { createContext, useContext, useState, useEffect, type ReactNode } from "react";
import { loginWithRedirect } from "../../services/auth/authService";

export interface DecodedUser {
    sub: string;           // Username or User ID
    roles: string[];       // e.g. ["ROLE_USER", "ROLE_ADMIN"]
    email?: string;
}

interface AuthContextType {
    user: DecodedUser | null;
    token: string | null;
    isAuthenticated: boolean;
    login: () => void;
    logout: () => void;
    setSession: (token: string) => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

// Helper to decode JWT payload without external libraries
function parseJwt(token: string): any {
    try {
        const base64Url = token.split(".")[1];
        const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
        return JSON.parse(window.atob(base64));
    } catch {
        return null;
    }
}

export function AuthProvider({ children }: { children: ReactNode }) {
    const [token, setToken] = useState<string | null>(() => localStorage.getItem("lms_token"));
    const [user, setUser] = useState<DecodedUser | null>(() => {
        const saved = localStorage.getItem("lms_token");
        if (!saved) return null;
        const claims = parseJwt(saved);
        return claims ? { sub: claims.sub, roles: claims.roles || [], email: claims.email } : null;
    });

    const setSession = (newToken: string) => {
        localStorage.setItem("lms_token", newToken);
        setToken(newToken);
        const claims = parseJwt(newToken);
        if (claims) {
            setUser({ sub: claims.sub, roles: claims.roles || [], email: claims.email });
        }
    };

    const logout = () => {
        localStorage.removeItem("lms_token");
        setToken(null);
        setUser(null);
    };

    return (
        <AuthContext.Provider
            value={{
                user,
                token,
                isAuthenticated: !!token,
                login: loginWithRedirect,
                logout,
                setSession,
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