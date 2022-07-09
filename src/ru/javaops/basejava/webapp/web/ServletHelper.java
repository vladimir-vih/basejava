package ru.javaops.basejava.webapp.web;

import ru.javaops.basejava.webapp.Config;
import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.model.Resume;
import ru.javaops.basejava.webapp.storage.Storage;

import java.util.List;

public class ServletHelper {
    private static final Storage STORAGE = Config.getInstance().getSqlStorage();
    private static final StringBuilder TABLE_HEAD = new StringBuilder();
    private static final String TABLE_FOOTER = "</table>\n";

    static  {
        TABLE_HEAD.append("<style>\n")
                .append("    table, th, td {\n")
                .append("        border:1px solid black;\n")
                .append("    }\n")
                .append("</style>")
                .append("<table style=\"width:20%\">\n")
                .append("    <tr>\n")
                .append("        <th>Full Name</th>\n")
                .append("        <th>UUID</th>\n")
                .append("    <tr>\n");
    }

    public static String getHtmlAllResume() {
        final StringBuilder finalTable = new StringBuilder(TABLE_HEAD);
        final List<Resume> resumeList = STORAGE.getAllSorted();
        for (Resume r : resumeList) {
            addResumeRow(r, finalTable);
        }
        finalTable.append(TABLE_FOOTER);
        return  finalTable.toString();
    }

    public static String getHtmlResume(String uuid) {
        final StringBuilder finalTable = new StringBuilder(TABLE_HEAD);
        try {
            final Resume r = STORAGE.get(uuid);
            addResumeRow(r, finalTable);
            finalTable.append(TABLE_FOOTER);
            return finalTable.toString();
        } catch (NotExistStorageException e) {
         return "Resume doesn't exist";
        }
    }

    private static void addResumeRow(Resume r, StringBuilder sb){
        final String fullName = r.getFullName();
        final String uuid = r.getUuid();
        sb.append("    <tr>\n")
                .append("        <td>").append(fullName).append("</td>\n")
                .append("        <td>").append(uuid).append("</td>\n")
                .append("    </tr>\n");
    }
}
