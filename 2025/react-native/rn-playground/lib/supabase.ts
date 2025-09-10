import * as Device from "expo-device";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { createClient } from "@supabase/supabase-js";

console.log("isDevice", Device.isDevice);

const supabaseUrl = Device.isDevice
  ? process.env.EXPO_PUBLIC_SUPABASE_URL!
  : process.env.EXPO_PUBLIC_SUPABASE_URL_AVD!;
const supabaseAnonKey = process.env.EXPO_PUBLIC_SUPABASE_ANON_KEY!;

export const supabase = createClient(supabaseUrl, supabaseAnonKey, {
  auth: {
    storage: AsyncStorage,
    autoRefreshToken: true,
    persistSession: true,
    detectSessionInUrl: false,
  },
});
