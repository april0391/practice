import { Stack } from "expo-router";

import { SignUpProvider } from "@/components/auth/SignUpProvider";

export const signUpSteps = [
  { route: "/send-otp" },
  { route: "/verify-otp" },
  { route: "/set-password" },
  { route: "/set-birth-date" },
  { route: "/set-name" },
  { route: "/agreements" },
];

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
