package com.dlsc.jfxcentral2.utils;

import com.dlsc.jfxcentral2.model.filter.IconPack;
import com.dlsc.jfxcentral2.model.ikon.IkonData;
import com.dlsc.jfxcentral2.model.ikon.IkonPackModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.IkonProvider;

import java.lang.reflect.Type;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;

public class IkonModelUtil {

    private static final IkonModelUtil instance = new IkonModelUtil();
    private final Map<Ikon, IkonData> dataMap = new HashMap<>();
    private final Map<String, IkonData> nameMap = new HashMap<>();
    private final Set<IkonData> ikonDataSet = new TreeSet<>();
    /**
     * Key: IkonPackModel #name
     * Value: IkonPackModel
     */
    private Map<String, IkonPackModel> ikonPackModelMap;

    private IkonModelUtil() {
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
            Class providerIkon = ikonProvider.getIkon();
            nameMap.put(providerIkon.getSimpleName(), data);
            EnumSet enumSet = EnumSet.allOf(providerIkon);
            enumSet.forEach(icon -> dataMap.put((Ikon) icon, data));
        });
    }

    public static IkonModelUtil getInstance() {
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

    /**
     * unmodifiable map of IkonPackModel
     */
    public Map<String, IkonPackModel> getIkonPackModelMap() {
        if (ikonPackModelMap == null) {
            try {
                URI uri = IkonModelUtil.class.getResource("/com/dlsc/jfxcentral2/data/ikon-pack-data.json").toURI();
                Path path = Path.of(uri);
                String json = Files.readString(path);
                Type listType = new TypeToken<List<IkonPackModel>>() {
                }.getType();
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new CustomLocalDateAdapter())
                        .create();
                List<IkonPackModel> list = gson.fromJson(json, listType);
                LinkedHashMap<String, IkonPackModel> tempHash = new LinkedHashMap<>();
                list.forEach(ikonPackModel -> tempHash.put(ikonPackModel.getName(), ikonPackModel));
                ikonPackModelMap = Map.copyOf(tempHash);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ikonPackModelMap;
    }

    public IkonPackModel getIkonPackModel(String name) {
        return getIkonPackModelMap().get(name);
    }

    public IkonPackModel getIkonPackModel(IconPack iconPack) {
        return getIkonPackModelMap().get(iconPack.getDescription());
    }

    public List<IkonPackModel> getIkonPackModelList() {
        return List.copyOf(getIkonPackModelMap().values());
    }

}
