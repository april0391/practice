import { createContext, useContext, useState } from "react";
import type { PropsWithChildren } from "react";
import type { SignUpData } from "@/types/app-types";
import { type Href, usePathname, useRouter } from "expo-router";

type SignUpContextProps = {
  signUpData: SignUpData;
  updateAndNext: <K extends keyof SignUpData>(k: K, v: SignUpData[K]) => void;
  submitSignUp: () => void;
};

const SignUpContext = createContext<SignUpContextProps | null>(null);

const signUpSteps: { href: Href }[] = [
  { href: "/send-otp" },
  { href: "/verify-otp" },
  { href: "/set-password" },
  { href: "/set-birth-date" },
  { href: "/set-name" },
  { href: "/set-username" },
  { href: "/agreements" },
];

export function SignUpProvider({ children }: PropsWithChildren) {
  const [signUpData, setSignUpData] = useState<SignUpData>({
    email: "",
    password: "",
    birthDate: "",
    name: "",
    username: "",
    agreedToAll: false,
  });

  const router = useRouter();
  const pathname = usePathname();

  function updateSignUpData<K extends keyof SignUpData>(
    k: K,
    v: SignUpData[K]
  ) {
    setSignUpData((prev) => ({ ...prev, [k]: v }));
  }

  function navigateNextStep() {
    const currentStepIdx = signUpSteps.findIndex((s) => s.href === pathname);
    const isLastStep = currentStepIdx === signUpSteps.length - 1;

    if (!isLastStep) {
      router.navigate(signUpSteps[currentStepIdx + 1].href);
      return;
    }

    // router.replace("/");
  }

  function updateAndNext<K extends keyof SignUpData>(k: K, v: SignUpData[K]) {
    updateSignUpData(k, v);
    navigateNextStep();
  }

  function submitSignUp() {
    console.log("signUpData", signUpData);
  }

  return (
    <SignUpContext
      value={{
        signUpData,
        updateAndNext,
        submitSignUp,
      }}
    >
      {children}
    </SignUpContext>
  );
}

export function useSignUpContext() {
  const value = useContext(SignUpContext);

  if (!value) {
    throw new Error("Not provided sign up context");
  }

  return value;
}
