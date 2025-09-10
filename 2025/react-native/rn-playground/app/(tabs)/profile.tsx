import { View } from "@/components/common/Themed";
import { useAuthStore } from "@/store/authStore";
import { Button } from "react-native";

export default function ProfileTab() {
  const { signOut } = useAuthStore();

  return (
    <View>
      <Button title="logout" onPress={signOut} />
    </View>
  );
}
