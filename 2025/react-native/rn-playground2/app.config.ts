import type { ConfigContext, ExpoConfig } from "expo/config";

const IS_DEV = !process.env.APP_VARIANT ||
  process.env.APP_VARIANT === "development";
const IS_PREVIEW = process.env.APP_VARIANT === "preview";

const getUniqueIdentifier = () => {
  if (IS_DEV) {
    return "com.april0391.RNplayground.dev";
  }

  if (IS_PREVIEW) {
    return "com.april0391.RNplayground.preview";
  }

  return "com.april0391.RNplayground";
};

const getAppName = () => {
  if (IS_DEV) {
    return "RNplayground (Dev)";
  }

  if (IS_PREVIEW) {
    return "RNplayground (Preview)";
  }

  return "RNplayground";
};

export default ({ config }: ConfigContext): ExpoConfig => ({
  ...config,
  name: getAppName(),
  slug: config.slug!,
  ios: {
    ...config.ios,
    bundleIdentifier: getUniqueIdentifier(),
  },
  android: {
    ...config.android,
    package: getUniqueIdentifier(),
  },
  plugins: [
    ...(config.plugins ?? []),
  ],
});
