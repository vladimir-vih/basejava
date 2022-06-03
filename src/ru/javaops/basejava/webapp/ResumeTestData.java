package ru.javaops.basejava.webapp;

import ru.javaops.basejava.webapp.model.*;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static ru.javaops.basejava.webapp.model.ContactType.*;
import static ru.javaops.basejava.webapp.model.SectionType.*;

public class ResumeTestData {
    static final Map<ContactType, String> CONTACTS = new EnumMap<>(ContactType.class);
    static final Map<SectionType, Section> SECTIONS = new EnumMap<>(SectionType.class);

    static {
        CONTACTS.put(MOB_NUMBER, "+7(921) 855-0482");
        CONTACTS.put(SKYPE, "skype:grigory.kislin");
        CONTACTS.put(MAIL, "gkislin@yandex.ru");
        CONTACTS.put(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        CONTACTS.put(GIT_HUB, "https://github.com/gkislin");
        CONTACTS.put(STACK_OVERFLOW, "https://stackoverflow.com/users/548473");
        CONTACTS.put(HOMEPAGE, "http://gkislin.ru/");
    }

    static {
        SECTIONS.put(PERSONAL, new CharacteristicSection(
                "Аналитический склад ума, сильная логика, креативность, инициативность. " +
                        "Пурист кода и архитектуры."));

        SECTIONS.put(OBJECTIVE, new CharacteristicSection(
                "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));

        List<String> achievementsList = new ArrayList<>();
        achievementsList.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: " +
                "приложения автопарк на стеке Spring Cloud/микросервисы, " +
                "система мониторинга показателей спортсменов на Spring Boot, " +
                "участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievementsList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов." +
                " Более 3500 выпускников.");
        achievementsList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievementsList.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                "Интеграция с 1С, Bonita BPM, CMIS, LDAP. " +
                "Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. " +
                "Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievementsList.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, " +
                "Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievementsList.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов " +
                "(SOA-base архитектура, JAX-WS, JMS, AS Glassfish). " +
                "Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. " +
                "Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievementsList.add("Реализация протоколов по приему платежей всех основных платежных системы России " +
                "(Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        SECTIONS.put(ACHIEVEMENT, new SkillsSection(achievementsList));

        List<String> qualificationList = new ArrayList<>();
        qualificationList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualificationList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualificationList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, " +
                "SQLite, MS SQL, HSQLDB");
        qualificationList.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualificationList.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualificationList.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring " +
                "(MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, " +
                "ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualificationList.add("Python: Django.");
        qualificationList.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualificationList.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualificationList.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, " +
                "SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, " +
                "LDAP, OAuth1, OAuth2, JWT.");
        qualificationList.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        qualificationList.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, " +
                "Nagios, iReport, OpenCmis, Bonita, pgBouncer");
        qualificationList.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, " +
                "архитектурных шаблонов, UML, функционального программирования");
        qualificationList.add("Родной русский, английский \"upper intermediate\"");
        SECTIONS.put(QUALIFICATIONS, new SkillsSection(qualificationList));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

        List<Experience> jobList = new LinkedList<>();

        final Link javaOpsLink = new Link("JavaOps", "http://javaops.ru/");
        final Company javaOps = new Company("Java Online Projects", javaOpsLink);
        Experience job1 = new Experience(javaOps, YearMonth.parse("10/2013", formatter).atDay(1),
                "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        jobList.add(job1);

        final Link wrikeLink = new Link("Wrike", "https://www.wrike.com/");
        final Company wrike = new Company("Wrike", wrikeLink);
        Experience job2 = new Experience(wrike,
                YearMonth.parse("10/2014", formatter).atDay(1),
                YearMonth.parse("01/2016", formatter).atDay(1),
                "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                        "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                        "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        jobList.add(job2);

        final Company ritCenter = new Company("RIT Center");
        Experience job3 = new Experience(ritCenter, YearMonth.parse("04/2012", formatter).atDay(1),
                YearMonth.parse("10/2014", formatter).atDay(1),
                "Java архитектор",
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, " +
                        "версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), " +
                        "конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. " +
                        "Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), " +
                        "сервисов общего назначения (почта, экспорт в pdf, doc, html). " +
                        "Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. " +
                        "Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, " +
                        "OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        jobList.add(job3);

        final Link luxoftLink = new Link("Luxoft", "http://www.luxoft.ru/");
        final Company luxoft = new Company("Luxoft (Deutsche Bank)", luxoftLink);
        Experience job4 = new Experience(luxoft, YearMonth.parse("12/2010", formatter).atDay(1),
                YearMonth.parse("04/2012", formatter).atDay(1),
                "Ведущий программист",
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, " +
                        "SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. " +
                        "Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области " +
                        "алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
        jobList.add(job4);

        final Link yotatLink = new Link("Yota", "https://www.yota.ru/");
        final Company yota = new Company("Yota", yotatLink);
        Experience job5 = new Experience(yota, YearMonth.parse("06/2008", formatter).atDay(1),
                YearMonth.parse("12/2010", formatter).atDay(1),
                "Ведущий специалист",
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" " +
                        "(GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). " +
                        "Реализация администрирования, статистики и мониторинга фреймворка. " +
                        "Разработка online JMX клиента (Python/ Jython, Django, ExtJS)");
        jobList.add(job5);

        final Link enkantaLink = new Link("Enkata", "http://enkata.com/");
        final Company enkanta = new Company("Enkata", enkantaLink);
        Experience job6 = new Experience(enkanta, YearMonth.parse("03/2007", formatter).atDay(1),
                YearMonth.parse("06/2008", formatter).atDay(1),
                "Разработчик ПО",
                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, " +
                        "Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).");
        jobList.add(job6);

        final Link siemensLink = new Link("Siemens AG", "https://www.siemens.com/ru/ru/home.html");
        final Company siemens = new Company("Siemens AG", siemensLink);
        Experience job7 = new Experience(siemens, YearMonth.parse("01/2005", formatter).atDay(1),
                YearMonth.parse("02/2007", formatter).atDay(1),
                "Разработчик ПО",
                "Разработка информационной модели, проектирование интерфейсов, " +
                        "реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
        jobList.add(job7);

        final Link alcatelLink = new Link("Alcatel", "http://www.alcatel.ru/");
        final Company alcatel = new Company("Alcatel", alcatelLink);
        Experience job8 = new Experience(alcatel, YearMonth.parse("09/1997", formatter).atDay(1),
                YearMonth.parse("01/2005", formatter).atDay(1),
                "Инженер по аппаратному и программному тестированию",
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции " +
                        "Alcatel 1000 S12 (CHILL, ASM).");
        jobList.add(job8);

        Collections.sort(jobList);
        SECTIONS.put(EXPERIENCE, new ExperienceSection(jobList));

        List<Experience> educationList = new LinkedList<>();

        final Link courseraLink = new Link("Coursera", "https://www.coursera.org/course/progfun");
        final Company coursera = new Company("Coursera", courseraLink);
        Experience education1 = new Experience(coursera, YearMonth.parse("03/2013", formatter).atDay(1),
                YearMonth.parse("05/2013", formatter).atDay(1),
                "'Functional Programming Principles in Scala' by Martin Odersky");
        educationList.add(education1);

        Experience education2 = new Experience(luxoft, YearMonth.parse("03/2011", formatter).atDay(1),
                YearMonth.parse("04/2011", formatter).atDay(1),
                "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'");
        educationList.add(education2);

        Experience education3 = new Experience(siemens, YearMonth.parse("01/2005", formatter).atDay(1),
                YearMonth.parse("04/2005", formatter).atDay(1),
                "3 месяца обучения мобильным IN сетям (Берлин)");
        educationList.add(education3);

        final Link ifmoLink = new Link("Санкт-Петербургский национальный исследовательский университет " +
                "информационных технологий, механики и оптики", "http://www.ifmo.ru/");
        final Company ifmo = new Company("Санкт-Петербургский национальный исследовательский университет " +
                "информационных технологий, механики и оптики", ifmoLink);
        Experience education4 = new Experience(ifmo, YearMonth.parse("09/1993", formatter).atDay(1),
                YearMonth.parse("07/1996", formatter).atDay(1),
                "Аспирантура (программист С, С++)");
        educationList.add(education4);

        Experience education5 = new Experience(ifmo, YearMonth.parse("09/1987", formatter).atDay(1),
                YearMonth.parse("07/1993", formatter).atDay(1),
                "Инженер (программист Fortran, C)");
        educationList.add(education5);

        final Link miptLink = new Link("Заочная физико-техническая школа при МФТИ",
                "http://www.school.mipt.ru/");
        final Company mipt = new Company("Заочная физико-техническая школа при МФТИ",
                miptLink);
        Experience education6 = new Experience(mipt, YearMonth.parse("09/1984", formatter).atDay(1),
                YearMonth.parse("06/1987", formatter).atDay(1),
                "Аспирантура (программист С, С++)");
        educationList.add(education6);

        Collections.sort(educationList);
        SECTIONS.put(EDUCATION, new ExperienceSection(educationList));
    }

    public static void main(String[] args) {
        Resume testResume = new Resume(UUID.randomUUID().toString(), "Григорий Кислин", CONTACTS, SECTIONS);
        System.out.println(testResume);
    }

    public static Resume getInstance(String uuid, String fullname) {
        return new Resume(uuid, fullname, CONTACTS, SECTIONS);
    }
}
