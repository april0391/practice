import type { AppStateStatus } from "react-native";
import type {
  AuthResponse,
  AuthTokenResponsePassword,
  Session,
} from "@supabase/supabase-js";
import { supabase } from "../libs/supabase";
import { create } from "zustand";

type ExtendedSession = Session & {
  claims: {
    app_role: string;
  };
};

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
    // await new Promise((res) => setTimeout(res, 1500));
    const { data: claimsData } = await supabase.auth.getClaims();
    const claims = claimsData?.claims;
    if (!session || !claims) {
      set({ session: null, loading: false });
      return;
    }

    set({
      session: {
        ...session,
        claims: { app_role: claims.app_role },
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
