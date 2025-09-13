import {
  View as RNView,
  ScrollView as RNScrollView,
  Text as RNText,
} from "react-native";
import type { ViewProps, TextProps } from "react-native";
import { cn } from "@/utils/cn";

export function View({ className, ...props }: ViewProps) {
  return (
    <RNView
      className={cn("bg-zinc-50 dark:bg-zinc-800", className)}
      {...props}
    />
  );
}

export function Text({ className, ...props }: TextProps) {
  return (
    <RNText
      {...props}
      className={cn("text-black dark:text-white", className)}
    />
  );
}

export function ScrollView({ className, ...props }: ViewProps) {
  return (
    <RNScrollView
      className={cn("bg-zinc-50 dark:bg-zinc-800", className)}
      {...props}
    />
  );
}
