import { defineConfig } from "drizzle-kit";

export default defineConfig({
  schema: "./db/drizzle/schema.ts",
  out: "./db/drizzle/out",
  dialect: "postgresql",
  dbCredentials: {
    url: "postgresql://postgres:postgres@127.0.0.1:54322/postgres",
  },
  schemaFilter: ["public", "private"],
  tablesFilter: ["*"],
});
