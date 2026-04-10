package com.dlsc.jfxcentral2.utils;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Dependency;
import com.dlsc.jfxcentral.data.model.IconStyle;
import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral.data.model.Installing;
import com.dlsc.jfxcentral.data.model.Maven;
import com.dlsc.jfxcentral2.model.IkonData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.IkonProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;

public class IkonliPackUtil {

    // ==================== Constants ====================

    private static final String ARTIFACT_PREFIX = "ikonli-";
    private static final String ARTIFACT_SUFFIX = "-pack";

    // ==================== Singleton ====================

    private static final IkonliPackUtil instance = new IkonliPackUtil();

    // ==================== Internal Data Structures ====================

    /**
     * Maps each individual Ikon enum constant to its IkonData.
     */
    private final Map<Ikon, IkonData> dataMap = new HashMap<>();

    /**
     * Maps enum class simple name to IkonData. Retained for internal compatibility.
     * Note: FA5/FA6 share the same simple names, so the last registered wins.
     * This is acceptable because dataMap is the authoritative lookup for individual icons.
     */
    private final Map<String, IkonData> nameMap = new HashMap<>();

    /**
     * All discovered IkonData entries, sorted by name.
     */
    private final Set<IkonData> ikonDataSet = new TreeSet<>();

    /**
     * Aggregated id (derived from artifactId) to aggregated IkonliPack instance.
     * Each artifactId maps to exactly one aggregated IkonliPack for display.
     */
    private final Map<String, IkonliPack> aggregatedPackMap = new LinkedHashMap<>();

    /**
     * Aggregated id to the list of IkonData entries belonging to that artifact.
     */
    private final Map<String, List<IkonData>> groupedByArtifact = new LinkedHashMap<>();

    private Set<Ikon> allIkons;

    // ==================== Constructor ====================

    private IkonliPackUtil() {
        // Step 1: Discover all IkonProviders via ServiceLoader
        if (null != IkonProvider.class.getModule().getLayer()) {
            for (IkonProvider provider : ServiceLoader.load(IkonProvider.class.getModule().getLayer(), IkonProvider.class)) {
                ikonDataSet.add(IkonData.of(provider));
            }
        } else {
            for (IkonProvider provider : ServiceLoader.load(IkonProvider.class)) {
                ikonDataSet.add(IkonData.of(provider));
            }
        }

        // Step 2: Match IkonData to IkonliPack using name + module (fixes FA5/FA6 conflict)
        List<IkonliPack> ikonliPacks = DataRepository.getInstance().getIkonliPacks();

        ikonDataSet.forEach(data -> {
            for (IkonliPack pack : ikonliPacks) {
                if (pack.getName().equals(data.getName())
                        && moduleMatches(data.getIkonProvider().getIkon().getPackageName(), pack.getModule())) {
                    data.setIkonliPack(pack);
                    break;
                }
            }
            IkonProvider ikonProvider = data.getIkonProvider();
            Class<?> ikonProviderClass = ikonProvider.getIkon();
            nameMap.put(ikonProviderClass.getSimpleName(), data);
            EnumSet<?> enumSet = EnumSet.allOf(ikonProviderClass.asSubclass(Enum.class));
            enumSet.forEach(icon -> dataMap.put((Ikon) icon, data));
        });

        // Step 3: Group by artifactId and create aggregated IkonliPack instances
        buildAggregatedPacks();
    }

    // ==================== Aggregation Logic ====================

    private void buildAggregatedPacks() {
        // Group IkonData by aggregated id (derived from artifactId)
        for (IkonData data : ikonDataSet) {
            IkonliPack originalPack = data.getIkonliPack();
            if (originalPack == null) {
                continue;
            }
            String aggregatedId = extractAggregatedId(originalPack);
            if (aggregatedId == null) {
                continue;
            }
            groupedByArtifact.computeIfAbsent(aggregatedId, k -> new ArrayList<>()).add(data);
        }

        // Create one aggregated IkonliPack per group
        for (Map.Entry<String, List<IkonData>> entry : groupedByArtifact.entrySet()) {
            String aggregatedId = entry.getKey();
            List<IkonData> dataList = entry.getValue();
            IkonliPack representative = dataList.get(0).getIkonliPack();

            IkonliPack aggregated = new IkonliPack();
            aggregated.setId(aggregatedId);
            aggregated.setName(representative.getTitle());
            aggregated.setTitle(representative.getTitle());
            aggregated.setDescription(representative.getDescription());
            aggregated.setModule(representative.getModule());
            aggregated.setInstalling(representative.getInstalling());
            aggregated.setUrl(representative.getUrl());
            aggregated.setFontVersion(representative.getFontVersion());
            aggregated.setCreatedOn(representative.getCreatedOn());
            aggregated.setModifiedOn(representative.getModifiedOn());
            aggregated.setIconStyle(resolveIconStyle(dataList));

            aggregatedPackMap.put(aggregatedId, aggregated);
        }
    }

