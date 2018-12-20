package com.sasucen.softinput.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.widget.TextView;
import com.sasucen.softinput.Constants;
import com.sasucen.softinput.span.VerticalImageSpan;

import java.util.regex.Pattern;

/**
 * 创建日期：2018/12/20 0020on 上午 10:44
 * 描述：
 *
 * @author：Vincent QQ：3332168769
 * 备注：
 */
public class TextLinkifyUtils {
    public enum TextLinkifyStatus {
        EMOJI, LINK, TOPIC, AT, ALL
    }

    public static SpannableStringBuilder setAllLinkifyTextContent(TextView textView, String content) {
        return getLinkifyTextContent(textView, content, TextLinkifyStatus.ALL);
    }

    public static SpannableStringBuilder getLinkifyTextContent(final TextView textView, String content, TextLinkifyStatus status) {

        Context context = textView.getContext();

        SpannableStringBuilder spannableStringBuilder = SpannableStringBuilder.valueOf(content);
        switch (status) {
            case LINK: {
                Linkify.addLinks(spannableStringBuilder, patternCompile(Constants.URL_REGEX), Constants.SCHEME_URL);
                break;
            }
            case TOPIC: {
                Linkify.addLinks(spannableStringBuilder, patternCompile(Constants.TOPIC_REGEX), Constants.SCHEME_TOPIC);
                break;
            }
            case AT: {
                Linkify.addLinks(spannableStringBuilder, patternCompile(Constants.AT_REGEX), Constants.SCHEME_AT);
                break;
            }
            case EMOJI: {
                Linkify.addLinks(spannableStringBuilder, patternCompile(Constants.EMOJI_REGEX), Constants.SCHEME_EMOJI);
                break;
            }
            case ALL: {
                Linkify.addLinks(spannableStringBuilder, patternCompile(Constants.EMOJI_REGEX), Constants.SCHEME_EMOJI);
                Linkify.addLinks(spannableStringBuilder, patternCompile(Constants.URL_REGEX), Constants.SCHEME_URL);
                Linkify.addLinks(spannableStringBuilder, patternCompile(Constants.TOPIC_REGEX), Constants.SCHEME_TOPIC);
                Linkify.addLinks(spannableStringBuilder, patternCompile(Constants.AT_REGEX), Constants.SCHEME_AT);
                break;
            }
        }

        //得到所有的span
        URLSpan[] urlSpans = spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), URLSpan.class);

        for (URLSpan urlSpan : urlSpans) {
            int start = spannableStringBuilder.getSpanStart(urlSpan);
            int end = spannableStringBuilder.getSpanEnd(urlSpan);
            String spanStr = urlSpan.getURL();
            spannableStringBuilder.removeSpan(urlSpan);
            if (start >= 0 && end > 0 && start < end) {
                if (spanStr.startsWith(Constants.SCHEME_EMOJI)) {
                    String emojiName = spanStr.replace(Constants.SCHEME_EMOJI, "");
                    spannableStringBuilder.setSpan(
                            new VerticalImageSpan(getEmojiDrawable(context, emojiName, textView)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
//                    spannableStringBuilder.setSpan(new TextClickSpan(context, spanStr), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                //spannableReplace(spannableStringBuilder, start, end, spanStr);
            }
        }
        return spannableStringBuilder;
    }

    private static Drawable getEmojiDrawable(Context context, String emojiName, TextView textView) {
        String[] keys01 = Constants.TYPE01_EMOJI_NAME;
        for (int i = 0; i < keys01.length; i++) {
            String key = keys01[i];
            if (key.equals(emojiName)) {
                return getDrawable(context, Constants.TYPE01_EMOJI_DREWABLES[i], textView);
            }
        }

        String[] keys02 = Constants.TYPE02_EMOJI_NAME;
        for (int i = 0; i < keys02.length; i++) {
            String key = keys02[i];
            if (key.equals(emojiName)) {
                return getDrawable(context, Constants.TYPE02_EMOJI_DREWABLES[i], textView);
            }
        }
        return null;
    }

    private static Drawable getDrawable(Context context, int id, TextView textView) {
        Drawable drawable = ContextCompat.getDrawable(context, id);
        if (drawable != null) {
            int size = (int) (textView.getTextSize());
            drawable.setBounds(0, 0, size, size);
        }
        return drawable;
    }

    private static TextLinkifyStatus getStatus(String spanStr) {
        if (spanStr.startsWith(Constants.SCHEME_URL)) {
            return TextLinkifyStatus.LINK;
        } else if (spanStr.startsWith(Constants.SCHEME_TOPIC)) {
            return TextLinkifyStatus.TOPIC;
        } else if (spanStr.startsWith(Constants.SCHEME_AT)) {
            return TextLinkifyStatus.AT;
        } else {
            return null;
        }
    }

    /**
     * 将匹配到的Span替换成自己的任意内容
     *
     * @param spannableStringBuilder
     * @param start
     * @param end
     * @param spanStr
     */
    private static void spannableReplace(SpannableStringBuilder spannableStringBuilder, int start, int end, String spanStr) {
        TextLinkifyStatus status = getStatus(spanStr);
        switch (status) {
            case LINK: {
                spannableStringBuilder.replace(start, end, "[网页链接]");
                break;
            }
            case TOPIC: {
                spannableStringBuilder.replace(start, end, "[话题]");
                break;
            }
            case AT: {
                spannableStringBuilder.replace(start, end, "[@用户]");
                break;
            }
        }
    }

    public static Pattern patternCompile(String pattern) {
        return Pattern.compile(pattern);
    }
}
