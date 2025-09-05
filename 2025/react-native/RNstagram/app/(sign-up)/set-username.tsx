import { useEffect, useState } from "react";
import { TextInput } from "react-native";
import { useDebounce } from "use-debounce";
import AntDesign from "@expo/vector-icons/AntDesign";

import { useSignUpContext } from "@/components/auth/SignUpProvider";
import useValidation from "@/hooks/useValidation";
import { signUpSchema } from "@/utils/zod-schema";
import { verifyUsername } from "@/utils/auth";

import Button from "@/components/common/Button";
import { Text, View } from "@/components/common/Themed";

const usernameSchema = signUpSchema.pick({ username: true });

export default function SetUserameScreen() {
  const [username, setUsername] = useState("");
  const [debouncedUsername] = useDebounce(username, 300);
  const { error, validate } = useValidation();
  const [serverError, setServerError] = useState("");
  const [serverValidated, setServerValidated] = useState<boolean | null>(null);

  const { updateAndNext } = useSignUpContext();

  useEffect(() => {
    const success = validate(usernameSchema, { username: debouncedUsername });
    if (!success) {
      setServerValidated(null);
      return;
    }

    async function requestVerifyUsername() {
      const { success } = await verifyUsername(debouncedUsername);

      if (!success) {
        setServerValidated(false);
        setServerError("사용할 수 없는 사용자 이름입니다");
      } else {
        setServerError("");
        setServerValidated(true);
      }
    }

    requestVerifyUsername();
  }, [validate, debouncedUsername]);

  function handleNext() {
    if (serverValidated) {
      updateAndNext("username", debouncedUsername);
    }
  }

  return (
    <View className="flex-1 gap-5 p-7">
      <Text type="title" className="mb-3">
        사용자 이름 만들기
      </Text>
      <Text>
        사용자 이름을 직접 추가하거나 추천 이름을 사용하세요. 언제든지 변경할 수
        있습니다.
      </Text>
      <View className="relative">
        <TextInput
          className="border border-gray-400 rounded-xl p-4"
          placeholder="성명"
          value={username}
          onChangeText={(text) => {
            setServerValidated(null);
            setUsername(text);
          }}
          autoCapitalize="none"
        />
        {!error && serverValidated === true && (
          <AntDesign
            name="checkcircleo"
            size={21}
            color="green"
            className="absolute right-4 top-1/2 -translate-y-1/2"
          />
        )}
        {serverValidated === false && (
          <AntDesign
            name="closecircle"
            size={21}
            color="red"
            className="absolute right-4 top-1/2 -translate-y-1/2"
          />
        )}
      </View>
      {username !== "" && error && (
        <Text className="text-red-500">{error}</Text>
      )}
      {serverValidated === false && serverError && (
        <Text className="text-red-500">{serverError}</Text>
      )}
      <Button onPress={handleNext} disabled={!serverValidated}>
        다음
      </Button>
    </View>
  );
}
