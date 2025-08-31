import { useState } from "react";
import { Text, TextInput } from "react-native";

import { useSignUpContext } from "@/components/auth/SignUpProvider";
import { passwordSchema, validateWithZod } from "@/utils/zod";

import Button from "@/components/common/Button";
import { ThemedText } from "@/components/ThemedText";
import { ThemedView } from "@/components/ThemedView";

export default function SetPasswordScreen() {
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const { updateAndNext } = useSignUpContext();

  function handleNext() {
    const result = validateWithZod(passwordSchema, password);
    if (!result.success) {
      setError(result.error);
      return;
    }

    updateAndNext("password", password);
  }

  return (
    <ThemedView className="flex-1 gap-5 p-7">
      <ThemedText type="title">비밀번호 만들기</ThemedText>
      <ThemedText>
        다른 사람이 추측할 수 없는 6자 이상의 문자 또는 숫자로 비밀번호를
        만드세요.
      </ThemedText>
      <TextInput
        className="border border-gray-400 rounded-xl p-4"
        placeholder="비밀번호"
        value={password}
        onChangeText={setPassword}
        secureTextEntry={true}
        autoCapitalize="none"
      />
      {error && <Text className="text-red-500">{error}</Text>}
      <Button onPress={handleNext}>다음</Button>
    </ThemedView>
  );
}
