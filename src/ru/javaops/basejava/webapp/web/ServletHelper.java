package ru.javaops.basejava.webapp.web;

import ru.javaops.basejava.webapp.Config;
import ru.javaops.basejava.webapp.storage.Storage;

public class ServletHelper {
    private static final Storage STORAGE = Config.getInstance().getSqlStorage();

    public static String getHtmlAllResume() {
        return  "<table style=\"width:20%\">\n" +
                "    <tr>\n" +
                "        <th>Full Name</th>\n" +
                "        <th>UUID</th>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td>FULLNAME1</td>\n" +
                "        <td>UUID1</td>\n" +
                "    </tr>\n" +
                "</table>\n";
    }
}
