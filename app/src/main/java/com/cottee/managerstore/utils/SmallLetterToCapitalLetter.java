package com.cottee.managerstore.utils;

import android.text.method.ReplacementTransformationMethod;

/**
 * Created by Administrator on 2017/11/28.
 */

public class SmallLetterToCapitalLetter extends ReplacementTransformationMethod {
    @Override
    protected char[] getOriginal() {
        char[] small = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z' };
        return small;
    }

    @Override
    protected char[] getReplacement() {
        char[] capital = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z' };
        return capital;
    }
}
