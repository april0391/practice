import { useEffect, useState } from "react";
import { Button, StyleSheet, Text, View } from "react-native";
import { Image } from "expo-image";
import { supabase } from "@/src/libs/supabase";
import useAuth from "@/src/stores/auth-store";

import ImagePickerExample from "@/src/components/image-picker";
import Avatar from "@/src/components/avatar";

export default function ProfileTab() {
  const [avatarUrl, setAvatarUrl] = useState("");
  const { session, refreshSession, signOut } = useAuth();

  useEffect(() => {
    const {
      data: { publicUrl },
    } = supabase.storage.from("avatars").getPublicUrl("1757944354160.png");

    setAvatarUrl(publicUrl);
  }, []);

  return (
    <View className="flex-1 justify-center items-center gap-4">
      {/* <ImagePickerExample /> */}
      <View style={styles.container}>
        <Image style={styles.image} source={avatarUrl} contentFit="cover" />
      </View>
      <Avatar
        size={200}
        url={null}
        onUpload={(url: string) => {
          // setAvatarUrl(url)
          // updateProfile({ username, website, avatar_url: url })
        }}
      />
      <Text>{session!.claims.user_role}</Text>
      <Button title="세션 갱신" onPress={refreshSession} />
      <Button title="로그아웃" onPress={signOut} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    width: 200,
    height: 200,
    alignItems: "center",
    justifyContent: "center",
  },
  image: {
    flex: 1,
    width: "100%",
    height: "100%",
    backgroundColor: "#0553",
  },
});
