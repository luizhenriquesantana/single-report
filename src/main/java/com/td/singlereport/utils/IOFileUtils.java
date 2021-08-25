package com.td.singlereport.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class IOFileUtils {

    private static final Logger LOG = LoggerFactory.getLogger(IOFileUtils.class);

    private IOFileUtils(){

    }

    public static boolean isDirectory(String directoryPath) {
        // make sure the folder exists
        File file = new File(directoryPath);
        return file.exists() && file.isDirectory();
    }
}
