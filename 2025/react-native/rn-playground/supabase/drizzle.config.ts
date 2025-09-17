import { defineConfig } from "drizzle-kit";

export default defineConfig({
  schema: "./functions/_shared/schema.ts",
  out: "./drizzle-kit/out",
  dialect: "postgresql",
  dbCredentials: {
    url: "postgresql://postgres:postgres@127.0.0.1:54322/postgres",
  },
  schemaFilter: ["app"],
  tablesFilter: ["*"],
});
