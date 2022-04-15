package ru.javaops.basejava.webapp.model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;

public class Experience implements Comparable<Experience>{
    private String companyName;
    private String companyUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private String shortInfo;
    private String detailedInfo;
    private boolean currentPosition = false;

    public boolean isCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(boolean currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getShortInfo() {
        return shortInfo;
    }

    public void setShortInfo(String shortInfo) {
        this.shortInfo = shortInfo;
    }

    public String getDetailedInfo() {
        return detailedInfo;
    }

    public void setDetailedInfo(String detailedInfo) {
        this.detailedInfo = detailedInfo;
    }

    @Override
    public String toString(){
        final StringBuilder sb = new StringBuilder();
        sb.append("\n").append(YearMonth.from(startDate)).append(" - ");
        if(currentPosition) {
            sb.append("по настоящее время");
        } else sb.append(YearMonth.from(endDate));
        sb.append("\n").append("Название компании: ").append(companyName);
        if(companyUrl != null) sb.append("\n").append("Web site компании: ").append(companyUrl);
        if(shortInfo != null) sb.append("\n").append(shortInfo);
        if(detailedInfo != null) sb.append("\n").append(detailedInfo);
        return sb.toString();
    }

    @Override
    public int compareTo(Experience o) {
        if (currentPosition && !o.currentPosition) {
            return -1;
        } else if (!currentPosition && o.currentPosition) {
            return 1;
        } else {
            int i = startDate.compareTo(o.startDate);
            if (i != 0) i = -i; //reverse sort by startDate
            return i;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        return currentPosition == that.currentPosition
                && getCompanyName().equals(that.getCompanyName())
                && Objects.equals(getCompanyUrl(), that.getCompanyUrl())
                && Objects.equals(getStartDate(), that.getStartDate())
                && Objects.equals(isCurrentPosition(), that.isCurrentPosition())
                && Objects.equals(getEndDate(), that.getEndDate())
                && Objects.equals(getShortInfo(), that.getShortInfo())
                && Objects.equals(getDetailedInfo(), that.getDetailedInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCompanyName(), getStartDate());
    }
}
