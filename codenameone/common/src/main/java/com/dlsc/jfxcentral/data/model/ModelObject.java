package com.dlsc.jfxcentral.data.model;

import com.dlsc.jfxcentral.data.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public abstract class ModelObject {

    private String id;
    private String name;
    private String summary;
    private String description;
    private LocalDate createdOn;
    private LocalDate modifiedOn;
    private String tags;
    private String canonicalUrl;
    private boolean hide;

    private List<String> personIds = new ArrayList<>();
    private List<String> tutorialIds = new ArrayList<>();
    private List<String> toolIds = new ArrayList<>();
    private List<String> utilityIds = new ArrayList<>();
    private List<String> libraryIds = new ArrayList<>();
    private List<String> bookIds = new ArrayList<>();
    private List<String> companyIds = new ArrayList<>();
    private List<String> downloadIds = new ArrayList<>();
    private List<String> videoIds = new ArrayList<>();
    private List<String> appIds = new ArrayList<>();
    private List<String> blogIds = new ArrayList<>();
    private List<String> tipIds = new ArrayList<>();
    private List<String> linksOfTheWeekIds = new ArrayList<>();
    private List<String> ikonliPackIds = new ArrayList<>();
    private List<String> memberIds = new ArrayList<>();
    private List<String> documentationIds = new ArrayList<>();
    private List<String> learnJavaFXIds = new ArrayList<>();
    private List<String> learnMobileIds = new ArrayList<>();
    private List<String> learnRaspberryPiIds = new ArrayList<>();
    private List<String> newsIds = new ArrayList<>();

    protected ModelObject() {
    }

    public boolean matches(String searchPattern) {
        if (tagsMatch(searchPattern)) {
            return true;
        }

        if (StringUtils.containsAnyIgnoreCase(getName(), searchPattern)) {
            return true;
        }

        if (StringUtils.containsAnyIgnoreCase(getSummary(), searchPattern)) {
            return true;
        }

        if (StringUtils.containsAnyIgnoreCase(getSummary(), searchPattern)) {
            return true;
        }

        return StringUtils.containsAnyIgnoreCase(getDescription(), searchPattern);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    protected boolean tagsMatch(String searchPattern) {
        if (StringUtils.isNotBlank(tags)) {
            StringTokenizer st = new StringTokenizer(getTags(), ",");
            while (st.hasMoreTokens()) {
                String tag = st.nextToken().trim();
                if (StringUtils.containsAnyIgnoreCase(tag, searchPattern)) {
                    return true;
                }
            }
        }

        return false;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCanonicalUrl() {
        return canonicalUrl;
    }

    public void setCanonicalUrl(String canonicalUrl) {
        this.canonicalUrl = canonicalUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ModelObject that = (ModelObject) o;

        return java.util.Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    public List<String> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<String> memberIds) {
        this.memberIds = memberIds;
    }

    public List<String> getPersonIds() {
        return personIds;
    }

    public void setPersonIds(List<String> personIds) {
        this.personIds = personIds;
    }

    public List<String> getTutorialIds() {
        return tutorialIds;
    }

    public void setTutorialIds(List<String> tutorialIds) {
        this.tutorialIds = tutorialIds;
    }

    public List<String> getToolIds() {
        return toolIds;
    }

    public void setToolIds(List<String> toolIds) {
        this.toolIds = toolIds;
    }

    public List<String> getUtilityIds() {
        return utilityIds;
    }

    public void setUtilityIds(List<String> utilityIds) {
        this.utilityIds = utilityIds;
    }

    public List<String> getLibraryIds() {
        return libraryIds;
    }

    public void setLibraryIds(List<String> libraryIds) {
        this.libraryIds = libraryIds;
    }

    public List<String> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<String> bookIds) {
        this.bookIds = bookIds;
    }

    public List<String> getCompanyIds() {
        return companyIds;
    }

    public void setCompanyIds(List<String> companyIds) {
        this.companyIds = companyIds;
    }

    public List<String> getDownloadIds() {
        return downloadIds;
    }

    public void setDownloadIds(List<String> downloadIds) {
        this.downloadIds = downloadIds;
    }

    public List<String> getVideoIds() {
        return videoIds;
    }

    public void setVideoIds(List<String> videoIds) {
        this.videoIds = videoIds;
    }

    public List<String> getAppIds() {
        return appIds;
    }

    public void setAppIds(List<String> appIds) {
        this.appIds = appIds;
    }

    public List<String> getNewsIds() {
        return newsIds;
    }

    public void setNewsIds(List<String> newsIds) {
        this.newsIds = newsIds;
    }

    public List<String> getBlogIds() {
        return blogIds;
    }

    public void setBlogIds(List<String> blogIds) {
        this.blogIds = blogIds;
    }

    public List<String> getTipIds() {
        return tipIds;
    }

    public void setTipIds(List<String> tipIds) {
        this.tipIds = tipIds;
    }

    public List<String> getLinksOfTheWeekIds() {
        return linksOfTheWeekIds;
    }

    public void setLinksOfTheWeekIds(List<String> linksOfTheWeekIds) {
        this.linksOfTheWeekIds = linksOfTheWeekIds;
    }

    public List<String> getIkonliPackIds() {
        return ikonliPackIds;
    }

    public void setIkonliPackIds(List<String> ikonliPackIds) {
        this.ikonliPackIds = ikonliPackIds;
    }

    public List<String> getDocumentationIds() {
        return documentationIds;
    }

    public void setDocumentationIds(List<String> documentationIds) {
        this.documentationIds = documentationIds;
    }

    public List<String> getLearnJavaFXIds() {
        return learnJavaFXIds;
    }

    public void setLearnJavaFXIds(List<String> learnJavaFXIds) {
        this.learnJavaFXIds = learnJavaFXIds;
    }

    public List<String> getLearnMobileIds() {
        return learnMobileIds;
    }

    public void setLearnMobileIds(List<String> learnMobileIds) {
        this.learnMobileIds = learnMobileIds;
    }

    public List<String> getLearnRaspberryPiIds() {
        return learnRaspberryPiIds;
    }

    public void setLearnRaspberryPiIds(List<String> learnRaspberryPiIds) {
        this.learnRaspberryPiIds = learnRaspberryPiIds;
    }

    public LocalDate getCreationOrUpdateDate() {
        LocalDate modifiedOn = getModifiedOn();
        if (modifiedOn != null) {
            return modifiedOn;
        }
        LocalDate createdOn = getCreatedOn();
        if (createdOn != null) {
            return createdOn;
        }

        return LocalDate.MIN; // no date means "very old"
    }

    /// Reads the fields declared here from a parsed JSON object.
    ///
    /// The generated mapper of a subclass covers only the subclass's own fields,
    /// so `DataRepository` calls this after it to fill in the inherited ones.
    public void readBaseFields(java.util.Map<String, Object> m) {
        id = str(m.get("id"));
        name = str(m.get("name"));
        summary = str(m.get("summary"));
        description = str(m.get("description"));
        tags = str(m.get("tags"));
        canonicalUrl = str(m.get("canonicalUrl"));
        hide = Boolean.TRUE.equals(m.get("hide")) || "true".equals(m.get("hide"));
        createdOn = date(m.get("createdOn"));
        modifiedOn = date(m.get("modifiedOn"));
        personIds = ids(m.get("personIds"));
        tutorialIds = ids(m.get("tutorialIds"));
        toolIds = ids(m.get("toolIds"));
        utilityIds = ids(m.get("utilityIds"));
        libraryIds = ids(m.get("libraryIds"));
        bookIds = ids(m.get("bookIds"));
        companyIds = ids(m.get("companyIds"));
        downloadIds = ids(m.get("downloadIds"));
        videoIds = ids(m.get("videoIds"));
        appIds = ids(m.get("appIds"));
        blogIds = ids(m.get("blogIds"));
        tipIds = ids(m.get("tipIds"));
        linksOfTheWeekIds = ids(m.get("linksOfTheWeekIds"));
        ikonliPackIds = ids(m.get("ikonliPackIds"));
        memberIds = ids(m.get("memberIds"));
        documentationIds = ids(m.get("documentationIds"));
        learnJavaFXIds = ids(m.get("learnJavaFXIds"));
        learnMobileIds = ids(m.get("learnMobileIds"));
        learnRaspberryPiIds = ids(m.get("learnRaspberryPiIds"));
        newsIds = ids(m.get("newsIds"));
    }

    private static String str(Object o) {
        return o == null ? null : o.toString();
    }

    static LocalDate date(Object o) {
        if (o instanceof String && ((String) o).length() >= 10) {
            try {
                return LocalDate.parse(((String) o).substring(0, 10));
            } catch (RuntimeException ex) {
                return null;
            }
        }
        return null;
    }

    private static List<String> ids(Object o) {
        List<String> out = new ArrayList<>();
        if (o instanceof List) {
            for (Object e : (List<?>) o) {
                if (e != null) {
                    out.add(e.toString());
                }
            }
        }
        return out;
    }
}
