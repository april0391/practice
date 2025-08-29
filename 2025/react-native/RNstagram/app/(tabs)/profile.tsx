import { useAuthContext } from "@/components/auth/AuthProvider";
import Button from "@/components/common/Button";
import ThemedSafeAreaView from "@/components/ThemedSafeAreaView";
import { ThemedText } from "@/components/ThemedText";

export default function ProfileScreen() {
  const { signOut } = useAuthContext();

  return (
    <ThemedSafeAreaView>
      <ThemedText>ProfileScreen</ThemedText>
      <Button
        onPress={() => {
          console.log("signout");
          signOut();
        }}
      >
        로그아웃
      </Button>
    </ThemedSafeAreaView>
  );
}