    /**
     * Derives the aggregated id from an IkonliPack's artifactId.
     * Example: "ikonli-materialdesign2-pack" becomes "materialdesign2".
     */
    private String extractAggregatedId(IkonliPack pack) {
        Installing installing = pack.getInstalling();
        if (installing == null || installing.getMaven() == null || installing.getMaven().getDependency() == null) {
            return null;
        }
        String artifactId = installing.getMaven().getDependency().getArtifactId();
        if (artifactId == null) {
            return null;
        }
        String id = artifactId;
        if (id.startsWith(ARTIFACT_PREFIX)) {
            id = id.substring(ARTIFACT_PREFIX.length());
        }
        if (id.endsWith(ARTIFACT_SUFFIX)) {
            id = id.substring(0, id.length() - ARTIFACT_SUFFIX.length());
        }
        return id;
    }

    /**
     * Resolves the iconStyle for an aggregated pack.
     * If all sub-packs share the same style, use that; otherwise MIXING.
     */
    private IconStyle resolveIconStyle(List<IkonData> dataList) {
        IconStyle first = null;
        for (IkonData data : dataList) {
            IkonliPack pack = data.getIkonliPack();
            if (pack == null || pack.getIconStyle() == null) {
                continue;
            }
            if (first == null) {
                first = pack.getIconStyle();
            } else if (first != pack.getIconStyle()) {
                return IconStyle.MIXING;
            }
        }
        return first != null ? first : IconStyle.MIXING;
    }

    /**
     * Checks whether a Java package name matches a JSON module field value.
     * Handles the case where the module field may be a short name (e.g., "materialdesign2")
     * instead of the full package name (e.g., "org.kordamp.ikonli.materialdesign2").
     */
    private static boolean moduleMatches(String packageName, String module) {
        if (packageName == null || module == null) {
            return false;
        }
        return packageName.equals(module) || packageName.endsWith("." + module);
    }

    // ==================== Singleton Access ====================

    public static IkonliPackUtil getInstance() {
        return instance;
    }

    // ==================== Individual Icon Lookup ====================

    /**
     * Gets the IkonData for a specific icon.
     */
    public IkonData getIkonData(Ikon ikon) {
        return dataMap.get(ikon);
    }

    /**
     * Gets the IkonData by enum class simple name.
     * Note: For FA5/FA6 with same simple names, only the last registered is returned.
     */
    public IkonData getIkonData(String simpleName) {
        return nameMap.get(simpleName);
    }

    /**
     * Gets all discovered IkonData entries.
     */
    public Set<IkonData> getIkonDataSet() {
        return ikonDataSet;
    }

    /**
     * Gets the raw data map (Ikon to IkonData).
     */
    public Map<Ikon, IkonData> getDataMap() {
        return dataMap;
    }

    // ==================== Aggregated Pack Access ====================

    /**
     * Returns the list of aggregated IkonliPack instances for display.
     * Each entry represents one Maven artifact (one dependency).
     */
    public List<IkonliPack> getAggregatedPacks() {
        return new ArrayList<>(aggregatedPackMap.values());
    }

    /**
     * Gets an aggregated IkonliPack by its aggregated id.
     */
    public IkonliPack getAggregatedPack(String aggregatedId) {
        return aggregatedPackMap.get(aggregatedId);
    }

    /**
     * Gets the aggregated id for a given IkonliPack.
     * Works for both original and aggregated IkonliPack instances.
     */
    public String getAggregatedId(IkonliPack pack) {
        // Check if this is already an aggregated pack
        if (aggregatedPackMap.containsKey(pack.getId())) {
            return pack.getId();
        }
        // Otherwise derive from artifactId
        return extractAggregatedId(pack);
    }

    // ==================== Icon List Methods ====================

    /**
     * Gets all icons for a given IkonliPack. Supports both original and aggregated packs.
     * For aggregated packs, returns the union of all sub-packs' icons.
     */
    public ObservableList<Ikon> getIkonList(IkonliPack iconPack) {
        String aggregatedId = getAggregatedId(iconPack);
        List<IkonData> dataList = groupedByArtifact.get(aggregatedId);

        if (dataList != null) {
            ObservableList<Ikon> result = FXCollections.observableArrayList();
            for (IkonData data : dataList) {
                IkonProvider ikonProvider = data.getIkonProvider();
                EnumSet<?> enumSet = EnumSet.allOf(ikonProvider.getIkon().asSubclass(Enum.class));
                for (Object icon : enumSet) {
                    Ikon ikon = (Ikon) icon;
                    result.add(ikon);
                    // Ensure IkonData has its IkonliPack set
                    IkonData tempData = getIkonData(ikon);
                    if (tempData != null && tempData.getIkonliPack() == null) {
                        tempData.setIkonliPack(iconPack);
                    }
                }
            }
            return result;
        }

        // Fallback: single pack lookup via nameMap
        IkonData ikonData = nameMap.get(iconPack.getName());
        if (ikonData != null) {
            IkonProvider ikonProvider = ikonData.getIkonProvider();
            EnumSet<?> enumSet = EnumSet.allOf(ikonProvider.getIkon().asSubclass(Enum.class));
            ObservableList<Ikon> list = FXCollections.observableArrayList();
            for (Object icon : enumSet) {
                list.add((Ikon) icon);
            }
            return list;
        }

        return FXCollections.observableArrayList();
    }

