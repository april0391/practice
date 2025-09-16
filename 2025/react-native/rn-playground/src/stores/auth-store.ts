import type { AppStateStatus } from "react-native";
import type {
  AuthResponse,
  AuthTokenResponsePassword,
  Session,
} from "@supabase/supabase-js";
import { supabase } from "../lib/supabase";
import { jwtDecode } from "jwt-decode";
import { create } from "zustand";

type Claims = {
  user_role: string;
};

type ExtendedSession = Session & { claims: Claims };

type AuthStore = {
  loading: boolean;
  session: ExtendedSession | null;
  initSession: () => Promise<void>;
  initAuthStateChangeCallback: () => void;
  setupAutoRefresh: (state: AppStateStatus) => void;
  signUp: (
    signUpForm: { email: string; password: string; role: string },
  ) => Promise<AuthResponse>;
  signIn: (
    credentials: { email: string; password: string },
  ) => Promise<AuthTokenResponsePassword>;
  refreshSession: () => void;
  signOut: () => void;
};

const useAuth = create<AuthStore>((set) => {
  const updateSession = async (session: Session | null) => {
    if (!session) {
      set({ session: null, loading: false });
      return;
    }

    const claims = jwtDecode<Claims>(session.access_token);

    set({
      session: {
        ...session,
        claims,
      },
      loading: false,
    });
  };

  return {
    loading: true,
    session: null,

    initSession: async () => {
      const { data: { session } } = await supabase.auth.getSession();
      updateSession(session);
    },

    initAuthStateChangeCallback: () => {
      supabase.auth.onAuthStateChange(async (_event, session) => {
        updateSession(session);
      });
    },

    setupAutoRefresh: (state) => {
      if (state === "active") {
        supabase.auth.startAutoRefresh();
      } else {
        supabase.auth.stopAutoRefresh();
      }
    },

    signUp: (signUpForm) => {
      return supabase.auth.signUp({
        email: signUpForm.email,
        password: signUpForm.password,
        options: {
          data: {
            init_data: { role: signUpForm.role },
          },
        },
      });
    },

    signIn: async (credentials) => {
      return await supabase.auth.signInWithPassword(credentials);
    },

    refreshSession: () => supabase.auth.refreshSession(),

    signOut: () => supabase.auth.signOut(),
  };
});

export default useAuth;
