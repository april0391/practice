import "../global.css";
import { useEffect } from "react";
import { SplashScreen, Stack } from "expo-router";

// import { AuthProvider, useAuth } from "@/store/AuthProvider";
import { useAuthStore } from "@/store/authStore";

SplashScreen.preventAutoHideAsync();

export default function RootLayout() {
  return (
    // <AuthProvider>
    <RootNavigator />
    // </AuthProvider>
  );
}

function RootNavigator() {
  // const { session, loading } = useAuth();
  const { loading, session, initialize } = useAuthStore();

  useEffect(() => {
    const subscription = initialize();

    return () => subscription.unsubscribe();
  }, [initialize]);

  useEffect(() => {
    if (!loading) SplashScreen.hideAsync();
  }, [loading]);

  if (loading) return null;

  const isSignedIn = !!session;

  return (
    <Stack>
      <Stack.Protected guard={!isSignedIn}>
        <Stack.Screen name="sign-in" />
        <Stack.Screen name="sign-up" />
      </Stack.Protected>

      <Stack.Protected guard={isSignedIn}>
        <Stack.Screen name="(tabs)" options={{ headerShown: false }} />
      </Stack.Protected>
    </Stack>
  );
}
