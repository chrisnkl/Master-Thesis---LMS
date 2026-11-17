import { createContext, type ReactNode, use, useEffect, useState } from "react";
import { COLOR_MODE_KEY, THEME_MODE_KEY } from "../../constants/StorageConstants.ts";

export type ThemeMode = "light" | "dark" | "system";
export type PrimaryColor = "blue" | "red" | "emerald";

interface ThemeContextTypeProps {
    mode: ThemeMode;
    color: PrimaryColor;
    setMode: (mode: ThemeMode) => void;
    setColor: (color: PrimaryColor) => void;
}

const ThemeContext = createContext<ThemeContextTypeProps | undefined>(undefined);

export function ThemeProvider({ children }: { children: ReactNode }) {

    const [mode, setMode] = useState<ThemeMode>(() => localStorage.getItem(THEME_MODE_KEY) as ThemeMode || "system");
    const [color, setColor] = useState<PrimaryColor>(() => localStorage.getItem(COLOR_MODE_KEY) as PrimaryColor || "blue");

    useEffect(() => {

        const root = document.documentElement;

        const systemPrefersDark = window.matchMedia("(prefers-color-scheme: dark)").matches;
        const isDark = mode === "dark" || (mode === "system" && systemPrefersDark);

        if (isDark) {
            root.classList.add("dark");
        } else {
            root.classList.remove("dark");
        }

        localStorage.setItem(THEME_MODE_KEY, mode);
    }, [mode]);

    useEffect(() => {
        document.documentElement.setAttribute("data-color", color);
        localStorage.setItem(COLOR_MODE_KEY, color);
    }, [color]);

    return (
        <ThemeContext.Provider value={{ mode, color, setMode, setColor }}>{children}</ThemeContext.Provider>
    )

}


export function useTheme() {
    const context = use(ThemeContext);
    if (!context) throw new Error("useTheme must be used within a ThemeProvider.");
    return context;
}