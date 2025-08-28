import type {
  IconLibrary,
  IconNames,
  Icon as IconType,
} from "@/types/app-types";
import {
  AntDesign,
  FontAwesome,
  Ionicons,
  MaterialIcons,
} from "@expo/vector-icons";
import type { StyleProp, TextStyle } from "react-native";

type Props<L extends IconLibrary = IconLibrary> = {
  color: string;
  size?: number;
  style?: StyleProp<TextStyle>;
} & IconType<L>;

export default function Icon<L extends IconLibrary = IconLibrary>({
  library,
  name,
  color,
  size = 24,
  style,
}: Props<L>) {
  const IconMap: Record<IconLibrary, any> = {
    ionicons: Ionicons,
    material: MaterialIcons,
    antDesign: AntDesign,
    fontAwesome: FontAwesome,
  };

  const IconComponent = IconMap[library];

  return (
    <IconComponent
      name={name as IconNames[L]}
      color={color}
      size={size}
      style={style}
    />
  );
}
