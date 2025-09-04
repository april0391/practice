import type { ComponentProps } from "react";
import type {
  AntDesign,
  FontAwesome,
  Ionicons,
  MaterialIcons,
} from "@expo/vector-icons";

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

export type ApiErrorCode =
  | "invalid_otp"
  | "otp_expired"
  | "duplicate_email"
  | "invalid_username"
  | "duplicate_username"
  //
  | "invalid_json"
  | "invalid_input"
  | "missing_field"
  //
  | "functions_http_error"
  | "functions_relay_error"
  | "functions_fetch_error"
  //
  | "bad_request"
  | "method_not_allowed"
  | "internal_server_error";

export type SignUpData = {
  email: string;
  password: string;
  username: string;
  name: string;
  birthDate: string;
  agreedToPolicies: boolean;
};
