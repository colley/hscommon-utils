/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.hs.common.xml;

import org.dom4j.Document;
import org.dom4j.Element;

import org.dom4j.io.SAXReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;


/**
 *@FileName  XMLUtilHelper.java
 *@Date  16-5-14 下午9:54
 *@author Colley
 *@version 1.0
 */
public class XMLUtilHelper {
    private static byte startTag = '<';
    private static byte endTag = '>';
    private static byte bias = '/';
    private static byte space = ' ';
    private static byte sigh = '!';
    private static byte across = '-';
    private static byte equal = '=';
    private static byte singleq = "'".getBytes()[0];
    private static byte doubleq = '"';
    private static String xmlstart = "<?xml";
    private static String xmlend = "?>";

    public static Element getDom4JRootElement(String filePath)
        throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(filePath));
        return document.getRootElement();
    }

    public static XMLHsMakeup getDataFromXml(String xmlpath)
        throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(xmlpath))));
        return getDataFromBufferedReader(reader);
    }

    public static XMLHsMakeup getDataFromStream(InputStream stream)
        throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        XMLHsMakeup makeup = getDataFromBufferedReader(reader);
        stream.close();
        return makeup;
    }

    public static XMLHsMakeup getDataFromString(String xmlContent)
        throws IOException {
        BufferedReader bufReader = new BufferedReader(new StringReader(xmlContent));
        return getDataFromBufferedReader(bufReader);
    }

    /**
     * @return
     * @throws java.io.IOException
     */
    @SuppressWarnings("unchecked")
    private static XMLHsMakeup getDataFromBufferedReader(BufferedReader reader)
        throws IOException {
        if (null != reader) {
            String line;
            XMLHsMakeup whole = null;
            XMLHsMakeup cur = null;
            StringBuffer sb = new StringBuffer();
            String left = "";
            String right = "";
            int state = 0;
            boolean isfirstline = true;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (isfirstline) {
                    isfirstline = false;
                    int start = line.indexOf(xmlstart);
                    int end = line.indexOf(xmlend);
                    if ((start >= 0) && (end > start)) {
                        line = line.substring(end + xmlend.length());
                        if ("".equals(line)) {
                            continue;
                        }
                    } else {
                        throw new IOException("the xml content must contans <?xml  ?>");
                    }
                }

                for (int i = 0; i < line.length(); i++) {
                    if ((state == 1) && (line.charAt(i) == space) && (null != cur) &&
                            ((null == cur.getName()) || "".equals(cur.getName()))) { //�� "<"��ʼ���������һ���ո�
                        cur.setName(sb.toString());
                        sb.delete(0, sb.length());
                    }

                    if ((state != 0) && (state != 5) && (state != 4)) {
                        if (line.charAt(i) == space) {
                            continue;
                        }
                    }

                    if ((state == 0) && (line.charAt(i) == startTag) &&
                            (((line.length() >= (i + 1)) && (line.charAt(i + 1) != bias) &&
                            (line.charAt(i + 1) != sigh)) || (line.length() < (i + 1)))) { //���� "<"��ʼ��
                        state = 1;

                        if (null == whole) {
                            whole = new XMLHsMakeup();
                            cur = whole;
                        } else {
                            XMLHsMakeup temp = new XMLHsMakeup();
                            temp.setParent(cur);
                            cur.getChildren().add(temp);
                            cur = temp;
                        }

                        continue;
                    }

                    if ((state == 0) && (line.charAt(i) == startTag) &&
                            ((line.length() >= (i + 1)) && (line.charAt(i + 1) == bias))) { //���� "</" ����ʼ��
                        state = 2;
                        cur.setText(sb.toString());
                        sb.delete(0, sb.length());

                        cur = cur.getParent();

                        i++;
                        continue;
                    }

                    if ((state == 0) && (line.charAt(i) == startTag) &&
                            ((line.length() >= (i + 3)) && (line.charAt(i + 1) == sigh) &&
                            (line.charAt(i + 2) == across) && (line.charAt(i + 3) == across))) { //���� "<!--" ע�Ϳ�ʼ��
                        state = 3;

                        i = i + 3;
                        continue;
                    }

                    if ((state == 1) && (line.charAt(i) == equal)) {
                        left = sb.toString();
                        sb.delete(0, sb.length());
                        continue;
                    }

                    if ((state == 1) && (line.charAt(i) == doubleq)) {
                        state = 5;
                        continue;
                    }

                    if ((state == 5) && (line.charAt(i) == doubleq)) {
                        state = 1;
                        right = sb.toString();
                        sb.delete(0, sb.length());
                        cur.getProperties().setProperty(left, right);
                        continue;
                    }

                    if ((state == 1) && (line.charAt(i) == singleq)) {
                        state = 4;
                        continue;
                    }

                    if ((state == 4) && (line.charAt(i) == singleq)) {
                        state = 1;
                        right = sb.toString();
                        sb.delete(0, sb.length());
                        cur.getProperties().setProperty(left, right);
                        continue;
                    }

                    if ((state == 1) && (line.charAt(i) == bias) && (line.length() >= (i + 1)) &&
                            (line.charAt(i + 1) == endTag)) { // ������/>�������
                        state = 0;
                        if ((null != cur) && ((null == cur.getName()) || "".equals(cur.getName()))) {
                            cur.setName(sb.toString());
                            sb.delete(0, sb.length());
                        }

                        cur = cur.getParent();

                        i++;
                        continue;
                    }

                    if ((state == 1) && (line.charAt(i) == endTag)) {
                        state = 0;
                        if ((null != cur) && ((null == cur.getName()) || "".equals(cur.getName()))) {
                            cur.setName(sb.toString());
                            sb.delete(0, sb.length());
                        }

                        continue;
                    }

                    if ((state == 2) && (line.charAt(i) == endTag)) {
                        state = 0;
                        continue;
                    }

                    if (state == 2) {
                        continue;
                    }

                    if ((state == 3) && (line.charAt(i) == across) && (line.length() > (i + 2)) &&
                            (line.charAt(i + 1) == across) && (line.charAt(i + 2) == endTag)) { //���� "<!--"��ʼ�Ľ���� "-->"
                        state = 0;

                        i = i + 2;
                        continue;
                    }

                    if (state == 3) {
                        continue;
                    }

                    sb.append(line.charAt(i));
                }
            }

            return whole;
        }

        return null;
    }
}
