// https://docs.expo.dev/guides/using-eslint/
const { defineConfig } = require("eslint/config");
const expoConfig = require("eslint-config-expo/flat");

module.exports = defineConfig([
  expoConfig,
  {
    ignores: ["dist/*"],
    rules: {
      "no-unused-vars": ["error"], // 빌드 시 오류로 처리
      "unused-imports/no-unused-imports": "off", // 자동 삭제 막기
      "import/no-unresolved": [
        "error",
        {
          ignore: ["^jsr:", "^npm:", "postgres", "drizzle-orm/*"], // deno
        },
      ],
    },
  },
]);
