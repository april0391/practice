import { Button, Text, View } from "react-native";
import useAuth from "@/src/stores/auth-store";
import ImagePickerExample from "@/src/components/image-picker";

export default function ProfileTab() {
  const { session, refreshSession, signOut } = useAuth();

  return (
    <View className="flex-1 justify-center items-center gap-4">
      <ImagePickerExample />
      <Text>{session?.claims.app_role}</Text>
      <Button title="세션 갱신" onPress={refreshSession} />
      <Button title="로그아웃" onPress={signOut} />
    </View>
  );
}
