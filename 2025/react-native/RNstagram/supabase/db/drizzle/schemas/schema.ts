import {
	char,
	foreignKey,
	index,
	pgSchema,
	pgTable,
	text,
	timestamp,
	unique,
	uuid,
	varchar,
} from "drizzle-orm/pg-core";
import { usersInAuth as users } from "./auth-schema.ts";
export { usersInAuth } from "./auth-schema.ts";

export const privateSchema = pgSchema("private");

export const otpsInPrivate = privateSchema.table("otps", {
	id: uuid().defaultRandom().primaryKey().notNull(),
	email: text().notNull(),
	otpNumber: char("otp_number", { length: 6 }).notNull(),
	createdAt: timestamp("created_at", { withTimezone: true, mode: "string" })
		.defaultNow().notNull(),
	expiresAt: timestamp("expires_at", { withTimezone: true, mode: "string" })
		.notNull(),
	consumedAt: timestamp("consumed_at", { withTimezone: true, mode: "string" }),
}, (table) => [
	index("idx_signup_otps_email").using(
		"btree",
		table.email.asc().nullsLast().op("text_ops"),
	),
]);

export const profiles = pgTable("profiles", {
	id: uuid().primaryKey().notNull(),
	email: varchar({ length: 100 }).notNull(),
	name: varchar({ length: 100 }),
	username: varchar({ length: 100 }).notNull(),
	birthDate: char("birth_date", { length: 10 }).notNull(),
	bio: text(),
	avatarUrl: varchar("avatar_url", { length: 200 }),
}, (table) => [
	foreignKey({
		columns: [table.id],
		foreignColumns: [users.id],
		name: "profiles_id_fkey",
	}),
	unique("profiles_email_key").on(table.email),
	unique("profiles_username_key").on(table.username),
]);
