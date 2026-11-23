const MUTATING_METHODS = new Set(["POST", "PUT", "PATCH", "DELETE"]);

/**
 * Reads the XSRF-TOKEN cookie issued by the BFF Gateway for CSRF double-submit protection.
 */
export function getCsrfToken(): string | null {
  const match = document.cookie
    .split("; ")
    .find((row) => row.startsWith("XSRF-TOKEN="));
  return match ? decodeURIComponent(match.split("=")[1]) : null;
}

/**
 * Centralized BFF fetch client:
 * - Always sends HttpOnly session cookies (credentials: "include")
 * - Automatically attaches X-XSRF-TOKEN header on state-changing requests
 * - Never touches or stores raw JWTs in JavaScript
 */
export async function apiFetch<T>(url: string, options: RequestInit = {}): Promise<T> {
  const method = (options.method || "GET").toUpperCase();
  const headers = new Headers(options.headers || {});

  if (options.body && !headers.has("Content-Type")) {
    headers.set("Content-Type", "application/json");
  }

  if (MUTATING_METHODS.has(method)) {
    const csrfToken = getCsrfToken();
    if (csrfToken) {
      headers.set("X-XSRF-TOKEN", csrfToken);
    }
  }

  const response = await fetch(url, {
    ...options,
    method,
    headers,
    credentials: "include",
  });

  if (response.status === 401) {
    throw new Error("Unauthorized");
  }

  if (!response.ok) {
    const errorData = await response.json().catch(() => ({}));
    throw new Error(errorData.detail || errorData.message || `Request failed with status ${response.status}`);
  }

  if (response.status === 204) {
    return undefined as T;
  }

  return response.json() as Promise<T>;
}
