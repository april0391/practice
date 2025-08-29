import Button from "@/components/common/Button";
import { ThemedText } from "@/components/ThemedText";
import { ThemedView } from "@/components/ThemedView";
import { useRouter } from "expo-router";
import { useState } from "react";
import { Text, TextInput } from "react-native";
import { z } from "zod";

const passwordSchema = z
  .string()
  .min(
    6,
    "비밀번호가 너무 짧습니다. 6자 이상의 문자와 숫자 조합으로 더 긴 비밀번호를 만드세요."
  )
  .regex(/^[A-Za-z0-9]+$/, "비밀번호는 문자 또는 숫자만 사용할 수 있습니다.");

export default function SetPasswordScreen() {
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const router = useRouter();

  function handleSetPassword() {
    const result = passwordSchema.safeParse(password);
    if (!result.success) {
      setError(result.error.issues[0].message);
      return;
    }

    setError("");

    router.navigate("/set-birthdate");
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
      <Button onPress={handleSetPassword}>다음</Button>
    </ThemedView>
  );
}
