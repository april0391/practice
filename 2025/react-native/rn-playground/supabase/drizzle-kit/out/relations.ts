import { relations } from "drizzle-orm/relations";
import { usersInAuth, profilesInApp, productsInApp, transactionsInApp } from "./schema";

export const profilesInAppRelations = relations(profilesInApp, ({one, many}) => ({
	usersInAuth: one(usersInAuth, {
		fields: [profilesInApp.id],
		references: [usersInAuth.id]
	}),
	productsInApps: many(productsInApp),
	transactionsInApps_buyerId: many(transactionsInApp, {
		relationName: "transactionsInApp_buyerId_profilesInApp_id"
	}),
	transactionsInApps_sellerId: many(transactionsInApp, {
		relationName: "transactionsInApp_sellerId_profilesInApp_id"
	}),
}));

export const usersInAuthRelations = relations(usersInAuth, ({many}) => ({
	profilesInApps: many(profilesInApp),
}));

export const productsInAppRelations = relations(productsInApp, ({one}) => ({
	profilesInApp: one(profilesInApp, {
		fields: [productsInApp.sellerId],
		references: [profilesInApp.id]
	}),
}));

export const transactionsInAppRelations = relations(transactionsInApp, ({one}) => ({
	profilesInApp_buyerId: one(profilesInApp, {
		fields: [transactionsInApp.buyerId],
		references: [profilesInApp.id],
		relationName: "transactionsInApp_buyerId_profilesInApp_id"
	}),
	profilesInApp_sellerId: one(profilesInApp, {
		fields: [transactionsInApp.sellerId],
		references: [profilesInApp.id],
		relationName: "transactionsInApp_sellerId_profilesInApp_id"
	}),
}));