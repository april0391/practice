-- Current sql file was generated after introspecting the database
-- If you want to run this migration please uncomment this code before executing migrations
/*
CREATE TABLE "profiles" (
	"id" uuid PRIMARY KEY NOT NULL,
	"email" varchar(100) NOT NULL,
	"name" varchar(100),
	"username" varchar(100) NOT NULL,
	"birth_date" char(10) NOT NULL,
	"bio" text,
	"avatar_url" varchar(200),
	CONSTRAINT "profiles_email_key" UNIQUE("email"),
	CONSTRAINT "profiles_username_key" UNIQUE("username")
);
--> statement-breakpoint
ALTER TABLE "profiles" ADD CONSTRAINT "profiles_id_fkey" FOREIGN KEY ("id") REFERENCES "auth"."users"("id") ON DELETE no action ON UPDATE no action;
*/