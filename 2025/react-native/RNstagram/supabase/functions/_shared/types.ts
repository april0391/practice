import type { Context } from "hono";

export type ContextBody<T> = Context<{ Variables: { body: T } }>;
