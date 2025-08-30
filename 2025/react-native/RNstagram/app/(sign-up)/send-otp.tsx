import { useState } from "react";
import { ActivityIndicator, Text, TextInput } from "react-native";
import { useRouter } from "expo-router";
import { z } from "zod";

import { sendOtp } from "@/utils/auth";

import Button from "@/components/common/Button";
import { ThemedText } from "@/components/ThemedText";
import { ThemedView } from "@/components/ThemedView";

const emailSchema = z.email("올바른 이메일 주소를 입력해주세요.");

export default function SendOtpScreen() {
  const [email, setEmail] = useState("");
  const [emailError, setEmailError] = useState("");

  const [isLoading, setIsLoading] = useState(false);
  const [otpRequestError, setOtpRequestError] = useState<string | null>(null);

  const router = useRouter();

  async function handleNext() {
    // email 형식 검증
    const result = emailSchema.safeParse(email);

    if (!result.success) {
      setEmailError(result.error.issues[0].message);
      return;
    }
    setEmailError("");

    // otp 발송
    setIsLoading(true);
    setOtpRequestError(null);
    const res = await sendOtp(email);

    if (!res.success) {
      setOtpRequestError(res.error.message);
      setIsLoading(false);
      return;
    }

    router.navigate({
      pathname: "/verify-otp",
      params: {
        email,
      },
    });
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
        {emailError ? (
          <Text className="text-red-500 mt-2">{emailError}</Text>
        ) : null}
        {otpRequestError ? (
          <Text className="text-red-500 mt-2">{otpRequestError}</Text>
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
