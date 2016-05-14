/*
 * Copyright (c) 2016-2017 by Colley
 * All rights reserved.
 */
package com.hs.common.xml;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;


/**
 *@FileName  XMLHsMakeup.java
 *@Date  16-5-14 下午9:54
 *@author Colley
 *@version 1.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class XMLHsMakeup {
    static String[] XMLChars = {
            "&",
            ">",
            "<",
            "'",
            "\""
        };
    static String[] XMLCharsTran = {
            "&amp;",
            "&gt;",
            "&lt;",
            "&apos;",
            "&quot;"
        };
    private Properties properties = new Properties();
    private String text;
    private String name;
    private ArrayList children = new ArrayList();
    private XMLHsMakeup parent;

    public String getId() {
        return getProperties().getProperty("id");
    }

    public static String toXMl(String s) {
        if (null == s) {
            return "";
        }

        StringBuffer sb = replace(new StringBuffer(s), XMLCharsTran, XMLChars);
        if (null != sb) {
            return sb.toString();
        }

        return "";
    }

    public void setText(String text) {
        if (StringUtils.isNotEmpty(text)) {
            StringBuffer sb = replace(new StringBuffer(text), XMLCharsTran, XMLChars);
            if (null != sb) {
                this.text = sb.toString();
            }
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getType() {
        return this.properties.getProperty("type");
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList getChildren() {
        return children;
    }

    public void setChildren(ArrayList children) {
        this.children = children;
    }

    public XMLHsMakeup getParent() {
        return parent;
    }

    public void setParent(XMLHsMakeup parent) {
        this.parent = parent;
    }

    public XMLHsMakeup[] getChild(String title) {
        if (null != this.children) {
            XMLHsMakeup temp;
            LinkedList list = new LinkedList();
            for (int i = 0; i < this.getChildren().size(); i++) {
                temp = (XMLHsMakeup) this.getChildren().get(i);
                if ((null != temp) && temp.getName().equalsIgnoreCase(title)) {
                    list.add(temp);
                }
            }

            return (XMLHsMakeup[]) list.toArray(new XMLHsMakeup[0]);
        }

        return null;
    }

    public XMLHsMakeup[] find(String title) {
        List li = new ArrayList();
        cyclefind(this, title, li);
        return (XMLHsMakeup[]) li.toArray(new XMLHsMakeup[0]);
    }

    private void cyclefind(XMLHsMakeup root, String title, List li) {
        if (root.getName().equalsIgnoreCase(title)) {
            li.add(root);
        }

        if ((null != root.getChildren()) && (root.getChildren().size() > 0)) {
            XMLHsMakeup temp;
            for (int i = 0; i < root.getChildren().size(); i++) {
                temp = (XMLHsMakeup) root.getChildren().get(i);
                cyclefind(temp, title, li);
            }
        }
    }

    private void cyclefindByProperty(XMLHsMakeup root, String key, String value, List li) {
        String v = (String) root.getProperties().get(key);
        if (!StringUtils.isBlank(v) && v.equalsIgnoreCase(value)) {
            li.add(root);
        }

        if ((null != root.getChildren()) && (root.getChildren().size() > 0)) {
            XMLHsMakeup temp;
            for (int i = 0; i < root.getChildren().size(); i++) {
                temp = (XMLHsMakeup) root.getChildren().get(i);
                cyclefindByProperty(temp, key, value, li);
            }
        }
    }

    private void cyclecontinsByProperty(XMLHsMakeup root, String key, List li) {
        String v = (String) root.getProperties().get(key);
        if (!StringUtils.isBlank(v)) {
            li.add(root);
        }

        if ((null != root.getChildren()) && (root.getChildren().size() > 0)) {
            XMLHsMakeup temp;
            for (int i = 0; i < root.getChildren().size(); i++) {
                temp = (XMLHsMakeup) root.getChildren().get(i);
                cyclecontinsByProperty(temp, key, li);
            }
        }
    }

    private void cyclefindByTitleProperty(XMLHsMakeup root, String title, String key, String value, List li) {
        if (root.getName().equalsIgnoreCase(title) && value.equalsIgnoreCase(root.getProperties().getProperty(key))) {
            li.add(root);
        }

        if ((null != root.getChildren()) && (root.getChildren().size() > 0)) {
            XMLHsMakeup temp;
            for (int i = 0; i < root.getChildren().size(); i++) {
                temp = (XMLHsMakeup) root.getChildren().get(i);
                cyclefindByTitleProperty(temp, title, key, value, li);
            }
        }
    }

    public XMLHsMakeup[] getByProperty(String key, String value) {
        if (!StringUtils.isBlank(key) && !StringUtils.isBlank(value)) {
            List li = new ArrayList();
            cyclefindByProperty(this, key, value, li);
            if (li.size() > 0) {
                return (XMLHsMakeup[]) li.toArray(new XMLHsMakeup[0]);
            }
        }

        return null;
    }

    public XMLHsMakeup[] getByTagProperty(String title, String key, String value) {
        if (!StringUtils.isBlank(title) && !StringUtils.isBlank(key) && !StringUtils.isBlank(value)) {
            List li = new ArrayList();
            cyclefindByTitleProperty(this, title, key, value, li);
            if (li.size() > 0) {
                return (XMLHsMakeup[]) li.toArray(new XMLHsMakeup[0]);
            }
        }

        return null;
    }

    public XMLHsMakeup[] getChildByType(String type) {
        return getByProperty("type", type);
    }

    public XMLHsMakeup[] contansProperty(String property) {
        List li = new ArrayList();
        cyclecontinsByProperty(this, property, li);
        if (li.size() > 0) {
            return (XMLHsMakeup[]) li.toArray(new XMLHsMakeup[0]);
        }

        return null;
    }

    /**
     *
     * @param xml
     */
    public boolean addChild(XMLHsMakeup xml) {
        XMLHsMakeup[] fs = find(xml.getName());
        XMLHsMakeup parent = this;
        if (ArrayUtils.isNotEmpty(fs)) {
            if (fs[0].getParent() != null) {
                parent = fs[0].getParent();
            }
        }

        parent.getChildren().add(xml);

        return true;
    }

    /**
     * xml
     * @param xmlId
     * @return
     */
    public boolean removeChild(String xmlId) {
        if (StringUtils.isNotEmpty(xmlId)) {
            XMLHsMakeup[] te = getByProperty("id", xmlId);
            if (ArrayUtils.isNotEmpty(te) && (te[0].getParent() != null)) {
                te[0].getParent().getChildren().remove(te[0]);
                return true;
            }
        }

        return false;
    }

    /**
     * @param xml
     * @return
     */
    public boolean updateChild(XMLHsMakeup xml) {
        if (removeChild(xml.getProperties().getProperty("id"))) {
            return addChild(xml);
        }

        return false;
    }

    public void getStringBuffer(XMLHsMakeup xml, StringBuffer sb) {
        sb.append("<").append(xml.getName());
        if (isNotNull(xml.getProperties())) {
            Enumeration es = xml.getProperties().keys();
            while (es.hasMoreElements()) {
                String key = (String) es.nextElement();
                sb.append(" ").append(key).append("=").append("\"").append(xml.getProperties().getProperty(key))
                  .append("\"");
            }
        }

        if (isNotNull(xml.getChildren()) && (xml.getChildren().size() > 0)) {
            sb.append(">");
            for (int i = 0; i < xml.getChildren().size(); i++) {
                sb.append("\n");
                getStringBuffer((XMLHsMakeup) xml.getChildren().get(i), sb);
            }

            sb.append("\n");
            sb.append("</").append(xml.getName()).append(">");
        } else if (isNotNull(xml.getText())) {
            sb.append(">");
            sb.append(xml.getText()).append("</").append(xml.getName()).append(">");
        } else {
            sb.append("/>");
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        getStringBuffer(this, sb);
        return sb.toString();
    }

    public static boolean isNotNull(Object o) {
        if (null == o) {
            return false;
        } else {
            return true;
        }
    }

    public static StringBuffer replace(StringBuffer longtxt, String[] ols, String[] nls) {
        StringBuffer ret = new StringBuffer();

        StringBuffer[] tems = new StringBuffer[ols.length];

        for (int i = 0; i < longtxt.length(); i++) {
            ret.append(longtxt.charAt(i));

            for (int j = 0; j < tems.length; j++) {
                if (null == tems[j]) {
                    tems[j] = new StringBuffer();
                }

                tems[j].append(longtxt.charAt(i));
            }

            for (int j = 0; j < ols.length; j++) {
                if (ols[j].indexOf(tems[j].toString()) == 0) {
                    if (tems[j].length() == ols[j].length()) {
                        ret.delete(ret.length() - ols[j].length(), ret.length());
                        ret.append(nls[j]);
                        tems[j].delete(0, tems[j].length());
                    }
                } else {
                    tems[j].delete(0, tems[j].length());
                }
            }
        }

        return ret;
    }
}
