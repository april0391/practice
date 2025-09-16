import { useState } from "react";
import { View } from "react-native";
import { Link } from "expo-router";
import useAuth from "../stores/auth-store";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Text } from "@/components/ui/text";

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
      <Input
        className="w-4/5"
        keyboardType="email-address"
        textContentType="emailAddress"
        autoComplete="email"
        autoCapitalize="none"
        placeholder="이메일"
        onChangeText={(text) => handleInputChange(text, "email")}
      />
      <Input
        className="w-4/5"
        placeholder="비밀번호"
        autoCapitalize="none"
        secureTextEntry={true}
        onChangeText={(text) => handleInputChange(text, "password")}
      />
      <Button onPress={handleSignIn} variant="default">
        <Text>로그인</Text>
      </Button>
      <Link href="/sign-up" asChild>
        <Button variant="outline">
          <Text>회원가입</Text>
        </Button>
      </Link>
    </View>
  );
}
