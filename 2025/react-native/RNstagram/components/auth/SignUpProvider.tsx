import { createContext, useContext, useState } from "react";
import type { PropsWithChildren } from "react";
import type { SignUpData } from "@/types/app-types";

type SignUpContextProps = {
  updateSignUpField: <K extends keyof SignUpData>(
    k: K,
    v: SignUpData[K]
  ) => void;
  submitSignUp: () => void;
};

const SignUpContext = createContext<SignUpContextProps | null>(null);

export function SignUpProvider({ children }: PropsWithChildren) {
  const [currentStep, setCurrentStep] = useState(1);
  const [signUpData, setSignUpData] = useState<SignUpData>({
    email: "",
    password: "",
    birthDate: "",
    name: "",
    agreedToAll: false,
  });

  function updateSignUpField<K extends keyof SignUpData>(
    k: K,
    v: SignUpData[K]
  ) {
    setSignUpData((prev) => ({ ...prev, [k]: v }));
  }

  function submitSignUp() {
    console.log("signUpData", signUpData);
  }

  return (
    <SignUpContext value={{ updateSignUpField, submitSignUp }}>
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
