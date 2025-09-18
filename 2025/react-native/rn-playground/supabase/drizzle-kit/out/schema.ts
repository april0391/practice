import { pgTable, pgSchema, foreignKey, uuid, text, integer } from "drizzle-orm/pg-core"
import { sql } from "drizzle-orm"

export const app = pgSchema("app");
export const userRoleInApp = app.enum("user_role", ['buyer', 'seller'])


export const profilesInApp = app.table("profiles", {
	id: uuid().primaryKey().notNull(),
	avatarUrl: text("avatar_url"),
	role: userRoleInApp().notNull(),
}, (table) => [
	foreignKey({
			columns: [table.id],
			foreignColumns: [users.id],
			name: "profiles_id_fkey"
		}).onDelete("cascade"),
]);

export const productsInApp = app.table("products", {
	id: integer().primaryKey().generatedByDefaultAsIdentity({ name: "app.products_id_seq", startWith: 1, increment: 1, minValue: 1, maxValue: 2147483647, cache: 1 }),
	name: text().notNull(),
	description: text(),
	imageUrl: text("image_url"),
	price: integer().notNull(),
	sellerId: uuid("seller_id"),
}, (table) => [
	foreignKey({
			columns: [table.sellerId],
			foreignColumns: [profilesInApp.id],
			name: "products_seller_id_fkey"
		}).onDelete("cascade"),
]);

export const transactionsInApp = app.table("transactions", {
	id: uuid().defaultRandom().primaryKey().notNull(),
	buyerId: uuid("buyer_id").notNull(),
	sellerId: uuid("seller_id").notNull(),
	amount: integer().notNull(),
}, (table) => [
	foreignKey({
			columns: [table.buyerId],
			foreignColumns: [profilesInApp.id],
			name: "transactions_buyer_id_fkey"
		}),
	foreignKey({
			columns: [table.sellerId],
			foreignColumns: [profilesInApp.id],
			name: "transactions_seller_id_fkey"
		}),
]);
