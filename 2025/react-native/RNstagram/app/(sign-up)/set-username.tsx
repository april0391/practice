import { useState } from "react";
import { Text, TextInput } from "react-native";
import AntDesign from "@expo/vector-icons/AntDesign";

import { useSignUpContext } from "@/components/auth/SignUpProvider";
import { nameSchema, validateWithZod } from "@/utils/zod";

import Button from "@/components/common/Button";
import { ThemedText } from "@/components/ThemedText";
import { ThemedView } from "@/components/ThemedView";

export default function SetUserameScreen() {
  const [username, setUsername] = useState("");
  const [error, setError] = useState("");

  const { updateAndNext } = useSignUpContext();

  function handleNext() {
    const result = validateWithZod(nameSchema, username);
    if (!result.success) {
      setError(result.error);
      return;
    }

    updateAndNext("username", username);
  }

  return (
    <ThemedView className="flex-1 gap-5 p-7">
      <ThemedText type="title" className="mb-3">
        사용자 이름 만들기
      </ThemedText>
      <ThemedText>
        사용자 이름을 직접 추가하거나 추천 이름을 사용하세요. 언제든지 변경할 수
        있습니다.
      </ThemedText>
      <ThemedView className="relative">
        <TextInput
          className="border border-gray-400 rounded-xl p-4"
          placeholder="성명"
          value={username}
          onChangeText={setUsername}
          autoCapitalize="none"
        />
        <AntDesign
          name="checkcircleo"
          size={21}
          color="green"
          className="absolute right-4 top-1/2 -translate-y-1/2"
        />
      </ThemedView>
      {error && <Text className="text-red-500">{error}</Text>}
      <Button onPress={handleNext}>다음</Button>
    </ThemedView>
  );
}
