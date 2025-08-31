import { Stack } from "expo-router";

import { SignUpProvider } from "@/components/auth/SignUpProvider";

export default function SignUpLayout() {
  return (
    <SignUpProvider>
      <Stack
        screenOptions={{
          headerShadowVisible: false,
          headerTitle: "",
        }}
      />
    </SignUpProvider>
  );
}
