import {
  createContext,
  useContext,
  useEffect,
  useState,
  type PropsWithChildren,
} from "react";
import { usePathname, useRouter, type Href } from "expo-router";

import type { SignUpData } from "@/types/app-types";
import { signUp } from "@/utils/auth";

type SignUpContextProps = {
  signUpData: SignUpData;
  updateAndNext: <K extends keyof SignUpData>(k: K, v: SignUpData[K]) => void;
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
    agreedToPolicies: false,
  });
  const [isAllStepsCompleted, setIsAllStepsCompleted] = useState(false);

  useEffect(() => {
    if (isAllStepsCompleted) {
      signUp(signUpData);
    }
  }, [signUpData, isAllStepsCompleted]);

  const router = useRouter();
  const pathname = usePathname();

  function updateSignUpData<K extends keyof SignUpData>(
    k: K,
    v: SignUpData[K]
  ) {
    setSignUpData((prev) => ({ ...prev, [k]: v }));
  }

  function handleNext() {
    const currentStepIdx = signUpSteps.findIndex((s) => s.href === pathname);

    const isLastStep = currentStepIdx === signUpSteps.length - 1;

    if (!isLastStep) {
      router.navigate(signUpSteps[currentStepIdx + 1].href);
      return;
    }

    setIsAllStepsCompleted(true);
  }

  function updateAndNext<K extends keyof SignUpData>(k: K, v: SignUpData[K]) {
    updateSignUpData(k, v);
    handleNext();
  }

  return (
    <SignUpContext
      value={{
        signUpData,
        updateAndNext,
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
