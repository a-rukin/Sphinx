package com.airwhip.sphinx.misc;

import android.net.Uri;

import com.airwhip.sphinx.R;

/**
 * Created by Whiplash on 17.03.14.
 */
public class Constants {

    public static final String VK_APP_ID = "4063561";
    public static final int LONER_ID = 1;
    public static final int STUDENT_ID = 3;
    public static final int PIKABU_LOVER_ID = 9;
    public static final int MIN = 50;
    public static final int BIG_WEIGHT = 1000;

    public static final String APP_SITE = "www.sphinx-app.com";
    public static final String HASH_TAG = "#SphinxApp";

    public static final String FILE_PATH = "/sdcard/sphinx_post.png";
    public static final Uri FILE_URI = Uri.parse("file://" + FILE_PATH);

    public static final String ERROR_TAG = "ERROR_TAG";
    public static final String DEBUG_TAG = "DEBUG_TAG";

    public static final int[] xmls = {
            R.xml.geek,
            R.xml.loner,
            R.xml.trendy,
            R.xml.student,
            R.xml.traveler,
            R.xml.anime_addicted,
            R.xml.sportsman,
            R.xml.music_lover,
            R.xml.animal_lover,
            R.xml.pikabu_lover};

    public static final int[][] imgs = {
            {R.drawable.geek, R.drawable.geek_w},
            {R.drawable.loner, R.drawable.loner_w},
            {R.drawable.trendy, R.drawable.trendy_w},
            {R.drawable.student, R.drawable.student_w},
            {R.drawable.traveler, R.drawable.traveler_w},
            {R.drawable.anime_addicted, R.drawable.anime_addicted_w},
            {R.drawable.sportsman, R.drawable.sportsman_w},
            {R.drawable.music_lover, R.drawable.music_lover_w},
            {R.drawable.animal_lover, R.drawable.animal_lover_w},
            {R.drawable.pikabu_lover, R.drawable.pikabu_lover_w}};

    public static final int[] colors = {
            0xFF220646,
            0xFF430808,
            0xFF31afbc,
            0xFFD56900,
            0xFF333C7A,
            0xFF001427};

    private Constants() {
    }
}
