package com.ingic.lmslawyer.util;

import android.text.Spanned;

public class Html {

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = android.text.Html.fromHtml(html, android.text.Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = android.text.Html.fromHtml(html);
        }
        return result;
    }
}
