import {
  foreignKey,
  integer,
  pgSchema,
  pgTable,
  text,
  uuid,
  varchar,
} from "drizzle-orm/pg-core";
import { usersInAuth as users } from "./auth-schema.ts";

export const privateSchema = pgSchema("private");

export const ordersInPrivate = privateSchema.table("orders", {
  id: uuid().defaultRandom().primaryKey().notNull(),
  customerId: uuid("customer_id").notNull(),
  paymentId: text("payment_id").notNull(),
  productId: uuid("product_id").notNull(),
  amount: integer().notNull(),
}, (table) => [
  foreignKey({
    columns: [table.customerId],
    foreignColumns: [users.id],
    name: "orders_customer_id_fkey",
  }),
  foreignKey({
    columns: [table.productId],
    foreignColumns: [productsInPrivate.id],
    name: "orders_product_id_fkey",
  }),
]);

export const profiles = pgTable("profiles", {
  id: uuid().primaryKey().notNull(),
  username: varchar({ length: 50 }),
}, (table) => [
  foreignKey({
    columns: [table.id],
    foreignColumns: [users.id],
    name: "profiles_id_fkey",
  }),
]);

export const productsInPrivate = privateSchema.table("products", {
  id: uuid().defaultRandom().primaryKey().notNull(),
  name: varchar({ length: 100 }).notNull(),
  description: text(),
  price: integer().notNull(),
});
