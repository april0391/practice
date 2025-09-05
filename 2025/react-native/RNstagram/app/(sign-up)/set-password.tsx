import { useState } from "react";
import { TextInput } from "react-native";

import { useSignUpContext } from "@/components/auth/SignUpProvider";
import useValidation from "@/hooks/useValidation";
import { signUpSchema } from "@/utils/zod-schema";

import Button from "@/components/common/Button";
import { Text, View } from "@/components/common/Themed";

const passwordSchema = signUpSchema.pick({ password: true });

export default function SetPasswordScreen() {
  const [password, setPassword] = useState("");
  const { error, validate } = useValidation();

  const { updateAndNext } = useSignUpContext();

  function handleNext() {
    const success = validate(passwordSchema, { password });
    if (!success) return;

    updateAndNext("password", password);
  }

  return (
    <View className="flex-1 gap-5 p-7">
      <Text type="title">비밀번호 만들기</Text>
      <Text>
        다른 사람이 추측할 수 없는 6자 이상의 문자 또는 숫자로 비밀번호를
        만드세요.
      </Text>
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
    </View>
  );
}
