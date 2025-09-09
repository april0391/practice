import { useState } from "react";
import { Button, TextInput } from "react-native";
import { supabase } from "@/lib/supabase";

import { Text, View } from "@/components/common/Themed";

export default function SignUpScreen() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  return (
    <View className="flex-1 gap-7 justify-center items-center p-10">
      <Text className="text-3xl">회원가입</Text>
      <TextInput
        className="border border-gray-400 w-full"
        placeholder="이메일"
        onChangeText={setEmail}
      />
      <TextInput
        placeholder="비밀번호"
        className="border border-gray-400 w-full"
        secureTextEntry={true}
        autoCapitalize="none"
        onChangeText={setPassword}
      />
      <Button
        title="회원가입"
        onPress={async () => {
          const { data, error } = await supabase.auth.signUp({
            email,
            password,
            options: {
              data: {
                username: "tester",
              },
            },
          });
          console.log("data", data);
          console.log("error", error);
        }}
      />
    </View>
  );
}
