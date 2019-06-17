package com.anlida.smartlock.utils;

import android.text.method.ReplacementTransformationMethod;

/**
 * Created by zhangcirui on 2018/11/21.
 */

public class AlphabetCapitalUtil extends ReplacementTransformationMethod {
    //自动将输入的小写字母转换成大写

    @Override
    protected char[] getOriginal() {
        return new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
                'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    }

    @Override
    protected char[] getReplacement() {
        return new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    }
}