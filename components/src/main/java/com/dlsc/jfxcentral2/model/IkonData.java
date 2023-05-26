package com.dlsc.jfxcentral2.model;

import org.kordamp.ikonli.IkonProvider;

public class IkonData implements Comparable<IkonData> {
    private String name;
    private IkonProvider ikonProvider;

    public IkonData() {
    }

    public IkonData(String name, IkonProvider ikonProvider) {
        this.name = name;
        this.ikonProvider = ikonProvider;
    }

    @Override
    public int compareTo(IkonData o) {
        return name.compareTo(o.name);
    }

    public IkonProvider getIkonProvider() {
        return ikonProvider;
    }

    static IkonData of(IkonProvider ikonProvider) {
        IkonData ikonData = new IkonData();
        ikonData.name = ikonProvider.getIkon().getSimpleName();
        ikonData.ikonProvider = ikonProvider;
        return ikonData;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIkonProvider(IkonProvider ikonProvider) {
        this.ikonProvider = ikonProvider;
    }
}