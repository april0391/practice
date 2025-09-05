import { Text } from "@/components/common/Themed";
import { View } from "@/components/common/Themed";
import { Ionicons } from "@expo/vector-icons";
import { StyleSheet } from "react-native";

export default function Header() {
  return (
    <View style={styles.header}>
      <Text type="title">RNstagram</Text>
      <View style={{ flexDirection: "row", gap: 10 }}>
        <Ionicons name="heart-outline" size={24} />
        <Ionicons name="send" size={24} />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  header: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    gap: 8,
    paddingVertical: 7,
    paddingHorizontal: 14,
  },
});
