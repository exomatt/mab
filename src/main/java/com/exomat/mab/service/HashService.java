package com.exomat.mab.service;

import java.security.NoSuchAlgorithmException;

public interface HashService {
    String getHash(String s) throws NoSuchAlgorithmException;
}