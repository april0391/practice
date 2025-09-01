import { relations } from "drizzle-orm/relations";
import { usersInAuth } from "./auth-schema";
import { profiles } from "./schema";

export const profilesRelations = relations(profiles, ({ one }) => ({
	usersInAuth: one(usersInAuth, {
		fields: [profiles.id],
		references: [usersInAuth.id],
	}),
}));

export const usersInAuthRelations = relations(usersInAuth, ({ many }) => ({
	profiles: many(profiles),
}));
