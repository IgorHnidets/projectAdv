package com.logos.projectadv.utills;

import java.io.File;

public class FileUtills {

    public static String getImagesFolder(){
        return System.getProperty("user.home") + File.separator + "images" + File.separator;
    }
}
