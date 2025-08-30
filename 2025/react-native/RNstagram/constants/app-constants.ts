import type { ApiErrorCode } from "@/types/app-types";

export const errorCodeToDefaultMessage: Record<ApiErrorCode, string> = {
  missing_field: "잘못된 요청입니다",
  invalid_json: "잘못된 요청입니다",
  invalid_input: "잘못된 요청입니다",

  invalid_otp: "코드가 유효하지 않습니다",
  otp_expired: "코드가 만료되었습니다. 새 코드를 요청하세요",

  functions_http_error: "잘못된 요청입니다",
  functions_relay_error: "문제가 발생했습니다. 잠시 후에 다시 시도해주세요",
  functions_fetch_error: "문제가 발생했습니다. 잠시 후에 다시 시도해주세요",

  bad_request: "잘못된 요청입니다",
  method_not_allowed: "잘못된 요청입니다",
  internal_server_error: "문제가 발생했습니다. 잠시 후에 다시 시도해주세요",
};
