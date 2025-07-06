import { createContext, useContext, useEffect, useState } from "react";
import { apiGet, HttpRequestError } from "../utils/api";

const SessionContext = createContext({
  session: { data: null, status: "loading" },
  setSession: () => {},
});

export function useSession() {
  return useContext(SessionContext);
}

export const SessionProvider = ({ children }) => {
  const [sessionState, setSessionState] = useState({
    data: null,
    status: "loading",
  });

  // Load session from localStorage first (for faster UI rendering)
  useEffect(() => {
    const stored = localStorage.getItem("session");
    if (stored) {
      try {
        const parsed = JSON.parse(stored);
        if (parsed?.data) {
          setSessionState({ data: parsed.data, status: "authenticated" });
        }
      } catch (e) {
        console.warn("Corrupted session in localStorage");
        localStorage.removeItem("session");
      }
    }

    // Always revalidate with API in background
    apiGet("/api/auth")
      .then((data) => {
        setSessionState({ data, status: "authenticated" });
        localStorage.setItem("session", JSON.stringify({ data }));
      })
      .catch((e) => {
        console.error("Session validation failed:", e);
        setSessionState({ data: null, status: "unauthenticated" });
        localStorage.removeItem("session");
      });
  }, []);

  const setSession = (session) => {
    setSessionState(session);
    if (session?.data) {
      localStorage.setItem("session", JSON.stringify({ data: session.data }));
    } else {
      localStorage.removeItem("session");
    }
  };

  return (
    <SessionContext.Provider value={{ session: sessionState, setSession }}>
      {children}
    </SessionContext.Provider>
  );
};
