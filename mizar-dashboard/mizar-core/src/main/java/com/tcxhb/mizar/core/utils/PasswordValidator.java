package com.tcxhb.mizar.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/10/24
 */

public class PasswordValidator {
    private static final Pattern BIG_LETTER_MATCH = Pattern.compile("(.*?)[A-Z]+(.*?)");
    private static final Pattern LITTLE_LETTER_MATCH = Pattern.compile("(.*?)[a-z]+(.*?)");
    private static final Pattern SYMBOL_MATCH = Pattern.compile("(.*?)[-`=\\\\\\[\\];',./~!@#$%^&*()_+|{}:\"<>?]+(.*?)");
    private static final Pattern NUMBER_MATCH = Pattern.compile("(.*?)\\d+(.*?)");
    private static final String[][] KEY_CODE = new String[][]{{"`~·", "1=", "2@@", "3#", "4$￥", "5%", "6^……", "7&", "8*", "9(（", "0）)", "-_", "=+"}, {" ", "qQ", "wW", "eE", "rR", "tT", "yY", "uU", "iI", "oO", "pP", "[{【", "]}】", "\\|、"}, {" ", "aA", "sS", "dD", "fF", "gG", "hH", "jJ", "kK", "lL", ";:", "'\"’“"}, {" ", "zZ", "xX", "cC", "vV", "bB", "nN", "mM", ",《<", ".>》", "/?？"}};

    public PasswordValidator() {
    }

    public static PasswordValidator.ValidResult validPwd(String pwd, String userName) {
        PasswordValidator.ValidResult validResult = validPwd(pwd);
        if (validResult != PasswordValidator.ValidResult.VALID) {
            return validResult;
        } else {
            return pwd.contains(userName) ? PasswordValidator.ValidResult.CONTAIN_ACCOUNT : PasswordValidator.ValidResult.VALID;
        }
    }

    public static PasswordValidator.ValidResult validPwd(String pwd) {
        if (pwd != null && !"".equals(pwd) && pwd.length() >= 6 && pwd.length() <= 10) {
            if (same(pwd)) {
                return PasswordValidator.ValidResult.SAME_CHARS;
            } else if (validateKeyboardKey(pwd)) {
                return PasswordValidator.ValidResult.CONTINUOUS_KEYBOARD;
            } else {
                return type(pwd) ? PasswordValidator.ValidResult.NOT_ENOUGH_TYPE : PasswordValidator.ValidResult.VALID;
            }
        } else {
            return PasswordValidator.ValidResult.LENGTH;
        }
    }

    private static boolean type(String pwd) {
        int type = 0;
        boolean bigLetter = BIG_LETTER_MATCH.matcher(pwd).matches();
        boolean symbol = SYMBOL_MATCH.matcher(pwd).matches();
        boolean littleLetter = LITTLE_LETTER_MATCH.matcher(pwd).matches();
        boolean number = NUMBER_MATCH.matcher(pwd).matches();
        if (bigLetter) {
            ++type;
        }

        if (symbol) {
            ++type;
        }

        if (littleLetter) {
            ++type;
        }

        if (number) {
            ++type;
        }

        return type < 3;
    }


    private static boolean same(String str) {
        String regx = "^.*(.)\\1{2}.*$";
        Matcher m = null;
        Pattern p = null;
        p = Pattern.compile(regx);
        m = p.matcher(str);
        return m.matches();
    }

    private static boolean validateKeyboardKey(String str) {
        char[] c = str.toCharArray();
        List<Integer> x = new ArrayList();
        List<Integer> y = new ArrayList();

        label71:
        for (int i = 0; i < c.length; ++i) {
            char temp = c[i];

            for (int j = 0; j < KEY_CODE.length; ++j) {
                for (int k = 0; k < KEY_CODE[j].length; ++k) {
                    String jk = KEY_CODE[j][k];
                    if (jk.contains(String.valueOf(temp))) {
                        x.add(j);
                        y.add(k);
                        continue label71;
                    }
                }
            }
        }

        boolean flag = false;

        for (int i = 0; i < x.size() - 2; ++i) {
            if (((Integer) x.get(i)).equals(x.get(i + 1)) && ((Integer) x.get(i + 1)).equals(x.get(i + 2))) {
                if ((Integer) y.get(i) > (Integer) y.get(i + 2)) {
                    if ((Integer) y.get(i) - 1 == (Integer) y.get(i + 1) && (Integer) y.get(i) - 2 == (Integer) y.get(i + 2)) {
                        flag = true;
                        break;
                    }
                } else if ((Integer) y.get(i) + 1 == (Integer) y.get(i + 1) && (Integer) y.get(i) + 2 == (Integer) y.get(i + 2)) {
                    flag = true;
                    break;
                }
            } else if (!((Integer) x.get(i)).equals(x.get(i + 1)) && !((Integer) x.get(i + 1)).equals(x.get(i + 2)) && !((Integer) x.get(i)).equals(x.get(i + 2)) && ((Integer) y.get(i)).equals(y.get(i + 1)) && ((Integer) y.get(i + 1)).equals(y.get(i + 2))) {
                flag = true;
                break;
            }
        }

        return flag;
    }

    public static enum ValidResult {
        VALID(true, ""),
        LENGTH(false, "密码长度应在6-20位"),
        SAME_CHARS(false, "密码不能包含三个重复的数字或字母"),
        CONTINUOUS_KEYBOARD(false, "密码不能包含键盘连续三个或者三个以上字符"),
        NOT_ENOUGH_TYPE(false, "至少包含3种（含）以上，大写字母、小写字母、数字、特殊字符"),
        CONTAIN_ACCOUNT(false, "密码不能包含账号");

        boolean valid;
        String msg;

        private ValidResult(final boolean valid, final String msg) {
            this.valid = valid;
            this.msg = msg;
        }

        public boolean isValid() {
            return this.valid;
        }

        public String getMsg() {
            return this.msg;
        }
    }
}
