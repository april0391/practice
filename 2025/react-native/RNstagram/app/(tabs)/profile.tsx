import { useAuthContext } from "@/components/auth/AuthProvider";
import Button from "@/components/common/Button";
import { SafeAreaView, Text } from "@/components/common/Themed";

export default function ProfileScreen() {
  const { signOut } = useAuthContext();

  return (
    <SafeAreaView>
      <Text>ProfileScreen</Text>
      <Button
        onPress={() => {
          console.log("signout");
          signOut();
        }}
      >
        로그아웃
      </Button>
    </SafeAreaView>
  );
}
