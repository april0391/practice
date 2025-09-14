import { useState } from "react";
import { Button, Text, TextInput, View } from "react-native";
import { Checkbox } from "expo-checkbox";
import useAuth from "../stores/auth-store";

export default function SignUpScreen() {
  const [signUpForm, setSignUpForm] = useState({
    email: "",
    password: "",
    role: "buyer",
  });
  const { signUp } = useAuth();

  function handleInputChange(input: string, field: keyof typeof signUpForm) {
    setSignUpForm({ ...signUpForm, [field]: input });
  }

  async function handleSignUp() {
    const { data, error } = await signUp(signUpForm);

    console.log("data", data);
    console.log("error", error);
  }

  return (
    <View className="flex-1 justify-center items-center gap-4 p-5">
      <TextInput
        className="border border-gray-500 w-full"
        placeholder="이메일"
        autoCapitalize="none"
        onChangeText={(text) => {
          handleInputChange(text, "email");
        }}
      />
      <TextInput
        className="border border-gray-500 w-full"
        placeholder="비밀번호"
        autoCapitalize="none"
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
