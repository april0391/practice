import { useCallback, useState } from "react";
import type { ZodType } from "zod";

export default function useValidation() {
  const [error, setError] = useState("");

  const validate = useCallback(
    <T>(schema: ZodType<T>, value: unknown) => {
      setError("");
      const result = schema.safeParse(value);

      if (!result.success) {
        setError(result.error.issues[0].message);
        return false;
      }

      return true;
    },
    [],
  );

  return { validate, error };
}
