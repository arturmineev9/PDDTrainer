package com.example.autoschool11.theme_changer;

import com.example.autoschool11.R;

public class Methods {

    public void setColorTheme() { // метод, меняющий цвет темы в приложении
        switch (ThemeColor.color) {
            case 0xffF44336:
                ThemeColor.theme = R.style.AppTheme_red;
                break;
            case 0xffE91E63:
                ThemeColor.theme = R.style.AppTheme_pink;
                break;
            case 0xff9C27B0:
                ThemeColor.theme = R.style.AppTheme_darkpink;
                break;
            case 0xff673AB7:
                ThemeColor.theme = R.style.AppTheme_violet;
                break;
            case 0xff3F51B5:
                ThemeColor.theme = R.style.AppTheme_blue;
                break;
            case 0xff2196F3:
                ThemeColor.theme = R.style.AppTheme_bluee;
                break;
            case 0xff03A9F4:
                ThemeColor.theme = R.style.AppTheme_skyblue;
                break;
            case 0xff4CAF50:
                ThemeColor.theme = R.style.AppTheme_green;
                break;
            case 0xffFF9800:
                ThemeColor.theme = R.style.AppTheme_orange;
                break;
            case 0xff9E9E9E:
                ThemeColor.theme = R.style.AppTheme_grey;
                break;
            case 0xff795548:
                ThemeColor.theme = R.style.AppTheme_brown;
                break;
            case 0xff000000:
                ThemeColor.theme = R.style.AppTheme_black;
                break;
            case 0xffffc107:
                ThemeColor.theme = R.style.AppTheme_amber;
                break;
            case 0xff00BCD4:
                ThemeColor.theme = R.style.AppTheme_cyan;
                break;
            case 0xff009688:
                ThemeColor.theme = R.style.AppTheme_teal;
                break;
            case 0xff8BC34A:
                ThemeColor.theme = R.style.AppTheme_lightgreen;
                break;
            case 0xffCDDC39:
                ThemeColor.theme = R.style.AppTheme_lime;
                break;
            case 0xffFFEB3B:
                ThemeColor.theme = R.style.AppTheme_yellow;
                break;
            case 0xffFF5722:
                ThemeColor.theme = R.style.AppTheme_deeporange;
                break;
            case 0xff607D8B:
                ThemeColor.theme = R.style.AppTheme_bluegray;
                break;
            default:
                ThemeColor.theme = R.style.AppTheme;
                break;
        }
    }
}
