import type { ApiErrorCode } from "../../../types/app-types.ts";

export function badRequest(code?: ApiErrorCode, message?: string) {
  return new Response(
    JSON.stringify({
      success: false,
      error: { code: code || "bad_request", message },
    }),
    {
      status: 400,
      headers: { "Content-Type": "application/json" },
    },
  );
}

export function ok<T>(data?: T) {
  return new Response(JSON.stringify({ success: true, data }), {
    status: 200,
    headers: { "Content-Type": "application/json" },
  });
}

export function serverError() {
  return new Response(
    JSON.stringify({
      success: false,
      error: { code: "internal_server_error" },
    }),
    { status: 500, headers: { "Content-Type": "application/json" } },
  );
}
