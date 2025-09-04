import fs from "fs/promises";
import path from "path";

const basePath = "supabase/db/sql";
const dirs = ["/schemas", "/functions", "/triggers", "/tables"];

const fileOrderMap = {
  // "/schemas": ["..."],
  "/tables": ["otps.sql", "profiles.sql", "follows.sql"],
};

function getTimestamp() {
  const now = new Date();

  const YYYY = now.getFullYear().toString();
  const MM = String(now.getMonth() + 1).padStart(2, "0");
  const DD = String(now.getDate()).padStart(2, "0");
  const HH = String(now.getHours()).padStart(2, "0");
  const mm = String(now.getMinutes()).padStart(2, "0");
  const ss = String(now.getSeconds()).padStart(2, "0");
  return `${YYYY}${MM}${DD}${HH}${mm}${ss}`;
}

const args = process.argv.slice(2);
let fileNameArg = "";
for (let i = 0; i < args.length; i++) {
  if (args[i] === "-n" && i + 1 < args.length) {
    fileNameArg = args[i + 1];
  }
}

const mergedFile = `supabase/migrations/${getTimestamp()}_${fileNameArg || "init"}.sql`;

async function mergeSQLFiles() {
  let mergedSQL = "";

  for (const dir of dirs) {
    const fullDir = path.join(basePath, dir);
    const files = await fs.readdir(fullDir);
    let sqlFiles = files.filter((f) => f.endsWith(".sql"));

    const order = fileOrderMap[dir];
    if (order) {
      sqlFiles.sort((a, b) => {
        const aIndex = order.indexOf(a);
        const bIndex = order.indexOf(b);
        if (aIndex !== -1 && bIndex !== -1) return aIndex - bIndex;
        if (aIndex !== -1) return -1;
        if (bIndex !== -1) return 1;
        return a.localeCompare(b);
      });
    } else {
      sqlFiles.sort();
    }

    for (const file of sqlFiles) {
      const filePath = path.join(fullDir, file);
      const content = await fs.readFile(filePath, "utf-8");

      mergedSQL += `\n-- FILE: ${filePath}\n`;
      mergedSQL += content + "\n";
    }
  }

  await fs.writeFile(mergedFile, mergedSQL, "utf-8");
  console.log(`${mergedFile} 생성 완료!`);
}

mergeSQLFiles().catch(console.error);
