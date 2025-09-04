import { useState } from "react";
import { Text, TextInput } from "react-native";

import { useSignUpContext } from "@/components/auth/SignUpProvider";
import useValidation from "@/hooks/useValidation";
import { signUpSchema } from "@/utils/zod-schema";

import Button from "@/components/common/Button";
import { ThemedText, ThemedView } from "@/components/common/Themed";

const nameSchema = signUpSchema.pick({ name: true });

export default function SetNameScreen() {
  const [name, setName] = useState("");
  const { error, validate } = useValidation();
  const { updateAndNext } = useSignUpContext();

  function handleNext() {
    const success = validate(nameSchema, { name });
    if (!success) return;

    updateAndNext("name", name);
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
