import { useEffect } from "react";
import {
  DarkTheme,
  DefaultTheme,
  ThemeProvider,
} from "@react-navigation/native";
import { SplashScreen, Stack } from "expo-router";
import { StatusBar } from "expo-status-bar";

import "../global.css";
// import {useColorScheme} from "nativewind";
import { useFonts } from "expo-font";
import {
  SafeAreaProvider,
  initialWindowMetrics,
} from "react-native-safe-area-context";
import "react-native-reanimated";
import { useColorScheme } from "@/hooks/useColorScheme";

import { GestureHandlerRootView } from "react-native-gesture-handler";
import { BottomSheetModalProvider } from "@gorhom/bottom-sheet";
import Toast from "react-native-toast-message";

import AuthProvider, { useAuthContext } from "@/components/auth/AuthProvider";

SplashScreen.preventAutoHideAsync();

export default function Root() {
  const colorScheme = useColorScheme();
  const [loaded] = useFonts({
    SpaceMono: require("../assets/fonts/SpaceMono-Regular.ttf"),
  });

  if (!loaded) {
    // Async font loading only occurs in development.
    return null;
  }

  return (
    <GestureHandlerRootView style={{ flex: 1 }}>
      <SafeAreaProvider initialMetrics={initialWindowMetrics}>
        <BottomSheetModalProvider>
          <ThemeProvider
            value={colorScheme === "dark" ? DarkTheme : DefaultTheme}
          >
            <AuthProvider>
              <RootNavigator />
              <Toast />
            </AuthProvider>
          </ThemeProvider>
        </BottomSheetModalProvider>
      </SafeAreaProvider>

      <StatusBar style="auto" />
    </GestureHandlerRootView>
  );
}

function RootNavigator() {
  const { isSessionInitialized, session } = useAuthContext();

  useEffect(() => {
    SplashScreen.hideAsync();
  }, [isSessionInitialized]);

  if (!isSessionInitialized) {
    return null;
  }

  const isSignedIn = !!session;
  console.log("session", session);

  return (
    <Stack
      screenOptions={{
        headerShadowVisible: false,
        headerTitle: "",
      }}
    >
      <Stack.Protected guard={!isSignedIn}>
        <Stack.Screen name="sign-in" options={{ headerShown: false }} />
        <Stack.Screen name="(sign-up)" options={{ headerShown: false }} />
      </Stack.Protected>

      <Stack.Protected guard={isSignedIn}>
        <Stack.Screen name="(onboarding)" options={{ headerShown: false }} />
        <Stack.Screen name="(tabs)" options={{ headerShown: false }} />
      </Stack.Protected>

      <Stack.Screen name="+not-found" />
    </Stack>
  );
}
