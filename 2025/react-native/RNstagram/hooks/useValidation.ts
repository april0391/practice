import { useState } from "react";
import { type ZodType } from "zod";

export default function useValidation() {
  const [error, setError] = useState("");

  function validate<T>(
    schema: ZodType<T>,
    value: unknown,
  ): boolean {
    setError("");
    const result = schema.safeParse(value);

    if (!result.success) {
      setError(result.error.issues[0].message);
      return false;
    }

    return true;
  }

  return { validate, error };
}
