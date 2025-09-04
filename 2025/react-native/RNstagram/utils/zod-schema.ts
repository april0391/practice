import { z } from "zod";

export const signUpSchema = z.object({
  email: z.email("올바른 이메일 주소를 입력해주세요."),
  password: z
    .string()
    .min(
      6,
      "비밀번호가 너무 짧습니다. 6자 이상의 문자, 숫자, 특수문자를 조합하여 만드세요.",
    )
    .regex(
      /^[\w!@#$%^&*(),.?":{}|<>~`\-_=+\\[\];'/]+$/,
      "비밀번호에 허용되지 않은 문자가 포함되어 있습니다.",
    ),
  username: z.string().min(2, "사용자 이름은 2자 이상이어야 합니다."),
  birthDate: z.string().refine(
    (date) => !isNaN(Date.parse(date)),
    "유효한 생년월일을 입력해주세요.",
  ),
  name: z.string()
    .min(2, "이름은 최소 2글자 이상이어야 합니다.")
    .max(20, "이름은 최대 20글자까지 입력 가능합니다.")
    .regex(
      /^[가-힣a-zA-Z\s]+$/,
      "이름에는 한글, 영어, 공백만 사용할 수 있습니다.",
    ),
  agreedToPolicies: z.boolean().refine(
    (val) => val === true,
    "모든 약관에 동의해야 합니다.",
  ),
});
