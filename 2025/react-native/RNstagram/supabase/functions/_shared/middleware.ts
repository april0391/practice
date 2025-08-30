import { serverError } from "./responses.ts";

export default function middleware(
  // deno-lint-ignore no-explicit-any
  handler: (req: Request, body: any) => Promise<Response>,
  options?: {
    requiredFields?: string[];
  },
) {
  return async (req: Request) => {
    // 웹브라우저 preflight 허용
    if (req.method === "OPTIONS") {
      return new Response("ok", {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "Access-Control-Allow-Headers":
            "authorization, x-client-info, apikey, content-type",
        },
      });
    }

    // 메서드 검증
    if (req.method !== "POST") {
      return new Response(
        JSON.stringify({
          success: false,
          error: { code: "method_not_allowed" },
        }),
        { status: 405 },
      );
    }

    // json 형식 검증
    let body;
    try {
      body = await req.json();
    } catch (e) {
      console.error(e);
      return new Response(
        JSON.stringify({ success: false, error: { code: "invalid_json" } }),
        {
          status: 400,
          headers: { "Content-Type": "application/json" },
        },
      );
    }

    if (options?.requiredFields?.length) {
      // 본문 필드 검증
      for (const field of options.requiredFields) {
        if (!(field in body)) {
          return new Response(
            JSON.stringify({
              success: false,
              error: {
                code: "missing_field",
                message: `Missing required field: ${field}`,
              },
            }),
            { status: 400, headers: { "Content-Type": "application/json" } },
          );
        }
      }
    }

    try {
      return await handler(req, body);
    } catch (e) {
      console.error(e);
      return serverError();
    }
  };
}
