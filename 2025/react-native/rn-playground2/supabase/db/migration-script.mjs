import fs from "fs/promises";
import path from "path";

const basePath = "db/sql";
const files = ["schemas.sql", "tables.sql", "functions.sql", "triggers.sql"];

async function mergeSQLFiles() {
  let mergedSQL = "";

  for (const file of files) {
    const filePath = path.join(basePath, file);
    try {
      const content = await fs.readFile(filePath, "utf-8");
      mergedSQL += `-- FILE: ${filePath}\n`;
      mergedSQL += content + "\n\n";
    } catch (err) {
      if (err.code === "ENOENT") {
        console.warn(`파일이 없습니다: ${filePath}`);
        continue;
      } else {
        throw err;
      }
    }
  }

  const mergedFileName = `migrations/${getTimestamp()}_${fileNameArg || "init"}.sql`;

  await fs.writeFile(mergedFileName, mergedSQL, "utf-8");
  console.log(`${mergedFileName} 생성 완료!`);
}

mergeSQLFiles().catch(console.error);

const args = process.argv.slice(2);
let fileNameArg = "";
for (let i = 0; i < args.length; i++) {
  if (args[i] === "-n" && i + 1 < args.length) {
    fileNameArg = args[i + 1];
  }
}

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
