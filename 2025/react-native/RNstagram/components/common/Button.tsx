import { Colors } from "@/constants/Colors";
import type { StyleProp, ViewStyle } from "react-native";
import { Pressable, StyleSheet } from "react-native";
import { ThemedText } from "../ThemedText";
import { ThemedView } from "../ThemedView";

type ButtonType = "default" | "outline" | "ghost";

type Props = {
  children: React.ReactNode;
  onPress: () => void;
  variant?: ButtonType;
  style?: StyleProp<ViewStyle>;
  textColor?: string;
};

export default function Button({
  children,
  onPress,
  variant = "default",
  style,
  textColor,
}: Props) {
  return (
    <Pressable onPress={onPress}>
      <ThemedView style={[styles.base, styles[variant], style]}>
        <ThemedText style={textColor && { color: textColor }}>
          {children}
        </ThemedText>
      </ThemedView>
    </Pressable>
  );
}

const styles = StyleSheet.create({
  base: {
    alignItems: "center",
    padding: 8,
  },
  default: {
    borderRadius: 20,
    backgroundColor: Colors.primary500,
  },
  outline: {
    borderWidth: 1,
    borderRadius: 20,
    borderColor: Colors.primary300,
  },
  ghost: {
    alignItems: "center",
  },
});
