import { supabase } from "@/utils/supabase";
import type { Session } from "@supabase/supabase-js";
import type { PropsWithChildren } from "react";
import { createContext, useContext, useEffect, useState } from "react";
import { AppState } from "react-native";

// Tells Supabase Auth to continuously refresh the session automatically if
// the app is in the foreground. When this is added, you will continue to receive
// `onAuthStateChange` events with the `TOKEN_REFRESHED` or `SIGNED_OUT` event
// if the user's session is terminated. This should only be registered once.
AppState.addEventListener("change", (state) => {
  if (state === "active") {
    supabase.auth.startAutoRefresh();
  } else {
    supabase.auth.stopAutoRefresh();
  }
});

type AuthState = {
  isSessionInitialized: boolean;
  session: Session | null;
  signIn: (email: string, password: string) => void;
  signOut: () => void;
};

const AuthContext = createContext<AuthState>({
  isSessionInitialized: false,
  session: null,
  signIn: () => {},
  signOut: () => {},
});

export default function AuthProvider({ children }: PropsWithChildren) {
  const [session, setSession] = useState<Session | null>(null);
  const [isSessionInitialized, setIsSessionInitialized] = useState(false);

  useEffect(() => {
    supabase.auth.getSession().then(({ data: { session } }) => {
      console.log("getSession().then()");
      setSession(session);
      setIsSessionInitialized(true);
    });

    supabase.auth.onAuthStateChange((_event, session) => {
      console.log("onAuthStateChange()", _event);
      setSession(session);
    });
  }, []);

  async function signIn(email: string, password: string) {
    return await supabase.auth.signInWithPassword({
      email,
      password,
    });
  }

  async function signOut() {
    const { error } = await supabase.auth.signOut();

    console.log("error", error);
  }

  return (
    <AuthContext value={{ isSessionInitialized, session, signIn, signOut }}>
      {children}
    </AuthContext>
  );
}

export function useAuthContext() {
  const ctx = useContext(AuthContext);

  if (!ctx) throw new Error("Auth context not provided.");

  return ctx;
}
