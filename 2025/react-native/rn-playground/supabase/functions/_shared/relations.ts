import { relations } from "drizzle-orm/relations";
import {
  flowStateInAuth,
  identitiesInAuth,
  mfaAmrClaimsInAuth,
  mfaChallengesInAuth,
  mfaFactorsInAuth,
  oneTimeTokensInAuth,
  refreshTokensInAuth,
  samlProvidersInAuth,
  samlRelayStatesInAuth,
  sessionsInAuth,
  ssoDomainsInAuth,
  ssoProvidersInAuth,
  usersInAuth,
} from "./auth-schema.ts";

import { productsInApp, profilesInApp, transactionsInApp } from "./schema.ts";

export const samlRelayStatesInAuthRelations = relations(
  samlRelayStatesInAuth,
  ({ one }) => ({
    ssoProvidersInAuth: one(ssoProvidersInAuth, {
      fields: [samlRelayStatesInAuth.ssoProviderId],
      references: [ssoProvidersInAuth.id],
    }),
    flowStateInAuth: one(flowStateInAuth, {
      fields: [samlRelayStatesInAuth.flowStateId],
      references: [flowStateInAuth.id],
    }),
  }),
);

export const ssoProvidersInAuthRelations = relations(
  ssoProvidersInAuth,
  ({ many }) => ({
    samlRelayStatesInAuths: many(samlRelayStatesInAuth),
    ssoDomainsInAuths: many(ssoDomainsInAuth),
    samlProvidersInAuths: many(samlProvidersInAuth),
  }),
);

export const flowStateInAuthRelations = relations(
  flowStateInAuth,
  ({ many }) => ({
    samlRelayStatesInAuths: many(samlRelayStatesInAuth),
  }),
);

export const refreshTokensInAuthRelations = relations(
  refreshTokensInAuth,
  ({ one }) => ({
    sessionsInAuth: one(sessionsInAuth, {
      fields: [refreshTokensInAuth.sessionId],
      references: [sessionsInAuth.id],
    }),
  }),
);

export const sessionsInAuthRelations = relations(
  sessionsInAuth,
  ({ one, many }) => ({
    refreshTokensInAuths: many(refreshTokensInAuth),
    usersInAuth: one(usersInAuth, {
      fields: [sessionsInAuth.userId],
      references: [usersInAuth.id],
    }),
    mfaAmrClaimsInAuths: many(mfaAmrClaimsInAuth),
  }),
);

export const usersInAuthRelations = relations(usersInAuth, ({ many }) => ({
  sessionsInAuths: many(sessionsInAuth),
  identitiesInAuths: many(identitiesInAuth),
  oneTimeTokensInAuths: many(oneTimeTokensInAuth),
  mfaFactorsInAuths: many(mfaFactorsInAuth),
}));

export const ssoDomainsInAuthRelations = relations(
  ssoDomainsInAuth,
  ({ one }) => ({
    ssoProvidersInAuth: one(ssoProvidersInAuth, {
      fields: [ssoDomainsInAuth.ssoProviderId],
      references: [ssoProvidersInAuth.id],
    }),
  }),
);

export const mfaAmrClaimsInAuthRelations = relations(
  mfaAmrClaimsInAuth,
  ({ one }) => ({
    sessionsInAuth: one(sessionsInAuth, {
      fields: [mfaAmrClaimsInAuth.sessionId],
      references: [sessionsInAuth.id],
    }),
  }),
);

export const samlProvidersInAuthRelations = relations(
  samlProvidersInAuth,
  ({ one }) => ({
    ssoProvidersInAuth: one(ssoProvidersInAuth, {
      fields: [samlProvidersInAuth.ssoProviderId],
      references: [ssoProvidersInAuth.id],
    }),
  }),
);

export const identitiesInAuthRelations = relations(
  identitiesInAuth,
  ({ one }) => ({
    usersInAuth: one(usersInAuth, {
      fields: [identitiesInAuth.userId],
      references: [usersInAuth.id],
    }),
  }),
);

export const oneTimeTokensInAuthRelations = relations(
  oneTimeTokensInAuth,
  ({ one }) => ({
    usersInAuth: one(usersInAuth, {
      fields: [oneTimeTokensInAuth.userId],
      references: [usersInAuth.id],
    }),
  }),
);

export const mfaFactorsInAuthRelations = relations(
  mfaFactorsInAuth,
  ({ one, many }) => ({
    usersInAuth: one(usersInAuth, {
      fields: [mfaFactorsInAuth.userId],
      references: [usersInAuth.id],
    }),
    mfaChallengesInAuths: many(mfaChallengesInAuth),
  }),
);

export const mfaChallengesInAuthRelations = relations(
  mfaChallengesInAuth,
  ({ one }) => ({
    mfaFactorsInAuth: one(mfaFactorsInAuth, {
      fields: [mfaChallengesInAuth.factorId],
      references: [mfaFactorsInAuth.id],
    }),
  }),
);

// ============ auth schema relations end ============

export const productsInAppRelations = relations(productsInApp, ({ one }) => ({
  usersInAuth: one(usersInAuth, {
    fields: [productsInApp.sellerId],
    references: [usersInAuth.id],
  }),
}));

export const transactionsInAppRelations = relations(
  transactionsInApp,
  ({ one }) => ({
    usersInAuth_buyerId: one(usersInAuth, {
      fields: [transactionsInApp.buyerId],
      references: [usersInAuth.id],
      relationName: "transactionsInApp_buyerId_usersInAuth_id",
    }),
    usersInAuth_sellerId: one(usersInAuth, {
      fields: [transactionsInApp.sellerId],
      references: [usersInAuth.id],
      relationName: "transactionsInApp_sellerId_usersInAuth_id",
    }),
  }),
);

export const profilesInAppRelations = relations(profilesInApp, ({ one }) => ({
  usersInAuth: one(usersInAuth, {
    fields: [profilesInApp.id],
    references: [usersInAuth.id],
  }),
}));
