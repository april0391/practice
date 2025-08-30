import { useState } from "react";
import { Text, TextInput } from "react-native";
import { useRouter } from "expo-router";
import { z } from "zod";

import { useSignUpContext } from "@/components/auth/SignUpProvider";

import { ThemedText } from "@/components/ThemedText";
import { ThemedView } from "@/components/ThemedView";
import Button from "@/components/common/Button";

export const nameSchema = z
  .string()
  .min(2, "이름은 최소 2글자 이상이어야 합니다.")
  .max(20, "이름은 최대 20글자까지 입력 가능합니다.")
  .regex(
    /^[가-힣a-zA-Z\s]+$/,
    "이름에는 한글, 영어, 공백만 사용할 수 있습니다."
  );

export default function SetNameScreen() {
  const [name, setName] = useState("");
  const [error, setError] = useState("");

  const { updateSignUpField } = useSignUpContext();

  const router = useRouter();

  function handleNext() {
    const result = nameSchema.safeParse(name);
    if (!result.success) {
      setError(result.error.issues[0].message);
      return;
    }

    updateSignUpField("name", name);
    router.navigate("/agreements");
  }

  return (
    <ThemedView className="flex-1 gap-5 p-7">
      <ThemedText type="title" className="mb-3">
        이름 입력
      </ThemedText>
      <TextInput
        className="border border-gray-400 rounded-xl p-4"
        placeholder="성명"
        value={name}
        onChangeText={setName}
        autoCapitalize="none"
      />
      {error && <Text className="text-red-500">{error}</Text>}
      <Button onPress={handleNext}>다음</Button>
    </ThemedView>
  );
}
