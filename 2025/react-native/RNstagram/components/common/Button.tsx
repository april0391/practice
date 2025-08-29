import { cn } from "@/utils/cn";
import { Pressable, Text, View } from "react-native";

type ButtonType = "default" | "outline" | "ghost";

type Props = {
  onPress: () => void;
  variant?: ButtonType;
  disabled?: boolean;
  className?: string;
  children: React.ReactNode;
};

export default function Button({
  onPress,
  variant = "default",
  disabled = false,
  className,
  children,
}: Props) {
  return (
    <Pressable onPress={onPress} disabled={disabled}>
      <View
        className={cn(
          "items-center p-3",
          variant === "default" && `rounded-3xl bg-primary-300`,
          variant === "outline" && `border rounded-3xl border-primary-300`,
          disabled && "opacity-60",
          className
        )}
      >
        <Text
          className={cn(
            variant === "default" && "text-white",
            variant === "outline" && "text-primary-300"
          )}
        >
          {children}
        </Text>
      </View>
    </Pressable>
  );
}
