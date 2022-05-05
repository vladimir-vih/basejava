package ru.javaops.basejava.webapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;

public class Experience implements Comparable<Experience>, Serializable {
    private final Company company;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String shortInfo;
    private final String detailedInfo;

    public Experience(Company company, LocalDate startDate, LocalDate endDate, String shortInfo) {
        this(company, startDate, endDate, shortInfo, null);
    }

    public Experience(Company company, LocalDate startDate, String shortInfo, String detailedInfo) {
        this(company, startDate, LocalDate.MAX, shortInfo, detailedInfo);
    }

    public Experience(Company company, LocalDate startDate, LocalDate endDate, String shortInfo, String detailedInfo) {
        Objects.requireNonNull(company, "Company can't be null");
        Objects.requireNonNull(startDate, "Start date can't be null");
        Objects.requireNonNull(shortInfo, "Short info can't be null");
        this.company = company;
        this.startDate = startDate;
        this.endDate = endDate;
        this.shortInfo = shortInfo;
        this.detailedInfo = detailedInfo;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getShortInfo() {
        return shortInfo;
    }

    public String getDetailedInfo() {
        return detailedInfo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\n").append(YearMonth.from(startDate)).append(" - ");
        if (endDate.equals(LocalDate.MAX)) {
            sb.append("по настоящее время");
        } else sb.append(YearMonth.from(endDate));
        sb.append("\n").append(company.toString());
        if (shortInfo != null) sb.append("\n").append(shortInfo);
        if (detailedInfo != null) sb.append("\n").append(detailedInfo);
        return sb.toString();
    }

    @Override
    public int compareTo(Experience o) {
        if (this.isCurrentPosition() && !o.isCurrentPosition()) {
            return -1;
        } else if (!this.isCurrentPosition() && o.isCurrentPosition()) {
            return 1;
        } else {
            int i = startDate.compareTo(o.startDate);
            if (i != 0) i = -i; //reverse sort by startDate
            return i;
        }
    }

    private boolean isCurrentPosition() {
        return endDate.equals(LocalDate.MAX);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        return company.equals(that.company)
                && getStartDate().equals(that.getStartDate())
                && getEndDate().equals(that.getEndDate())
                && getShortInfo().equals(that.getShortInfo())
                && Objects.equals(getDetailedInfo(), that.getDetailedInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, getStartDate(), getEndDate(), getShortInfo());
    }
}
