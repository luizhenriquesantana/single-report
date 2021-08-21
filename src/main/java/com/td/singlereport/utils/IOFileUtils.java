package com.td.singlereport.utils;

import org.apache.commons.configuration.ConfigurationUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class IOFileUtils {

    private static final Logger LOG = LoggerFactory.getLogger(IOFileUtils.class);

    private IOFileUtils(){

    }

    public static File[] listFilesFromFolder(String sourceDirectory) {
        createSilentFolder(sourceDirectory);
        File directory = new File(sourceDirectory);
        File[] files = directory.listFiles();

        if (files != null && files.length > 1) {
            Arrays.sort(files, (f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));
        }

        return files;
    }

    public static File[] listFilesFromFolder(String sourceDirectory, String filePattern){
        createSilentFolder(sourceDirectory);
        File directory = new File(sourceDirectory);
        FileFilter fileFilter = new WildcardFileFilter(filePattern);
        File[] files = directory.listFiles(fileFilter);

        if (files != null && files.length > 1) {
            Arrays.sort(files, (f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));
        }

        return files;
    }

    public static void createSilentFolder(String directoryPath) {
        try {
            File folder = new File(directoryPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        } catch (Exception e) {
            LOG.error("{} occurred while trying to create the silent folder '{}'.", directoryPath, e);
        }
    }

    public static boolean isDirectory(String directoryPath) {
//        if (StringUtils.isNotEmpty(directoryPath)) {
//            return false;
//        }
        // make sure the folder exists
        File file = new File(directoryPath);
        if (!(file.exists() && file.isDirectory())) {
            return false;
        }
        return true;
    }

    public static void copyFileFromResource(String resourceName, String destination){
        try (InputStream inputStream = getResourceAsStream(resourceName)) {
            Path target = Paths.get(destination);
            Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);
            //closing the inputStream
            inputStream.close();
        } catch(Exception ex){
            LOG.error("{} ocurred while trying to copy the file '{}' to destination '{}'. Details: {}.", ex.getClass().getSimpleName(), resourceName, destination, ex.getMessage());
        }
    }
    public static byte[] getResourceAsByteArray(String name) throws IOException {
        LOG.debug("Entering getResourceAsByteArray(name={})", name);

        byte[] byteArray = null;
        try(InputStream stream = getResourceAsStream(name)){
            byteArray = IOUtils.toByteArray(stream);
        }

        LOG.debug("Leaving getResourceAsByteArray(name)={}", byteArray);
        return byteArray;
    }

    public static InputStream getResourceAsStream(String name) throws IOException {
        LOG.debug("Entering getResourceAsStream(name={})", name);
        ClassLoader classLoader = null;
        InputStream inputStream = null;

        if(!StringUtils.isEmpty(name)){
            URL resourceUrl = null;
            resourceUrl = ConfigurationUtils.locate(name);
            inputStream = resourceUrl.openStream();
            if(inputStream == null){
                LOG.warn("Unable to locate the resource {} using the apache ConfigurationUtils. Using now ClassLoader.", name);
                if(!name.startsWith("./")){
                    name = String.format("./%s", name);
                }
                inputStream = ClassLoader.class.getResourceAsStream(name);
                if(inputStream == null){

                    LOG.warn("Unable to locate the resource {} using both ClassLoader and ConfigurationUtils. Using now Thread.currentThread().getContextClassLoader().", name);
                    classLoader = Thread.currentThread().getContextClassLoader();
                    inputStream = classLoader.getResourceAsStream(name);

                    if(inputStream == null){
                        LOG.warn("Unable to locate the resource {} using both ClassLoader and ConfigurationUtils. Using now Thread.currentThread().getSystemResourceAsStream().", name);
                        inputStream = ClassLoader.getSystemResourceAsStream(name);
                    }
                }
            }
        }

        if(inputStream == null){
            LOG.warn("Unable to locate the resource {} using both ClassLoader and ConfigurationUtils. Using now Thread.currentThread().getSystemResourceAsStream().", name);
        }

        LOG.debug("Leaving getResourceAsStream(name)={}",  inputStream);
        return inputStream;
    }

    public static InputStream loadResource(String name) {
        LOG.trace("Entering loadResource(name={})", name);
        InputStream stream = null;
        try {
            stream = getResourceAsStream(name);
        } catch (IOException e) {
            LOG.warn("A IOException exception ocurred while loading resource {}", name);
        }
        LOG.trace("Leaving loadResource(name)={}", stream);
        return stream;
    }
}
