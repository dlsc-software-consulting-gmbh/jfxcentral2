package com.dlsc.jfxcentral2.utils;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Dependency;
import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral2.model.IkonData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.IkonProvider;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class IkonliPackUtil {

    private static final IkonliPackUtil instance = new IkonliPackUtil();
    private final Map<Ikon, IkonData> dataMap = new HashMap<>();
    private final Map<String, IkonData> nameMap = new HashMap<>();
    private final Set<IkonData> ikonDataSet = new TreeSet<>();
    private Set<Ikon> allIkons;

    private IkonliPackUtil() {
        if (null != IkonProvider.class.getModule().getLayer()) {
            for (IkonProvider provider : ServiceLoader.load(IkonProvider.class.getModule().getLayer(), IkonProvider.class)) {
                ikonDataSet.add(IkonData.of(provider));
            }
        } else {
            for (IkonProvider provider : ServiceLoader.load(IkonProvider.class)) {
                ikonDataSet.add(IkonData.of(provider));
            }
        }
        List<IkonliPack> ikonliPacks = DataRepository.getInstance().getIkonliPacks();

        ikonDataSet.forEach(data -> {
            for (IkonliPack pack : ikonliPacks) {
                if (pack.getName().equals(data.getName())) {
                    data.setIkonliPack(pack);
                    break;
                }
            }
            IkonProvider ikonProvider = data.getIkonProvider();
            Class ikonProviderClass = ikonProvider.getIkon();
            nameMap.put(ikonProviderClass.getSimpleName(), data);
            EnumSet enumSet = EnumSet.allOf(ikonProviderClass);
            enumSet.forEach(icon -> dataMap.put((Ikon) icon, data));
        });
    }

    public static IkonliPackUtil getInstance() {
        return instance;
    }

    public IkonData getIkonData(Ikon ikon) {
        return dataMap.get(ikon);
    }

    public Set<IkonData> getIkonDataSet() {
        return ikonDataSet;
    }

    public Map<Ikon, IkonData> getDataMap() {
        return dataMap;
    }

    public IkonData getIkonData(String simpleName) {
        return nameMap.get(simpleName);
    }

    public ObservableList<Ikon> getIkonList(IkonliPack iconPack) {
        IkonData ikonData = getIkonData(iconPack.getName());
        IkonProvider ikonProvider = ikonData.getIkonProvider();
        EnumSet enumSet = EnumSet.allOf(ikonProvider.getIkon());
        ObservableList<Ikon> list = FXCollections.observableArrayList(enumSet);
        for (Ikon ikon : list) {
            IkonData tempData = getIkonData(ikon);
            if (tempData != null && tempData.getIkonliPack() == null) {
                tempData.setIkonliPack(iconPack);
            }
        }
        return list;
    }

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

    /**
     * Find a specific Ikon by its description.
     *
     * @param iconPack    the pack to search in
     * @param description the description to search for
     * @return optional containing the Ikon if found
     */
    public Optional<Ikon> getIkon(IkonliPack iconPack, String description) {
        IkonData ikonData = getIkonData(iconPack.getName());
        IkonProvider ikonProvider = ikonData.getIkonProvider();
        Set<Ikon> enumSet = EnumSet.allOf(ikonProvider.getIkon());
        for (Ikon ikon : enumSet) {
            if (StringUtils.equals(ikon.getDescription(), description)) {
                return Optional.of(ikon);
            }
        }
        return Optional.empty();
    }

    /**
     * Retrieves all available Ikons as an unmodifiable set. If the set has not
     * been initialized yet, it is created from the keys of the data map and
     * cached for future access.
     *
     * @return a set containing all available Ikons
     */
    public Set<Ikon> getAllIkons() {
        if (allIkons == null) {
            allIkons = Collections.unmodifiableSet(dataMap.keySet());
        }
        return allIkons;
    }


    /**
     * Filters and retrieves a list of Ikons that belong to the specified collection of IkonliPacks.
     *
     * @param packs the collection of IkonliPacks to filter Ikons by; if null or empty, the method returns an empty list
     * @return a list of Ikons associated with the specified IkonliPacks, or an empty list if no matching Ikons are found
     */
    public List<Ikon> getIkonsForPacksFiltered(Collection<IkonliPack> packs) {
        if (packs == null || packs.isEmpty()) {
            return Collections.emptyList();
        }
        long start = System.currentTimeMillis();

        Set<String> packNames = packs.stream()
                .map(IkonliPack::getName)
                .collect(Collectors.toSet());

        List<Ikon> list = dataMap.entrySet().stream()
                .filter(entry -> {
                    IkonData data = entry.getValue();
                    return data.getIkonliPack() != null && packNames.contains(data.getIkonliPack().getName());
                })
                .map(Map.Entry::getKey)
                .toList();
        System.out.println("IkonliPackUtil.getIkonsForPacksFiltered took " + (System.currentTimeMillis() - start) + "ms");
        return list;
    }

}
