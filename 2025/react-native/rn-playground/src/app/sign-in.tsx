import { useState } from "react";
import { View } from "react-native";
import { Link } from "expo-router";
import { createClient } from "@supabase/supabase-js";
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
      <Button
        variant="outline"
        onPress={async () => {
          const supabase = createClient(
            "http://10.0.2.2:54321",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZS1kZW1vIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImV4cCI6MTk4MzgxMjk5Nn0.EGIM96RAZx35lJzdJsyH-qQwv8Hdp7fsn3W0YpN81IU"
          );
          const { data, error } = await supabase.auth.admin.createUser({
            email: `admin23@test.com`,
            password: "1234qwer",
            email_confirm: true,
            user_metadata: {
              init_data: { role: "seller" },
            },
          });
          console.log("data", data);
          console.log("error", error);
        }}
      >
        <Text>어드민 생성</Text>
      </Button>
    </View>
  );
}
