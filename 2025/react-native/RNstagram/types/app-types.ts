import type {
  AntDesign,
  FontAwesome,
  Ionicons,
  MaterialIcons,
} from "@expo/vector-icons";
import type { ComponentProps } from "react";

export type IconLibrary = "ionicons" | "material" | "antDesign" | "fontAwesome";

export type IconNames = {
  ionicons: ComponentProps<typeof Ionicons>["name"];
  material: ComponentProps<typeof MaterialIcons>["name"];
  antDesign: ComponentProps<typeof AntDesign>["name"];
  fontAwesome: ComponentProps<typeof FontAwesome>["name"];
};

export type Icon<L extends IconLibrary = IconLibrary> = {
  library: L;
  name: IconNames[L];
};

export type Tab = {
  name: string;
  label: string;
  icon: Icon;
};
