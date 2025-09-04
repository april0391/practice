import { ThemedText } from "@/components/common/Themed";
import { ThemedView } from "@/components/common/Themed";
import { Ionicons } from "@expo/vector-icons";
import { StyleSheet } from "react-native";

export default function Header() {
  return (
    <ThemedView style={styles.header}>
      <ThemedText type="title">RNstagram</ThemedText>
      <ThemedView style={{ flexDirection: "row", gap: 10 }}>
        <Ionicons name="heart-outline" size={24} />
        <Ionicons name="send" size={24} />
      </ThemedView>
    </ThemedView>
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
