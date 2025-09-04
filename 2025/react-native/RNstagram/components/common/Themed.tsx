import { View, Text } from "react-native";
import type { ViewProps, TextProps } from "react-native";
import {
  SafeAreaView,
  type SafeAreaViewProps,
} from "react-native-safe-area-context";
import { cn } from "@/utils/cn";

import { useThemeColor } from "@/hooks/useThemeColor";

export type ThemedViewProps = ViewProps & {
  lightColor?: string;
  darkColor?: string;
};

export type ThemedTextProps = TextProps & {
  lightColor?: string;
  darkColor?: string;
  className?: string;
  type?: "default" | "title" | "defaultSemiBold" | "subtitle" | "link";
};

export function ThemedView({
  style,
  lightColor,
  darkColor,
  ...otherProps
}: ThemedViewProps) {
  const backgroundColor = useThemeColor(
    { light: lightColor, dark: darkColor },
    "background"
  );

  return <View style={[{ backgroundColor }, style]} {...otherProps} />;
}

export function ThemedText({
  style,
  lightColor,
  darkColor,
  type = "default",
  className,
  ...rest
}: ThemedTextProps) {
  const color = useThemeColor({ light: lightColor, dark: darkColor }, "text");

  return (
    <Text
      style={!className ? { color } : undefined}
      className={cn(
        type === "title" && "text-3xl font-semibold leading-8",
        type === "subtitle" && "text-xl font-bold",
        type === "default" && "text-lg leading-6",
        type === "defaultSemiBold" && "text-base font-semibold",
        type === "link" && "text-base leading-7 text-[#0a7ea]",
        className
      )}
      {...rest}
    />
  );
}

export type ThemedSafeAreaViewProps = SafeAreaViewProps & {
  lightColor?: string;
  darkColor?: string;
};

export function ThemedSafeAreaView({
  style,
  lightColor,
  darkColor,
  ...otherProps
}: ThemedSafeAreaViewProps) {
  const backgroundColor = useThemeColor(
    { light: lightColor, dark: darkColor },
    "background"
  );

  return (
    <SafeAreaView
      style={[{ backgroundColor }, { flex: 1 }, style]}
      {...otherProps}
    />
  );
}
