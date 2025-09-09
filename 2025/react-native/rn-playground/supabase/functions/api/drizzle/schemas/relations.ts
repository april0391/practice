import { relations } from "drizzle-orm/relations";
import { usersInAuth } from "./auth-schema.ts";
import { ordersInPrivate, profiles } from "./schema.ts";

export const profilesRelations = relations(profiles, ({ one }) => ({
  usersInAuth: one(usersInAuth, {
    fields: [profiles.id],
    references: [usersInAuth.id],
  }),
}));

export const usersInAuthRelations = relations(usersInAuth, ({ many }) => ({
  profiles: many(profiles),
  ordersInPrivates: many(ordersInPrivate),
}));

export const ordersInPrivateRelations = relations(
  ordersInPrivate,
  ({ one }) => ({
    usersInAuth: one(usersInAuth, {
      fields: [ordersInPrivate.customerId],
      references: [usersInAuth.id],
    }),
  }),
);