    /**
     * Retrieves all available Ikons as an unmodifiable set.
     */
    public Set<Ikon> getAllIkons() {
        if (allIkons == null) {
            allIkons = Collections.unmodifiableSet(dataMap.keySet());
        }
        return allIkons;
    }

    /**
     * Filters and retrieves icons belonging to the specified collection of IkonliPacks.
     * Supports both original and aggregated IkonliPack instances.
     */
    public List<Ikon> getIkonsForPacksFiltered(Collection<IkonliPack> packs) {
        if (packs == null || packs.isEmpty()) {
            return Collections.emptyList();
        }

        // Collect all original pack names from the given packs (expanding aggregated ones)
        Set<String> allOriginalPackNames = new java.util.HashSet<>();
        for (IkonliPack pack : packs) {
            String aggregatedId = getAggregatedId(pack);
            List<IkonData> dataList = groupedByArtifact.get(aggregatedId);
            if (dataList != null) {
                for (IkonData data : dataList) {
                    if (data.getIkonliPack() != null) {
                        allOriginalPackNames.add(data.getIkonliPack().getName());
                    }
                }
            } else {
                // Fallback: treat as a direct pack name
                allOriginalPackNames.add(pack.getName());
            }
        }

        return dataMap.entrySet().stream()
                .filter(entry -> {
                    IkonData data = entry.getValue();
                    return data.getIkonliPack() != null && allOriginalPackNames.contains(data.getIkonliPack().getName());
                })
                .map(Map.Entry::getKey)
                .toList();
    }

    // ==================== Icon Search ====================

    /**
     * Finds a specific Ikon by its description within a pack.
     * Supports both original and aggregated IkonliPack instances.
     *
     * @param iconPack    the pack to search in
     * @param description the description to search for
     * @return optional containing the Ikon if found
     */
    public Optional<Ikon> getIkon(IkonliPack iconPack, String description) {
        String aggregatedId = getAggregatedId(iconPack);
        List<IkonData> dataList = groupedByArtifact.get(aggregatedId);

        if (dataList != null) {
            for (IkonData data : dataList) {
                IkonProvider ikonProvider = data.getIkonProvider();
                Set<?> enumSet = EnumSet.allOf(ikonProvider.getIkon().asSubclass(Enum.class));
                for (Object icon : enumSet) {
                    Ikon ikon = (Ikon) icon;
                    if (StringUtils.equals(ikon.getDescription(), description)) {
                        return Optional.of(ikon);
                    }
                }
            }
            return Optional.empty();
        }

        // Fallback: single pack lookup
        IkonData ikonData = nameMap.get(iconPack.getName());
        if (ikonData != null) {
            IkonProvider ikonProvider = ikonData.getIkonProvider();
            Set<?> enumSet = EnumSet.allOf(ikonProvider.getIkon().asSubclass(Enum.class));
            for (Object icon : enumSet) {
                Ikon ikon = (Ikon) icon;
                if (StringUtils.equals(ikon.getDescription(), description)) {
                    return Optional.of(ikon);
                }
            }
        }
        return Optional.empty();
    }

    // ==================== Dependency Info ====================

    public String getMavenDependency(Ikon ikon) {
        IkonliPack ikonliPack = getIkonData(ikon).getIkonliPack();
        if (ikonliPack == null) {
            return "No dependency found.";
        }

        Dependency dependency = ikonliPack.getInstalling().getMaven().getDependency();
        String line = System.lineSeparator();
        return "<dependency>" + line +
                "    <groupId>" + dependency.getGroupId() + "</groupId>" + line +
                "    <artifactId>" + dependency.getArtifactId() + "</artifactId>" + line +
                "    <version>" + dependency.getVersion() + "</version>" + line +
                "</dependency>";
    }

    public String getGradleDependency(Ikon ikon) {
        IkonliPack ikonliPack = getIkonData(ikon).getIkonliPack();
        if (ikonliPack == null) {
            return "No dependency found.";
        }

        return ikonliPack.getInstalling().getGradle();
    }
}
