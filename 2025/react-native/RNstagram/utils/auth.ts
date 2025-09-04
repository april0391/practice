import {
  FunctionsFetchError,
  FunctionsHttpError,
  FunctionsRelayError,
} from "@supabase/supabase-js";
import { supabase } from "./supabase";
import { errorCodeToDefaultMessage } from "@/constants/app-constants";
import type { ApiErrorCode, SignUpData } from "@/types/app-types";

export async function sendOtp(email: string) {
  const { data, error } = await supabase.functions.invoke("api/auth/otp/send", {
    body: { email },
  });

  if (error) {
    return await handleFunctionsError(error);
  }

  return data;
}

export async function verifyOtp(email: string, otpNumber: string) {
  const { data, error } = await supabase.functions.invoke(
    "api/auth/otp/verify",
    {
      body: { email, otpNumber },
    },
  );

  if (error) {
    return await handleFunctionsError(error);
  }

  return data;
}

export async function verifyUsername(username: string) {
  const { data, error } = await supabase.functions.invoke(
    "api/auth/verify-username",
    {
      body: { username },
    },
  );

  if (error) {
    return await handleFunctionsError(error);
  }

  return data;
}

export async function signUp({
  email,
  password,
  username,
  name,
  birthDate,
  agreedToPolicies,
}: SignUpData) {
  const { error } = await supabase.auth.signUp({
    email,
    password,
    options: {
      data: {
        username,
        name,
        birthDate,
        agreedToPolicies,
      },
    },
  });

  if (error) {
    console.error(error);
  }
}

async function handleFunctionsError(
  error: FunctionsHttpError | FunctionsRelayError | FunctionsFetchError,
) {
  let code: ApiErrorCode;
  let defaultMessage = "";

  if (error instanceof FunctionsHttpError) {
    const serverRes = await error.context.json();
    console.log("Function returned an error", serverRes);

    code = serverRes.error.code;
  } else if (error instanceof FunctionsRelayError) {
    console.error("Relay error:", error.message);
    code = "functions_relay_error";
  } else if (error instanceof FunctionsFetchError) {
    console.error("Fetch error:", error.message);
    code = "functions_fetch_error";
  } else {
    throw new Error();
  }

  defaultMessage = errorCodeToDefaultMessage[code];
  console.log("code", code);

  return { success: false, error: { code, message: defaultMessage } };
}
