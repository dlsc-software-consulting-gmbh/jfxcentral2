package com.dlsc.jfxcentral2.utils;

import com.dlsc.jfxcentral2.model.IkonData;
import com.dlsc.jfxcentral2.model.filter.IconPack;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.IkonProvider;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;

public class IkonliPackUtil {

    private static final IkonliPackUtil instance = new IkonliPackUtil();
    private final Map<Ikon, IkonData> dataMap = new HashMap<>();
    private final Map<String, IkonData> nameMap = new HashMap<>();
    private final Set<IkonData> ikonDataSet = new TreeSet<>();

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

        ikonDataSet.forEach(data -> {
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

    public List<Ikon> getIkonList(IconPack iconPack) {
        IkonProvider ikonProvider = iconPack.getIkonData().getIkonProvider();
        EnumSet enumSet = EnumSet.allOf(ikonProvider.getIkon());
        return List.copyOf(new ArrayList<Ikon>(enumSet));
    }
}
