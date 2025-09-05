import {
  View as RNView,
  Text as RNText,
  TextInput as RNTextInput,
  useColorScheme,
} from "react-native";
import type { ViewProps, TextProps, TextInputProps } from "react-native";
import {
  SafeAreaView as RNSafeAreaView,
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

export function View({
  style,
  lightColor,
  darkColor,
  ...otherProps
}: ThemedViewProps) {
  const backgroundColor = useThemeColor(
    { light: lightColor, dark: darkColor },
    "background"
  );

  return <RNView style={[{ backgroundColor }, style]} {...otherProps} />;
}

export function Text({
  style,
  lightColor,
  darkColor,
  type = "default",
  className,
  ...rest
}: ThemedTextProps) {
  const color = useThemeColor({ light: lightColor, dark: darkColor }, "text");

  return (
    <RNText
      style={!className && { color }}
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

export function SafeAreaView({
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
    <RNSafeAreaView
      style={[{ backgroundColor }, { flex: 1 }, style]}
      {...otherProps}
    />
  );
}

export function TextInput({ className, ...otherProps }: TextInputProps) {
  const colorScheme = useColorScheme();

  return (
    <RNTextInput
      className={cn("text-black dark:text-white", className)}
      placeholderTextColor={colorScheme === "dark" ? "#aaa" : "#666"}
      {...otherProps}
    />
  );
}
