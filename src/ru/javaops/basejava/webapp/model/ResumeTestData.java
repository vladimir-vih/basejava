package ru.javaops.basejava.webapp.model;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static ru.javaops.basejava.webapp.model.ContactType.*;
import static ru.javaops.basejava.webapp.model.SectionType.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
        contacts.put(MOB_NUMBER, "+7(921) 855-0482");
        contacts.put(SKYPE, "skype:grigory.kislin");
        contacts.put(MAIL, "gkislin@yandex.ru");
        contacts.put(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        contacts.put(GIT_HUB, "https://github.com/gkislin");
        contacts.put(STACK_OVERFLOW, "https://stackoverflow.com/users/548473");
        contacts.put(HOMEPAGE, "http://gkislin.ru/");

        Map<SectionType, Section<?>> sections = new EnumMap<>(SectionType.class);

        sections.put(PERSONAL, new CharacteristicSection(
                "Аналитический склад ума, сильная логика, креативность, инициативность. " +
                        "Пурист кода и архитектуры."));

        sections.put(OBJECTIVE, new CharacteristicSection(
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
        sections.put(ACHIEVEMENT, new SkillsSection(achievementsList));

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
        sections.put(QUALIFICATIONS, new SkillsSection(qualificationList));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

        List<Experience> jobList = new LinkedList<>();

        Experience job1 = new Experience();
        job1.setCompanyName("Java Online Projects");
        job1.setCompanyUrl("http://javaops.ru/");
        job1.setStartDate(YearMonth.parse("10/2013", formatter).atDay(1));
        job1.setCurrentPosition(true);
        job1.setShortInfo("Автор проекта.");
        job1.setDetailedInfo("Создание, организация и проведение Java онлайн проектов и стажировок.");
        jobList.add(job1);

        Experience job2 = new Experience();
        job2.setCompanyName("Wrike");
        job2.setCompanyUrl("https://www.wrike.com/");
        job2.setStartDate(YearMonth.parse("10/2014", formatter).atDay(1));
        job2.setEndDate(YearMonth.parse("01/2016", formatter).atDay(1));
        job2.setShortInfo("Старший разработчик (backend)");
        job2.setDetailedInfo("Проектирование и разработка онлайн платформы управления проектами Wrike " +
                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        jobList.add(job2);

        Experience job3 = new Experience();
        job3.setCompanyName("RIT Center");
        job3.setStartDate(YearMonth.parse("04/2012", formatter).atDay(1));
        job3.setEndDate(YearMonth.parse("10/2014", formatter).atDay(1));
        job3.setShortInfo("Java архитектор");
        job3.setDetailedInfo("Организация процесса разработки системы ERP для разных окружений: релизная политика, " +
                "версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), " +
                "конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. " +
                "Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), " +
                "сервисов общего назначения (почта, экспорт в pdf, doc, html). " +
                "Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. " +
                "Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, " +
                "OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        jobList.add(job3);

        Experience job4 = new Experience();
        job4.setCompanyName("Luxoft (Deutsche Bank)");
        job4.setCompanyUrl("http://www.luxoft.ru/");
        job4.setStartDate(YearMonth.parse("12/2010", formatter).atDay(1));
        job4.setEndDate(YearMonth.parse("04/2012", formatter).atDay(1));
        job4.setShortInfo("Ведущий программист");
        job4.setDetailedInfo("Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, " +
                "SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. " +
                "Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области " +
                "алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
        jobList.add(job4);

        Experience job5 = new Experience();
        job5.setCompanyName("Yota");
        job5.setCompanyUrl("https://www.yota.ru/");
        job5.setStartDate(YearMonth.parse("06/2008", formatter).atDay(1));
        job5.setEndDate(YearMonth.parse("12/2010", formatter).atDay(1));
        job5.setShortInfo("Ведущий специалист");
        job5.setDetailedInfo("Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" " +
                "(GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). " +
                "Реализация администрирования, статистики и мониторинга фреймворка. " +
                "Разработка online JMX клиента (Python/ Jython, Django, ExtJS)");
        jobList.add(job5);

        Experience job6 = new Experience();
        job6.setCompanyName("Enkata");
        job6.setCompanyUrl("http://enkata.com/");
        job6.setStartDate(YearMonth.parse("03/2007", formatter).atDay(1));
        job6.setEndDate(YearMonth.parse("06/2008", formatter).atDay(1));
        job6.setShortInfo("Разработчик ПО");
        job6.setDetailedInfo("Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, " +
                "Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).");
        jobList.add(job6);

        Experience job7 = new Experience();
        job7.setCompanyName("Siemens AG");
        job7.setCompanyUrl("https://www.siemens.com/ru/ru/home.html");
        job7.setStartDate(YearMonth.parse("01/2005", formatter).atDay(1));
        job7.setEndDate(YearMonth.parse("02/2007", formatter).atDay(1));
        job7.setShortInfo("Разработчик ПО");
        job7.setDetailedInfo("Разработка информационной модели, проектирование интерфейсов, " +
                "реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
        jobList.add(job7);

        Experience job8 = new Experience();
        job8.setCompanyName("Alcatel");
        job8.setCompanyUrl("http://www.alcatel.ru/");
        job8.setStartDate(YearMonth.parse("09/1997", formatter).atDay(1));
        job8.setEndDate(YearMonth.parse("01/2005", formatter).atDay(1));
        job8.setShortInfo("Инженер по аппаратному и программному тестированию");
        job8.setDetailedInfo("Тестирование, отладка, внедрение ПО цифровой телефонной станции " +
                "Alcatel 1000 S12 (CHILL, ASM).");
        jobList.add(job8);

        Collections.sort(jobList);
        sections.put(EXPERIENCE, new ExperienceSection(jobList));

        List<Experience> educationList = new LinkedList<>();

        Experience education1 = new Experience();
        education1.setCompanyName("Coursera");
        education1.setCompanyUrl("https://www.coursera.org/course/progfun");
        education1.setStartDate(YearMonth.parse("03/2013", formatter).atDay(1));
        education1.setEndDate(YearMonth.parse("05/2013", formatter).atDay(1));
        education1.setShortInfo("'Functional Programming Principles in Scala' by Martin Odersky");
        educationList.add(education1);

        Experience education2 = new Experience();
        education2.setCompanyName("Luxoft");
        education2.setCompanyUrl("http://www.luxoft-training.ru/training/catalog/course.html?ID=22366");
        education2.setStartDate(YearMonth.parse("03/2011", formatter).atDay(1));
        education2.setEndDate(YearMonth.parse("04/2011", formatter).atDay(1));
        education2.setShortInfo("Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'");
        educationList.add(education2);

        Experience education3 = new Experience();
        education3.setCompanyName("Siemens AG");
        education3.setCompanyUrl("http://www.siemens.ru/");
        education3.setStartDate(YearMonth.parse("01/2005", formatter).atDay(1));
        education3.setEndDate(YearMonth.parse("04/2005", formatter).atDay(1));
        education3.setShortInfo("3 месяца обучения мобильным IN сетям (Берлин)");
        educationList.add(education3);

        Collections.sort(educationList);
        sections.put(EDUCATION, new ExperienceSection(educationList));

        Resume testResume = new Resume(UUID.randomUUID().toString(), "Григорий Кислин", contacts, sections);
        System.out.println(testResume);
    }
}
