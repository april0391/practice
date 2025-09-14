import { useEffect } from "react";
import { AppState } from "react-native";
import { Stack } from "expo-router";
import * as SplashScreen from "expo-splash-screen";
import useAuth from "../stores/auth-store";
import "../global.css";

export default function RootLayout() {
  const {
    loading,
    session,
    initSession,
    initAuthStateChangeCallback,
    setupAutoRefresh,
  } = useAuth();

  useEffect(() => {
    if (!loading) {
      SplashScreen.hideAsync();
    }
  }, [loading]);

  useEffect(() => {
    initSession();
    initAuthStateChangeCallback();

    // Tells Supabase Auth to continuously refresh the session automatically if
    // the app is in the foreground. When this is added, you will continue to receive
    // `onAuthStateChange` events with the `TOKEN_REFRESHED` or `SIGNED_OUT` event
    // if the user's session is terminated. This should only be registered once.
    const subscription = AppState.addEventListener("change", setupAutoRefresh);

    return () => {
      subscription.remove();
    };
  }, [initSession, initAuthStateChangeCallback, setupAutoRefresh]);

  if (loading) return null;

  const isSignedIn = !!session;

  return (
    <Stack>
      <Stack.Protected guard={isSignedIn}>
        <Stack.Screen name="(tabs)" options={{ headerShown: false }} />
      </Stack.Protected>

      <Stack.Protected guard={!isSignedIn}>
        <Stack.Screen name="sign-in" options={{ title: "로그인" }} />
        <Stack.Screen name="sign-up" options={{ title: "회원가입" }} />
      </Stack.Protected>
    </Stack>
  );
}
