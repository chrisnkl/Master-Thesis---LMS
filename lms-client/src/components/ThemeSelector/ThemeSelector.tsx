import { useTheme, type PrimaryColor } from "../../context/Theme/ThemeContext";

const COLOR_OPTIONS: { id: PrimaryColor; name: string; bgClass: string }[] = [
  { id: "blue", name: "Blue", bgClass: "bg-blue-600" },
  { id: "emerald", name: "Emerald", bgClass: "bg-emerald-600" },
  { id: "red", name: "Red", bgClass: "bg-red-600" },
];

export default function ThemeSelector() {
  const { mode, setMode, color, setColor } = useTheme();
  const isDark = mode === "dark";

  return (
    <div className="flex items-center gap-1.5 p-1 pl-1.5 pr-2 rounded-full border border-border bg-surface shadow-xs transition-colors">
      {/* Light / Dark Mode Toggle */}
      <button
        type="button"
        onClick={() => setMode(isDark ? "light" : "dark")}
        className="flex items-center justify-center w-7 h-7 rounded-full text-muted hover:text-foreground hover:bg-background transition-all duration-200 focus:outline-hidden focus-visible:ring-2 focus-visible:ring-primary cursor-pointer"
        title={`Switch to ${isDark ? "light" : "dark"} mode`}
        aria-label="Toggle theme mode"
      >
        {isDark ? (
          <svg
            className="w-4 h-4 text-amber-400 transition-transform duration-300 hover:rotate-45"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
            strokeWidth={2}
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364 6.364l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707M16 12a4 4 0 11-8 0 4 4 0 018 0z"
            />
          </svg>
        ) : (
          <svg
            className="w-4 h-4 text-slate-600 hover:text-slate-900 transition-transform duration-300 hover:-rotate-12"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
            strokeWidth={2}
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="M20.354 15.354A9 9 0 018.646 3.646 9.003 9.003 0 0012 21a9.003 9.003 0 008.354-5.646z"
            />
          </svg>
        )}
      </button>

      {/* Subtle Divider */}
      <div className="w-px h-3.5 bg-border" aria-hidden="true" />

      {/* Accent Color Swatches */}
      <div className="flex items-center gap-1.5 pl-0.5">
        {COLOR_OPTIONS.map((c) => {
          const isSelected = color === c.id;
          return (
            <button
              key={c.id}
              type="button"
              onClick={() => setColor(c.id)}
              title={`${c.name} accent`}
              aria-label={`Select ${c.name} accent`}
              className={`relative flex items-center justify-center w-5 h-5 rounded-full ${c.bgClass} transition-all duration-200 hover:scale-110 active:scale-95 cursor-pointer focus:outline-hidden ${
                isSelected
                  ? "ring-2 ring-offset-2 ring-offset-surface ring-primary scale-105 shadow-xs"
                  : "opacity-75 hover:opacity-100"
              }`}
            >
              {isSelected && (
                <svg
                  className="w-2.5 h-2.5 text-white stroke-3"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path strokeLinecap="round" strokeLinejoin="round" d="M5 13l4 4L19 7" />
                </svg>
              )}
            </button>
          );
        })}
      </div>
    </div>
  );
}