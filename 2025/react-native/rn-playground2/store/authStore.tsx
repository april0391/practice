import { AppState } from "react-native";
import { create } from "zustand";
import type { Session, Subscription } from "@supabase/supabase-js";
import { supabase } from "@/lib/supabase";

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

interface AuthStore {
  loading: boolean;
  session: Session | null;
  signIn: (email: string, password: string) => void;
  signOut: () => void;
  initialize: () => Subscription;
}

export const useAuthStore = create<AuthStore>((set) => ({
  loading: true,
  session: null,
  signIn: async (email: string, password: string) => {
    const { data, error } = await supabase.auth.signInWithPassword({
      email,
      password,
    });
    console.log("data", data);
    console.log("error", error);
  },
  signOut: () => supabase.auth.signOut(),
  initialize: () => {
    // 초기 세션 가져오기
    supabase.auth
      .getSession()
      .then(({ data: { session } }) => set({ session, loading: false }));

    // 세션 상태 변경 리스너 등록
    const {
      data: { subscription },
    } = supabase.auth.onAuthStateChange((_event, session) => set({ session }));

    return subscription;
  },
}));
