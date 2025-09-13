import { Button } from "react-native";
import { supabase } from "../utils/supabase";

export default function Temp() {
  return (
    <Button
      title="dd"
      onPress={() => {
        supabase.auth.updateUser({
          data: {
            role: "admin",
          },
        });
      }}
    />
  );
}
