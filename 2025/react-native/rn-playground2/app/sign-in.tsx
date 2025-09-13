import { useState } from "react";
import { Button, TextInput } from "react-native";
import { Link } from "expo-router";

// import { useAuth } from "@/store/AuthProvider";
import { useAuthStore } from "@/store/authStore";

import { Text, View } from "@/components/common/Themed";

export default function SignInScreen() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  // const { signIn } = useAuth();
  const { signIn } = useAuthStore();

  return (
    <View className="flex-1 gap-7 justify-center items-center p-10">
      <Text className="text-3xl">로그인</Text>
      <TextInput
        className="border border-gray-400 w-full"
        placeholder="이메일"
        autoCapitalize="none"
        onChangeText={setEmail}
      />
      <TextInput
        placeholder="비밀번호"
        className="border border-gray-400 w-full"
        secureTextEntry={true}
        autoCapitalize="none"
        onChangeText={setPassword}
      />

      <Button title="로그인" onPress={() => signIn(email, password)} />
      <Link href="/sign-up" asChild>
        <Button title="회원가입" />
      </Link>
    </View>
  );
}
