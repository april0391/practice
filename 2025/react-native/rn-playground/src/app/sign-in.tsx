import { useState } from "react";
import { Button, TextInput, View } from "react-native";
import { Link } from "expo-router";
import useAuth from "../stores/auth-store";

export default function SignInScreen() {
  const [credentials, setCredentials] = useState({
    email: "",
    password: "",
  });
  const { signIn } = useAuth();

  function handleInputChange(text: string, field: "email" | "password") {
    setCredentials({ ...credentials, [field]: text });
  }

  async function handleSignIn() {
    const { data, error } = await signIn({ ...credentials });

    console.log("data", data);
    console.log("error", error);
  }

  return (
    <View className="flex-1 justify-center items-center gap-3">
      <TextInput
        className="border w-4/5"
        placeholder="이메일"
        autoCapitalize="none"
        onChangeText={(text) => handleInputChange(text, "email")}
      />
      <TextInput
        className="border w-4/5"
        placeholder="비밀번호"
        autoCapitalize="none"
        secureTextEntry={true}
        onChangeText={(text) => handleInputChange(text, "password")}
      />
      <Button title="로그인" onPress={handleSignIn} />
      <Link href="/sign-up" asChild>
        <Button title="회원가입" onPress={() => {}} />
      </Link>
    </View>
  );
}
