import { useState } from "react";
import { ActivityIndicator, TextInput } from "react-native";

import { useSignUpContext } from "@/components/auth/SignUpProvider";
import { sendOtp } from "@/utils/auth";
import { signUpSchema } from "@/utils/zod-schema";
import useValidation from "@/hooks/useValidation";

import Button from "@/components/common/Button";
import { ThemedText, ThemedView } from "@/components/common/Themed";

const emailSchema = signUpSchema.pick({ email: true });

export default function SendOtpScreen() {
  const [email, setEmail] = useState("");
  const { error, validate } = useValidation();

  const [isLoading, setIsLoading] = useState(false);
  const [otpRequestError, setOtpRequestError] = useState<string | null>(null);

  const { updateAndNext } = useSignUpContext();

  async function handleNext() {
    const success = validate(emailSchema, { email });
    if (!success) return;

    setIsLoading(true);
    setOtpRequestError(null);
    const res = await sendOtp(email);

    if (!res.success) {
      setOtpRequestError(res.error.message);
      setIsLoading(false);
      return;
    }

    setIsLoading(false);
    updateAndNext("email", email);
  }

  return (
    <ThemedView className="flex-1 gap-9 p-5">
      <ThemedText type="title">이메일 주소 입력</ThemedText>
      <ThemedText>
        회원님에게 연락할 수 있는 이메일 주소를 입력하세요. 이 이메일 주소는
        프로필에서 다른 사람에게 공개되지 않습니다.
      </ThemedText>
      <ThemedView>
        <TextInput
          className="border border-gray-400 rounded-xl p-4"
          value={email}
          onChangeText={(text) => setEmail(text)}
          placeholder="이메일 주소"
          autoCapitalize="none"
          autoCorrect={false}
        />
        {error && (
          <ThemedText className="text-red-500 mt-2">{error}</ThemedText>
        )}
        {otpRequestError ? (
          <ThemedText className="text-red-500 mt-2">
            {otpRequestError}
          </ThemedText>
        ) : null}
      </ThemedView>
      <ThemedText>
        또한 회원님은 저희가 보내는 이메일을 받게 되며 언제든지 이를 수신 거부할
        수 있습니다.
      </ThemedText>
      <Button onPress={handleNext} disabled={isLoading}>
        {!isLoading ? "다음" : <ActivityIndicator />}
      </Button>
    </ThemedView>
  );
}
