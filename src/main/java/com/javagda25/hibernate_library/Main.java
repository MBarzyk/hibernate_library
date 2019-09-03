package com.javagda25.hibernate_library;

import com.javagda25.hibernate_library.utils.HibernateUtil;
import com.javagda25.hibernate_library.utils.ScannerLoader;

public class Main {
    public static void main(String[] args) {
        HibernateUtil.getSessionFactory().openSession();

        ScannerLoader loader = new ScannerLoader();
        loader.initiate();
    }
}