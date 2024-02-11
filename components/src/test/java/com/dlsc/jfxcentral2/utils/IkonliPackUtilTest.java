package com.dlsc.jfxcentral2.utils;

import com.dlsc.jfxcentral2.model.IkonData;
import org.junit.jupiter.api.Test;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class IkonliPackUtilTest {

    @Test
    public void testGetInstance() {
        IkonliPackUtil instance = IkonliPackUtil.getInstance();
        assertNotNull(instance);
    }

    @Test
    public void testGetIkonData() {
        IkonliPackUtil instance = IkonliPackUtil.getInstance();
        IkonData data = instance.getIkonData("TestIcon");
        assertNull(data); // Assumes "TestIcon" does not exist in initial data set
    }

    @Test
    public void testGetIkonDataSet() {
        IkonliPackUtil instance = IkonliPackUtil.getInstance();
        Set<IkonData> dataSet = instance.getIkonDataSet();
        assertNotNull(dataSet);
    }

    @Test
    public void testGetDataMap() {
        IkonliPackUtil instance = IkonliPackUtil.getInstance();
        Map<Ikon, IkonData> dataMap = instance.getDataMap();
        assertNotNull(dataMap);
    }

    @Test
    public void testGetMavenDependency() {
        IkonliPackUtil instance = IkonliPackUtil.getInstance();
        String dependency = instance.getMavenDependency(MaterialDesign.MDI_ACCOUNT_CHECK);
        assertNotNull(dependency); // Assumes "TestIcon" does not exist in initial data set
    }

    @Test
    public void testGetGradleDependency() {
        IkonliPackUtil instance = IkonliPackUtil.getInstance();
        String dependency = instance.getGradleDependency(MaterialDesign.MDI_ACCOUNT_CHECK);
        assertNotNull(dependency); // Assumes "TestIcon" does not exist in initial data set
    }
}