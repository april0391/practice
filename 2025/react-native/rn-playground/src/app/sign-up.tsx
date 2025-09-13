import { useState } from "react";
import { Button, Text, TextInput, View } from "react-native";
import { Checkbox } from "expo-checkbox";
import { supabase } from "../utils/supabase";

export default function SignUpScreen() {
  const [signUpForm, setSignUpForm] = useState({
    email: "",
    password: "",
    avatarUrl: "",
    role: "seller",
  });

  function handleInputChange(input: string, field: keyof typeof signUpForm) {
    setSignUpForm({ ...signUpForm, [field]: input });
  }

  async function handleSignUp() {
    console.log("signUpForm", signUpForm);
    const { data, error } = await supabase.auth.signUp({
      email: signUpForm.email,
      password: signUpForm.password,
      options: {
        data: {
          avarar_url: signUpForm.avatarUrl,
          role: signUpForm.role,
        },
      },
    });
    console.log("data", data);
    console.log("error", error);
  }

  return (
    <View className="flex-1 justify-center items-center gap-4 p-5">
      <TextInput
        className="border border-gray-500 w-full"
        placeholder="이메일"
        onChangeText={(text) => {
          handleInputChange(text, "email");
        }}
      />
      <TextInput
        className="border border-gray-500 w-full"
        placeholder="비밀번호"
        secureTextEntry={true}
        onChangeText={(text) => {
          handleInputChange(text, "password");
        }}
      />
      <View className="flex-row gap-2">
        <Text>구매자</Text>
        <Checkbox
          value={signUpForm.role === "buyer"}
          onValueChange={(_v) => {
            handleInputChange("buyer", "role");
          }}
        />
      </View>
      <View className="flex-row gap-2">
        <Text>판매자</Text>
        <Checkbox
          value={signUpForm.role === "seller"}
          onValueChange={(_v) => {
            handleInputChange("seller", "role");
          }}
        />
      </View>
      <Button title="회원가입" onPress={handleSignUp} />
    </View>
  );
}
