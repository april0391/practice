import { pgTable, varchar } from "drizzle-orm/pg-core";
import { sql } from "drizzle-orm";

export const profiles = pgTable("profiles", {
	avataUrl: varchar("avata_url", { length: 200 }),
	role: varchar({ length: 20 }),
});
