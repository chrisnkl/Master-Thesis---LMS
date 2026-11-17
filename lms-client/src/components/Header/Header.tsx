import { Link } from "react-router-dom";
import ThemeSelector from "../ThemeSelector/ThemeSelector";
import { useAuth } from "../../context/Auth/AuthContext";

export default function Header() {
    const { user, isAuthenticated, login, logout } = useAuth();

    return (
        <header className="bg-surface border-b border-border px-6 py-4 flex justify-between items-center transition-colors">
            <Link to="/" className="text-2xl font-bold text-foreground">
                e<span className="text-primary">LMS</span>
            </Link>

            <nav className="flex gap-4 items-center">
                <Link to="/courses" className="text-muted hover:text-foreground">Courses</Link>
                <Link to="/instructors" className="text-muted hover:text-foreground">Instructors</Link>

                <ThemeSelector />

                {/* Auth Button */}
                {isAuthenticated ? (
                    <div className="flex items-center gap-3 pl-2">
                        <span className="text-sm font-medium text-foreground">{user?.sub}</span>
                        <button
                            onClick={logout}
                            className="text-xs px-2.5 py-1.5 rounded-lg border border-border hover:bg-background text-muted hover:text-foreground transition-colors cursor-pointer"
                        >
                            Sign out
                        </button>
                    </div>
                ) : (
                    <button
                        onClick={login}
                        className="bg-primary hover:bg-primary-hover text-primary-fg text-sm font-medium px-4 py-1.5 rounded-lg transition-colors cursor-pointer shadow-xs"
                    >
                        Sign in
                    </button>
                )}
            </nav>
        </header>
    );
}